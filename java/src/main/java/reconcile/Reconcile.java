package reconcile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Reconcile {

    public static void main(String[] args) {
        List<String> ledgerIds = Arrays.asList("c", "a");
        List<Long> ledgerAmounts = Arrays.asList(100L, 50L);

        List<String> bankIds = Arrays.asList("b", "a");
        List<Long> bankAmounts = Arrays.asList(7L, 1000L);

//        List<String> ledgerIds = Arrays.asList("t1", "t1");
//        List<Long> ledgerAmounts = Arrays.asList(1000L, 2000L);
//
//        List<String> bankIds = Arrays.asList("t1");
//        List<Long> bankAmounts = Arrays.asList(3000L);

//        List<String> ledgerIds = List.of();
//        List<Long> ledgerAmounts = Arrays.asList(1000L, 2000L);
//
//        List<String> bankIds = Arrays.asList("y", "x");
//        List<Long> bankAmounts = Arrays.asList(1L, 5L);

//        List<String> ledgerIds = List.of("big");
//        List<Long> ledgerAmounts = Arrays.asList(3000000000000L);
//
//        List<String> bankIds = Arrays.asList("big");
//        List<Long> bankAmounts = Arrays.asList(-1000000000000L);

        List<String> result = reconcile(ledgerIds, ledgerAmounts, bankIds, bankAmounts);

        System.out.println(result);
    }

//    public static List<String> reconcile(List<String> ledgerIds, List<Long> ledgerAmounts,
//                                         List<String> bankIds, List<Long> bankAmounts) {
//        // Write your code here
//        Map<String, Long> diff = new TreeMap<>();
//
//        for (int i = 0; i < ledgerIds.size(); i++) {
//            String ledgerId = ledgerIds.get(i);
//            diff.put(ledgerId, diff.getOrDefault(ledgerId, 0L) + ledgerAmounts.get(i));
//        }
//        for (int i = 0; i < bankIds.size(); i++) {
//            String bankId = bankIds.get(i);
//            diff.put(bankId, diff.getOrDefault(bankId, 0L) - bankAmounts.get(i));
//
//        }
//
//        return diff.entrySet().stream()
//                .filter(data -> data.getValue() != 0)
//                .map(entry -> entry.getKey() + " " + entry.getValue())
//                .toList();
//    }

    public static List<String> reconcile(List<String> ledgerIds, List<Long> ledgerAmounts,
                                         List<String> bankIds, List<Long> bankAmounts) {
        // Write your code here
//        List<String> resultList = new ArrayList<>();
        Map<String, Long> difference  = new TreeMap<>();
        for (int i = 0; i < ledgerIds.size(); i++) {
            String ledgerId = ledgerIds.get(i);
            difference.put(ledgerId, difference.getOrDefault(ledgerId, 0L) + ledgerAmounts.get(i));
        }
//        System.out.println(difference);

        for (int i = 0; i < bankIds.size(); i++) {
            String bankId = bankIds.get(i);
            difference.put(bankId, difference.getOrDefault(bankId, 0L) - bankAmounts.get(i));
        }
//        System.out.println(difference);
//        for(Map.Entry<String, Long> entry: difference.entrySet()) {
//            String data = entry.getKey() + " " + entry.getValue();
//            if(entry.getValue() != 0) {
//                resultList.add(data);
//            }
//
//        }
        return difference.entrySet().stream()
                .filter(entry -> entry.getValue() != 0)
                .map(entry -> entry.getKey() + " " + entry.getValue())
                .toList();
    }
}
