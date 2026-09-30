# Java rules

- Java 21, Maven. No Gradle, no extra dependencies without asking.
- One package per topic (lowercase, no hyphens). One public class per file, PascalCase, with a `public static` entry method (default name `solve`).
- Tests use JUnit 5 `@ParameterizedTest` with `@MethodSource` returning `Stream<Arguments>`. Cover the empty input, a single element, duplicates, negatives, and the no-answer case where relevant.
- Use `assertArrayEquals` for arrays. Never compare arrays with `assertEquals`.
- Prefer interfaces on the left (`Map<..> m = new HashMap<>()`).
- Run `mvn test` after every change.
