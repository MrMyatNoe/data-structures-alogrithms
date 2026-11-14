package stream;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

public class LongestFailStreak {

    public static void main(String[] args) {
        List<String> users = Arrays.asList("u1", "u2", "u1", "u1", "u2", "u1");
        List<String> statuses = Arrays.asList("FAIL", "FAIL", "FAIL", "OK", "FAIL", "FAIL");

//        List<String> users = Arrays.asList("a");
//        List<String>  statuses = Arrays.asList("OK");

//        List<String> users = Arrays.asList("b", "a", "b", "b");
//        List<String> statuses = Arrays.asList("FAIL", "FAIL", "FAIL", "FAIL");
// Expected: b 3
// Expected: NONE

        System.out.println(longestFailStreak(users, statuses));
    }

    public static String longestFailStreak(List<String> users, List<String> statuses) {
        // Write your code here
        var currentStreak = new LinkedHashMap<String, Integer>();
        String bestUser = null;
        int bestStreak = 0;
        for(int i = 0; i < users.size(); i++) {
            String user = users.get(i);
            String status = statuses.get(i);

            if("FAIL".equals(status)){
                int streak = currentStreak.merge(user, 1, Integer::sum);
                boolean longer = streak > bestStreak;
                boolean tieButSmallerName = streak == bestStreak && user.compareTo(Objects.requireNonNull(bestUser)) < 0;

                if (longer || tieButSmallerName) {
                    bestStreak = streak;
                    bestUser = user;
                }

            } else {
                currentStreak.put(user, 0);
            }

        }
        return bestUser == null ? "NONE" : bestUser + " " + bestStreak;
    }

}
