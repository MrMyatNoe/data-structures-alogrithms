    package stream;

    import java.util.Comparator;
    import java.util.List;
    import java.util.Map;
    import java.util.Optional;
    import java.util.TreeMap;
    import java.util.stream.Collectors;

    public class SecondHighest {

        static List<Employee> employees = List.of(
                new Employee("Alice",   "Engineering", 120000),
                new Employee("Bob",     "Engineering",  95000),
                new Employee("Charlie", "Engineering", 110000),
                new Employee("Diana",   "Engineering", 110000),
                new Employee("Eve",     "HR",           60000),
                new Employee("Frank",   "HR",           72000),
                new Employee("Grace",   "HR",           65000),
                new Employee("Henry",   "Sales",        80000),
                new Employee("Ivy",     "Sales",        85000),
                new Employee("Jack",    "Finance",      90000)
        );

        public static void main(String[] args) {
            Map<String, Optional<Integer>> secondHighests = employees.stream()
                    .collect(Collectors.groupingBy
                            (Employee::department,
                                    TreeMap::new,
                                    Collectors.collectingAndThen(
                                            Collectors.mapping(Employee::salary,
                                                    Collectors.toList()),
                                            salaries -> salaries.stream()
                                                    .distinct()
                                                    .sorted(Comparator.reverseOrder())
                                                    .skip(1)
                                                    .findFirst()
                                    )

                    ));

            secondHighests.forEach((dept, salary) -> {
                System.out.println(dept + " -> " + salary.map(String::valueOf).orElse("N/A"));
            });
        }

        record Employee(String name, String department, int salary) {}

    }
