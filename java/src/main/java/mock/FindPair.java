package mock;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class FindPair {

    public static void main(String[] args) {
        List<Long> payments = Arrays.asList(300L, 300L, 700L);
        long deposit = 1000;

        System.out.println(findPair(payments, deposit));
    }


    static List<Integer> findPair(List<Long> payments, long deposit) {
        // Write your code here
        var seen = new HashMap<Long, Integer>();
        for (int i = 0; i < payments.size(); i ++) {
            Long payment = payments.get(i);
            Integer index = seen.get(deposit - payment);
            if(index != null) {
                return Arrays.asList(index, i);
            }
            seen.putIfAbsent(payment, i);
        }
        return Arrays.asList(-1, -1);
    }
}
