package stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TopSenders {

    public static void main(String[] args) {
//        List<String> senders = Arrays.asList("a", "b", "a", "c", "b");
//        List<Long> amounts = Arrays.asList(300L, 400L, 200L, 200L, 500L);

        List<String> senders = Arrays.asList("y", "x", "z");
        List<Long> amounts = Arrays.asList(100L, 100L, 50L);


//        List<String> senders = List.of("a");
//        List<Long> amounts = List.of(10L);

//        int k = 2;
        int k = 3;
        System.out.println(topSenders(senders, amounts, k));
    }

//    public static List<String> topSenders(List<String> senders, List<Long> amounts, int k) {
//        // Write your code here
//        var tempMap = new HashMap<String, Long>();
//        for (int i = 0; i < senders.size(); i++) {
//            tempMap.merge(senders.get(i), amounts.get(i), Long::sum);
//        }
//
//        return tempMap.entrySet().stream()
//                .sorted(Map.Entry.<String, Long>comparingByValue().reversed()
//                        .thenComparing(Map.Entry::getKey))
//                .limit(k)
//                .map(Map.Entry::getKey)
//                .toList();
//    }

    public static List<String> topSenders(List<String> senders, List<Long> amounts, int k) {
        // Write your code here
        var totalsBySender = new HashMap<String, Long>();
        for(int i = 0; i < senders.size(); i++) {
            totalsBySender.merge(senders.get(i), amounts.get(i), Long::sum);
        }
        System.out.println(totalsBySender);

        return totalsBySender.entrySet().stream()
                .sorted((Map.Entry.<String, Long>comparingByValue()
                        .reversed()
                        .thenComparing(Map.Entry.comparingByKey())))
                .map(entry -> entry.getKey() + " " + entry.getValue())
                .limit(k)
                .toList();
    }
}
