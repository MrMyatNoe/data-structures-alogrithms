package convertpayment;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ConvertByCurrency {

    public static void main(String[] args) {
        List<String> from = Arrays.asList("USD", "EUR");
        List<String> to = Arrays.asList("EUR", "GBP");
        List<Double> rates = Arrays.asList(0.9, 0.8);

        System.out.println(convert(from, to, rates, "USD", "GBP")); // 0.7200
        System.out.println(convert(from, to, rates, "GBP", "USD")); // 1.3889
        System.out.println(convert(from, to, rates, "EUR", "USD")); // 1.1111
        System.out.println(convert(from, to, rates, "USD", "USD")); // 1.0000
        System.out.println(convert(from, to, rates, "USD", "JPY")); // -1

        System.out.println(convert(
                Arrays.asList("USD", "THB"), Arrays.asList("EUR", "MMK"),
                Arrays.asList(0.9, 60.0), "USD", "MMK"));

        System.out.println(new BigDecimal("1.0").equals(new BigDecimal("1.00")));

    }

    public static String convert(List<String> from, List<String> to, List<Double> rates,
                                 String src, String dst) {
        // Write your code here
        Map<String, Map<String, Double>> graph = new LinkedHashMap<>();
        for (int i = 0; i < from.size(); i++) {
            String a = from.get(i);
            String b = to.get(i);
            double rate = rates.get(i);

            graph.computeIfAbsent(a, k -> new HashMap<>()).put(b, rate);
            graph.computeIfAbsent(b, k -> new HashMap<>()).put(a, 1.0 / rate);

        }

        System.out.println(graph);

        if(!graph.containsKey(src) || !graph.containsKey(dst)) {
            return "-1";
        }
        Deque<String> queue = new ArrayDeque<>();
        Map<String, Double> rateFromSrc = new HashMap<>();
        queue.add(src);
        rateFromSrc.put(src, 1.0);

        while (!queue.isEmpty()) {
            String current = queue.poll();

            if (current.equals(dst)) {
                return BigDecimal.valueOf(rateFromSrc.get(current))
                        .setScale(4, RoundingMode.HALF_EVEN)
                        .toPlainString();
            }

            for (Map.Entry<String, Double> edge : graph.get(current).entrySet()) {
                String next = edge.getKey();
                if (!rateFromSrc.containsKey(next)) {
                    rateFromSrc.put(next, rateFromSrc.get(current) * edge.getValue());
                    queue.add(next);
                }
            }
        }

        return "-1";
    }
}
