import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    // Your tests go here.
    @Test
    public void testequalChars() {
        assertTrue(offByOne.equalChars('a', 'b'));
        assertTrue(offByOne.equalChars('c', 'b'));
        assertTrue(offByOne.equalChars('e', 'f'));
        assertFalse(offByOne.equalChars('b', 'q'));
        assertFalse(offByOne.equalChars('z', 'e'));
        assertFalse(offByOne.equalChars('z', 'A'));
    }
}
