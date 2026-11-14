package stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Rules under test: add up the amounts per sender, rank by total (highest first),
 * break ties by sender name (A to Z), and return the first k as "<sender> <total>".
 */
class TopSendersTest {

    // sample

    @Test
    @DisplayName("totals a=500, b=900, c=200; top 2")
    void topTwo() {
        List<String> senders = List.of("a", "b", "a", "c", "b");
        List<Long> amounts = List.of(300L, 400L, 200L, 200L, 500L);

        assertEquals(List.of("b 900", "a 500"), TopSenders.topSenders(senders, amounts, 2));
    }

    // edge cases

    @Test
    @DisplayName("empty input")
    void emptyInput() {
        List<String> senders = List.of();
        List<Long> amounts = List.of();

        assertEquals(List.of(), TopSenders.topSenders(senders, amounts, 3));
    }

    @Test
    @DisplayName("single sender")
    void singleSender() {
        List<String> senders = List.of("a");
        List<Long> amounts = List.of(10L);

        assertEquals(List.of("a 10"), TopSenders.topSenders(senders, amounts, 1));
    }

    @Test
    @DisplayName("k larger than the number of senders")
    void kLargerThanSenders() {
        List<String> senders = List.of("a", "b");
        List<Long> amounts = List.of(1L, 2L);

        assertEquals(List.of("b 2", "a 1"), TopSenders.topSenders(senders, amounts, 5));
    }

    @Test
    @DisplayName("k is 0")
    void kIsZero() {
        List<String> senders = List.of("a", "b");
        List<Long> amounts = List.of(1L, 2L);

        assertEquals(List.of(), TopSenders.topSenders(senders, amounts, 0));
    }

    // ranking

    @Test
    @DisplayName("repeated senders are merged: a=3 beats b=2")
    void repeatedSendersAreMerged() {
        List<String> senders = List.of("a", "a", "a", "b");
        List<Long> amounts = List.of(1L, 1L, 1L, 2L);

        assertEquals(List.of("a 3"), TopSenders.topSenders(senders, amounts, 1));
    }

    @Test
    @DisplayName("negative totals: a=-6 ranks above b=-10")
    void negativeTotals() {
        List<String> senders = List.of("a", "b", "a");
        List<Long> amounts = List.of(-5L, -10L, -1L);

        assertEquals(List.of("a -6", "b -10"), TopSenders.topSenders(senders, amounts, 2));
    }

    // ties

    @Test
    @DisplayName("tie on total: name order decides")
    void tieOnTotal() {
        List<String> senders = List.of("y", "x", "z");
        List<Long> amounts = List.of(100L, 100L, 50L);

        assertEquals(List.of("x 100", "y 100", "z 50"), TopSenders.topSenders(senders, amounts, 3));
    }

    @Test
    @DisplayName("three-way tie cut at k=2: a and b stay")
    void tieAtTheCutoff() {
        List<String> senders = List.of("b", "a", "c");
        List<Long> amounts = List.of(10L, 10L, 10L);

        assertEquals(List.of("a 10", "b 10"), TopSenders.topSenders(senders, amounts, 2));
    }
}
