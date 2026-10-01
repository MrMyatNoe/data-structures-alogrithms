package strings.easy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Method;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ValidAnagramTest {

    static Stream<Arguments> cases() {
        return Stream.of(
            Arguments.of("listen", "silent", true),
            Arguments.of("anagram", "nagaram", true),
            Arguments.of("aabb", "bbaa", true),
            Arguments.of("a", "a", true),
            Arguments.of("", "", true),
            Arguments.of("rat", "car", false),
            Arguments.of("aab", "abb", false),
            Arguments.of("a", "ab", false)
        );
    }

    @BeforeEach
    void logHeader(TestInfo info) {
        System.out.printf("%nstdout | %s > %s > %s%n",
            info.getTestClass().map(Class::getName).orElse("?"),
            info.getTestMethod().map(Method::getName).orElse("?"),
            info.getDisplayName());
    }

    @ParameterizedTest(name = "s=\"{0}\" t=\"{1}\" -> {2}")
    @MethodSource("cases")
    void checksAnagram(String s, String t, boolean expected) {
        assertEquals(expected, ValidAnagram.solve(s, t));
    }
}
