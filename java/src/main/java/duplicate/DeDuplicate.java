package duplicate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeDuplicate {

    private static final long DUPLICATE_WINDOW_SECONDS = 60;

    public static void main(String[] args) {
        // Test 1
        List<String> keys = List.of();
        List<Long> timestamps = List.of();
        List<String> expected = List.of();

        List<String> actual = deduplicatePayments(keys, timestamps);

        System.out.println("Actual:   " + actual);
        System.out.println("Expected: " + expected);
        System.out.println(actual.equals(expected) ? "PASS" : "FAIL");
    }

//    public static List<String> deduplicatePayments(List<String> keys, List<Long> timestamps) {
//        // Write your code here
//
//        var result = new ArrayList<String>(keys.size());
//        Map<String, Long> lastUpdated = new HashMap<>();
//        for (int i = 0; i < keys.size(); i++){
//            String key = keys.get(i);
//            Long time = timestamps.get(i);
//            Long last = lastUpdated.get(key);
//            System.out.println(key +  " " + time + " " + last);
//            if(last != null && time - last < 60) {
//                result.add("DUPLICATE");
//            } else {
//                result.add("KEEP");
//                lastUpdated.put(key, time);
//            }
//
//        }
//        return result;
//    }

    public static List<String> deduplicatePayments(List<String> keys, List<Long> timestamps) {
        // Write your code here
        List<String> result = new ArrayList<>();
        Map<String, Long> lastUpdatedAt = new HashMap<>();
        for(int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            Long timestamp = timestamps.get(i);
            Long lastAccepted  = lastUpdatedAt.get(key);

            boolean isDuplicate = lastAccepted != null
                    && timestamp - lastAccepted < DUPLICATE_WINDOW_SECONDS;
            if(isDuplicate){
                result.add("DUPLICATE");
            } else {
                result.add("KEEP");
                lastUpdatedAt.put(key, timestamp);
            }

        }
        return result;
    }
}
