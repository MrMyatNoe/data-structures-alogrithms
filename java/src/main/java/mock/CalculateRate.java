package mock;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculateRate {

    public static void main(String[] args) {
        System.out.println(tieredFee("1.50"));
    }

    private static final BigDecimal TIER_1_LIMIT = new BigDecimal("1000.00");
    private static final BigDecimal TIER_2_LIMIT = new BigDecimal("10000.00");
    private static final BigDecimal TIER_1_RATE = new BigDecimal("0.01");
    private static final BigDecimal TIER_2_RATE = new BigDecimal("0.005");
    private static final BigDecimal TIER_3_RATE = new BigDecimal("0.0025");

    public static String tieredFee(String amount) {
        // Write your code here
        BigDecimal data = new BigDecimal(amount);

        BigDecimal tier1 = data.min(TIER_1_LIMIT);
        BigDecimal tier2 = data.min(TIER_2_LIMIT).subtract(TIER_1_LIMIT).max(BigDecimal.ZERO);
        BigDecimal tier3 = data.subtract(TIER_2_LIMIT).max(BigDecimal.ZERO);

        BigDecimal fee = tier1.multiply(TIER_1_RATE)
                        .add(tier2.multiply(TIER_2_RATE)
                        .add(tier3.multiply(TIER_3_RATE)));
        return fee.setScale(2, RoundingMode.HALF_EVEN).toPlainString();
    }

//    public static String tieredFee(String amount) {
//        BigDecimal value = new BigDecimal(amount);
//
//        BigDecimal tier1 = value.min(TIER_1_LIMIT);
//        BigDecimal tier2 = value.min(TIER_2_LIMIT).subtract(TIER_1_LIMIT).max(BigDecimal.ZERO);
//        BigDecimal tier3 = value.subtract(TIER_2_LIMIT).max(BigDecimal.ZERO);
//
//        BigDecimal fee = tier1.multiply(TIER_1_RATE)
//                .add(tier2.multiply(TIER_2_RATE))
//                .add(tier3.multiply(TIER_3_RATE));
//
//        return fee.setScale(2, RoundingMode.HALF_EVEN).toPlainString();
//    }
}
