package stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import stream.SecondHighest.Employee;

/**
 * Rules under test: for each department, find the second highest distinct salary.
 * A repeated top salary does not count as second. With fewer than two distinct
 * salaries the department has no second highest (Optional.empty).
 */
class SecondHighestTest {

    // sample

    @Test
    @DisplayName("several departments")
    void severalDepartments() {
        List<Employee> employees = List.of(
                new Employee("Alice", "Engineering", 120000),
                new Employee("Bob", "Engineering", 95000),
                new Employee("Charlie", "Engineering", 110000),
                new Employee("Diana", "Engineering", 110000),
                new Employee("Eve", "HR", 60000),
                new Employee("Frank", "HR", 72000),
                new Employee("Grace", "HR", 65000),
                new Employee("Henry", "Sales", 80000),
                new Employee("Ivy", "Sales", 85000),
                new Employee("Jack", "Finance", 90000));

        Map<String, Optional<Integer>> expected = Map.of(
                "Engineering", Optional.of(110000),
                "Finance", Optional.empty(),
                "HR", Optional.of(65000),
                "Sales", Optional.of(80000));

        assertEquals(expected, SecondHighest.solve(employees));
    }

    // edge cases

    @Test
    @DisplayName("empty input")
    void emptyInput() {
        List<Employee> employees = List.of();

        assertEquals(Map.of(), SecondHighest.solve(employees));
    }

    @Test
    @DisplayName("single employee has no second highest")
    void singleEmployee() {
        List<Employee> employees = List.of(new Employee("Solo", "Ops", 50000));

        assertEquals(Map.of("Ops", Optional.empty()), SecondHighest.solve(employees));
    }

    // duplicates

    @Test
    @DisplayName("duplicate top salary is skipped")
    void duplicateTopSalaryIsSkipped() {
        List<Employee> employees = List.of(
                new Employee("A", "Dev", 100),
                new Employee("B", "Dev", 100),
                new Employee("C", "Dev", 50));

        assertEquals(Map.of("Dev", Optional.of(50)), SecondHighest.solve(employees));
    }

    @Test
    @DisplayName("only one distinct salary")
    void onlyOneDistinctSalary() {
        List<Employee> employees = List.of(
                new Employee("A", "Dev", 100),
                new Employee("B", "Dev", 100));

        assertEquals(Map.of("Dev", Optional.empty()), SecondHighest.solve(employees));
    }

    // negatives

    @Test
    @DisplayName("negative salaries: highest -10, second -20")
    void negativeSalaries() {
        List<Employee> employees = List.of(
                new Employee("A", "Dev", -10),
                new Employee("B", "Dev", -30),
                new Employee("C", "Dev", -20));

        assertEquals(Map.of("Dev", Optional.of(-20)), SecondHighest.solve(employees));
    }
}
