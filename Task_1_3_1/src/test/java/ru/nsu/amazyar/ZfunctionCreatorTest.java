package ru.nsu.amazyar;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ZfunctionCreatorTest {

    @Test
    public void getZfunctionTest() {
        int[] zfunction = ZfunctionCreator.getZfunction("abacabacaba");
        int[] reference = {0, 0, 1, 0, 7, 0, 1, 0, 3, 0, 1};
        Assertions.assertArrayEquals(reference, zfunction);
    }
}