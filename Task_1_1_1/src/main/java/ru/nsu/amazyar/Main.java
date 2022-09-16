package ru.nsu.amazyar;

import java.util.Scanner;

/**
 * Scans and sorts array.
 * Useful only for main method
 */
public class Main {
  /**
   * Scans an integer array, sorts it and prints it out.
   * Uses {@link Scanner} to read from System.in
   * Creates {@link BinaryHeap} and calls {@code BinHeap#heapSort} to sort the array
   * Prints sorted array to System.out
   */
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    int num = sc.nextInt();
    int[] ints = new int[num];
    for (int i = 0; i < num; i++) {
      ints[i] = sc.nextInt();
    }
    BinaryHeap bh = new BinaryHeap();
    bh.heapSort(ints);
    for (Integer integer : ints) {
      System.out.print(integer + " ");
    }
    sc.close();
  }
}
