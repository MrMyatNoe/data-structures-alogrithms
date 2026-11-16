package binarysearch;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Rules under test: the smallest daily capacity that ships all payouts, in
 * order, within the given number of days. Payouts cannot be split or reordered.
 */
class MinCapacityTest {

    // sample

    @Test
    @DisplayName("sample: 5 payouts in 3 days")
    void sample() {
        List<Integer> payouts = List.of(500, 300, 700, 200, 400);

        assertEquals(800, MinCapacity.minCapacity(payouts, 3));
    }

    @Test
    @DisplayName("1..10 in 5 days")
    void oneToTen() {
        List<Integer> payouts = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        assertEquals(15, MinCapacity.minCapacity(payouts, 5));
    }

    // day limits

    @Test
    @DisplayName("one day needs the total")
    void oneDay() {
        List<Integer> payouts = List.of(100, 100, 100);

        assertEquals(300, MinCapacity.minCapacity(payouts, 1));
    }

    // duplicates

    @Test
    @DisplayName("identical payouts, 3 in 3 days")
    void identicalPayouts() {
        List<Integer> payouts = List.of(100, 100, 100);

        assertEquals(100, MinCapacity.minCapacity(payouts, 3));
    }

    // large values

    @Test
    @DisplayName("sum exceeds int range")
    void overflow() {
        List<Integer> payouts = List.of(2_000_000_000, 2_000_000_000);

        assertEquals(4_000_000_000L, MinCapacity.minCapacity(payouts, 1));
    }
}
