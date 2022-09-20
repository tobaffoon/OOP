package ru.nsu.amazyar;

import java.util.EmptyStackException;

/**
 * Stack based on an array Stores only Objects, no primitive types allowed
 *
 * @param <E> type of Objects to be stored in stack
 */
public class ArrayStack<E> {

  /**
   * Minimum size of stack Maximum capacity of the stack will never go below this point
   */
  private final int MIN_SIZE = 32;
  /**
   * Capacity of stack Can be enlarged and reduced if needed
   */
  private int cap;
  /**
   * Number of elements in stack
   */
  private int count;
  /**
   * Array of stack Used to store elements in it
   */
  private E[] stack;

  /**
   * Creates array avoiding warnings Uses safe but unknown cast to create array of type E
   *
   * @param length length of created array
   * @return generated array of type E
   */
  @SuppressWarnings("unchecked")
  private E[] generateArray(int length) {
    return (E[]) new Object[length];
  }

  /**
   * Creates empty stack
   */
  public ArrayStack() {
    count = 0;
    cap = MIN_SIZE;
    stack = generateArray(cap);
  }

  /**
   * Creates stack of set length
   *
   * @param minCapacity number of elements that can be stored if stack without resizing
   */
  public ArrayStack(int minCapacity) {
    count = 0;
    cap = Math.max(MIN_SIZE, minCapacity);
    stack = generateArray(cap);
  }

  /**
   * Enlarges or shrinks stack
   * <ul>
   *  <li>If {@link #stack} is too small to accommodate new number of elements it enlarges to include
   *  at least two times the newSize</li>
   *  <li>If {@link #stack} is 4 times bigger than the newSize, it is reasonable to reduce
   *  it, so we shrink it two times, but keep it at least {@link #MIN_SIZE}</li>
   * </ul>
   *
   * @param newSize number of elements we want to be stored in stack
   */
  private void resize(int newSize) {
    if (newSize >= 0) {
      if (newSize > cap) {
        cap = newSize * 2;
        E[] newStack = generateArray(cap);
        System.arraycopy(stack, 0, newStack, 0, count);
        stack = newStack;
      } else if (cap / 2 >= MIN_SIZE && newSize < cap / 4) {
        cap /= 2;
        E[] newStack = generateArray(cap);
        System.arraycopy(stack, 0, newStack, 0, newSize);
        stack = newStack;
      }
    }
  }

  /**
   * Pushes element on stack Maybe enlarges stack with {@link #resize}
   *
   * @param nextElem element being pushed
   */
  public void push(E nextElem) {
    resize(count + 1);
    stack[count++] = nextElem;
  }

  /**
   * Pushes an array on stack Catches null Array
   *
   * @param nextElems array of elements being pushed
   */
  public void pushStack(E[] nextElems) {
    if (nextElems == null) {
      throw new NullPointerException();
    }

    resize(count + nextElems.length);
    for (E elem : nextElems) {
      stack[count++] = elem;
    }
  }

  /**
   * Pops an element from stack May shrink stack with {@link #resize} Checks for empty array call
   *
   * @return top element on stack
   */
  public E pop() {
    if (count == 0) {
      throw new EmptyStackException();
    }
    resize(count - 1);
    return stack[--count];
  }

  /**
   * Pops a subStack from stack Most likely causes stack to shrink with {@link #resize} Checks for
   * popping a subStack bigger that original stack
   *
   * @param length length of subStack
   * @return subStack of set length
   */
  public ArrayStack<E> popStack(int length) {
    if (length > count) {
      throw new IndexOutOfBoundsException();
    }
    ArrayStack<E> resStack = new ArrayStack<>(length);
    System.arraycopy(this.stack, count - length, resStack.stack, 0, length);
    resStack.count = length;
    resize(count - length);
    count -= length;
    return resStack;
  }

  /**
   * Gets number of elements
   *
   * @return number of elements on stack
   */
  public int getCount() {
    return count;
  }
}
