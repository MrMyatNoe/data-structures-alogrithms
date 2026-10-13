# Second Highest Salary per Department

## Problem

Given a list of employees, return the second-highest **distinct** salary for each department, or nothing if a department has fewer than two distinct salaries.

## Approach

**Java** (`stream/SecondHighest.java`): one stream pipeline.

1. `groupingBy(department, TreeMap::new, ...)` splits employees into groups, with departments sorted A → Z.
2. `mapping(salary, toCollection(() -> new TreeSet<>(reverseOrder())))` keeps only the salaries, in a `TreeSet` ordered from highest to lowest. The set drops duplicates automatically.
3. `collectingAndThen(..., set -> set.stream().skip(1).findFirst())` skips the highest and returns the next one as an `Optional<Integer>`.

**TypeScript** (`arrays/secondHighest.ts`): group with `Map.groupBy`, find the max with `Math.max`, filter out every salary equal to the max (so ties are removed), then take `Math.max` of what is left. An empty remainder gives `undefined`.

For a walkthrough of `groupingBy`, `TreeMap::new`, `mapping` and `collectingAndThen`, see [secondHighest.md](../secondHighest.md).

## Why it works

"Second highest" means the second **distinct** value, so duplicates must be removed before ranking. The reverse-ordered `TreeSet` keeps values unique and sorted, so after skipping the first element, the next one is the second-highest distinct salary. If the set has fewer than two elements, `findFirst()` is empty. In TypeScript, removing all values equal to the max has the same effect: whatever remains is strictly lower than the top salary.

## Complexity

- **Time: O(n log n).** Each salary is inserted into a `TreeSet` (O(log k) per insert for a group of size k).
- **Space: O(n).** The groups and their sets hold up to n salaries in total.

TypeScript: O(n) time per department (two linear passes with `Math.max`) and O(n) space.

## Edge cases

| Test case | What it protects against |
| --- | --- |
| Sample data | The whole flow works with several departments and sorted output. |
| Empty input | Returns an empty result instead of throwing. |
| Single employee | A department with one salary gives "no second highest", not an error. |
| Duplicate top salary (100, 100, 50) | The tie is not counted as the second highest; the answer is 50. |
| All salaries equal | Only one distinct salary exists, so there is no answer. |
| Negative salaries | Ordering is correct for values below zero (no `0` or `-1` sentinel assumptions). |

## Alternative

Track the top two values in two variables while scanning each group once: O(n) time and O(1) extra space per group, with no sorting. The trade-off is more manual code and careful handling of ties (update the second value only when a number is strictly between the two). The stream version is shorter and easier to read, at the cost of O(n log n).

## Real-world: business scenario

HR or payroll reports that rank pay inside a team: "who is the runner-up in salary per department?" is used for pay-band reviews and for checking that the top earner is not an outlier compared with the next level. Sales leaderboards work the same way, such as the second-best monthly revenue per region.

## Real-world: technical / framework use

- **SQL**: the same logic is `DENSE_RANK() OVER (PARTITION BY department ORDER BY salary DESC)` and keeping rank 2. `DENSE_RANK` (not `RANK`) is what treats ties like the distinct-value rule here. When the data is in a database, this is usually better than loading it into Java.
- **Java `TreeSet`/`TreeMap`**: red-black trees that keep keys sorted, the same structure used for sorted indexes and range queries in memory.
- **Java Streams `Collectors.groupingBy`**: the in-memory equivalent of SQL `GROUP BY`, with a downstream collector doing the per-group work.
