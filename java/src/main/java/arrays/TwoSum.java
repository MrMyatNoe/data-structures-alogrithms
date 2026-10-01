package arrays;

import java.util.HashMap;
import java.util.Map;

public class TwoSum {

    // Time: O(n)  Space: O(n)
    public static int[] solve(int[] nums, int target) {
        Map<Integer, Integer> seen = new HashMap<>(); // value -> index
        for (int i = 0; i < nums.length; i++) {
            var value = nums[i];
            Integer complementIndex = seen.get(target - value);
            if (complementIndex != null) { 
                System.out.printf("FOUND  i=%d value=%d need=%d at index %d%n", i, value, target - value, complementIndex);
                return new int[]{complementIndex, i}; 
            }
            seen.put(value, i);
            System.out.printf("MISS   i=%d value=%d need=%d seen=%s%n", i, value, target - value, seen);
        }
        return new int[]{};
    }
}
