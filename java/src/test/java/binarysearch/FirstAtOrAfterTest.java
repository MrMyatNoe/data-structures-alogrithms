package binarysearch;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Rules under test: index of the first time that is >= t in a sorted list,
 * or -1 when every time is before t.
 */
class FirstAtOrAfterTest {

    // sample

    @Test
    @DisplayName("exact match returns the first of the duplicates")
    void exactMatchReturnsFirstDuplicate() {
        List<Long> times = List.of(10L, 20L, 20L, 30L);

        assertEquals(1, FirstAtOrAfter.firstAtOrAfter(times, 20));
    }

    @Test
    @DisplayName("between two values returns the next larger one")
    void betweenValues() {
        List<Long> times = List.of(10L, 20L, 20L, 30L);

        assertEquals(3, FirstAtOrAfter.firstAtOrAfter(times, 25));
    }

    // edge cases

    @Test
    @DisplayName("empty input")
    void emptyInput() {
        assertEquals(-1, FirstAtOrAfter.firstAtOrAfter(List.of(), 5));
    }

    @Test
    @DisplayName("single element after t")
    void singleElementAfter() {
        assertEquals(0, FirstAtOrAfter.firstAtOrAfter(List.of(5L), 3));
    }

    @Test
    @DisplayName("single element before t")
    void singleElementBefore() {
        assertEquals(-1, FirstAtOrAfter.firstAtOrAfter(List.of(5L), 7));
    }

    // bounds

    @Test
    @DisplayName("t before every element returns 0")
    void tBeforeAll() {
        List<Long> times = List.of(10L, 20L, 30L);

        assertEquals(0, FirstAtOrAfter.firstAtOrAfter(times, 5));
    }

    @Test
    @DisplayName("t equal to the last element returns the last index")
    void tEqualsLast() {
        List<Long> times = List.of(10L, 20L, 30L);

        assertEquals(2, FirstAtOrAfter.firstAtOrAfter(times, 30));
    }

    @Test
    @DisplayName("no answer when t is after every element")
    void tAfterAll() {
        List<Long> times = List.of(10L, 20L, 20L, 30L);

        assertEquals(-1, FirstAtOrAfter.firstAtOrAfter(times, 31));
    }

    // duplicates
    @Test
    @DisplayName("all elements equal to t returns 0")
    void allDuplicates() {
        List<Long> times = List.of(7L, 7L, 7L, 7L);

        assertEquals(0, FirstAtOrAfter.firstAtOrAfter(times, 7));
    }

    // negatives
    @Test
    @DisplayName("negative times")
    void negativeTimes() {
        List<Long> times = List.of(-30L, -20L, -20L, -5L);

        assertEquals(1, FirstAtOrAfter.firstAtOrAfter(times, -20));
        assertEquals(3, FirstAtOrAfter.firstAtOrAfter(times, -10));
    }
}
