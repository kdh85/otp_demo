package com.example.otp_demo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
class RealTimeOtpServiceTest {
    private static final int THREAD_COUNT = 10000;
    private final Logger log = LoggerFactory.getLogger(RealTimeOtpServiceTest.class);
    @Autowired
    private RealTimeOtpService realTimeOtpService;
    private ExecutorService executorService;
    private CountDownLatch countDownLatch;

    @BeforeEach
    void beforeEach() {
        executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        countDownLatch = new CountDownLatch(THREAD_COUNT);
    }

    @DisplayName("다중 사용자가 otp 번호 요청시 중복없는 키발급이 이루어진다.")
    @RepeatedTest(1)
    void multiThreadCreateTest() throws InterruptedException {
        //given

        //when
        Set<String> expectValues = new HashSet<>();
        IntStream.range(0, THREAD_COUNT).forEach(
                e -> executorService.submit(
                        () -> {
                            try {
                            } finally {
                                countDownLatch.countDown();
                            }
                        }
                )
        );
        countDownLatch.await();

        assertEquals(expectValues.size(), 10000);
    }
}