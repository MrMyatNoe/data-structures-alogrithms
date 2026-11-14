package mock;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Rules under test: find two different payments (by position) that add up to the
 * deposit and return their indexes [first, second] with first < second. When
 * several pairs exist, the one whose second index comes earliest wins.
 * No pair returns [-1, -1]. The same payment cannot be used twice.
 */
class FindPairTest {

    // sample

    @Test
    @DisplayName("300 + 700, first 300 is used")
    void sample() {
        List<Long> payments = List.of(300L, 300L, 700L);

        assertEquals(List.of(0, 2), FindPair.findPair(payments, 1000));
    }

    // edge cases

    @Test
    @DisplayName("empty input")
    void emptyInput() {
        List<Long> payments = List.of();

        assertEquals(List.of(-1, -1), FindPair.findPair(payments, 1000));
    }

    @Test
    @DisplayName("single payment")
    void singlePayment() {
        List<Long> payments = List.of(500L);

        assertEquals(List.of(-1, -1), FindPair.findPair(payments, 1000));
    }

    @Test
    @DisplayName("a payment cannot be used twice")
    void paymentCannotBeUsedTwice() {
        List<Long> payments = List.of(5L);

        assertEquals(List.of(-1, -1), FindPair.findPair(payments, 10));
    }

    @Test
    @DisplayName("no pair adds up")
    void noPairAddsUp() {
        List<Long> payments = List.of(1L, 2L, 3L);

        assertEquals(List.of(-1, -1), FindPair.findPair(payments, 10));
    }

    // pairs

    @Test
    @DisplayName("duplicates: 500 + 500")
    void duplicates() {
        List<Long> payments = List.of(500L, 500L);

        assertEquals(List.of(0, 1), FindPair.findPair(payments, 1000));
    }

    @Test
    @DisplayName("negative: -5 + 15")
    void negative() {
        List<Long> payments = List.of(-5L, 15L);

        assertEquals(List.of(0, 1), FindPair.findPair(payments, 10));
    }

    @Test
    @DisplayName("zeros: 0 + 0")
    void zeros() {
        List<Long> payments = List.of(0L, 0L);

        assertEquals(List.of(0, 1), FindPair.findPair(payments, 0));
    }

    @Test
    @DisplayName("first pair found: 2 + 7")
    void firstPairFound() {
        List<Long> payments = List.of(2L, 7L, 11L, 15L);

        assertEquals(List.of(0, 1), FindPair.findPair(payments, 9));
    }

    @Test
    @DisplayName("4 + 6 (5 cannot pair with itself)")
    void fiveCannotPairWithItself() {
        List<Long> payments = List.of(1L, 4L, 5L, 6L);

        assertEquals(List.of(1, 3), FindPair.findPair(payments, 10));
    }

    @Test
    @DisplayName("values beyond int range")
    void valuesBeyondIntRange() {
        List<Long> payments = List.of(3000000000000L, 1000000000000L);

        assertEquals(List.of(0, 1), FindPair.findPair(payments, 4000000000000L));
    }
}
