# DSA Practice Repo Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Set up a TypeScript + Java DSA practice repo with per-language rules, one root `CLAUDE.md`, two project skills, and a working Two Sum sample in each language.

**Architecture:** Two sibling toolchain folders (`typescript/`, `java/`), each self-contained with its own test runner. One root `CLAUDE.md` holds shared rules and imports each folder's `rules.md`. Two project skills under `.claude/skills/` scaffold problems and write post-solution explanation notes to `notes/`.

**Tech Stack:** Node 22 / npm 10 / TypeScript / tsx / Vitest; Java 21 / Maven 3.9 / JUnit 5.

**Spec:** `docs/superpowers/specs/2026-09-30-dsa-practice-design.md`

## Global Constraints

- Root is `D:\BestITSolutions\data-structures-alogrithms` (repo dir name is spelled `alogrithms`; do not rename).
- Local toolchain: Node v22.23.1, npm 10.9.8, Java 21.0.8 LTS, Maven 3.9.7. No global `tsc`, no Gradle.
- TypeScript strict mode, no `any`, named exports, tests via `it.each`, run with `npm test`.
- Java 21, one package per topic (no hyphens in package names), `@ParameterizedTest`, run with `mvn test`.
- Solution and test are separate files: `typescript/src/<topic>/<name>.ts` + `<name>.test.ts`; `java/src/main/java/<topic>/<Name>.java` + `java/src/test/java/<topic>/<Name>Test.java`.
- Exactly one `CLAUDE.md` (root only). Rules live in `typescript/rules.md` and `java/rules.md`, imported by root `CLAUDE.md` via `@typescript/rules.md` and `@java/rules.md`.
- Skills: `.claude/skills/new-problem/SKILL.md` and `.claude/skills/explain-solution/SKILL.md`. No hooks.
- The folder is not a git repo. No `git init`, and plan steps contain no commit commands. If the executor's workflow requires commits or a worktree, record a ledger ruling that the repo is not under git and proceed without.
- Explanation notes go to `notes/<topic>/<name>.md`, only after tests pass, and must include a business scenario and a technical/framework section that are specific and accurate.

## Review Focus

1. Two Sum with no valid pair: returns an empty result, does not throw (pinned in Task 1 and Task 2 tests).
2. Duplicate values (`[3, 3]`, target 6): must not reuse the same index twice (pinned in Tasks 1 and 2).
3. Negative numbers and zero (`[-3, 4, 3, 90]`, target 0): hash-map complement logic must still work (pinned in Tasks 1 and 2).
4. `explain-solution` invoked on a stub or failing solution: must stop and say so, not write a note (pinned by the SKILL.md guard check in Task 4).
5. `new-problem` invoked for a topic like `linked-list`: Java package must be `linkedlist`, folder for TypeScript stays `linked-list` (pinned by the SKILL.md rule check in Task 4).

---

### Task 1: TypeScript project with Two Sum sample

**Files:**
- Create: `typescript/package.json`, `typescript/tsconfig.json`
- Create: `typescript/src/arrays/twoSum.test.ts`, `typescript/src/arrays/twoSum.ts`

**Interfaces:**
- Consumes: nothing.
- Produces: `export function twoSum(nums: number[], target: number): number[]` in `typescript/src/arrays/twoSum.ts`; `npm test` in `typescript/` runs Vitest once (`vitest run`).

- [ ] **Step 1: Create the project and install dev dependencies**

Run (PowerShell, from `D:\BestITSolutions\data-structures-alogrithms`):
```powershell
New-Item -ItemType Directory -Force typescript/src/arrays | Out-Null
Set-Location typescript
npm init -y
npm install -D typescript tsx vitest @types/node
```
Expected: `package.json` and `node_modules/` exist; npm exits 0.

- [ ] **Step 2: Set package.json scripts and module type**

Edit `typescript/package.json` so it contains these fields (keep the dependency versions npm wrote; remove `main` if present):
```json
{
  "name": "dsa-typescript",
  "version": "1.0.0",
  "private": true,
  "type": "module",
  "scripts": {
    "test": "vitest run",
    "test:watch": "vitest",
    "typecheck": "tsc --noEmit"
  }
}
```

- [ ] **Step 3: Create tsconfig.json**

`typescript/tsconfig.json`:
```json
{
  "compilerOptions": {
    "target": "ES2022",
    "module": "ESNext",
    "moduleResolution": "Bundler",
    "strict": true,
    "noUncheckedIndexedAccess": true,
    "noEmit": true,
    "skipLibCheck": true,
    "types": ["node"]
  },
  "include": ["src"]
}
```

