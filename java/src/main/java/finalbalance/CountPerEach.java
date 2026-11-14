package finalbalance;

import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

public class CountPerEach {
    public static void main(String[] args) {
        List<String> accountIds = Arrays.asList("0", "1", "3", "1", "6", "9", "10");

        List<String> result = findCount(accountIds);

        System.out.println(result);
    }


    public static List<String> findCount(List<String> accountIds) {
        var dataWithCountMap = new TreeMap<String, Long>();
        accountIds.forEach(accountId -> dataWithCountMap.merge(accountId, 1L, Long::sum));
        return dataWithCountMap.entrySet().stream()
                .map(entry-> entry.getKey() + "=" + entry.getValue())
                .toList();
    }
}
