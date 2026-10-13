# Java Stream Collectors — TreeMap, Collectors.mapping, collectingAndThen

This note explains the three collector pieces used in the "second-highest salary per department" problem.

---

## Sample Data

```java
record Employee(String name, String department, int salary) {}

List<Employee> employees = List.of(
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
```

## Full Code

```java
Map<String, Optional<Integer>> result = employees.stream()
    .collect(Collectors.groupingBy(
        Employee::department,                       // ① how to group
        TreeMap::new,                               // ② which Map to use
        Collectors.collectingAndThen(               // ③ collect first, then transform
            Collectors.mapping(Employee::salary,    // ④ extract just the salaries
                               Collectors.toList()),
            salaries -> salaries.stream()
                .distinct()
                .sorted(Comparator.reverseOrder())
                .skip(1)
                .findFirst()
        )
    ));
```

---

## 0. `groupingBy` (the basics)

**Meaning:** splits items into groups.

```java
Collectors.groupingBy(Employee::department)
```

Result:

```
Engineering → [Alice, Bob, Charlie, Diana]
HR          → [Eve, Frank, Grace]
Sales       → [Henry, Ivy]
Finance     → [Jack]
```

`groupingBy` has three forms:

| Form                                | Example                                     |
| ----------------------------------- | ------------------------------------------- |
| key only                            | `groupingBy(key)`                           |
| key + downstream                    | `groupingBy(key, downstream)`               |
| key + Map type + downstream         | `groupingBy(key, TreeMap::new, downstream)` |

A **downstream** collector answers "what do we do inside each group?" `mapping` and `collectingAndThen` are downstream collectors.

---

## 1. `TreeMap::new`

**Meaning:** use a Map that keeps its keys sorted **A → Z**.

### Without it (default = HashMap)

The order is not guaranteed.

```
Sales -> 80000
Engineering -> 110000
HR -> 65000
Finance -> N/A
```

### With it

Always sorted A → Z.

```
Engineering -> 110000
Finance -> N/A
HR -> 65000
Sales -> 80000
```

### Comparison

|                | HashMap (default)       | TreeMap                    |
| -------------- | ----------------------- | -------------------------- |
| Order          | not guaranteed          | A → Z                      |
| Speed          | slightly faster         | slightly slower            |
| Use it when    | order does not matter   | you want sorted output     |

> **Note:** the result (the salaries) does not change. Only the **order** in which it is shown changes.

---

## 2. `Collectors.mapping`

**Meaning:** **transform** each item in a group before collecting it.

```java
Collectors.mapping(Employee::salary, Collectors.toList())
//                 ↑ what to turn each item into   ↑ how to collect
```

### Without it

You get the full Employee objects.

```
Engineering → [Employee(Alice...), Employee(Bob...), ...]
```

### With it

You get just the salary numbers.

```
Engineering → [120000, 95000, 110000, 110000]
```

### How is it different from the stream's `map()`?

They do the same job. The difference is **where** you use them.

|                           | Where it is used                                   |
| ------------------------- | -------------------------------------------------- |
| `stream.map(...)`         | on the whole stream                                |
| `Collectors.mapping(...)` | inside `groupingBy` — once for each group          |

### Another example

```java
// Employee names per department
Collectors.groupingBy(
    Employee::department,
    Collectors.mapping(Employee::name, Collectors.toList())
)
// Engineering → [Alice, Bob, Charlie, Diana]
```

---

## 3. `Collectors.collectingAndThen`

**Meaning:** **collect first, then (and then) transform the result.**

```java
Collectors.collectingAndThen(
    ① how to collect first,
    ② what to do with the collected result
)
```

### ① Collect first

```java
Collectors.mapping(Employee::salary, Collectors.toList())
```

```
Engineering → [120000, 95000, 110000, 110000]
```

### ② Then transform

```java
salaries -> salaries.stream()
    .distinct()
    .sorted(Comparator.reverseOrder())
    .skip(1)
    .findFirst()
```

`salaries` is the list produced by ①.

| Step                                          | Result                            |
| --------------------------------------------- | --------------------------------- |
| Start                                         | `[120000, 95000, 110000, 110000]` |
| `distinct()` — remove duplicates              | `[120000, 95000, 110000]`         |
| `sorted(reverseOrder())` — largest to smallest | `[120000, 110000, 95000]`         |
| `skip(1)` — skip the first                    | `[110000, 95000]`                 |
| `findFirst()` — first of what is left         | `110000` ✅                       |

### Without it vs. with it

```
Without:  Engineering → [120000, 95000, 110000, 110000]   (the list as is)
With:     Engineering → 110000                            (the final answer)
```

### Analogy — shopping

- **①** put the items into a basket (collect)
- **②** pick the 2nd-best item from the basket (transform)

### Another example — taking the latest record

If payroll keeps a salary history, this picks the current salary.

```java
Collectors.groupingBy(
    SalaryRecord::employeeId,
    Collectors.collectingAndThen(
        Collectors.maxBy(Comparator.comparing(SalaryRecord::effectiveDate)),
        latest -> latest.get().baseSalary()
    )
)
```

- ① for each employee, find the record with the latest date
- ② extract only the salary from that record

---

## Summary

| Collector            | One-line meaning              |
| -------------------- | ----------------------------- |
| `groupingBy`         | split into groups             |
| `TreeMap::new`       | keep keys sorted A → Z        |
| `Collectors.mapping` | transform, then collect       |
| `collectingAndThen`  | collect, then transform       |

## Things to watch out for

- **Don't forget `distinct()`** — if two people tie for the top salary, `skip(1)` would return the highest salary again instead of the second-highest.
- **`Optional`** — a department with only one employee has no second salary, so you get `Optional.empty()`. Handle it with `orElse("N/A")`.
- **If you use `BigDecimal`** — `distinct()` treats `115000.00` and `115000` as different values. Normalize them first with `stripTrailingZeros()`.
- **Complexity** — sorting makes this O(n log n). With large data, or in an interview, mention the O(n) approach (track the top two values in variables).
- **If the data lives in a database** — it is more efficient to do this in SQL with `DENSE_RANK() OVER (PARTITION BY department ORDER BY salary DESC)` than to pull everything into Java.
