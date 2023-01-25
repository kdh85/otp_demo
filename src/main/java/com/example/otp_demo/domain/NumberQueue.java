package com.example.otp_demo.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.*;

@Getter
public class NumberQueue {
    private final Deque<String> numberQueue;

    @Builder
    public NumberQueue(LinkedList<String> numberQueue) {
        this.numberQueue = numberQueue;
    }

    public static NumberQueue makeShuffleNumberQueue(List<String> numbers) {
        LinkedList<String> tempQueue = new LinkedList<>(numbers);
        Collections.shuffle(tempQueue);

        return NumberQueue.builder()
                .numberQueue(tempQueue)
                .build();
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
