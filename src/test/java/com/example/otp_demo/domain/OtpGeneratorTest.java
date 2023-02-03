package com.example.otp_demo.domain;

import com.example.otp_demo.enums.OtpOption;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotEquals;


class OtpGeneratorTest {

    private final Logger log = LoggerFactory.getLogger(OtpGeneratorTest.class);

    @Test
    void name() {

        String testId = "kdh";
        String testId1 = "kdh1";
        LocalDateTime testTime = LocalDateTime.now();
        int formatLength = 1000000;

        log.info("testTime : {}", testTime);
        String otpValue = OtpGenerator.makeOtpValue(testTime, testId, OtpOption.OTP_LENGTH_6_DURATION_1000MS);
        String otpValue1 = OtpGenerator.makeOtpValue(testTime, testId1, OtpOption.OTP_LENGTH_6_DURATION_1000MS);
        log.info("otpValue : {}", otpValue);
        log.info("otpValue1 : {}", otpValue1);

        assertNotEquals(otpValue, otpValue1);
    }

    @RepeatedTest(20)
    void test2() throws InterruptedException {

        String randomAlphanumeric = RandomStringUtils.randomAlphanumeric(4);
        log.info(randomAlphanumeric);

        String id = String.valueOf(UUID.randomUUID());
        String hintKey = OtpGenerator.makeOtpValue(LocalDateTime.now(), id, OtpOption.OTP_LENGTH_6_DURATION_30000MS);
        String hintKey1 = OtpGenerator.makeOtpValue(LocalDateTime.now(), id, OtpOption.OTP_LENGTH_4_DURATION_UNLIMITED);
        log.info("hintKey : {}", hintKey);
        log.info("hintKey1 : {}", hintKey1);
        Thread.sleep(1000);
    }
}