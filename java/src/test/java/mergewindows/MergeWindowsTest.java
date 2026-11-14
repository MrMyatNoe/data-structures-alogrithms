package mergewindows;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Rules under test: merge windows that overlap or touch (the next start is not
 * after the current end). Windows may come in any order. The result is sorted
 * by start. Windows with a gap between them stay separate.
 *
 * mergeWindows sorts its argument in place, so every input is wrapped in
 * new ArrayList<>(...) to be mutable.
 */
class MergeWindowsTest {

    // sample

    @Test
    @DisplayName("unsorted input, [3,6] overlaps [1,4]")
    void unsortedInput() {
        List<List<Integer>> windows = new ArrayList<>(List.of(List.of(8, 9), List.of(1, 4), List.of(3, 6)));

        assertEquals(List.of(List.of(1, 6), List.of(8, 9)), MergeWindows.mergeWindows(windows));
    }

    // edge cases

    @Test
    @DisplayName("empty input")
    void emptyInput() {
        List<List<Integer>> windows = new ArrayList<>();

        assertEquals(List.of(), MergeWindows.mergeWindows(windows));
    }

    @Test
    @DisplayName("single window")
    void singleWindow() {
        List<List<Integer>> windows = new ArrayList<>(List.of(List.of(5, 7)));

        assertEquals(List.of(List.of(5, 7)), MergeWindows.mergeWindows(windows));
    }

    @Test
    @DisplayName("duplicate windows collapse")
    void duplicateWindows() {
        List<List<Integer>> windows = new ArrayList<>(List.of(List.of(1, 3), List.of(1, 3)));

        assertEquals(List.of(List.of(1, 3)), MergeWindows.mergeWindows(windows));
    }

    // merging

    @Test
    @DisplayName("touching windows merge")
    void touchingWindowsMerge() {
        List<List<Integer>> windows = new ArrayList<>(List.of(List.of(1, 3), List.of(3, 5)));

        assertEquals(List.of(List.of(1, 5)), MergeWindows.mergeWindows(windows));
    }

    @Test
    @DisplayName("window inside another")
    void windowInsideAnother() {
        List<List<Integer>> windows = new ArrayList<>(List.of(List.of(1, 10), List.of(2, 3)));

        assertEquals(List.of(List.of(1, 10)), MergeWindows.mergeWindows(windows));
    }

    @Test
    @DisplayName("chain of overlaps")
    void chainOfOverlaps() {
        List<List<Integer>> windows = new ArrayList<>(List.of(List.of(1, 3), List.of(2, 6), List.of(5, 9)));

        assertEquals(List.of(List.of(1, 9)), MergeWindows.mergeWindows(windows));
    }

    @Test
    @DisplayName("negative times")
    void negativeTimes() {
        List<List<Integer>> windows = new ArrayList<>(List.of(List.of(-3, 2), List.of(-5, -1)));

        assertEquals(List.of(List.of(-5, 2)), MergeWindows.mergeWindows(windows));
    }

    // no merging

    @Test
    @DisplayName("gap between windows")
    void gapBetweenWindows() {
        List<List<Integer>> windows = new ArrayList<>(List.of(List.of(1, 2), List.of(4, 5)));

        assertEquals(List.of(List.of(1, 2), List.of(4, 5)), MergeWindows.mergeWindows(windows));
    }

    @Test
    @DisplayName("next to each other but not touching")
    void nextToEachOtherButNotTouching() {
        List<List<Integer>> windows = new ArrayList<>(List.of(List.of(1, 2), List.of(3, 4)));

        assertEquals(List.of(List.of(1, 2), List.of(3, 4)), MergeWindows.mergeWindows(windows));
    }
}
