package ru.nsu.amazyar;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HeapSortTest {
  /**
   * main binary heap that we will use.
   */
  private BinaryHeap binaryHeap;

  /**
   * initialize binary heap.
   */
  @BeforeEach
  public void beforeSortTest() {
    binaryHeap = new BinaryHeap();
  }

  /**
   * Passes an empty array.
   * Expects an empty array
   */
  @Test
  public void sortEmptyArray() {
    int[] emptArr = {};
    binaryHeap.heapSort(emptArr);
    Assertions.assertArrayEquals(new int[0], emptArr);
  }

  /**
   * Passes an array consisting of one element.
   * Expects an array consisting of the same element
   */
  @Test
  public void sortOneElem() {
    int[] singleton = {5};
    int[] hardRes = {5};
    binaryHeap.heapSort(singleton);
    Assertions.assertArrayEquals(hardRes, singleton);
  }

  /**
   * Passes sorted array.
   * Expects the same array
   */
  @Test
  public void sortSorted() {
    int[] sortArr = {-4, -3, -2, -1, 0, 1, 2, 3, 4};
    int[] hardRes = {-4, -3, -2, -1, 0, 1, 2, 3, 4};
    binaryHeap.heapSort(sortArr);
    Assertions.assertArrayEquals(hardRes, sortArr);
  }

  /**
   * Passes an array sorted in reversed order.
   * Expects the reversed array
   */
  @Test
  public void sortReversedSorted() {
    int[] reSortArr = {4, 3, 2, 1, 0, -1, -2, -3, -4};
    int[] hardRes = {-4, -3, -2, -1, 0, 1, 2, 3, 4};
    binaryHeap.heapSort(reSortArr);
    Assertions.assertArrayEquals(hardRes, reSortArr);
  }

  /**
   * Passes an array of the same element.
   * Expects the same array
   */
  @Test
  public void sortSameElem() {
    int[] sameElemArr = {-3, -3, -3, -3, -3, -3, -3};
    int[] hardRes = {-3, -3, -3, -3, -3, -3, -3};
    binaryHeap.heapSort(sameElemArr);
    Assertions.assertArrayEquals(sameElemArr, hardRes);
  }

  /**
   * Passes an array of arbitrary numbers.
   * Expects a sorted array
   */
  @Test
  public void sortArbitraryTest() {
    int[] justArr = {0, -2, 8, -10, 1, 2, 0, 2, -11, -10};
    int[] hardRes = {-11, -10, -10, -2, 0, 0, 1, 2, 2, 8};
    binaryHeap.heapSort(justArr);
    Assertions.assertArrayEquals(justArr, hardRes);
  }
}