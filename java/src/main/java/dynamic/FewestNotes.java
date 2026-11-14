package dynamic;

import java.util.Arrays;
import java.util.List;

public class FewestNotes {

    public static void main(String[] args) {
        List<Integer> notes = Arrays.asList(5, 10, 25);
        int amount = 30;

        System.out.println(fewestNotes(notes, amount));
    }

    public static int fewestNotes(List<Integer> notes, int amount) {
        // Write your code here
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);
        dp[0] = 0;

        for (int i = 1; i <= amount; i++) {
            for (Integer note: notes) {
                if (note <= i) {
                    int first = dp[i];
                    int second = dp[i - note];
                    dp[i] = Math.min(first, second + 1);
                }
            }
        }
        int amountDp = dp[amount];
        return amountDp > amount ? -1 : amountDp;
    }
}
