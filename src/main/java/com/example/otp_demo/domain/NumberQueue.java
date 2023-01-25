package com.example.otp_demo.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

@Getter
public class NumberQueue {
    private final Deque<String> numberQueue;

    @Builder
    public NumberQueue(LinkedList<String> numberQueue) {
        this.numberQueue = numberQueue;
    }

    public static NumberQueue makeShuffleNumberQueue(List<String> numbers) {
        NumberValidation(numbers);
        return NumberQueue.builder()
                .numberQueue(makeShuffleNumbers(numbers))
                .build();
    }

    private static LinkedList<String> makeShuffleNumbers(List<String> numbers) {
        LinkedList<String> tempQueue = new LinkedList<>(numbers);
        Collections.shuffle(tempQueue);
        return tempQueue;
    }

    private static void NumberValidation(List<String> numbers) {
        if (CollectionUtils.isEmpty(numbers)) {
            throw new IllegalArgumentException("번호대기열 생성시 번호정보는 필수입니다.");
        }
    }

    public String choseNumber() {
        String number = getFirstNumber();
        moveToLast(number);
        return number;
    }

    private String getFirstNumber() {
        return numberQueue.pollFirst();
    }

    private void moveToLast(String number) {
        numberQueue.offerLast(number);
    }

}
