package arrays;

import java.util.HashMap;
import java.util.Map;

public class TwoSum {

    // Time: O(n)  Space: O(n)
    public static int[] solve(int[] nums, int target) {
        Map<Integer, Integer> seen = new HashMap<>(); // value -> index
        for (int i = 0; i < nums.length; i++) {
            Integer complementIndex = seen.get(target - nums[i]);
            if (complementIndex != null) return new int[]{complementIndex, i};
            seen.put(nums[i], i);
        }
        return new int[]{};
    }
}
