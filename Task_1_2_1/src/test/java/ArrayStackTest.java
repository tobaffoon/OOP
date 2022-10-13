import java.util.EmptyStackException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.amazyar.ArrayStack;

class ArrayStackTest {

  ArrayStack<Integer> intStack;
  ArrayStack<String> stringStack;

  @BeforeEach
  public void beforeArrayStackTest() {
    intStack = new ArrayStack<>();
    stringStack = new ArrayStack<>();
  }

  @Test
  public void pushPopTest() {
    String[] initText = {"making", "the mother", "of all", "omelettes", "son", "machines", "nano"};
    stringStack.push("nano");
    stringStack.push("machines");
    stringStack.push("son");
    ArrayStack<String> strTestStack = new ArrayStack<>();
    strTestStack.push("making");
    strTestStack.push("the mother");
    strTestStack.push("of all");
    strTestStack.push("omelettes");
    stringStack.pushStack(strTestStack);
    for (String s : initText) {
      Assertions.assertEquals(s, stringStack.pop());
    }
    stringStack.popStack(0);
    stringStack.push("it's");
    stringStack.push("morbin");
    stringStack.push("time");
    ArrayStack<String> strStackFromPop = stringStack.popStack(2);
    Assertions.assertEquals("time", strStackFromPop.pop());
    Assertions.assertEquals("morbin", strStackFromPop.pop());
  }

  @Test
  public void resizeTest() {
    for (int i = 0; i < 128; i++) {
      intStack.push(i);
    }
    Assertions.assertEquals(128, intStack.getCount());
    intStack.popStack(1);
    Assertions.assertEquals(127, intStack.getCount());
    intStack.popStack(110);
    Assertions.assertEquals(17, intStack.getCount());
    intStack.popStack(15);
    intStack.pop();
    Assertions.assertEquals(1, intStack.getCount());
  }

  @Test
  public void nullPushTest() {
    ArrayStack<String> nullStrArray = null;
    Assertions.assertThrows(NullPointerException.class, () -> stringStack.pushStack(nullStrArray));
    String nullStr = null;
    Assertions.assertThrows(NullPointerException.class, () -> stringStack.push(nullStr));
  }

  @Test
  public void emptyStackTest() {
    Integer[] ints = {2, 6, 23, -1, 0, 89, -9};
    ArrayStack<Integer> intsStack = new ArrayStack<>();
    for (int i : ints) {
      intsStack.push(i);
    }
    intStack.pushStack(intsStack);
    Assertions.assertThrows(IndexOutOfBoundsException.class, () -> intStack.popStack(100));
    intStack.popStack(intStack.getCount());
    Assertions.assertEquals(0, intStack.getCount());
    Assertions.assertThrows(EmptyStackException.class, () -> intStack.pop());
  }
}