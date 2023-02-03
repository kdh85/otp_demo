package com.example.otp_demo.annotation.aspect;

import com.example.otp_demo.repository.OtpRedisRepository;
import com.example.otp_demo.enums.OtpOption;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@RequiredArgsConstructor
@Component
@Slf4j
public class OtpRedisAspect {

    private final OtpRedisRepository otpRedisRepository;

    @Pointcut("@annotation(com.example.otp_demo.annotation.OtpHintInRedisCash)")
    private void otpHintInRedisCash() {
    }

    @Pointcut("@annotation(com.example.otp_demo.annotation.OtpHintVerifyInRedisCash)")
    private void otpHintVerifyInRedisCash() {
    }

    @Around(value = "otpHintInRedisCash()")
    public Object makeOtpAndPut(ProceedingJoinPoint joinPoint) {
        try {
            Object proceed = joinPoint.proceed();
            log.info("redis binding key is : {}", proceed);
            otpRedisRepository.makeData(String.valueOf(proceed), String.valueOf(LocalDateTime.now()));
            return proceed;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Before(value = "otpHintVerifyInRedisCash()&&args(userOtpNumber, time, key, otpOption)", argNames = "userOtpNumber,time,key,otpNumberFormat")
    public void matchDataFromRedis(String userOtpNumber, LocalDateTime time, String key, OtpOption otpOption) {
        log.info("try match each key parameter : {}", key);
        if (!otpRedisRepository.isMatchByKey(key)) {
            throw new IllegalArgumentException("is not match by hintKey : " + key);
        }
    }

    @Before(value = "otpHintVerifyInRedisCash()&&args(userOtpNumber, key, otpOption)", argNames = "userOtpNumber,key,otpNumberFormat")
    public void matchDataFromRedis(String userOtpNumber, String key, OtpOption otpOption) {
        log.info("try match each key parameter : {}", key);
        if (!otpRedisRepository.isMatchByKey(key)) {
            throw new IllegalArgumentException("is not match by hintKey : " + key);
        }
    }
}