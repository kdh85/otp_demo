package com.example.otp_demo.enums;

import lombok.Getter;

@Getter
public enum OtpOption {
    OTP_LENGTH_6_DURATION_1000MS(1000000,"%06d",1000),
    OTP_LENGTH_6_DURATION_30000MS(1000000,"%06d",30000),
    OTP_LENGTH_4_DURATION_1000MS(10000,"%04d", 1000),
    OTP_LENGTH_4_DURATION_3000MS(10000,"%04d", 30000),
    OTP_LENGTH_4_DURATION_UNLIMITED(10000,"%04d", 0);

    private final int dividedNumber;
    private final String viewFormat;
    private final long duration;

    OtpOption(int dividedNumber, String viewFormat, long duration) {
        this.dividedNumber = dividedNumber;
        this.viewFormat = viewFormat;
        this.duration = duration;
    }
}
