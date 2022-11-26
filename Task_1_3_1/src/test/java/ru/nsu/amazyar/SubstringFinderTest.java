package ru.nsu.amazyar;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SubstringFinderTest {

    @Test
    public void normalFileTest() {
        try {
            List<Integer> resList =
                SubstringFinder.getAllEntries("seven", "src/test/resources/normalFile.txt");
            int[] reference = {34, 113, 152, 161, 174, 182, 189, 203, 216, 230, 241, 255, 263};
            Assertions.assertArrayEquals(reference,
                resList.stream().mapToInt(Integer::intValue).toArray());
        } catch (IOException ignored) {
        }
    }

    @Test
    public void smallFileTest() {
        try {
            List<Integer> resList =
                SubstringFinder.getAllEntries("understandable", "src/test/resources/smallFile.txt");
            int[] reference = {};
            Assertions.assertArrayEquals(reference,
                resList.stream().mapToInt(Integer::intValue).toArray());
        } catch (IOException ignored) {
        }
    }

    @Test
    public void fileEqualsPattern() {
        try {
            List<Integer> resList =
                SubstringFinder.getAllEntries("standable", "src/test/resources/smallFile.txt");
            int[] reference = {0};
            Assertions.assertArrayEquals(reference,
                resList.stream().mapToInt(Integer::intValue).toArray());
        } catch (IOException ignored) {
        }
    }

    @Test
    public void fileIsPatterns() {
        try {
            List<Integer> resList =
                SubstringFinder.getAllEntries("IAmNotHungry", "src/test/resources/allPatterns.txt");
            int[] reference = {0, 12, 24, 36, 48, 60, 72, 84};
            Assertions.assertArrayEquals(reference,
                resList.stream().mapToInt(Integer::intValue).toArray());
        } catch (IOException ignored) {
        }
    }

    @Test
    public void noMatchTest() {
        try {
            List<Integer> resList =
                SubstringFinder.getAllEntries("eighty", "src/test/resources/normalFile.txt");
            int[] reference = {};
            Assertions.assertArrayEquals(reference,
                resList.stream().mapToInt(Integer::intValue).toArray());
        } catch (IOException ignored) {
        }
    }

    @Test
    public void biggerFileTest() {
        try {
            List<Integer> resList =
                SubstringFinder.getAllEntries("AGAIN", "src/test/resources/ultraFile.txt");
            int[] reference =
                {131, 348, 358, 368, 378, 388, 398, 408, 418, 428, 438, 448, 458, 468, 478, 488,
                    498};
            Assertions.assertArrayEquals(reference,
                resList.stream().mapToInt(Integer::intValue).toArray());
        } catch (IOException ignored) {
        }
    }

    @Test
    public void stringReaderTest() {
        try {
            List<Integer> resList =
                SubstringFinder.getAllEntries("can't",
                    new StringReader("can't read my, can't read my\n"
                        + "No, he can't read my poker face"));
            int[] reference =
                {0, 15, 36};
            Assertions.assertArrayEquals(reference,
                resList.stream().mapToInt(Integer::intValue).toArray());
        } catch (IOException ignored) {
        }
    }

    @Test
    public void emptyPatternTest() {
        Assertions.assertThrows(IllegalStateException.class, () ->
            SubstringFinder.getAllEntries("", new StringReader("what")));
    }
}