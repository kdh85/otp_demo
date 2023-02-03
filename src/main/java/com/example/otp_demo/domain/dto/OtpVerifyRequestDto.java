package com.example.otp_demo.domain.dto;

import com.example.otp_demo.enums.OtpOption;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Arrays;

@Getter
@Slf4j
public class OtpVerifyRequestDto {

    private final String otpNumber;
    private final LocalDateTime time;
    private final String key;
    private final OtpOption otpOption;

    @JsonCreator
    public OtpVerifyRequestDto(@JsonProperty(value = "otpNumber") String otpNumber, @JsonProperty("time") LocalDateTime time, @JsonProperty("key") String key, @JsonProperty("otpNumberFormat") String otpNumberFormat) {
        this.otpNumber = otpNumber;
        this.time = time;
        this.key = key;
        this.otpOption = getEnumFromValue(otpNumberFormat);
    }

    @JsonCreator
    public static OtpOption getEnumFromValue(String enumName) {
        log.info("enum name : {}", enumName);
        return Arrays.stream(OtpOption.values())
                .filter(otpNumberFormat1 -> otpNumberFormat1.name().equals(enumName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("일치하는 OtpNumberFormat 이 없습니다."));
    }
}
