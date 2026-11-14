package dynamic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Rules under test: the smallest number of notes that add up to exactly the
 * amount. Each note value can be used any number of times. If the amount cannot
 * be made, the answer is -1. An amount of 0 needs 0 notes.
 */
class FewestNotesTest {

    // sample

    @Test
    @DisplayName("25 + 5")
    void sample() {
        List<Integer> notes = List.of(5, 10, 25);

        assertEquals(2, FewestNotes.fewestNotes(notes, 30));
    }

    // edge cases

    @Test
    @DisplayName("empty notes, amount 5")
    void emptyNotes() {
        List<Integer> notes = List.of();

        assertEquals(-1, FewestNotes.fewestNotes(notes, 5));
    }

    @Test
    @DisplayName("empty notes, amount 0")
    void emptyNotesAmountZero() {
        List<Integer> notes = List.of();

        assertEquals(0, FewestNotes.fewestNotes(notes, 0));
    }

    @Test
    @DisplayName("amount 0 needs no notes")
    void amountZero() {
        List<Integer> notes = List.of(5);

        assertEquals(0, FewestNotes.fewestNotes(notes, 0));
    }

    @Test
    @DisplayName("single note used 3 times")
    void singleNoteUsedThreeTimes() {
        List<Integer> notes = List.of(5);

        assertEquals(3, FewestNotes.fewestNotes(notes, 15));
    }

    @Test
    @DisplayName("duplicate note values")
    void duplicateNoteValues() {
        List<Integer> notes = List.of(2, 2);

        assertEquals(2, FewestNotes.fewestNotes(notes, 4));
    }

    // no answer

    @Test
    @DisplayName("single note cannot make the amount")
    void singleNoteCannotMakeTheAmount() {
        List<Integer> notes = List.of(3);

        assertEquals(-1, FewestNotes.fewestNotes(notes, 4));
    }

    @Test
    @DisplayName("note 2 cannot make 1")
    void noteTwoCannotMakeOne() {
        List<Integer> notes = List.of(2);

        assertEquals(-1, FewestNotes.fewestNotes(notes, 1));
    }

    @Test
    @DisplayName("every note is bigger than the amount")
    void everyNoteIsBiggerThanTheAmount() {
        List<Integer> notes = List.of(10, 20);

        assertEquals(-1, FewestNotes.fewestNotes(notes, 5));
    }

    // choosing the best combination

    @Test
    @DisplayName("5 + 5 + 1")
    void fiveFiveOne() {
        List<Integer> notes = List.of(1, 2, 5);

        assertEquals(3, FewestNotes.fewestNotes(notes, 11));
    }

    @Test
    @DisplayName("3 + 3 beats greedy 4 + 1 + 1")
    void threeThreeBeatsGreedy() {
        List<Integer> notes = List.of(1, 3, 4);

        assertEquals(2, FewestNotes.fewestNotes(notes, 6));
    }
}
