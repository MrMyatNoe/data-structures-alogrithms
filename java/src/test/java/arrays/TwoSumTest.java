package arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.lang.reflect.Method;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

class TwoSumTest {

    @BeforeEach
    void logHeader(TestInfo info) {
        System.out.printf("%nstdout | %s > %s > %s%n",
            info.getTestClass().map(Class::getName).orElse("?"),
            info.getTestMethod().map(Method::getName).orElse("?"),
            info.getDisplayName());
    }

    // sample

    @Test
    @DisplayName("2 + 7 = 9")
    void firstTwoNumbers() {
        int[] nums = {2, 7, 11, 15};

        assertArrayEquals(new int[]{0, 1}, TwoSum.solve(nums, 9));
    }

    @Test
    @DisplayName("pair is not at the start: 2 + 4 = 6")
    void pairInTheMiddleAndEnd() {
        int[] nums = {3, 2, 4};

        assertArrayEquals(new int[]{1, 2}, TwoSum.solve(nums, 6));
    }

    // edge cases

    @Test
    @DisplayName("empty input")
    void emptyInput() {
        int[] nums = {};

        assertArrayEquals(new int[]{}, TwoSum.solve(nums, 5));
    }

    @Test
    @DisplayName("duplicates: 3 + 3 = 6")
    void duplicates() {
        int[] nums = {3, 3};

        assertArrayEquals(new int[]{0, 1}, TwoSum.solve(nums, 6));
    }

    @Test
    @DisplayName("negative: -3 + 3 = 0")
    void negative() {
        int[] nums = {-3, 4, 3, 90};

        assertArrayEquals(new int[]{0, 2}, TwoSum.solve(nums, 0));
    }

    // no answer

    @Test
    @DisplayName("no pair adds up to 100")
    void noAnswer() {
        int[] nums = {1, 2, 3};

        assertArrayEquals(new int[]{}, TwoSum.solve(nums, 100));
    }
}
