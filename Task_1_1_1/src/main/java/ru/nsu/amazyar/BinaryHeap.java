package ru.nsu.amazyar;

/**
 * Executes heapSort.
 * <p>
 * Sorts only int[]
 * </p>
 */
public class BinaryHeap {
  /**
   * binary Heap, used in HeapSort algorithm.
   */
  private int[] tree;
  /**
   * Number of elements in {@link #tree}.
   */
  private int binLength = 0;

  /**
   * Initialises {@code #tree}.
   *
   * @param arr initial array
   */
  private void createTree(int[] arr) {
    int length = arr.length;
    tree = new int[length];
    this.binLength = length;
    for (int i = 0; i < length; i++) {
      tree[i] = arr[i];
      siftUp(i);
    }
  }

  /**
   * Gets number of elements in Binary Heap.
   *
   * @return number of elements in {@code #tree}
   */

  private int getLength() {
    return this.binLength;
  }

  /**
   * Returns an element from binary heap.
   *
   * @param idx index of desired element in {@code #tree}
   * @return element in idx position in {@code #tree}
   */
  private int getElement(int idx) {
    return this.tree[idx];
  }

  /**
   * Sets the element of binary heap.
   *
   * @param idx      index of desired element in {@code #tree}
   * @param newValue value to assign to desired element
   */
  private void setElement(int idx, int newValue) {
    this.tree[idx] = newValue;
  }

  /**
   * Changes values of two binary heap elements.
   *
   * @param idxA index of the first element in {@code #tree}
   * @param idxB index of the second element in {@code #tree}
   */
  private void swap(int idxA, int idxB) {
    int temp = this.getElement(idxA);
    this.setElement(idxA, this.getElement(idxB));
    this.setElement(idxB, temp);
  }

  /**
   * Ensures that a kid in HeapSort algorithm is smaller than its parent.
   *
   * @param idx idx of a kid
   */
  private void siftUp(int idx) {
    if (idx == 0) {
      return;
    }
    int dadIdx = (idx + 1) / 2 - 1;
    if (this.getElement(idx) < this.getElement(dadIdx)) {
      this.swap(idx, dadIdx);
      siftUp(dadIdx);
    }
  }

  /**
   * Ensures that parents in HeapSort are smaller than their children.
   *
   * @param idx idx of a parent
   */
  private void siftDown(int idx) {
    int smallest = idx;
    int leftChild = 2 * idx + 1;
    int rightChild = 2 * idx + 2;

    if (leftChild < this.getLength() && this.getElement(leftChild) < this.getElement(smallest)) {
      smallest = leftChild;
    }

    if (rightChild < this.getLength() && this.getElement(rightChild) < this.getElement(smallest)) {
      smallest = rightChild;
    }

    if (smallest != idx) {
      this.swap(smallest, idx);
      siftDown(smallest);
    }
  }

  /**
   * Sorts an array using HeapSort.
   *
   * @param arr initial array that becomes sorted after method execution
   */
  public void heapSort(int[] arr) {
    this.createTree(arr);
    int length = this.getLength();
    for (int i = 0; i < length; i++) {
      arr[i] = this.getElement(0);
      this.swap(0, length - i - 1);
      this.binLength--;
      siftDown(0);
    }
  }
}
