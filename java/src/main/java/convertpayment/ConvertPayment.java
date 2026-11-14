package convertpayment;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConvertPayment {

    public static void main(String[] args) {
        List<String> amounts = Arrays.asList("19.99", "19.99", "2.5");
        List<String> rates = Arrays.asList("0.86", "151.37", "1");
        List<Integer> targetScales = Arrays.asList(2, 0, 0);

        List<String> result = convertPayments(amounts, rates, targetScales);

        System.out.println(result);
    }

    public static List<String> convertPayments(List<String> amounts, List<String> rates,
                                               List<Integer> targetScales) {
        // Write your code here
        var resultList = new ArrayList<String>();
        for(int i = 0; i < amounts.size(); i++) {
            resultList.add(new BigDecimal(amounts.get(i))
                    .multiply(new BigDecimal(rates.get(i)))
                    .setScale(targetScales.get(i), RoundingMode.HALF_DOWN).toPlainString());
        }
        return resultList;
    }

//    public static List<String> convertPayments(List<String> amounts, List<String> rates,
//                                               List<Integer> targetScales) {
//        // Write your code here
//        var resultList = new ArrayList<String>();
//        for(int i = 0; i < amounts.size(); i++) {
//            BigDecimal amount = new BigDecimal(amounts.get(i));
//            BigDecimal rate = new BigDecimal(rates.get(i));
//            BigDecimal converted  = amount.multiply(rate);
//            System.out.println(converted);
//            BigDecimal rounded  = converted.setScale(targetScales.get(i), RoundingMode.HALF_EVEN);
//            resultList.add(rounded.toPlainString());
//        }
//        return resultList;
//    }
}
