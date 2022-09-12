package ru.nsu.amazyar;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class heapsortTest {
    private final BinHeap binHeap = new BinHeap();

    @Test
    void sort_emptyArray() {
        int[] emptArr = {};
        binHeap.heapSort(emptArr);
        Assertions.assertArrayEquals(new int[0], emptArr);
    }
}