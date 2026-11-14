package stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Rules under test: a user's streak counts only that user's own events in a row.
 * An OK from the same user resets it. The winner has the longest streak; ties go
 * to the alphabetically smaller user. No FAIL at all returns "NONE".
 * Expected format: "<user> <streakLength>".
 */
class LongestFailStreakTest {

    // sample

    @Test
    @DisplayName("OK resets u1's streak")
    void okResetsStreak() {
        List<String> users = List.of("u1", "u2", "u1", "u1", "u2", "u1");
        List<String> statuses = List.of("FAIL", "FAIL", "FAIL", "OK", "FAIL", "FAIL");

        assertEquals("u1 2", LongestFailStreak.longestFailStreak(users, statuses));
    }

    // edge cases

    @Test
    @DisplayName("empty input")
    void emptyInput() {
        List<String> users = List.of();
        List<String> statuses = List.of();

        assertEquals("NONE", LongestFailStreak.longestFailStreak(users, statuses));
    }

    @Test
    @DisplayName("single OK")
    void singleOk() {
        List<String> users = List.of("a");
        List<String> statuses = List.of("OK");

        assertEquals("NONE", LongestFailStreak.longestFailStreak(users, statuses));
    }

    @Test
    @DisplayName("single FAIL")
    void singleFail() {
        List<String> users = List.of("a");
        List<String> statuses = List.of("FAIL");

        assertEquals("a 1", LongestFailStreak.longestFailStreak(users, statuses));
    }

    @Test
    @DisplayName("no FAIL at all")
    void noFailAtAll() {
        List<String> users = List.of("a", "b", "a");
        List<String> statuses = List.of("OK", "OK", "OK");

        assertEquals("NONE", LongestFailStreak.longestFailStreak(users, statuses));
    }

    // streaks

    @Test
    @DisplayName("longest streak wins")
    void longestStreakWins() {
        List<String> users = List.of("b", "a", "b", "b");
        List<String> statuses = List.of("FAIL", "FAIL", "FAIL", "FAIL");

        assertEquals("b 3", LongestFailStreak.longestFailStreak(users, statuses));
    }

    @Test
    @DisplayName("later streak beats an earlier one after a reset")
    void laterStreakBeatsEarlierOne() {
        List<String> users = List.of("a", "a", "a", "a", "a");
        List<String> statuses = List.of("FAIL", "OK", "FAIL", "FAIL", "FAIL");

        assertEquals("a 3", LongestFailStreak.longestFailStreak(users, statuses));
    }

    @Test
    @DisplayName("another user's OK does not reset my streak")
    void otherUsersOkDoesNotReset() {
        List<String> users = List.of("a", "b", "a");
        List<String> statuses = List.of("FAIL", "OK", "FAIL");

        assertEquals("a 2", LongestFailStreak.longestFailStreak(users, statuses));
    }

    // ties

    @Test
    @DisplayName("tie at 1: smaller name wins")
    void tieAtOne() {
        List<String> users = List.of("x", "a");
        List<String> statuses = List.of("FAIL", "FAIL");

        assertEquals("a 1", LongestFailStreak.longestFailStreak(users, statuses));
    }

    @Test
    @DisplayName("tie at 2: smaller name wins even if it got there later")
    void tieAtTwo() {
        List<String> users = List.of("b", "b", "a", "a");
        List<String> statuses = List.of("FAIL", "FAIL", "FAIL", "FAIL");

        assertEquals("a 2", LongestFailStreak.longestFailStreak(users, statuses));
    }
}
