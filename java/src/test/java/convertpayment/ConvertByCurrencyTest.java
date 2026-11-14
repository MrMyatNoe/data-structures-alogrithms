package convertpayment;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Rules under test: each rate means 1 from = rate to, and also 1 to = 1/rate from.
 * Rates can be chained through other currencies. The answer is the rate from the
 * source to the destination, rounded to 4 decimals (HALF_EVEN) as text.
 * "-1" when either currency is unknown or no chain connects them.
 *
 * The sample group shares: USD -> EUR at 0.9, EUR -> GBP at 0.8.
 */
class ConvertByCurrencyTest {

    private static final List<String> FROM = List.of("USD", "EUR");
    private static final List<String> TO = List.of("EUR", "GBP");
    private static final List<Double> RATES = List.of(0.9, 0.8);

    // sample

    @ParameterizedTest(name = "{0} -> {1} = {2}")
    @CsvSource({
            "USD, GBP, 0.7200",   // 0.9 x 0.8
            "GBP, USD, 1.3889",   // 1/0.8 x 1/0.9
            "EUR, USD, 1.1111",   // 1/0.9
            "USD, USD, 1.0000",   // same currency
            "USD, JPY, -1",       // unknown destination
            "JPY, USD, -1"        // unknown source
    })
    void sampleQueries(String src, String dst, String expected) {
        assertEquals(expected, ConvertByCurrency.convert(FROM, TO, RATES, src, dst));
    }

    // edge cases

    @Test
    @DisplayName("empty rates: even USD to USD is unknown")
    void emptyRates() {
        List<String> from = List.of();
        List<String> to = List.of();
        List<Double> rates = List.of();

        assertEquals("-1", ConvertByCurrency.convert(from, to, rates, "USD", "USD"));
    }

    @ParameterizedTest(name = "single rate USD->EUR 0.9: {0} -> {1} = {2}")
    @CsvSource({
            "USD, EUR, 0.9000",   // forward
            "EUR, USD, 1.1111"    // reverse: 1/0.9
    })
    void singleRate(String src, String dst, String expected) {
        List<String> from = List.of("USD");
        List<String> to = List.of("EUR");
        List<Double> rates = List.of(0.9);

        assertEquals(expected, ConvertByCurrency.convert(from, to, rates, src, dst));
    }

    @ParameterizedTest(name = "rate {0} rounds to {1}")
    @CsvSource({
            "0.00005, 0.0000",    // tie rounds to even (down)
            "0.00015, 0.0002"     // tie rounds to even (up)
    })
    void roundingTies(double rate, String expected) {
        List<String> from = List.of("A");
        List<String> to = List.of("B");
        List<Double> rates = List.of(rate);

        assertEquals(expected, ConvertByCurrency.convert(from, to, rates, "A", "B"));
    }

    // no answer and chains

    @Test
    @DisplayName("two groups are not connected")
    void twoGroupsAreNotConnected() {
        List<String> from = List.of("USD", "THB");
        List<String> to = List.of("EUR", "MMK");
        List<Double> rates = List.of(0.9, 60.0);

        assertEquals("-1", ConvertByCurrency.convert(from, to, rates, "USD", "MMK"));
    }

    @Test
    @DisplayName("three hops: 2 x 3 x 4")
    void threeHops() {
        List<String> from = List.of("USD", "EUR", "GBP");
        List<String> to = List.of("EUR", "GBP", "JPY");
        List<Double> rates = List.of(2.0, 3.0, 4.0);

        assertEquals("24.0000", ConvertByCurrency.convert(from, to, rates, "USD", "JPY"));
    }
}
