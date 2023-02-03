package com.example.otp_demo.service;

import com.example.otp_demo.annotation.OtpHintInRedisCash;
import com.example.otp_demo.annotation.OtpHintVerifyInRedisCash;
import com.example.otp_demo.domain.OtpGenerator;
import com.example.otp_demo.enums.OtpOption;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class SpecificTimeOtpService {

    public String otpIssuanceWithTime(LocalDateTime time, String key) {
        return OtpGenerator.makeOtpValue(time, key, OtpOption.OTP_LENGTH_6_DURATION_30000MS);
    }

    @OtpHintVerifyInRedisCash
    public boolean otpNumberVerifyWithTime(String userOtpNumber, LocalDateTime time, String key, OtpOption otpOption) {
        return OtpGenerator.checkOtpNumber(userOtpNumber, time, key, otpOption);
    }

    @OtpHintInRedisCash
    public String otpHintIssuanceWithTime(LocalDateTime time, String id) {
        return OtpGenerator.makeOtpValue(time, id, OtpOption.OTP_LENGTH_4_DURATION_UNLIMITED);
    }

}
