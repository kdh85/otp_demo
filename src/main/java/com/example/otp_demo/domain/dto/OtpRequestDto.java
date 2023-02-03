package com.example.otp_demo.domain.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OtpRequestDto {

    private final LocalDateTime time;
    private final String id;

    @JsonCreator
    public OtpRequestDto(@JsonProperty(value = "time") LocalDateTime time, @JsonProperty(value = "id")String id) {
        this.time = time;
        this.id = id;
    }
}
