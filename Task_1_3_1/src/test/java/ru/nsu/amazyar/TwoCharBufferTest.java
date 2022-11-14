package ru.nsu.amazyar;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TwoCharBufferTest {

    @Test
    public void bufferTest() {
        TwoCharBuffer twoBuffer = new TwoCharBuffer(3);
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> twoBuffer.get(-2));
        for (int i = 0; i < 6; i++) {
            twoBuffer.set(i, (char) (i + 1 + '0'));
        }
        Assertions.assertEquals('2', twoBuffer.get(1));
        Assertions.assertEquals('5', twoBuffer.get(4));

        twoBuffer.switchBuffers();
        Assertions.assertEquals('5', twoBuffer.get(1));
        Assertions.assertEquals('5', twoBuffer.getFirstBuffer()[1]);
        for (int i = 3; i < 6; i++) {
            twoBuffer.set(i, (char) (i + 4 + '0'));
        }
        Assertions.assertEquals('8', twoBuffer.get(4));
        Assertions.assertEquals('8', twoBuffer.getSecondBuffer()[1]);
    }
}