- [ ] **Step 4: Write the failing test**

`typescript/src/arrays/twoSum.test.ts`:
```ts
import { describe, it, expect } from "vitest";
import { twoSum } from "./twoSum";

describe("twoSum", () => {
  it.each([
    { nums: [2, 7, 11, 15], target: 9, expected: [0, 1] },
    { nums: [3, 2, 4], target: 6, expected: [1, 2] },
    { nums: [3, 3], target: 6, expected: [0, 1] },
    { nums: [-3, 4, 3, 90], target: 0, expected: [0, 2] },
    { nums: [1, 2, 3], target: 100, expected: [] },
    { nums: [], target: 5, expected: [] },
  ])("nums=$nums target=$target -> $expected", ({ nums, target, expected }) => {
    expect(twoSum(nums, target)).toEqual(expected);
  });
});
```

- [ ] **Step 5: Run test to verify it fails**

Run: `npm test` (in `typescript/`)
Expected: FAIL, cannot resolve `./twoSum` (module not found).

- [ ] **Step 6: Write the implementation**

`typescript/src/arrays/twoSum.ts`:
```ts
// Time: O(n)  Space: O(n)
export function twoSum(nums: number[], target: number): number[] {
  const seen = new Map<number, number>(); // value -> index
  for (let i = 0; i < nums.length; i++) {
    const value = nums[i]!;
    const complementIndex = seen.get(target - value);
    if (complementIndex !== undefined) return [complementIndex, i];
    seen.set(value, i);
  }
  return [];
}
```

- [ ] **Step 7: Run tests and typecheck**

Run: `npm test; npm run typecheck`
Expected: `6 passed`; typecheck exits 0 with no output.

---

### Task 2: Java project with Two Sum sample

**Files:**
- Create: `java/pom.xml`
- Create: `java/src/test/java/arrays/TwoSumTest.java`, `java/src/main/java/arrays/TwoSum.java`

**Interfaces:**
- Consumes: nothing.
- Produces: `public static int[] TwoSum.solve(int[] nums, int target)` in package `arrays`; `mvn test` in `java/` runs JUnit 5.

- [ ] **Step 1: Create pom.xml**

`java/pom.xml`:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <groupId>dsa</groupId>
  <artifactId>dsa-java</artifactId>
  <version>1.0.0</version>

  <properties>
    <maven.compiler.release>21</maven.compiler.release>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <junit.version>5.11.4</junit.version>
  </properties>

  <dependencies>
    <dependency>
      <groupId>org.junit.jupiter</groupId>
      <artifactId>junit-jupiter</artifactId>
      <version>${junit.version}</version>
      <scope>test</scope>
    </dependency>
  </dependencies>

  <build>
    <plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-surefire-plugin</artifactId>
        <version>3.5.2</version>
      </plugin>
    </plugins>
  </build>
</project>
```

- [ ] **Step 2: Write the failing test**

`java/src/test/java/arrays/TwoSumTest.java`:
```java
package arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class TwoSumTest {

    static Stream<Arguments> cases() {
        return Stream.of(
            Arguments.of(new int[]{2, 7, 11, 15}, 9, new int[]{0, 1}),
            Arguments.of(new int[]{3, 2, 4}, 6, new int[]{1, 2}),
            Arguments.of(new int[]{3, 3}, 6, new int[]{0, 1}),
            Arguments.of(new int[]{-3, 4, 3, 90}, 0, new int[]{0, 2}),
            Arguments.of(new int[]{1, 2, 3}, 100, new int[]{}),
            Arguments.of(new int[]{}, 5, new int[]{})
        );
    }

    @ParameterizedTest
    @MethodSource("cases")
    void findsIndices(int[] nums, int target, int[] expected) {
        assertArrayEquals(expected, TwoSum.solve(nums, target));
    }
}
```

- [ ] **Step 3: Run test to verify it fails**

Run (from `java/`): `mvn -q test`
Expected: BUILD FAILURE, compilation error `cannot find symbol ... TwoSum`. (First run downloads dependencies; needs network.)

- [ ] **Step 4: Write the implementation**

`java/src/main/java/arrays/TwoSum.java`:
```java
package arrays;

import java.util.HashMap;
import java.util.Map;

public class TwoSum {

