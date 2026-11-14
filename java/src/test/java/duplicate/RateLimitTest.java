package duplicate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Rules under test: sliding window rate limit. A request is ALLOWED when fewer
 * than `limit` allowed requests happened in the last `windowMs` milliseconds.
 * An allowed request expires once windowMs or more has passed (a gap of exactly
 * windowMs is already free). Rejected requests are not counted.
 */
class RateLimitTest {

    // sample

    @Test
    @DisplayName("limit 1: second request at the same time is rejected")
    void sample() {
        List<Long> times = List.of(0L, 0L);

        assertEquals(List.of("ALLOW", "REJECT"), RateLimit.rateLimit(times, 1, 1000));
    }

    // edge cases

    @Test
    @DisplayName("empty input")
    void emptyInput() {
        List<Long> times = List.of();

        assertEquals(List.of(), RateLimit.rateLimit(times, 1, 1000));
    }

    @Test
    @DisplayName("limit 0: nothing is allowed")
    void limitZero() {
        List<Long> times = List.of(0L);

        assertEquals(List.of("REJECT"), RateLimit.rateLimit(times, 0, 1000));
    }

    // the window boundary

    @Test
    @DisplayName("gap of 999 is inside a 1000 ms window")
    void gapOf999() {
        List<Long> times = List.of(0L, 999L);

        assertEquals(List.of("ALLOW", "REJECT"), RateLimit.rateLimit(times, 1, 1000));
    }

    @Test
    @DisplayName("gap of exactly 1000 is outside the window")
    void gapOfExactly1000() {
        List<Long> times = List.of(0L, 1000L);

        assertEquals(List.of("ALLOW", "ALLOW"), RateLimit.rateLimit(times, 1, 1000));
    }

    @Test
    @DisplayName("negative times, 50 apart in a 100 window")
    void negativeTimes() {
        List<Long> times = List.of(-100L, -50L);

        assertEquals(List.of("ALLOW", "REJECT"), RateLimit.rateLimit(times, 1, 100));
    }

    // counting

    @Test
    @DisplayName("limit 2: the third request is rejected")
    void limitTwo() {
        List<Long> times = List.of(0L, 100L, 200L);

        assertEquals(List.of("ALLOW", "ALLOW", "REJECT"), RateLimit.rateLimit(times, 2, 1000));
    }

    @Test
    @DisplayName("a rejected request is not counted")
    void rejectedRequestIsNotCounted() {
        List<Long> times = List.of(0L, 500L, 1000L);

        assertEquals(List.of("ALLOW", "REJECT", "ALLOW"), RateLimit.rateLimit(times, 1, 1000));
    }

    @Test
    @DisplayName("sliding: at 10 the request from 0 expires")
    void sliding() {
        List<Long> times = List.of(0L, 5L, 10L, 11L, 12L);

        assertEquals(List.of("ALLOW", "ALLOW", "ALLOW", "REJECT", "REJECT"), RateLimit.rateLimit(times, 2, 10));
    }
}
