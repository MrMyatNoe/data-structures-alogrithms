package convertpayment;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Rules under test: convert each amount with its rate (amount x rate), then round
 * to the target number of decimals using HALF_DOWN (a tie rounds toward zero:
 * 2.5 -> 2, 3.5 -> 3, -2.5 -> -2). The result is plain text, never scientific notation.
 */
class ConvertPaymentTest {

    @Test
    @DisplayName("17.1914 -> 17.19, 3025.8863 -> 3026, 2.5 -> 2")
    void sample() {
        List<String> amounts = List.of("19.99", "19.99", "2.5");
        List<String> rates = List.of("0.86", "151.37", "1");
        List<Integer> targetScales = List.of(2, 0, 0);

        assertEquals(List.of("17.19", "3026", "2"), ConvertPayment.convertPayments(amounts, rates, targetScales));
    }

    // edge cases

    @Test
    @DisplayName("empty input")
    void emptyInput() {
        List<String> amounts = List.of();
        List<String> rates = List.of();
        List<Integer> targetScales = List.of();

        assertEquals(List.of(), ConvertPayment.convertPayments(amounts, rates, targetScales));
    }

    @Test
    @DisplayName("zero amount keeps the decimals")
    void zeroAmount() {
        List<String> amounts = List.of("0");
        List<String> rates = List.of("5");
        List<Integer> targetScales = List.of(2);

        assertEquals(List.of("0.00"), ConvertPayment.convertPayments(amounts, rates, targetScales));
    }

    // rounding, ties go toward zero

    @Test
    @DisplayName("3.5 -> 3 (HALF_EVEN would give 4)")
    void positiveTieRoundsDown() {
        List<String> amounts = List.of("3.5");
        List<String> rates = List.of("1");
        List<Integer> targetScales = List.of(0);

        assertEquals(List.of("3"), ConvertPayment.convertPayments(amounts, rates, targetScales));
    }

    @Test
    @DisplayName("-2.5 -> -2")
    void negativeTieRoundsTowardZero() {
        List<String> amounts = List.of("-2.5");
        List<String> rates = List.of("1");
        List<Integer> targetScales = List.of(0);

        assertEquals(List.of("-2"), ConvertPayment.convertPayments(amounts, rates, targetScales));
    }

    @Test
    @DisplayName("large value stays exact")
    void largeValueStaysExact() {
        List<String> amounts = List.of("123456789012.34");
        List<String> rates = List.of("1000");
        List<Integer> targetScales = List.of(2);

        assertEquals(List.of("123456789012340.00"),
                ConvertPayment.convertPayments(amounts, rates, targetScales));
    }

    @Test
    @DisplayName("tiny value is not printed as 1E-7")
    void tinyValueIsNotScientific() {
        List<String> amounts = List.of("0.0000001");
        List<String> rates = List.of("1");
        List<Integer> targetScales = List.of(7);

        assertEquals(List.of("0.0000001"), ConvertPayment.convertPayments(amounts, rates, targetScales));
    }
}
