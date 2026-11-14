package duplicate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

public class DuplicatePayment {

    public static List<String> deduplicate(List<String> keys) {
        // Write your code here
        return new LinkedHashSet<>(keys).stream().toList();
    }

    public static void main(String[] args) {
        List<String> keys = List.of("3","6", "0", "9", "3", "6", "2", "4");
        List<String> expected = List.of("3","6", "0", "9", "2", "4");
//        List<String> actual = deduplicate(keys);
        List<String> actual = keys.stream().distinct().toList();

        System.out.println("Actual:   " + actual);
        System.out.println("Expected: " + expected);
        System.out.println(actual.equals(expected) ? "PASS" : "FAIL");
    }
}
