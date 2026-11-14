package dynamic;

import java.util.Arrays;
import java.util.List;

public class MinTransferFee {

    public static void main(String[] args) {
//        List<Integer> sizes = Arrays.asList(1, 5, 10);
//        List<Long> fees = Arrays.asList(3L, 4L, 10L);
//        int amount = 10;

//        List<Integer> sizes = Arrays.asList(3);
//        List<Long> fees = Arrays.asList(5L);
//        int amount = 4;

//        List<Integer> sizes = Arrays.asList(1, 5);
//        List<Long> fees = Arrays.asList(3L, 4L);
//        int amount = 0;

        List<Integer> sizes = Arrays.asList(1);
        List<Long> fees = Arrays.asList(3L);
        int amount = 4;
//
//        List<Integer> sizes = Arrays.asList(2, 3);
//        List<Long> fees = Arrays.asList(1L, 5L);
//        int amount = 7;

        System.out.println(minTransferFee(sizes, fees, amount));
    }

    public static long minTransferFee(List<Integer> sizes, List<Long> fees, int amount) {
        // Write your code here
        long[] dp = new long[amount + 1];
        Arrays.fill(dp, Long.MAX_VALUE);
        dp[0] = 0;

        for (int currentAmount = 1; currentAmount <= amount; currentAmount++) {
            for(int j = 0; j < sizes.size(); j++){
                int size = sizes.get(j);
                long fee = fees.get(j);
                if(size <= currentAmount) {
                    long first = dp[currentAmount];
                    int rest = currentAmount - size;
                    long second = dp[rest];

                    if(dp[rest] != Long.MAX_VALUE) {
                        dp[currentAmount] = Math.min(first, second + fee);
                    }
                }
            }
        }
        long dpAmount = dp[amount];
        return dpAmount == Long.MAX_VALUE ?  -1 : dpAmount;
    }
}
