package finalbalance;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Rules under test: the fewest workers needed so no worker does two jobs at the
 * same time. A job is [start, end]. A worker can start a new job exactly when
 * the previous one ends (end == start). Jobs may come in any order.
 */
class MinWorkersTest {

    // sample

    @Test
    @DisplayName("all three overlap at time 15")
    void sample() {
        List<List<Integer>> jobs = List.of(List.of(0, 30), List.of(10, 20), List.of(15, 40));

        assertEquals(3, MinWorkers.minWorkers(jobs));
    }

    // edge cases

    @Test
    @DisplayName("empty input")
    void emptyInput() {
        List<List<Integer>> jobs = List.of();

        assertEquals(0, MinWorkers.minWorkers(jobs));
    }

    @Test
    @DisplayName("single job")
    void singleJob() {
        List<List<Integer>> jobs = List.of(List.of(1, 5));

        assertEquals(1, MinWorkers.minWorkers(jobs));
    }

    @Test
    @DisplayName("identical jobs")
    void identicalJobs() {
        List<List<Integer>> jobs = List.of(List.of(1, 5), List.of(1, 5), List.of(1, 5));

        assertEquals(3, MinWorkers.minWorkers(jobs));
    }

    // reusing a worker

    @Test
    @DisplayName("next job starts exactly when the first ends")
    void nextJobStartsWhenTheFirstEnds() {
        List<List<Integer>> jobs = List.of(List.of(0, 10), List.of(10, 20));

        assertEquals(1, MinWorkers.minWorkers(jobs));
    }

    @Test
    @DisplayName("next job starts 1 before the first ends")
    void nextJobStartsOneBeforeTheFirstEnds() {
        List<List<Integer>> jobs = List.of(List.of(0, 10), List.of(9, 20));

        assertEquals(2, MinWorkers.minWorkers(jobs));
    }

    @Test
    @DisplayName("unsorted input still chains")
    void unsortedInputStillChains() {
        List<List<Integer>> jobs = List.of(List.of(10, 20), List.of(0, 5), List.of(5, 10));

        assertEquals(1, MinWorkers.minWorkers(jobs));
    }

    @Test
    @DisplayName("[1,3] ends before [4,6] starts, so that worker is reused")
    void finishedWorkerIsReused() {
        List<List<Integer>> jobs = List.of(List.of(0, 5), List.of(1, 3), List.of(4, 6));

        assertEquals(2, MinWorkers.minWorkers(jobs));
    }

    // negatives

    @Test
    @DisplayName("negative times")
    void negativeTimes() {
        List<List<Integer>> jobs = List.of(List.of(-10, -5), List.of(-7, -1));

        assertEquals(2, MinWorkers.minWorkers(jobs));
    }
}
