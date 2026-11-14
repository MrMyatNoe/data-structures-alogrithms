package finalbalance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class DailyLimit {

    public static void main(String[] args) {
        List<String> users = Arrays.asList("a1", "a");
        List<Integer> days = Arrays.asList(1, 11);
        List<Long> amounts = Arrays.asList(800L, 800L);
        long limit = 1000;

//        users = Arrays.asList("u1", "u1");
//        days = Arrays.asList(1, 1);
//        amounts = Arrays.asList(1500L, 400L);
//        limit = 1000;
// Expected: [REJECT, ACCEPT]

        System.out.println(checkDailyLimit(users, days, amounts, limit));
    }

    public static List<String> checkDailyLimit(List<String> users, List<Integer> days,
                                               List<Long> amounts, long limit) {
        // Write your code here
        var dailyUsages = new HashMap<String, Long>();
        List<String> results = new ArrayList<>();
        for(int i = 0; i < users.size(); i++) {
            String key = users.get(i) + ":" +days.get(i);
            long daily = dailyUsages.getOrDefault(key, 0L) + amounts.get(i);
            System.out.println(daily);
            if(daily > limit) {
                results.add("REJECT");
            } else {
                results.add("ACCEPT");
                dailyUsages.put(key, daily);
            }

        }
        System.out.println(dailyUsages);

        return results;
    }


//    public static List<String> checkDailyLimit(List<String> users, List<Integer> days,
//                                               List<Long> amounts, long limit) {
//        // Write your code here
//        Map<String, Long> dailyTotals = new HashMap<>();
//        List<String> returnList = new ArrayList<>(users.size());
//        for (int i = 0; i < users.size(); i++) {
//            String key = users.get(i) + ":" + days.get(i);
//            long newTotal = dailyTotals.getOrDefault(key, 0L) + amounts.get(i);
//            if(newTotal > limit) {
//                returnList.add("REJECT");
//            } else {
//                returnList.add("ACCEPT");
//            }
//        }
//        return returnList;
//    }
}
