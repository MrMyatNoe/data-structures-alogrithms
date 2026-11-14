package finalbalance;

import java.util.Arrays;
import java.util.List;

public class BusiestWindow {

    public static void main(String[] args) {
        List<Long> times = Arrays.asList(0L, 30L, 70L);
        List<Long> amounts = Arrays.asList(50L, 20L, 40L);
        long windowSeconds = 60;

        System.out.println(busiestWindow(times, amounts, windowSeconds));
    }

    public static long busiestWindow(List<Long> times, List<Long> amounts, long windowSeconds) {
        // Write your code here
        int left = 0;
        long sum = 0;
        long best = 0;

        for(int i = 0; i < times.size(); i++) {
            Long time = times.get(i);
            sum += amounts.get(i);

            while(time - times.get(left) >= windowSeconds) {
                sum -= amounts.get(left);
                left++;
                System.out.println(sum);
            }
            best = Math.max(best, sum);
        }
        return best;
    }
}
