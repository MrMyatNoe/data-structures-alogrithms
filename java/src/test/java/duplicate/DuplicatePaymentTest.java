package duplicate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Rules under test: remove repeated keys and keep the first occurrence of each,
 * in the original order. Keys are compared exactly (case-sensitive).
 */
class DuplicatePaymentTest {

    // sample

    @Test
    @DisplayName("3 and 6 repeat later and are dropped")
    void sample() {
        List<String> keys = List.of("3", "6", "0", "9", "3", "6", "2", "4");

        assertEquals(List.of("3", "6", "0", "9", "2", "4"), DuplicatePayment.deduplicate(keys));
    }

    // edge cases

    @Test
    @DisplayName("empty input")
    void emptyInput() {
        List<String> keys = List.of();

        assertEquals(List.of(), DuplicatePayment.deduplicate(keys));
    }

    @Test
    @DisplayName("all the same")
    void allTheSame() {
        List<String> keys = List.of("a", "a", "a");

        assertEquals(List.of("a"), DuplicatePayment.deduplicate(keys));
    }

    @Test
    @DisplayName("no duplicates: order kept, not sorted")
    void noDuplicates() {
        List<String> keys = List.of("c", "a", "b");

        assertEquals(List.of("c", "a", "b"), DuplicatePayment.deduplicate(keys));
    }

    // exact comparison

    @Test
    @DisplayName("case-sensitive: a and A differ")
    void caseSensitive() {
        List<String> keys = List.of("a", "A", "a");

        assertEquals(List.of("a", "A"), DuplicatePayment.deduplicate(keys));
    }

    @Test
    @DisplayName("negative-looking keys are plain text")
    void negativeLookingKeys() {
        List<String> keys = List.of("-1", "1", "-1");

        assertEquals(List.of("-1", "1"), DuplicatePayment.deduplicate(keys));
    }
}
