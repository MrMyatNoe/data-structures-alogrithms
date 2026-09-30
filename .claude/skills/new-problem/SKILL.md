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
