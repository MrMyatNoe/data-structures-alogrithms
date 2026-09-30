package arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class TwoSumTest {

    static Stream<Arguments> cases() {
        return Stream.of(
            Arguments.of(new int[]{2, 7, 11, 15}, 9, new int[]{0, 1}),
            Arguments.of(new int[]{3, 2, 4}, 6, new int[]{1, 2}),
            Arguments.of(new int[]{3, 3}, 6, new int[]{0, 1}),
            Arguments.of(new int[]{-3, 4, 3, 90}, 0, new int[]{0, 2}),
            Arguments.of(new int[]{1, 2, 3}, 100, new int[]{}),
            Arguments.of(new int[]{}, 5, new int[]{})
        );
    }

    @ParameterizedTest
    @MethodSource("cases")
    void findsIndices(int[] nums, int target, int[] expected) {
        assertArrayEquals(expected, TwoSum.solve(nums, target));
    }
}
