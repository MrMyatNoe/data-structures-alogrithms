package binarysearch;

import java.util.Arrays;
import java.util.List;

public class MinCapacity {

    public static void main(String[] args) {
        List<Integer> payouts = Arrays.asList(500, 300, 700, 200, 400);

        System.out.println(minCapacity(payouts, 3));                                      // expected 800
//        System.out.println(minCapacity(Arrays.asList(100, 100, 100), 3));                 // expected 100
//        System.out.println(minCapacity(Arrays.asList(100, 100, 100), 1));                 // expected 300
//        System.out.println(minCapacity(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), 5)); // expected 15
//        System.out.println(minCapacity(Arrays.asList(2_000_000_000, 2_000_000_000), 1));  // expected 4000000000
    }

    public static long minCapacity(List<Integer> payouts, int days) {
        long lo = 0, hi = 0;
        for (int p : payouts) {
            lo = Math.max(lo, p);
            hi += p;
        }

        while (lo < hi) {
            long mid = lo + (hi - lo) / 2;
            if (canFinish(payouts, days, mid)) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }
        return lo;
    }

    private static boolean canFinish(List<Integer> payouts, int days, long cap) {
        int used = 1;
        long load = 0;
        for (int p : payouts) {
            if (load + p > cap) {
                used++;
                load = 0;
            }
            load += p;
        }
        return used <= days;
    }
}
