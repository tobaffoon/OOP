package ru.nsu.amazyar;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ZFunctionCreatorTest {
    @Test
    public void getZfunctionTest(){
        int[] zFunction = ZFunctionCreator.getZfunction("abacabacaba");
        int[] reference = {11, 0, 1, 0, 7, 0, 1, 0, 3, 0, 1};
        Assertions.assertArrayEquals(reference, zFunction);
    }
}