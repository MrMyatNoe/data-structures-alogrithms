# Java rules

- Java 21, Maven. No Gradle, no extra dependencies without asking.
- One package per topic (lowercase, no hyphens). One public class per file, PascalCase, with a `public static` entry method (default name `solve`).
- Tests use JUnit 5. Use one `@Test` method per case, each with a `@DisplayName` that says what the case checks.
- `@ParameterizedTest` is allowed when several cases share the same data and differ only in simple inputs and the expected value. Prefer inline `@CsvSource` (or `@ValueSource`) so the cases read top to bottom, and give each row a short trailing comment. Avoid `@MethodSource` unless the arguments cannot be written as literals.
- Keep each test's data inside the test method, as plain `List.of(...)` / literals, so the case reads top to bottom. No shared test-data helper classes and no string-parsing helpers.
- Group cases with short comments (`// edge cases`, `// ties`, ...). Cover the empty input, a single element, duplicates, negatives, and the no-answer case where relevant.
- Use `assertArrayEquals` for arrays. Never compare arrays with `assertEquals`.
- Prefer interfaces on the left (`Map<..> m = new HashMap<>()`).
- Run `mvn test` after every change.
