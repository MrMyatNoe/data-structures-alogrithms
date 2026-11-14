package mock;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Rules under test: tiered fee on an amount.
 * First 1000.00 at 1%, next up to 10000.00 at 0.5%, anything above at 0.25%.
 * The fee is rounded to 2 decimals, half-to-even (0.015 -> 0.02, 0.025 -> 0.02,
 * 0.035 -> 0.04).
 */
class CalculateRateTest {

    // edge case

    @Test
    @DisplayName("zero")
    void zero() {
        assertEquals("0.00", CalculateRate.tieredFee("0"));
    }

    // tier 1

    @Test
    @DisplayName("tier 1 only: 100 x 1%")
    void tierOneOnly() {
        assertEquals("1.00", CalculateRate.tieredFee("100"));
    }

    @Test
    @DisplayName("exactly the tier 1 limit: 1000 x 1%")
    void exactlyTierOneLimit() {
        assertEquals("10.00", CalculateRate.tieredFee("1000.00"));
    }

    // tier 2

    @Test
    @DisplayName("just past tier 1: 10.00005 -> 10.00")
    void justPastTierOne() {
        assertEquals("10.00", CalculateRate.tieredFee("1000.01"));
    }

    @Test
    @DisplayName("tier 1 + 4000 x 0.5%")
    void tierOneAndTwo() {
        assertEquals("30.00", CalculateRate.tieredFee("5000"));
    }

    @Test
    @DisplayName("exactly the tier 2 limit: 10 + 9000 x 0.5%")
    void exactlyTierTwoLimit() {
        assertEquals("55.00", CalculateRate.tieredFee("10000"));
    }

    @Test
    @DisplayName("fractional result: 11.1728 -> 11.17")
    void fractionalResult() {
        assertEquals("11.17", CalculateRate.tieredFee("1234.56"));
    }

    // tier 3

    @Test
    @DisplayName("all three tiers: 55 + 2000 x 0.25%")
    void allThreeTiers() {
        assertEquals("60.00", CalculateRate.tieredFee("12000"));
    }

    // rounding, half to even

    @Test
    @DisplayName("0.005 rounds to 0.00")
    void halfCentRoundsToZero() {
        assertEquals("0.00", CalculateRate.tieredFee("0.50"));
    }

    @Test
    @DisplayName("0.015 rounds to 0.02")
    void oneAndAHalfCentsRoundsToTwo() {
        assertEquals("0.02", CalculateRate.tieredFee("1.50"));
    }

    @Test
    @DisplayName("0.025 rounds to 0.02")
    void twoAndAHalfCentsRoundsToTwo() {
        assertEquals("0.02", CalculateRate.tieredFee("2.50"));
    }

    @Test
    @DisplayName("0.035 rounds to 0.04")
    void threeAndAHalfCentsRoundsToFour() {
        assertEquals("0.04", CalculateRate.tieredFee("3.50"));
    }
}
