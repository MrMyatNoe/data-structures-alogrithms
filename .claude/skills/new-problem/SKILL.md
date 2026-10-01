---
name: new-problem
description: Scaffold a new DSA practice problem (empty solution stub plus a test file) in TypeScript, Java, or both. Use when the user runs /new-problem or asks to start a new problem.
argument-hint: <topic> <level> <name> [ts|java|both]
disable-model-invocation: true
---

Create the scaffold for one problem. Never write the solution.

Arguments: `$ARGUMENTS` = `<topic> <level> <name> [ts|java|both]`. `level` must be `easy`, `medium`, or `hard`. Language defaults to `both`. If topic, level, or name is missing, ask for it. If level is not one of the three, ask again; do not guess.

## Naming
- TypeScript: folder `typescript/src/<topic>/<level>/` (keep hyphens in the topic, e.g. `linked-list`); files `<camelName>.ts` and `<camelName>.test.ts`; export `function <camelName>`.
- Java: the topic in lowercase with hyphens removed (`linked-list` becomes `linkedlist`); package is `<pkg>.<level>` (e.g. `linkedlist.easy`) and the folder is `<topic-no-hyphens>/<level>/`; files `<PascalName>.java` under `java/src/main/java/<pkg>/<level>/` and `<PascalName>Test.java` under `java/src/test/java/<pkg>/<level>/`; entry method `public static ... solve(...)`.
- Create the topic and level folders if they do not exist.
- If either target file already exists, stop and tell the user. Never overwrite.

## Steps
1. If the user gave the problem signature and examples, use them. Otherwise ask once for the input/output types and 2-3 example cases. Use these as the test rows; if none are given, add one placeholder row marked `// TODO: add cases` and say so.
2. Create the stub. TypeScript stub body: `throw new Error("not implemented");`. Java stub body: `throw new UnsupportedOperationException("not implemented");`. Add the comment `// Time: ?  Space: ?`.
3. Create the test file following the existing twoSum tests under `arrays/` for style (TypeScript: `it.each`, object cases; Java: `@ParameterizedTest` + `@MethodSource`). Include empty-input and no-answer cases where they apply.
4. Confirm the tests fail because the stub throws, not because of a compile or import error: run `npx vitest run <camelName>` in `typescript/` and/or `mvn -q test -Dtest=<PascalName>Test` in `java/`.
5. Report the created paths and the run commands. Do not give hints unless asked.
