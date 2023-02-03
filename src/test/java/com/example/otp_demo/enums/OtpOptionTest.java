package com.example.otp_demo.enums;

import org.junit.jupiter.api.Test;

class OtpOptionTest {

    @Test
    void test() {
        for (OtpOption otpOption : OtpOption.values()) {
            System.out.println(otpOption.name());
        }
    }
}