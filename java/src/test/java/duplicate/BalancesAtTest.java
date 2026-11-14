package duplicate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Rules under test: the balance at query time t is the sum of all amounts with
 * time <= t. Events may arrive in any order and several can share a time.
 * Before the first event the balance is 0.
 */
class BalancesAtTest {

    // sample

    @Test
    @DisplayName("unsorted events: 1 -> 100, 5 -> 70, 9 -> 90")
    void unsortedEvents() {
        List<Long> times = List.of(5L, 1L, 9L);
        List<Long> amounts = List.of(-30L, 100L, 20L);
        List<Long> queries = List.of(6L, 0L, 9L, 1L);

        assertEquals(List.of(70L, 0L, 90L, 100L), BalancesAt.balancesAt(times, amounts, queries));
    }

    // edge cases

    @Test
    @DisplayName("no events")
    void noEvents() {
        List<Long> times = List.of();
        List<Long> amounts = List.of();
        List<Long> queries = List.of(5L);

        assertEquals(List.of(0L), BalancesAt.balancesAt(times, amounts, queries));
    }

    @Test
    @DisplayName("no queries")
    void noQueries() {
        List<Long> times = List.of(1L);
        List<Long> amounts = List.of(10L);
        List<Long> queries = List.of();

        assertEquals(List.of(), BalancesAt.balancesAt(times, amounts, queries));
    }

    @Test
    @DisplayName("two events at time 2 add up to 15")
    void twoEventsAtTheSameTime() {
        List<Long> times = List.of(2L, 2L);
        List<Long> amounts = List.of(10L, 5L);
        List<Long> queries = List.of(2L, 1L);

        assertEquals(List.of(15L, 0L), BalancesAt.balancesAt(times, amounts, queries));
    }

    @Test
    @DisplayName("balance can go below zero")
    void balanceBelowZero() {
        List<Long> times = List.of(1L);
        List<Long> amounts = List.of(-20L);
        List<Long> queries = List.of(1L);

        assertEquals(List.of(-20L), BalancesAt.balancesAt(times, amounts, queries));
    }

    // negatives and large values

    @Test
    @DisplayName("negative times: 10, then 10 - 4 = 6")
    void negativeTimes() {
        List<Long> times = List.of(-5L, -1L);
        List<Long> amounts = List.of(10L, -4L);
        List<Long> queries = List.of(-6L, -5L, -3L, -1L);

        assertEquals(List.of(0L, 10L, 10L, 6L), BalancesAt.balancesAt(times, amounts, queries));
    }

    @Test
    @DisplayName("sums beyond int range")
    void sumsBeyondIntRange() {
        List<Long> times = List.of(1L, 2L);
        List<Long> amounts = List.of(3000000000000L, 3000000000000L);
        List<Long> queries = List.of(2L);

        assertEquals(List.of(6000000000000L), BalancesAt.balancesAt(times, amounts, queries));
    }
}
