package com.practice.math;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

class MathUtilsTest {

    private final MathUtils mathUtils = new MathUtils();

    @ParameterizedTest
    @MethodSource("provideNumbersForAverage")
    @DisplayName("Should calculate correct average for various inputs")
    void shouldCalculateAverage(List<Integer> input, double expected) {
        double result = mathUtils.calculateAverage(input);
        assertThat(result).isEqualTo(expected);
    }

    private static Stream<Arguments> provideNumbersForAverage() {
        return Stream.of(
                Arguments.of(Arrays.asList(1, 2, 3), 2.0),          // Standard case
                Arguments.of(Arrays.asList(10, 20), 15.0),          // Even count
                Arguments.of(List.of(5), 5.0),                      // Single element
                Arguments.of(null, 0.0),                            // Null safety
                Arguments.of(List.of(), 0.0),                       // Empty list
                Arguments.of(Arrays.asList(-1, -5, -3), -3.0)       // Negative numbers
        );
    }

    @Test
    @DisplayName("Should handle potential integer overflow during summation")
    void shouldHandleLargeNumbers() {
        List<Integer> largeNumbers = Arrays.asList(Integer.MAX_VALUE, Integer.MAX_VALUE);
        double result = mathUtils.calculateAverage(largeNumbers);
        assertThat(result).isEqualTo((double) Integer.MAX_VALUE);
    }
}
