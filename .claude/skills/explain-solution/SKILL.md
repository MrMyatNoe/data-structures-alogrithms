---
name: explain-solution
description: Write an explanation note for a DSA problem after its solution is finished and its tests pass. Use only when a problem's tests pass or the user says they are done. Never use while the user is still solving.
argument-hint: <topic> <level> <name>
---

Write `notes/<topic>/<level>/<name>.md` explaining a finished solution. Arguments: `$ARGUMENTS` = `<topic> <level> <name>`, where `level` is `easy`, `medium`, or `hard`. If the level is omitted or the solution is not found under it, check whether the problem sits directly under the topic (older layout); if so, write `notes/<topic>/<name>.md` instead.

## Guard (do this first)
1. Find the solution file(s) for `<name>` (see naming in the root CLAUDE.md). If a solution file still throws "not implemented" or is empty, stop: say the solution is not finished and write nothing.
2. Run the tests for that problem (`npx vitest run <camelName>` in `typescript/`, `mvn test -Dtest=<PascalName>Test` in `java/`) for each language that has a solution. If any fail, stop: report the failure and write nothing.
3. If the note file already exists, ask before overwriting.

## Note contents (in this order, `#` title is the problem name)
1. **Problem**: one line.
2. **Approach**: the idea in the user's own solution (read the file; describe what it does, not a different algorithm).
3. **Why it works**: the invariant or reasoning, short.
4. **Complexity**: time and space, with the reason.
5. **Edge cases**: the ones the tests cover, and what each protects against.
6. **Alternative**: one other approach and the trade-off (for example brute force O(n^2), O(1) space).
7. **Real-world: business scenario**: a concrete business rule this pattern solves (for example Two Sum: matching a payment to an invoice total; LRU cache: session or product-page caching).
8. **Real-world: technical / framework use**: where the same structure or algorithm appears in real tech (for example hash maps in Java `HashMap`/`ConcurrentHashMap`, Redis, database hash indexes, JS `Map` for memoization; heaps in job schedulers; graphs in dependency resolution in npm and Maven).

Sections 7 and 8 must be specific and accurate. If there is no honest real-world link, say so in one line instead of inventing one.

Finish by giving the note's path. Do not modify the solution or tests.