    // Time: O(n)  Space: O(n)
    public static int[] solve(int[] nums, int target) {
        Map<Integer, Integer> seen = new HashMap<>(); // value -> index
        for (int i = 0; i < nums.length; i++) {
            Integer complementIndex = seen.get(target - nums[i]);
            if (complementIndex != null) return new int[]{complementIndex, i};
            seen.put(nums[i], i);
        }
        return new int[]{};
    }
}
```

- [ ] **Step 5: Run tests**

Run (from `java/`): `mvn test`
Expected: `Tests run: 6, Failures: 0, Errors: 0` and `BUILD SUCCESS`.

---

### Task 3: Root CLAUDE.md, rules files, .gitignore

**Files:**
- Create: `CLAUDE.md`, `.gitignore`, `typescript/rules.md`, `java/rules.md`

**Interfaces:**
- Consumes: the file paths and run commands from Tasks 1 and 2.
- Produces: root `CLAUDE.md` importing `@typescript/rules.md` and `@java/rules.md`; the practice-partner behavior that Task 4's skills rely on ("once tests pass, offer `/explain-solution`").

- [ ] **Step 1: Write root CLAUDE.md**

`CLAUDE.md`:
```markdown
# DSA Practice

Data structures and algorithms practice in TypeScript (`typescript/`) and Java (`java/`).
The user is practicing. Claude is a practice partner, not a solution generator.

## How to help
- Give a hint first. Escalate: nudge, then approach outline, then pseudocode. Write a full solution only when the user explicitly asks for it.
- Never overwrite a solution the user is working on unless asked.
- State time and space complexity in a comment above each finished solution (`// Time: O(n)  Space: O(n)`).
- Once a problem's tests pass, offer to run `/explain-solution <topic> <name>`. Do not run it while the user is still solving, or if tests fail.

## Layout
- One problem per file. Solution and test are separate files.
- Topic folders: `arrays`, `linked-list`, `trees`, `graphs`, `dp`, and so on. Create on demand.
- TypeScript: `typescript/src/<topic>/<name>.ts` and `<name>.test.ts` (camelCase names).
- Java: `java/src/main/java/<topic>/<Name>.java` and `java/src/test/java/<topic>/<Name>Test.java` (PascalCase; package has no hyphens, e.g. `linked-list` becomes `linkedlist`).
- Explanation notes: `notes/<topic>/<name>.md`, shared by both languages.

## Running tests
- TypeScript (in `typescript/`): `npm test`; one problem: `npx vitest run <name>`; watch: `npm run test:watch`.
- Java (in `java/`): `mvn test`; one problem: `mvn test -Dtest=<Name>Test`.

## Skills
- `/new-problem <topic> <name> [ts|java|both]`: scaffold stub + test file.
- `/explain-solution <topic> <name>`: write the explanation note after tests pass.

## Language rules
@typescript/rules.md
@java/rules.md
```

- [ ] **Step 2: Write typescript/rules.md**

`typescript/rules.md`:
```markdown
# TypeScript rules

- Node 22, TypeScript strict mode. No `any`; use `unknown` or precise types.
- Named exports only, no default exports. One solution function per file.
- Tests use Vitest with `it.each` and object cases (`{ input, expected }`). Cover the empty input, a single element, duplicates, negatives, and the no-answer case where relevant.
- Prefer `Map`/`Set` over plain objects for hash lookups.
- Run `npm test` after every change and `npm run typecheck` before calling a problem done.
- Do not add dependencies without asking.
```

- [ ] **Step 3: Write java/rules.md**

`java/rules.md`:
```markdown
# Java rules

