package dynamic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Rules under test: split the amount into transfers of the allowed sizes, each
 * size having its own fee (sizes.get(i) costs fees.get(i)), and return the
 * lowest total fee. A size can be used any number of times. If the amount cannot
 * be made exactly, the answer is -1. An amount of 0 costs 0.
 */
class MinTransferFeeTest {

    // sample

    @Test
    @DisplayName("5 + 5 costs 8, cheaper than one 10 or ten 1s")
    void sample() {
        List<Integer> sizes = List.of(1, 5, 10);
        List<Long> fees = List.of(3L, 4L, 10L);

        assertEquals(8L, MinTransferFee.minTransferFee(sizes, fees, 10));
    }

    // edge cases

    @Test
    @DisplayName("empty sizes, amount 5")
    void emptySizes() {
        List<Integer> sizes = List.of();
        List<Long> fees = List.of();

        assertEquals(-1L, MinTransferFee.minTransferFee(sizes, fees, 5));
    }

    @Test
    @DisplayName("empty sizes, amount 0")
    void emptySizesAmountZero() {
        List<Integer> sizes = List.of();
        List<Long> fees = List.of();

        assertEquals(0L, MinTransferFee.minTransferFee(sizes, fees, 0));
    }

    @Test
    @DisplayName("amount 0 costs nothing")
    void amountZero() {
        List<Integer> sizes = List.of(1, 5);
        List<Long> fees = List.of(3L, 4L);

        assertEquals(0L, MinTransferFee.minTransferFee(sizes, fees, 0));
    }

    @Test
    @DisplayName("single size fits exactly")
    void singleSizeFitsExactly() {
        List<Integer> sizes = List.of(5);
        List<Long> fees = List.of(2L);

        assertEquals(2L, MinTransferFee.minTransferFee(sizes, fees, 5));
    }

    @Test
    @DisplayName("four transfers of 1")
    void fourTransfersOfOne() {
        List<Integer> sizes = List.of(1);
        List<Long> fees = List.of(3L);

        assertEquals(12L, MinTransferFee.minTransferFee(sizes, fees, 4));
    }

    // no answer

    @Test
    @DisplayName("size 3 cannot make 4")
    void sizeThreeCannotMakeFour() {
        List<Integer> sizes = List.of(3);
        List<Long> fees = List.of(5L);

        assertEquals(-1L, MinTransferFee.minTransferFee(sizes, fees, 4));
    }

    // choosing the cheapest combination

    @Test
    @DisplayName("2 + 2 + 3 costs 1 + 1 + 5")
    void twoTwoThree() {
        List<Integer> sizes = List.of(2, 3);
        List<Long> fees = List.of(1L, 5L);

        assertEquals(7L, MinTransferFee.minTransferFee(sizes, fees, 7));
    }

    @Test
    @DisplayName("duplicate size 2: the cheaper fee is used twice")
    void duplicateSizeUsesCheaperFee() {
        List<Integer> sizes = List.of(2, 2);
        List<Long> fees = List.of(4L, 1L);

        assertEquals(2L, MinTransferFee.minTransferFee(sizes, fees, 4));
    }

    @Test
    @DisplayName("four small transfers beat one expensive big one")
    void smallTransfersBeatOneBig() {
        List<Integer> sizes = List.of(1, 4);
        List<Long> fees = List.of(1L, 10L);

        assertEquals(4L, MinTransferFee.minTransferFee(sizes, fees, 4));
    }

    // large values

    @Test
    @DisplayName("fee beyond int range: 3 x 3_000_000_000_000")
    void feeBeyondIntRange() {
        List<Integer> sizes = List.of(1);
        List<Long> fees = List.of(3000000000000L);

        assertEquals(9000000000000L, MinTransferFee.minTransferFee(sizes, fees, 3));
    }
}
