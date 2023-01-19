package com.example.otp_demo.domain;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Component
public class NumberGenerator {

    private static final List<String> numbers = new ArrayList<>();

    static {
        for (int number = 0; number < 10000; number++) {
            numbers.add(makeNumberFormat(number));
        }
    }
    private static String makeNumberFormat(int number) {
        return String.format("%04d", number);
    }

    public static List<String> getNumbers(){
        return numbers;
    }
}