- Java 21, Maven. No Gradle, no extra dependencies without asking.
- One package per topic (lowercase, no hyphens). One public class per file, PascalCase, with a `public static` entry method (default name `solve`).
- Tests use JUnit 5 `@ParameterizedTest` with `@MethodSource` returning `Stream<Arguments>`. Cover the empty input, a single element, duplicates, negatives, and the no-answer case where relevant.
- Use `assertArrayEquals` for arrays. Never compare arrays with `assertEquals`.
- Prefer interfaces on the left (`Map<..> m = new HashMap<>()`).
- Run `mvn test` after every change.
```

- [ ] **Step 4: Write .gitignore**

`.gitignore`:
```
node_modules/
target/
.superpowers/
```

- [ ] **Step 5: Verify structure**

Run (from repo root): `Get-ChildItem CLAUDE.md, typescript/rules.md, java/rules.md, .gitignore | Select-Object Name; (Get-ChildItem -Recurse -Filter CLAUDE.md -File | Where-Object { $_.FullName -notmatch 'node_modules' }).Count`
Expected: all four files listed; the CLAUDE.md count is `1`.

---

### Task 4: `new-problem` and `explain-solution` skills

**Files:**
- Create: `.claude/skills/new-problem/SKILL.md`, `.claude/skills/explain-solution/SKILL.md`

**Interfaces:**
- Consumes: the layout, naming, and run commands from Task 3's `CLAUDE.md`; sample files from Tasks 1-2 as the pattern for stubs and tests.
- Produces: `/new-problem <topic> <name> [ts|java|both]` and `/explain-solution <topic> <name>`; notes at `notes/<topic>/<name>.md`.

- [ ] **Step 1: Write new-problem skill**

`.claude/skills/new-problem/SKILL.md`:
````markdown
---
name: new-problem
description: Scaffold a new DSA practice problem (empty solution stub plus a test file) in TypeScript, Java, or both. Use when the user runs /new-problem or asks to start a new problem.
argument-hint: <topic> <name> [ts|java|both]
disable-model-invocation: true
---

Create the scaffold for one problem. Never write the solution.

Arguments: `$ARGUMENTS` = `<topic> <name> [ts|java|both]`. Language defaults to `both`. If topic or name is missing, ask for it.

## Naming
- TypeScript: folder `typescript/src/<topic>/` (keep hyphens, e.g. `linked-list`); files `<camelName>.ts` and `<camelName>.test.ts`; export `function <camelName>`.
- Java: package and folder are the topic in lowercase with hyphens removed (`linked-list` becomes `linkedlist`); files `<PascalName>.java` under `java/src/main/java/<pkg>/` and `<PascalName>Test.java` under `java/src/test/java/<pkg>/`; entry method `public static ... solve(...)`.
- If either target file already exists, stop and tell the user. Never overwrite.

## Steps
1. If the user gave the problem signature and examples, use them. Otherwise ask once for the input/output types and 2-3 example cases. Use these as the test rows; if none are given, add one placeholder row marked `// TODO: add cases` and say so.
2. Create the stub. TypeScript stub body: `throw new Error("not implemented");`. Java stub body: `throw new UnsupportedOperationException("not implemented");`. Add the comment `// Time: ?  Space: ?`.
3. Create the test file following `typescript/src/arrays/twoSum.test.ts` (`it.each`, object cases) and `java/src/test/java/arrays/TwoSumTest.java` (`@ParameterizedTest` + `@MethodSource`). Include empty-input and no-answer cases where they apply.
4. Confirm the tests fail because the stub throws, not because of a compile or import error: run `npx vitest run <camelName>` in `typescript/` and/or `mvn -q test -Dtest=<PascalName>Test` in `java/`.
5. Report the created paths and the run commands. Do not give hints unless asked.
````

- [ ] **Step 2: Write explain-solution skill**

`.claude/skills/explain-solution/SKILL.md`:
````markdown
---
name: explain-solution
description: Write an explanation note for a DSA problem after its solution is finished and its tests pass. Use only when a problem's tests pass or the user says they are done. Never use while the user is still solving.
argument-hint: <topic> <name>
---

Write `notes/<topic>/<name>.md` explaining a finished solution. Arguments: `$ARGUMENTS` = `<topic> <name>`.

## Guard (do this first)
1. Find the solution file(s) for `<name>` (see naming in the root CLAUDE.md). If a solution file still throws "not implemented" or is empty, stop: say the solution is not finished and write nothing.
2. Run the tests for that problem (`npx vitest run <camelName>` in `typescript/`, `mvn test -Dtest=<PascalName>Test` in `java/`) for each language that has a solution. If any fail, stop: report the failure and write nothing.
3. If `notes/<topic>/<name>.md` exists, ask before overwriting.

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
````

- [ ] **Step 3: Verify the skills**

Run (from repo root):
```powershell
Select-String -Path .claude/skills/*/SKILL.md -Pattern '^name:|^description:' | Select-Object Path, Line
Select-String -Path .claude/skills/explain-solution/SKILL.md -Pattern 'Guard','not implemented','business scenario','technical / framework' | Select-Object LineNumber, Line
Select-String -Path .claude/skills/new-problem/SKILL.md -Pattern 'linkedlist','Never overwrite' | Select-Object LineNumber, Line
```
Expected: two skills each with `name:` and `description:`; the explain-solution matches include the Guard, the stub check, and both real-world sections; the new-problem matches include the `linkedlist` rule and `Never overwrite`.

- [ ] **Step 4: End-to-end check**

Run both suites once more from a clean state:
```powershell
Set-Location typescript; npm test; Set-Location ..\java; mvn test; Set-Location ..
```
Expected: TypeScript `6 passed`; Java `Tests run: 6, Failures: 0, Errors: 0`, `BUILD SUCCESS`.
