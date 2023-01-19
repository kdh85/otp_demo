package com.example.otp_demo.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class NumberGeneratorTest {

    @DisplayName("0 부터 9999까지 4자리 포맷의 자연수를 생성하여 반환한다.")
    @Test
    void test() {
        //given
        List<String> numbers = NumberGenerator.getNumbers();
        //then
        assertAll(
                () -> assertEquals(numbers.size(), 10000),
                () -> assertEquals(numbers.get(0), "0000"),
                () -> {
                    int lastIndex = numbers.size() - 1;
                    assertEquals(numbers.get(lastIndex), "9999");
                }
        );
    }
}