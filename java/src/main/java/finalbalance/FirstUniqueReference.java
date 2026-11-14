package finalbalance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FirstUniqueReference {

    public static void main(String[] args) {
//        List<String> references = Arrays.asList("A", "B", "A", "C", "B");
//        List<String> references = Arrays.asList("X", "X");
//        List<String> references = new ArrayList<>();
//        List<String> references = Arrays.asList("Q", "R", "R");
        List<String> references = Arrays.asList("Z", "A");
// Expected: Q
// Expected: NONE
// Expected: NONE

        System.out.println(firstUniqueReference(references));
    }

    public static String firstUniqueReference(List<String> references) {
        // Write your code here
        var countMap = new LinkedHashMap<String, Long>();
        for (String reference: references) {
            countMap.merge(reference, 1L, Long::sum);
        }
        for(Map.Entry<String, Long> data: countMap.entrySet()) {
            if(data.getValue() == 1){
                return data.getKey();
            }
        }

        return "NONE";
    }

}
