package com.example.otp_demo.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class NumberQueueTest {

    @DisplayName("번호들을 주입받아 번호 대기열을 생성.")
    @Test
    void createQueueTest() {

        NumberQueue numberQueue = NumberQueue.makeShuffleNumberQueue(NumberGenerator.getNumbers());

        assertAll(
                () -> assertEquals(numberQueue.getNumberQueue().size(), 10000)
        );
    }

    @DisplayName("번호대기열에서 한개의 번호를 받는다.")
    @Test
    void drawNumberTest() {
        NumberQueue numberQueue = NumberQueue.makeShuffleNumberQueue(NumberGenerator.getNumbers());

        String expectNumber = numberQueue.getNumberQueue().peek();

        assertAll(
                () -> assertEquals(numberQueue.choseNumber(), expectNumber),
                () -> assertEquals(numberQueue.getNumberQueue().size(), 10000)
        );
    }
}