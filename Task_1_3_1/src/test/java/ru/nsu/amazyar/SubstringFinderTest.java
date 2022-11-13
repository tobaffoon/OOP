package ru.nsu.amazyar;

import java.io.IOException;
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
    public void SmallFileTest(){

    }
}