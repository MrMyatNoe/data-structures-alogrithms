package finalbalance;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Rules under test: each user has a limit per day. A payment is ACCEPTED when the
 * user's accepted total for that day, including this payment, is at most the
 * limit. Otherwise it is REJECTED and not counted. Different users and different
 * days are tracked separately.
 */
class DailyLimitTest {

    // sample

    @Test
    @DisplayName("different users and days")
    void differentUsersAndDays() {
        List<String> users = List.of("a1", "a");
        List<Integer> days = List.of(1, 11);
        List<Long> amounts = List.of(800L, 800L);

        assertEquals(List.of("ACCEPT", "ACCEPT"), DailyLimit.checkDailyLimit(users, days, amounts, 1000));
    }

    @Test
    @DisplayName("1500 is rejected and not counted, so 400 is accepted")
    void rejectedPaymentIsNotCounted() {
        List<String> users = List.of("u1", "u1");
        List<Integer> days = List.of(1, 1);
        List<Long> amounts = List.of(1500L, 400L);

        assertEquals(List.of("REJECT", "ACCEPT"), DailyLimit.checkDailyLimit(users, days, amounts, 1000));
    }

    // edge cases

    @Test
    @DisplayName("empty input")
    void emptyInput() {
        List<String> users = List.of();
        List<Integer> days = List.of();
        List<Long> amounts = List.of();

        assertEquals(List.of(), DailyLimit.checkDailyLimit(users, days, amounts, 1000));
    }

    @Test
    @DisplayName("exactly the limit is accepted")
    void exactlyTheLimit() {
        List<String> users = List.of("u");
        List<Integer> days = List.of(1);
        List<Long> amounts = List.of(1000L);

        assertEquals(List.of("ACCEPT"), DailyLimit.checkDailyLimit(users, days, amounts, 1000));
    }

    @Test
    @DisplayName("one over the limit is rejected")
    void oneOverTheLimit() {
        List<String> users = List.of("u");
        List<Integer> days = List.of(1);
        List<Long> amounts = List.of(1001L);

        assertEquals(List.of("REJECT"), DailyLimit.checkDailyLimit(users, days, amounts, 1000));
    }

    @Test
    @DisplayName("limit 0: zero is fine, 1 is rejected")
    void limitZero() {
        List<String> users = List.of("u", "u");
        List<Integer> days = List.of(1, 1);
        List<Long> amounts = List.of(0L, 1L);

        assertEquals(List.of("ACCEPT", "REJECT"), DailyLimit.checkDailyLimit(users, days, amounts, 0));
    }

    // accumulating

    @Test
    @DisplayName("same user, new day starts at 0")
    void newDayStartsAtZero() {
        List<String> users = List.of("u", "u");
        List<Integer> days = List.of(1, 2);
        List<Long> amounts = List.of(800L, 800L);

        assertEquals(List.of("ACCEPT", "ACCEPT"), DailyLimit.checkDailyLimit(users, days, amounts, 1000));
    }

    @Test
    @DisplayName("600 + 400 = 1000 is fine, 1 more is rejected")
    void totalReachesTheLimit() {
        List<String> users = List.of("u", "u", "u");
        List<Integer> days = List.of(1, 1, 1);
        List<Long> amounts = List.of(600L, 400L, 1L);

        assertEquals(List.of("ACCEPT", "ACCEPT", "REJECT"),
                DailyLimit.checkDailyLimit(users, days, amounts, 1000));
    }

    @Test
    @DisplayName("rejected 200 is not counted, so 900 + 100 fits")
    void rejectedPaymentDoesNotUseTheLimit() {
        List<String> users = List.of("u", "u", "u");
        List<Integer> days = List.of(1, 1, 1);
        List<Long> amounts = List.of(900L, 200L, 100L);

        assertEquals(List.of("ACCEPT", "REJECT", "ACCEPT"),
                DailyLimit.checkDailyLimit(users, days, amounts, 1000));
    }

    // negatives

    @Test
    @DisplayName("negative amount lowers the total: -50 + 1000 = 950")
    void negativeAmountLowersTheTotal() {
        List<String> users = List.of("u", "u");
        List<Integer> days = List.of(1, 1);
        List<Long> amounts = List.of(-50L, 1000L);

        assertEquals(List.of("ACCEPT", "ACCEPT"), DailyLimit.checkDailyLimit(users, days, amounts, 1000));
    }
}
