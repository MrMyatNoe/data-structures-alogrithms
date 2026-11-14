package finalbalance;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Rules under test: return the first reference (in the original order) that
 * appears exactly once. "NONE" when every reference repeats or the list is empty.
 * References are compared exactly (case-sensitive).
 */
class FirstUniqueReferenceTest {

    // sample

    @Test
    @DisplayName("A and B repeat; C is the first single")
    void sample() {
        List<String> references = List.of("A", "B", "A", "C", "B");

        assertEquals("C", FirstUniqueReference.firstUniqueReference(references));
    }

    // edge cases

    @Test
    @DisplayName("empty input")
    void emptyInput() {
        List<String> references = List.of();

        assertEquals("NONE", FirstUniqueReference.firstUniqueReference(references));
    }

    @Test
    @DisplayName("single reference")
    void singleReference() {
        List<String> references = List.of("A");

        assertEquals("A", FirstUniqueReference.firstUniqueReference(references));
    }

    @Test
    @DisplayName("everything repeats")
    void everythingRepeats() {
        List<String> references = List.of("X", "X");

        assertEquals("NONE", FirstUniqueReference.firstUniqueReference(references));
    }

    // choosing the first unique one

    @Test
    @DisplayName("Q is unique and first")
    void uniqueAndFirst() {
        List<String> references = List.of("Q", "R", "R");

        assertEquals("Q", FirstUniqueReference.firstUniqueReference(references));
    }

    @Test
    @DisplayName("first in order, not alphabetically")
    void firstInOrderNotAlphabetically() {
        List<String> references = List.of("Z", "A");

        assertEquals("Z", FirstUniqueReference.firstUniqueReference(references));
    }

    @Test
    @DisplayName("unique one sits between duplicates")
    void uniqueBetweenDuplicates() {
        List<String> references = List.of("A", "B", "A");

        assertEquals("B", FirstUniqueReference.firstUniqueReference(references));
    }

    @Test
    @DisplayName("case-sensitive: a and A differ")
    void caseSensitive() {
        List<String> references = List.of("a", "A", "a");

        assertEquals("A", FirstUniqueReference.firstUniqueReference(references));
    }
}
