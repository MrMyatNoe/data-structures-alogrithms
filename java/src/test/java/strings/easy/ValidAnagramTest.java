package strings.easy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Method;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

class ValidAnagramTest {

    @BeforeEach
    void logHeader(TestInfo info) {
        System.out.printf("%nstdout | %s > %s > %s%n",
            info.getTestClass().map(Class::getName).orElse("?"),
            info.getTestMethod().map(Method::getName).orElse("?"),
            info.getDisplayName());
    }

    // anagrams

    @Test
    @DisplayName("listen and silent")
    void listenAndSilent() {
        assertEquals(true, ValidAnagram.solve("listen", "silent"));
    }

    @Test
    @DisplayName("anagram and nagaram")
    void anagramAndNagaram() {
        assertEquals(true, ValidAnagram.solve("anagram", "nagaram"));
    }

    @Test
    @DisplayName("repeated letters: aabb and bbaa")
    void repeatedLetters() {
        assertEquals(true, ValidAnagram.solve("aabb", "bbaa"));
    }

    // edge cases

    @Test
    @DisplayName("single letter, same")
    void singleLetter() {
        assertEquals(true, ValidAnagram.solve("a", "a"));
    }

    @Test
    @DisplayName("both empty")
    void bothEmpty() {
        assertEquals(true, ValidAnagram.solve("", ""));
    }

    // not anagrams

    @Test
    @DisplayName("different letters: rat and car")
    void differentLetters() {
        assertEquals(false, ValidAnagram.solve("rat", "car"));
    }

    @Test
    @DisplayName("same letters, different counts: aab and abb")
    void differentCounts() {
        assertEquals(false, ValidAnagram.solve("aab", "abb"));
    }

    @Test
    @DisplayName("different lengths: a and ab")
    void differentLengths() {
        assertEquals(false, ValidAnagram.solve("a", "ab"));
    }
}
