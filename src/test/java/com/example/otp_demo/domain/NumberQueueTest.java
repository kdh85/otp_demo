package com.example.otp_demo.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
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
    void choseNumberTest() {
        NumberQueue numberQueue = NumberQueue.makeShuffleNumberQueue(NumberGenerator.getNumbers());

        String expectNumber = numberQueue.getNumberQueue().peek();

        assertAll(
                () -> assertEquals(numberQueue.choseNumber(), expectNumber),
                () -> assertEquals(numberQueue.getNumberQueue().size(), 10000)
        );
    }


    @DisplayName("빈 번호들을 주입받으면 번호 대기열을 생성 실패.")
    @ParameterizedTest
    @NullAndEmptySource
    void createQueueFailTest(List<String> data) {
        assertThatThrownBy(
                ()->NumberQueue.makeShuffleNumberQueue(data)
        ).isInstanceOf(IllegalArgumentException.class);
    }
}