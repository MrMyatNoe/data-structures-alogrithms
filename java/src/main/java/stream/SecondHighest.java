package stream;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

public final class SecondHighest {

    public record Employee(String name, String department, int salary) {}

    private SecondHighest() {}

    // Time: O(n log n)  Space: O(n)
    public static Map<String, Optional<Integer>> solve(List<Employee> employees) {
        return employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::department,
                        TreeMap::new,
                        Collectors.collectingAndThen(
                                Collectors.mapping(
                                        Employee::salary,
                                        Collectors.toCollection(() -> new TreeSet<>(Collections.reverseOrder()))),
                                salaries -> salaries.stream().skip(1).findFirst())));
    }
}
