package reconcile;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Rules under test: for each transaction id, total the ledger amounts and the
 * bank amounts, and report ids where ledger minus bank is not zero as
 * "<id> <difference>". Ids that appear on only one side count as 0 on the other.
 * The result is sorted by id (A to Z).
 */
class ReconcileTest {

    // sample

    @Test
    @DisplayName("ids on one side only and a mismatch")
    void idsOnOneSideAndAMismatch() {
        List<String> ledgerIds = List.of("c", "a");
        List<Long> ledgerAmounts = List.of(100L, 50L);
        List<String> bankIds = List.of("b", "a");
        List<Long> bankAmounts = List.of(7L, 1000L);

        assertEquals(List.of("a -950", "b -7", "c 100"),
                Reconcile.reconcile(ledgerIds, ledgerAmounts, bankIds, bankAmounts));
    }

    // edge cases

    @Test
    @DisplayName("both sides empty")
    void bothSidesEmpty() {
        List<String> ledgerIds = List.of();
        List<Long> ledgerAmounts = List.of();
        List<String> bankIds = List.of();
        List<Long> bankAmounts = List.of();

        assertEquals(List.of(), Reconcile.reconcile(ledgerIds, ledgerAmounts, bankIds, bankAmounts));
    }

    @Test
    @DisplayName("empty ledger: bank-only ids are negative")
    void emptyLedger() {
        List<String> ledgerIds = List.of();
        List<Long> ledgerAmounts = List.of();
        List<String> bankIds = List.of("y", "x");
        List<Long> bankAmounts = List.of(1L, 5L);

        assertEquals(List.of("x -5", "y -1"),
                Reconcile.reconcile(ledgerIds, ledgerAmounts, bankIds, bankAmounts));
    }

    @Test
    @DisplayName("single entry, ledger only")
    void singleEntryLedgerOnly() {
        List<String> ledgerIds = List.of("a");
        List<Long> ledgerAmounts = List.of(5L);
        List<String> bankIds = List.of();
        List<Long> bankAmounts = List.of();

        assertEquals(List.of("a 5"), Reconcile.reconcile(ledgerIds, ledgerAmounts, bankIds, bankAmounts));
    }

    @Test
    @DisplayName("single entry, matching")
    void singleEntryMatching() {
        List<String> ledgerIds = List.of("a");
        List<Long> ledgerAmounts = List.of(5L);
        List<String> bankIds = List.of("a");
        List<Long> bankAmounts = List.of(5L);

        assertEquals(List.of(), Reconcile.reconcile(ledgerIds, ledgerAmounts, bankIds, bankAmounts));
    }

    // totals

    @Test
    @DisplayName("repeated ids add up and match")
    void repeatedIdsAddUpAndMatch() {
        List<String> ledgerIds = List.of("t1", "t1");
        List<Long> ledgerAmounts = List.of(1000L, 2000L);
        List<String> bankIds = List.of("t1");
        List<Long> bankAmounts = List.of(3000L);

        assertEquals(List.of(), Reconcile.reconcile(ledgerIds, ledgerAmounts, bankIds, bankAmounts));
    }

    @Test
    @DisplayName("repeated ids add up, 2 vs 1")
    void repeatedIdsAddUpTwoVsOne() {
        List<String> ledgerIds = List.of("a", "a");
        List<Long> ledgerAmounts = List.of(1L, 1L);
        List<String> bankIds = List.of("a");
        List<Long> bankAmounts = List.of(1L);

        assertEquals(List.of("a 1"), Reconcile.reconcile(ledgerIds, ledgerAmounts, bankIds, bankAmounts));
    }

    // negatives and large values

    @Test
    @DisplayName("negatives that match")
    void negativesThatMatch() {
        List<String> ledgerIds = List.of("a");
        List<Long> ledgerAmounts = List.of(-5L);
        List<String> bankIds = List.of("a");
        List<Long> bankAmounts = List.of(-5L);

        assertEquals(List.of(), Reconcile.reconcile(ledgerIds, ledgerAmounts, bankIds, bankAmounts));
    }

    @Test
    @DisplayName("negative only in the ledger")
    void negativeOnlyInTheLedger() {
        List<String> ledgerIds = List.of("a");
        List<Long> ledgerAmounts = List.of(-5L);
        List<String> bankIds = List.of();
        List<Long> bankAmounts = List.of();

        assertEquals(List.of("a -5"), Reconcile.reconcile(ledgerIds, ledgerAmounts, bankIds, bankAmounts));
    }

    @Test
    @DisplayName("values beyond int range")
    void valuesBeyondIntRange() {
        List<String> ledgerIds = List.of("big");
        List<Long> ledgerAmounts = List.of(3000000000000L);
        List<String> bankIds = List.of("big");
        List<Long> bankAmounts = List.of(-1000000000000L);

        assertEquals(List.of("big 4000000000000"),
                Reconcile.reconcile(ledgerIds, ledgerAmounts, bankIds, bankAmounts));
    }

    // ordering

    @Test
    @DisplayName("sorted as text: t1 < t10 < t2")
    void sortedAsText() {
        List<String> ledgerIds = List.of("t10", "t2", "t1");
        List<Long> ledgerAmounts = List.of(1L, 1L, 1L);
        List<String> bankIds = List.of();
        List<Long> bankAmounts = List.of();

        assertEquals(List.of("t1 1", "t10 1", "t2 1"),
                Reconcile.reconcile(ledgerIds, ledgerAmounts, bankIds, bankAmounts));
    }
}
