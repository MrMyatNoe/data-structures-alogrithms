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
