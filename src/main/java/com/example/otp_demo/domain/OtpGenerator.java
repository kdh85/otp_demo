package com.example.otp_demo.domain;

import com.example.otp_demo.enums.OtpOption;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Component
@Slf4j
public class OtpGenerator {

    private static final String ALGORITHM = "HmacSHA1";

    public static String makeOtpValue(LocalDateTime time, String key, OtpOption otpOption) {
        log.info("makeOtpValue parameters info : {}, {}, {}", time, key, otpOption.name());
        try {
            return makeOtpNumberFormat(otpOption, generateOtpNumber(key.getBytes(), time, otpOption));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    private static String makeOtpNumberFormat(OtpOption otpOption, Long otpNumber) {
        return String.format(otpOption.getViewFormat(), otpNumber);
    }

    public static boolean checkOtpNumber(String userOtpNumber, LocalDateTime requestTime, String key, OtpOption otpOption) {
        boolean result = false;
        try {
            byte[] decodedKey = key.getBytes();
            int window = 3;
            for (int i = -window; i <= window; ++i) {
                String hash = makeOtpNumberFormat(otpOption, generateOtpNumber(decodedKey, requestTime, otpOption));
                log.debug("hash : {} / userOtpNumber : {}", hash, userOtpNumber);
                if (hash.equals(userOtpNumber)) {
                    result = true;
                }
            }
        } catch (InvalidKeyException | NoSuchAlgorithmException e) {
            log.error(e.getMessage());
        }
        return result;
    }

    private static long generateOtpNumber(byte[] key, LocalDateTime time, OtpOption otpOption) throws NoSuchAlgorithmException, InvalidKeyException {

        log.debug("request Time stamp info : {}", time);
        log.debug("request key info : {}", key);
        log.debug("request otpNumberFormat info : {}", otpOption.name());

        byte[] data = new byte[8];

        long value = Timestamp.valueOf(time).getTime();

        if (otpOption.getDuration() != 0) {
            value /= otpOption.getDuration();
        }

        for (int i = 8; i-- > 0; value >>>= 8) {
            data[i] = (byte) value;
        }

        SecretKeySpec signKey = new SecretKeySpec(key, ALGORITHM);

        Mac mac = Mac.getInstance(ALGORITHM);
        mac.init(signKey);

        byte[] hash = mac.doFinal(data);
        int offset = hash[20 - 1] & 0xF;

        long truncatedHash = 0;
        for (int i = 0; i < 4; ++i) {
            truncatedHash <<= 8;
            truncatedHash |= (hash[offset + i] & 0xFF);
        }

        truncatedHash &= 0x7FFFFFFF;//양수처리.
        truncatedHash %= otpOption.getDividedNumber();//나누는 자릿수가 작아지면 자릿수 조절 가능

        return truncatedHash;
    }
}
