package finalbalance;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Rules under test: count how many times each account id appears and report
 * "<id>=<count>" sorted by id as text (so "10" comes before "3").
 */
class CountPerEachTest {

    // sample

    @Test
    @DisplayName("\"1\" appears twice; \"10\" sorts before \"3\"")
    void sample() {
        List<String> accountIds = List.of("0", "1", "3", "1", "6", "9", "10");

        assertEquals(List.of("0=1", "1=2", "10=1", "3=1", "6=1", "9=1"), CountPerEach.findCount(accountIds));
    }

    // edge cases

    @Test
    @DisplayName("empty input")
    void emptyInput() {
        List<String> accountIds = List.of();

        assertEquals(List.of(), CountPerEach.findCount(accountIds));
    }

    @Test
    @DisplayName("single id")
    void singleId() {
        List<String> accountIds = List.of("a");

        assertEquals(List.of("a=1"), CountPerEach.findCount(accountIds));
    }

    @Test
    @DisplayName("all the same")
    void allTheSame() {
        List<String> accountIds = List.of("a", "a", "a");

        assertEquals(List.of("a=3"), CountPerEach.findCount(accountIds));
    }

    // sorting

    @Test
    @DisplayName("case-sensitive: uppercase sorts first")
    void caseSensitive() {
        List<String> accountIds = List.of("a", "A", "a");

        assertEquals(List.of("A=1", "a=2"), CountPerEach.findCount(accountIds));
    }

    @Test
    @DisplayName("\"-\" sorts before digits")
    void minusSortsBeforeDigits() {
        List<String> accountIds = List.of("1", "-1");

        assertEquals(List.of("-1=1", "1=1"), CountPerEach.findCount(accountIds));
    }
}
