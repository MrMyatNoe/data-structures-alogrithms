# Data Structures & Algorithms

Practice repo for data structures and algorithms in **TypeScript** and **Java**. Each problem has a solution file and a separate test file, so every attempt gets instant pass/fail feedback.

## Requirements

| Tool | Version used |
|------|--------------|
| Node.js | 22 |
| npm | 10 |
| Java (JDK) | 21 |
| Maven | 3.9 |

## Layout

```
├── CLAUDE.md                 shared rules for Claude Code
├── .claude/skills/           /new-problem, /explain-solution
├── notes/<topic>/<name>.md   explanation notes (written after a solution passes)
├── typescript/
│   ├── rules.md
│   └── src/<topic>/<name>.ts + <name>.test.ts
└── java/
    ├── rules.md
    ├── src/main/java/<topic>/<Name>.java
    └── src/test/java/<topic>/<Name>Test.java
```

Topics are folders such as `arrays`, `linked-list`, `trees`, `graphs` and `dp`, created as needed. In Java the package name has no hyphens (`linked-list` becomes `linkedlist`).

## Running tests

**TypeScript** (from `typescript/`)
```bash
npm install          # first time only
npm test             # run everything
npx vitest run twoSum   # run one problem
npm run test:watch   # re-run on save
npm run typecheck
```

**Java** (from `java/`)
```bash
mvn test                     # run everything
mvn test -Dtest=TwoSumTest   # run one problem
```

## Working with Claude Code

Claude acts as a practice partner: it gives hints first and writes a full solution only when asked.

- `/new-problem <topic> <name> [ts|java|both]` creates an empty stub and a test file.
- `/explain-solution <topic> <name>` runs after a solution passes its tests and writes `notes/<topic>/<name>.md`. The note covers the approach, complexity, edge cases, a business scenario and where the technique appears in real frameworks.

## Sample

`arrays/twoSum` is included in both languages as a working example (hash map, O(n) time and space).
