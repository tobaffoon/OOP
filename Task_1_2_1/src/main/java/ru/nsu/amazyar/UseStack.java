package ru.nsu.amazyar;

/**
 * Class with main method Executes some {@link ArrayStack} methods
 */
public class UseStack {

  public static void main(String[] args) {
    ArrayStack<Integer> intStack = new ArrayStack<>();
    intStack.push(2);
    intStack.push(7);
    Integer[] ints = {2, -9, 0, 8, 12, -4};
    intStack.pushStack(ints);
    System.out.println(intStack.pop());
    System.out.println(intStack.pop());
    ArrayStack<Integer> stackFromPop = intStack.popStack(3);
    for (int i = 0; i < 3; i++) {
      System.out.print(stackFromPop.pop() + " ");
    }
    System.out.println();
    System.out.println(intStack.getCount());
  }
}
