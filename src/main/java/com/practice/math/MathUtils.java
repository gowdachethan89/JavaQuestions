package com.practice.math;

import java.util.List;

public class MathUtils {
    public double calculateAverage(List<Integer> numbers) {
        // Bug 1: Is this check sufficient?
        if (numbers == null) {
            return 0.0;
        }

        int sum = 0; // Bug 2: Think about large values
        for (int number : numbers) {
            sum += number;
        }

        // Bug 3: Precision and potential runtime exceptions
        return sum / numbers.size();
    }
}
