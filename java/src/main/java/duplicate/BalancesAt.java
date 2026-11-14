package duplicate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class BalancesAt {

    public static void main(String[] args) {
        List<Long> times = Arrays.asList(5L, 1L, 9L);
        List<Long> amounts = Arrays.asList(-30L, 100L, 20L);
        List<Long> queries = Arrays.asList(6L, 0L, 9L, 1L);

//        List<Long> times = Arrays.asList(2L, 2L);
//        List<Long> amounts = Arrays.asList(10L, 5L);
//        List<Long> queries = Arrays.asList(2L, 1L);

        List<Long> result = balancesAt(times, amounts, queries);

        System.out.println(result);
    }

    public static List<Long> balancesAt(List<Long> times, List<Long> amounts, List<Long> queries) {
        // Write your code here
        var resultMap = new TreeMap<Long, Long>();
        for (int i = 0; i < times.size(); i++) {
            resultMap.merge(times.get(i), amounts.get(i), Long::sum);
        }
        System.out.println("resultMap : " + resultMap);

        long runningBalance = 0;
        for (Map.Entry<Long, Long> entry : resultMap.entrySet()) {
            runningBalance += entry.getValue();
            entry.setValue(runningBalance);
            System.out.println("entry : " + entry);
        }

        List<Long> results = new ArrayList<>(queries.size());
        for (long t : queries) {
            Map.Entry<Long, Long> latest = resultMap.floorEntry(t);
            System.out.println("latest : " + latest);
            results.add(latest == null ? 0L : latest.getValue());
        }

        return results;
    }
}
