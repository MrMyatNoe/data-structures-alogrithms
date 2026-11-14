package duplicate;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

public class RateLimit {

    public static void main(String[] args) {
        List<Long> times = Arrays.asList(0L, 0L);
        int limit = 1;
        long windowMs = 1000;

        System.out.println(rateLimit(times, limit, windowMs));
    }

    public static List<String> rateLimit(List<Long> times, int limit, long windowMs) {
        // Write your code here
        Deque<Long> allowed = new ArrayDeque<>();
        List<String> resultList = new ArrayList<>();
        for (Long time : times) {
            System.out.println("before " + allowed);

            while (!allowed.isEmpty() && time - allowed.peekFirst() >= windowMs) {
                allowed.pollFirst();
            }

            if (allowed.size() < limit) {
                allowed.addLast(time);
                resultList.add("ALLOW");
            } else {
                resultList.add("REJECT");
            }
            System.out.println("after " + allowed);
        }
        return resultList;
    }
}
