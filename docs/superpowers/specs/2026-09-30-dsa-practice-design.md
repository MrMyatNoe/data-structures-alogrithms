# DSA Practice Repo — Design

Date: 2026-09-30

## Purpose
A personal repo for practicing data structures and algorithms (LeetCode-style problems) in TypeScript and Java. Claude acts as a practice partner, not a solution generator. Success: `npm test` and `mvn test` each run and pass on a sample problem, Claude follows the rules for each language, and `/new-problem` scaffolds a problem in both.

## Local toolchain (verified 2026-09-30)
- Node v22.23.1, npm 10.9.8 (no global `tsc`; TypeScript is a dev dependency)
- Java 21.0.8 LTS (JDK), Maven 3.9.7 (no Gradle)

## Layout
```
data-structures-alogrithms/
├── CLAUDE.md                     the only CLAUDE.md; shared rules + imports the two rules files
├── .gitignore                    node_modules, target
├── .claude/skills/new-problem/SKILL.md
├── .claude/skills/explain-solution/SKILL.md
├── notes/<topic>/<name>.md       written by explain-solution
├── typescript/
│   ├── rules.md                  TypeScript rules
│   ├── package.json              typescript, tsx, vitest
│   ├── tsconfig.json             strict
│   └── src/<topic>/<name>.ts + <name>.test.ts
└── java/
    ├── rules.md                  Java rules
    ├── pom.xml                   Java 21, JUnit 5
    ├── src/main/java/<topic>/<Name>.java
    └── src/test/java/<topic>/<Name>Test.java
```
Topics are folders/packages, e.g. `arrays`, `linked-list`, `trees`, `graphs`, `dp`. Created on demand, not up front.

## Test approach
Each problem has a solution file and a separate test file, using parameterized cases (`it.each` / `@ParameterizedTest`). Run all: `npm test` / `mvn test`. Run one: `npx vitest <name>` / `mvn test -Dtest=<Name>Test`.

## Rules content
- Root `CLAUDE.md` (single file): user is practicing, so give hints first and full solutions only when asked; state time/space complexity in a comment; one problem per file; topic-folder naming; how to run tests in each language. It imports `@typescript/rules.md` and `@java/rules.md`.
- `typescript/rules.md`: strict mode, no `any`, named exports, `it.each` for cases, `npm test`.
- `java/rules.md`: Java 21, one package per topic, `@ParameterizedTest` for cases, `mvn test`.

## Skill: `/new-problem <topic> <name> [ts|java|both]`
Project skill at `.claude/skills/new-problem/SKILL.md`. Creates the empty solution stub and a test file with a placeholder case list in the requested language(s) (default both), following the naming rules above. It does not write the solution. It ends by telling the user the run command. If the user is stuck later, the root rules already define the hint-first behavior; no separate hint skill.

## Skill: `/explain-solution <topic> <name>`
Project skill at `.claude/skills/explain-solution/SKILL.md`. Runs only after a solution is finished, meaning its tests pass. It is not used while the user is still solving.
- **Loading:** the skill's `description` says "use after a problem's tests pass or the user says it's done", so Claude loads it then and not earlier. The root `CLAUDE.md` also says: once tests pass, offer to run `/explain-solution`. Claude never runs it mid-attempt.
- **Output:** writes `notes/<topic>/<name>.md` at the repo root, one note per problem shared by both languages. Sections: problem in one line, approach, why it works, time/space complexity, edge cases covered by the tests, one alternative approach with its trade-off, and a real-world section:
  - **Business scenario:** a concrete business rule the pattern solves (e.g. Two Sum: matching a payment to an invoice total; LRU cache: session or product-page caching).
  - **Technical/framework use:** where the same data structure or algorithm appears in real tech (e.g. hash map in Spring `HashMap`/`ConcurrentHashMap`, Redis lookups, a database hash index, JS `Map` in React state or memoization; heap in job schedulers; graph in dependency resolution such as npm/Maven).
  - Both must be specific and accurate. If no honest real-world link exists, say so instead of inventing one.
- **Guard:** if the tests don't pass or the solution file is still a stub, the skill stops and says so instead of writing a note.

## Scope of initial setup
Config files, root `CLAUDE.md`, two `rules.md` files, `.gitignore`, the `new-problem` and `explain-solution` skills, and one sample problem (Two Sum) in both languages to prove the setup runs. Verify by running `npm test` and `mvn test`. No `git init` (left to the user).

## Out of scope
Hooks, Gradle, CI, linting/formatting, a problem-tracking index, more than one sample problem.
