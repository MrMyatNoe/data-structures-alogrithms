package finalbalance;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Rules under test: the largest total amount inside any window of
 * windowSeconds. Two events are in the same window when their times differ by
 * LESS than windowSeconds (a gap of exactly windowSeconds is a new window).
 * Times are given in increasing order. The answer is never below 0.
 * Every test below uses a 60 second window.
 */
class BusiestWindowTest {

    // sample

    @Test
    @DisplayName("0 and 30 give 70; the event at 0 is too old for 70")
    void sample() {
        List<Long> times = List.of(0L, 30L, 70L);
        List<Long> amounts = List.of(50L, 20L, 40L);

        assertEquals(70L, BusiestWindow.busiestWindow(times, amounts, 60));
    }

    // edge cases

    @Test
    @DisplayName("empty input")
    void emptyInput() {
        List<Long> times = List.of();
        List<Long> amounts = List.of();

        assertEquals(0L, BusiestWindow.busiestWindow(times, amounts, 60));
    }

    @Test
    @DisplayName("single event")
    void singleEvent() {
        List<Long> times = List.of(5L);
        List<Long> amounts = List.of(100L);

        assertEquals(100L, BusiestWindow.busiestWindow(times, amounts, 60));
    }

    @Test
    @DisplayName("same time: both count")
    void sameTime() {
        List<Long> times = List.of(5L, 5L);
        List<Long> amounts = List.of(10L, 10L);

        assertEquals(20L, BusiestWindow.busiestWindow(times, amounts, 60));
    }

    // the window boundary

    @Test
    @DisplayName("gap of 59 is inside the window: 10 + 20")
    void gapOf59() {
        List<Long> times = List.of(0L, 59L);
        List<Long> amounts = List.of(10L, 20L);

        assertEquals(30L, BusiestWindow.busiestWindow(times, amounts, 60));
    }

    @Test
    @DisplayName("gap of exactly 60 is outside: best is 20 alone")
    void gapOfExactly60() {
        List<Long> times = List.of(0L, 60L);
        List<Long> amounts = List.of(10L, 20L);

        assertEquals(20L, BusiestWindow.busiestWindow(times, amounts, 60));
    }

    @Test
    @DisplayName("every event alone in its window")
    void everyEventAlone() {
        List<Long> times = List.of(0L, 100L, 200L);
        List<Long> amounts = List.of(5L, 7L, 3L);

        assertEquals(7L, BusiestWindow.busiestWindow(times, amounts, 60));
    }

    // negatives and large values

    @Test
    @DisplayName("negative amount lowers the total: -5 + 10")
    void negativeAmountLowersTheTotal() {
        List<Long> times = List.of(0L, 1L);
        List<Long> amounts = List.of(-5L, 10L);

        assertEquals(5L, BusiestWindow.busiestWindow(times, amounts, 60));
    }

    @Test
    @DisplayName("all negative: result is 0")
    void allNegative() {
        List<Long> times = List.of(0L, 1L);
        List<Long> amounts = List.of(-5L, -3L);

        assertEquals(0L, BusiestWindow.busiestWindow(times, amounts, 60));
    }

    @Test
    @DisplayName("values beyond int range")
    void valuesBeyondIntRange() {
        List<Long> times = List.of(0L, 1L);
        List<Long> amounts = List.of(3000000000000L, 3000000000000L);

        assertEquals(6000000000000L, BusiestWindow.busiestWindow(times, amounts, 60));
    }
}
