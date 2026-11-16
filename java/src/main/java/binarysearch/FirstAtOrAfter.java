package binarysearch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FirstAtOrAfter {

    public static void main(String[] args) {
        List<Long> times = Arrays.asList(10L, 20L, 20L, 30L);

//        System.out.println(firstAtOrAfter(times, 20));            // expected 1
//        System.out.println(firstAtOrAfter(times, 25));            // expected 3
//        System.out.println(firstAtOrAfter(times, 5));             // expected 0
        System.out.println(firstAtOrAfter(times, 31));            // expected -1
//        System.out.println(firstAtOrAfter(new ArrayList<>(), 5)); // expected -1
    }

//    public static int firstAtOrAfter(List<Long> times, long t) {
//        // Write your code here
//            int low = 0;
//            int high = times.size();
//            while(low < high) {
//                int mid = low + (high - low) / 2;
//                if (times.get(mid) >= t) {
//                    high = mid;
//                } else {
//                    low = mid + 1;
//                }
//            }
//        return low == times.size() ? -1 : low;
//    }

    public static int firstAtOrAfter(List<Long> times, long t) {
        int low = 0;
        int high = times.size();
        while(low < high) {
            int mid = low + (high - low) / 2;
            if(times.get(mid) > t) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        return low == times.size() ? - 1 : low;
    }
}
