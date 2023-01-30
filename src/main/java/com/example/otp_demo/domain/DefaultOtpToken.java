package com.example.otp_demo.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Getter
@ToString
@EqualsAndHashCode
public class DefaultOtpToken {
    private static final String DATE_FORMAT = "yyyyMMddHHmm";
    private final String id;
    private final String custId;
    private final String otpNumber;
    private final String createDateTime;
    private final String expireDateTime;
    private final String leaseDateTime;

    @Builder
    private DefaultOtpToken(String id, String custId, String otpNumber, String createDateTime, String expireDateTime, String leaseDateTime) {
        this.id = id;
        this.custId = custId;
        this.otpNumber = otpNumber;
        this.createDateTime = createDateTime;
        this.expireDateTime = expireDateTime;
        this.leaseDateTime = leaseDateTime;
    }

    public static DefaultOtpToken DefaultOtpOf(String id, String custId, String otpNumber, ZonedDateTime day) {
        return DefaultOtpToken.builder()
                .id(id)
                .custId(custId)
                .otpNumber(otpNumber)
                .createDateTime(makeCreateDateTime(day))
                .expireDateTime(makeExpireDateTime(day))
                .leaseDateTime(makeLeaseDateTime(day))
                .build();
    }

    public static DefaultOtpToken todayOtpOf(String id, String custId, String otpNumber) {
        ZonedDateTime today = ZonedDateTime.now();
        return DefaultOtpToken.builder()
                .id(id)
                .custId(custId)
                .otpNumber(otpNumber)
                .createDateTime(makeCreateDateTime(today))
                .expireDateTime(makeExpireDateTime(today))
                .leaseDateTime(makeLeaseDateTime(today))
                .build();
    }

    private static String makeCreateDateTime(ZonedDateTime dateTime) {

        return dateTime.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
    }

    private static String makeLeaseDateTime(ZonedDateTime dateTime) {

        return dateTime.plus(1, ChronoUnit.SECONDS)
                .format(DateTimeFormatter.ofPattern(DATE_FORMAT));
    }

    private static String makeExpireDateTime(ZonedDateTime dateTime) {
        return dateTime.plus(3, ChronoUnit.MINUTES)
                .format(DateTimeFormatter.ofPattern(DATE_FORMAT));
    }

    public String makeValidValue() {
        return this.custId + "_" + this.createDateTime + "_" + this.otpNumber;
    }
}
