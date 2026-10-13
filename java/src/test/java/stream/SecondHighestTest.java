package stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import stream.SecondHighest.Employee;

class SecondHighestTest {

    @Test
    @DisplayName("sample data: one result per department, sorted by department name")
    void sampleData() {
        List<Employee> employees = List.of(
                employee("Alice", "Engineering", 120000),
                employee("Bob", "Engineering", 95000),
                employee("Charlie", "Engineering", 110000),
                employee("Diana", "Engineering", 110000),
                employee("Eve", "HR", 60000),
                employee("Frank", "HR", 72000),
                employee("Grace", "HR", 65000),
                employee("Henry", "Sales", 80000),
                employee("Ivy", "Sales", 85000),
                employee("Jack", "Finance", 90000));

        Map<String, Optional<Integer>> expected = Map.of(
                "Engineering", Optional.of(110000),
                "Finance", Optional.empty(),
                "HR", Optional.of(65000),
                "Sales", Optional.of(80000));

        assertEquals(expected, SecondHighest.solve(employees));
    }

    @Test
    @DisplayName("empty input returns an empty map")
    void emptyInput() {
        assertEquals(Map.of(), SecondHighest.solve(List.of()));
    }

    @Test
    @DisplayName("single employee has no second highest")
    void singleEmployee() {
        List<Employee> employees = List.of(employee("Solo", "Ops", 50000));

        assertEquals(Map.of("Ops", Optional.empty()), SecondHighest.solve(employees));
    }

    @Test
    @DisplayName("duplicate top salary does not count as the second highest")
    void duplicateTopSalary() {
        List<Employee> employees = List.of(
                employee("A", "Dev", 100),
                employee("B", "Dev", 100),
                employee("C", "Dev", 50));

        assertEquals(Map.of("Dev", Optional.of(50)), SecondHighest.solve(employees));
    }

    @Test
    @DisplayName("all salaries equal means no second highest")
    void allSalariesEqual() {
        List<Employee> employees = List.of(
                employee("A", "Dev", 100),
                employee("B", "Dev", 100));

        assertEquals(Map.of("Dev", Optional.empty()), SecondHighest.solve(employees));
    }

    @Test
    @DisplayName("negative salaries are ordered correctly")
    void negativeSalaries() {
        List<Employee> employees = List.of(
                employee("A", "Dev", -10),
                employee("B", "Dev", -30),
                employee("C", "Dev", -20));

        assertEquals(Map.of("Dev", Optional.of(-20)), SecondHighest.solve(employees));
    }

    private static Employee employee(String name, String department, int salary) {
        return new Employee(name, department, salary);
    }
}
