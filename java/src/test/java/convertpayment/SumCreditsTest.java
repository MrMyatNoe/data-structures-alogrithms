package convertpayment;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Rules under test: add up the "credit" amounts of one user across all pages and
 * return the total in cents (a long). Debits are ignored.
 *
 * The data comes from the built-in getPage(), which has 2 pages:
 *   u1: credit 1234.56, debit 5.00, credit 0.10 (second page)
 *   u2: credit 10.00
 */
class SumCreditsTest {

    @Test
    @DisplayName("u1: 1234.56 + 0.10 across two pages, debit ignored")
    void userWithCreditsOnTwoPages() {
        assertEquals(123466L, SumCredits.sumCredits("u1"));
    }

    @Test
    @DisplayName("u2: 10.00 on page 1")
    void userWithOneCredit() {
        assertEquals(1000L, SumCredits.sumCredits("u2"));
    }

    @Test
    @DisplayName("unknown user")
    void unknownUser() {
        assertEquals(0L, SumCredits.sumCredits("u9"));
    }

    @Test
    @DisplayName("empty user id")
    void emptyUserId() {
        assertEquals(0L, SumCredits.sumCredits(""));
    }

    @Test
    @DisplayName("user ids are case-sensitive")
    void userIdsAreCaseSensitive() {
        assertEquals(0L, SumCredits.sumCredits("U1"));
    }
}
