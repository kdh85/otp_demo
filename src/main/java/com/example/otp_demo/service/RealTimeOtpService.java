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
public class RealTimeOtpService implements OtpService{

    public String otpIssuanceNow(String key) {
        return OtpGenerator.makeOtpValue(LocalDateTime.now(), key, OtpOption.OTP_LENGTH_6_DURATION_30000MS);
    }

    @OtpHintVerifyInRedisCash
    public boolean otpNumberVerifyNow(String userOtpNumber, String key, OtpOption otpOption) {
        return OtpGenerator.checkOtpNumber(userOtpNumber, LocalDateTime.now(), key, otpOption);
    }

    @OtpHintInRedisCash
    public String otpHintIssuanceNow(String id) {
        return OtpGenerator.makeOtpValue(LocalDateTime.now(), id, OtpOption.OTP_LENGTH_4_DURATION_UNLIMITED);
    }
}
