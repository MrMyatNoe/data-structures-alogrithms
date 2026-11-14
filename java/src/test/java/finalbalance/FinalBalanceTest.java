package finalbalance;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Rules under test: apply the transactions in order to each account. A
 * transaction that would make the balance negative is skipped. Accounts with at
 * least one accepted transaction are reported as "<id> <balance>", sorted by id.
 */
class FinalBalanceTest {

    // sample

    @Test
    @DisplayName("a: 100, -150 skipped, -100 -> 0; b: 50")
    void sample() {
        List<String> accountIds = List.of("a", "a", "b", "a");
        List<Long> amounts = List.of(100L, -150L, 50L, -100L);

        assertEquals(List.of("a 0", "b 50"), FinalBalance.finalBalances(accountIds, amounts));
    }

    // edge cases

    @Test
    @DisplayName("empty input")
    void emptyInput() {
        List<String> accountIds = List.of();
        List<Long> amounts = List.of();

        assertEquals(List.of(), FinalBalance.finalBalances(accountIds, amounts));
    }

    @Test
    @DisplayName("single transaction")
    void singleTransaction() {
        List<String> accountIds = List.of("x");
        List<Long> amounts = List.of(10L);

        assertEquals(List.of("x 10"), FinalBalance.finalBalances(accountIds, amounts));
    }

    @Test
    @DisplayName("zero amount is accepted")
    void zeroAmountIsAccepted() {
        List<String> accountIds = List.of("a");
        List<Long> amounts = List.of(0L);

        assertEquals(List.of("a 0"), FinalBalance.finalBalances(accountIds, amounts));
    }

    // skipped transactions

    @Test
    @DisplayName("-10 is skipped, then +5 -> 5")
    void overdraftIsSkipped() {
        List<String> accountIds = List.of("c", "c");
        List<Long> amounts = List.of(-10L, 5L);

        assertEquals(List.of("c 5"), FinalBalance.finalBalances(accountIds, amounts));
    }

    @Test
    @DisplayName("account whose only transaction is skipped is not reported")
    void accountWithOnlySkippedTransaction() {
        List<String> accountIds = List.of("x");
        List<Long> amounts = List.of(-10L);

        assertEquals(List.of(), FinalBalance.finalBalances(accountIds, amounts));
    }

    // large values

    @Test
    @DisplayName("values beyond int range")
    void valuesBeyondIntRange() {
        List<String> accountIds = List.of("a", "a");
        List<Long> amounts = List.of(3000000000000L, 3000000000000L);

        assertEquals(List.of("a 6000000000000"), FinalBalance.finalBalances(accountIds, amounts));
    }

    // ordering

    @Test
    @DisplayName("sorted as text: t1 < t10 < t2")
    void sortedAsText() {
        List<String> accountIds = List.of("t10", "t2", "t1");
        List<Long> amounts = List.of(1L, 1L, 1L);

        assertEquals(List.of("t1 1", "t10 1", "t2 1"), FinalBalance.finalBalances(accountIds, amounts));
    }
}
