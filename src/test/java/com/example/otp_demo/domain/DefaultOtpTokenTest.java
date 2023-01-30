package com.example.otp_demo.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DefaultOtpTokenTest {

    @DisplayName("id, 가맹점Id, 발급 otp번호를 주입받아 검증OTP객체를 생성.")
    @Test
    void createDefaultOtpTokenTest() {
        String id = String.valueOf(UUID.randomUUID());
        String custId = "A0001";
        String otpNumber = "0000";

        DefaultOtpToken otpToken = DefaultOtpToken.todayOtpOf(id, custId, otpNumber);

        assertAll(
                () -> assertEquals(otpToken.getId(), id),
                () -> assertEquals(otpToken.getCustId(), custId),
                () -> assertEquals(otpToken.getOtpNumber(), otpNumber)
        );
    }

    @DisplayName("검증용 OTP 값을 반환 검증.")
    @Test
    void otpValueCreateTest() {
        //given
        String id = String.valueOf(UUID.randomUUID());
        String custId = "A0001";
        String otpNumber = "0000";
        ZonedDateTime testDateTime = ZonedDateTime.now();

        String expectValue = custId + "_" + testDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm")) + "_" + otpNumber;

        //when
        DefaultOtpToken otpToken = DefaultOtpToken.DefaultOtpOf(id, custId, otpNumber, testDateTime);
        String validValue = otpToken.makeValidValue();

        //then
        assertEquals(validValue, expectValue);
    }
}