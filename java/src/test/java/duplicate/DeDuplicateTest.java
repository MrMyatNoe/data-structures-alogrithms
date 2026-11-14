package duplicate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Rules under test: a payment is a DUPLICATE when the same key was KEPT less than
 * 60 seconds earlier. Otherwise it is KEPT. The window is measured from the last
 * KEPT payment of that key, so duplicates do not extend it.
 */
class DeDuplicateTest {

    @Test
    @DisplayName("empty input")
    void emptyInput() {
        List<String> keys = List.of();
        List<Long> timestamps = List.of();

        assertEquals(List.of(), DeDuplicate.deduplicatePayments(keys, timestamps));
    }

    @Test
    @DisplayName("different keys never clash")
    void differentKeysNeverClash() {
        List<String> keys = List.of("a", "b");
        List<Long> timestamps = List.of(0L, 10L);

        assertEquals(List.of("KEEP", "KEEP"), DeDuplicate.deduplicatePayments(keys, timestamps));
    }

    @Test
    @DisplayName("exactly 60 seconds apart is kept")
    void exactlySixtySecondsApart() {
        List<String> keys = List.of("a", "a");
        List<Long> timestamps = List.of(0L, 60L);

        assertEquals(List.of("KEEP", "KEEP"), DeDuplicate.deduplicatePayments(keys, timestamps));
    }

    @Test
    @DisplayName("same timestamp is a duplicate")
    void sameTimestamp() {
        List<String> keys = List.of("a", "a");
        List<Long> timestamps = List.of(5L, 5L);

        assertEquals(List.of("KEEP", "DUPLICATE"), DeDuplicate.deduplicatePayments(keys, timestamps));
    }

    @Test
    @DisplayName("negative timestamps, 50 apart")
    void negativeTimestamps() {
        List<String> keys = List.of("a", "a");
        List<Long> timestamps = List.of(-100L, -50L);

        assertEquals(List.of("KEEP", "DUPLICATE"), DeDuplicate.deduplicatePayments(keys, timestamps));
    }

    @Test
    @DisplayName("a duplicate at 30 does not restart the window, so 60 is kept")
    void duplicateDoesNotRestartTheWindow() {
        List<String> keys = List.of("a", "a", "a");
        List<Long> timestamps = List.of(0L, 30L, 60L);

        assertEquals(List.of("KEEP", "DUPLICATE", "KEEP"), DeDuplicate.deduplicatePayments(keys, timestamps));
    }
}
