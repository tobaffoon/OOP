package ru.nsu.amazyar;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HeapSortTest {
    /**
     * main binary heap that we will use
     */
    private BinHeap binHeap;

    /**
     * initialize binary heap
     */
    @BeforeEach
    void beforeSortTest(){
        binHeap = new BinHeap();
    }

    /**
     * Passes an empty array
     * Expects an empty array
     */
    @Test
    void sortEmptyArray() {
        int[] emptArr = {};
        binHeap.heapSort(emptArr);
        Assertions.assertArrayEquals(new int[0], emptArr);
    }

    /**
     * Passes an array consisting of one element
     * Expects an array consisting of the same element
     */
    @Test
    void sortOneElem() {
        int[] singleton = {5};
        int[] hardRes = {5};
        binHeap.heapSort(singleton);
        Assertions.assertArrayEquals(hardRes, singleton);
    }

    /**
     * Passes sorted array
     * Expects the same array
     */
    @Test
    void sortSorted() {
        int[] sortArr = {-4, -3, -2, -1, 0, 1, 2, 3, 4};
        int[] hardRes = {-4, -3, -2, -1, 0, 1, 2, 3, 4};
        binHeap.heapSort(sortArr);
        Assertions.assertArrayEquals(hardRes, sortArr);
    }

    /**
     * Passes an array sorted in reversed order
     * Expects the reversed array
     */
    @Test
    void sortReversedSorted() {
        int[] reSortArr = {4, 3, 2, 1, 0, -1, -2, -3, -4};
        int[] hardRes = {-4, -3, -2, -1, 0, 1, 2, 3, 4};
        binHeap.heapSort(reSortArr);
        Assertions.assertArrayEquals(hardRes, reSortArr);
    }

    /**
     * Passes an array of the same element
     * Expects the same array
     */
    @Test
    void sortSameElem() {
        int[] sameElemArr = {-3, -3, -3, -3, -3, -3, -3};
        int[] hardRes = {-3, -3, -3, -3, -3, -3, -3};
        binHeap.heapSort(sameElemArr);
        Assertions.assertArrayEquals(sameElemArr, hardRes);
    }

    /**
     * Passes an array of arbitrary numbers
     * Expects a sorted array
     */
    @Test
    void sortArbitraryTest(){
        int[] justArr = {0, -2, 8, -10, 1, 2, 0, 2, -11, -10};
        int[] hardRes = {-11, -10, -10, -2, 0, 0, 1, 2, 2, 8};
        binHeap.heapSort(justArr);
        Assertions.assertArrayEquals(justArr, hardRes);
    }
}