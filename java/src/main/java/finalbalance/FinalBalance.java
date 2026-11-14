package finalbalance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public class FinalBalance  {

    public static void main(String[] args) {
//        List<String> accountIds = Arrays.asList("c", "c");
//        List<Long> amounts = Arrays.asList(-10L, 5L);

//        List<String> accountIds = Arrays.asList("a", "a", "b", "a");
//        List<Long> amounts = Arrays.asList(100L, -150L, 50L, -100L);

        List<String> accountIds = Arrays.asList("x");
        List<Long> amounts = Arrays.asList(-10L);

        List<String> result = finalBalances(accountIds, amounts);

        System.out.println(result);
    }

//    public static List<String> finalBalances(List<String> accountIds, List<Long> amounts) {
//        // Write your code here
//        var balances = new TreeMap<String, Long>();
//
//        for(int i = 0; i < accountIds.size(); i++){
//
//            String accountId = accountIds.get(i);
//            long amount = amounts.get(i);
//            long currentBalance = balances.getOrDefault(accountId, 0L);
//            long newBalance = currentBalance + amount;
//
//            if(newBalance >= 0) {
//                balances.put(accountId, newBalance);
//            }
//        }
//        return balances.entrySet().stream()
//                .map(entry -> entry.getKey() + " " + entry.getValue())
//                .toList();
//    }

    public static List<String> finalBalances(List<String> accountIds, List<Long> amounts) {
        // Write your code here
        var balances = new TreeMap<String, Long>();
        for(int i = 0; i < accountIds.size(); i++) {
            String accountId = accountIds.get(i);
            Long amount = amounts.get(i);
            long newBalance = balances.getOrDefault(accountId, 0L) + amount;
            if(newBalance >= 0L) {
                balances.put(accountId, newBalance);
            }

        }
        System.out.println(balances);
        return balances.entrySet().stream()
                .map(entry -> entry.getKey() + " " + entry.getValue())
                .toList();
    }
}
