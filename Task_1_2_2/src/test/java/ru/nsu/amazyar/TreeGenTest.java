package ru.nsu.amazyar;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TreeGenTest {

  TreeGen<Integer> intTree;
  TreeGen<String> stringTree;

  @BeforeEach
  public void beforeTreeGenTest() {
    intTree = new TreeGen<>();
    stringTree = new TreeGen<>();
  }

  @Test
  public void addSizeSetTest() {
    Assertions.assertThrows(NullPointerException.class, () -> new TreeGen<Double>(null));
    Assertions.assertTrue(intTree.isEmpty());
    intTree.add(1);
    TreeGen<Integer>.Node<Integer> nodeTwo = intTree.add(2);
    intTree.add(4);
    intTree.add(nodeTwo, 3);
    Assertions.assertThrows(NullPointerException.class, () -> intTree.add(null, 2));
    Assertions.assertThrows(NullPointerException.class, () -> intTree.add(nodeTwo, null));
    Assertions.assertThrows(NullPointerException.class, () -> intTree.add(null));
    Assertions.assertFalse(intTree.isEmpty());
    Assertions.assertEquals(4, intTree.size());
    Assertions.assertThrows(IllegalStateException.class, () -> stringTree.size());
    Assertions.assertFalse(intTree.contains(null));
    Assertions.assertFalse(intTree.contains(10));
    Assertions.assertTrue(intTree.contains(3));

    Assertions.assertThrows(NullPointerException.class, () -> intTree.set(null, 2));
    Assertions.assertThrows(NullPointerException.class, () -> intTree.set(nodeTwo, null));
    intTree.set(nodeTwo, -2);
    Assertions.assertTrue(intTree.contains(-2));
  }

  @Test
  public void remove() {
    stringTree.add("Bread");
    TreeGen<String>.Node<String> mayo = stringTree.add("Mayo");
    TreeGen<String>.Node<String> sausage = stringTree.add(mayo, "Sausage");
    stringTree.add(mayo, "Onion");
    stringTree.add(sausage, "Cheese");
    stringTree.add("Bread");

    Assertions.assertThrows(IndexOutOfBoundsException.class,
        () -> stringTree.remove(stringTree.size()));
    Assertions.assertEquals("Sausage", stringTree.remove(3));

    TreeGen<String> testSandwich = new TreeGen<>("Bread");
    TreeGen<String>.Node<String> mayo1 = testSandwich.add("Mayo");
    testSandwich.add(mayo1, "Onion");
    testSandwich.add(mayo1, "Cheese");
    testSandwich.add("Bread");

    Assertions.assertEquals(testSandwich, stringTree);
    testSandwich.clear();
    Assertions.assertTrue(testSandwich.isEmpty());

    Assertions.assertEquals(mayo, stringTree.remove(mayo));

    testSandwich.add("Bread");
    testSandwich.add("Bread");
    testSandwich.add("Onion");
    testSandwich.add("Cheese");

    Assertions.assertEquals(testSandwich, stringTree);
    testSandwich.clear();

    testSandwich.add("Bread");
    testSandwich.add("Onion");
    testSandwich.add("Cheese");
    stringTree.remove(0);

    Assertions.assertEquals(testSandwich, stringTree);

    testSandwich.clear();
    testSandwich.remove(testSandwich.add("Air"));
    Assertions.assertTrue(testSandwich.isEmpty());

    Assertions.assertThrows(NullPointerException.class, () -> stringTree.remove(null));
  }

  private int forEachSum = 0;

  @Test
  public void forEachTest() {
    int sum = 0;
    for (int i = 0; i < 15; i++) {
      intTree.add(i);
      sum += i;
    }
    intTree.forEach(x -> this.forEachSum += x);
    Assertions.assertEquals(sum, forEachSum);
    intTree.clear();
    Assertions.assertThrows(IllegalStateException.class,
        () -> intTree.forEach(x -> this.forEachSum -= x));
    Assertions.assertEquals(sum, forEachSum);
  }

  @Test
  public void breadFirstSearchIteratorTest() {
    stringTree.add("Water");
    TreeGen<String>.Node<String> fire = stringTree.add("Fire");
    stringTree.add("Air");
    TreeGen<String>.Node<String> earth = stringTree.add(fire, "Earth");
    stringTree.add(earth, "Magnets don't work");
    Iterator<String> it = stringTree.iterator();

    //iterate
    Assertions.assertThrows(IllegalStateException.class, it::remove);
    Assertions.assertEquals("Water", it.next());
    Assertions.assertEquals("Fire", it.next());
    it.remove();
    Assertions.assertThrows(IllegalStateException.class, it::remove);
    Assertions.assertEquals("Air", it.next());
    Assertions.assertEquals("Earth", it.next());
    Assertions.assertEquals("Magnets don't work", it.next());
    Assertions.assertThrows(NoSuchElementException.class, it::next);
    it.remove();

    //iterator add
    TreeGen<String>.breadthFirstSearchIterator bfsIt = stringTree.bfsIterator();
    Assertions.assertEquals("Water", bfsIt.next());
    Assertions.assertEquals("Air", bfsIt.next());
    Assertions.assertThrows(NullPointerException.class, () -> bfsIt.add(null));
    bfsIt.add("Math");
    Assertions.assertThrows(IllegalStateException.class, () -> bfsIt.add("Metal"));
    Assertions.assertThrows(IllegalStateException.class, () -> bfsIt.set("Meth"));
    Assertions.assertTrue(stringTree.contains("Math"));
    Assertions.assertEquals("Earth", bfsIt.next());
    bfsIt.set("Ground");
    Assertions.assertTrue(stringTree.contains("Ground"));
    Assertions.assertEquals("Math", bfsIt.next());
    Assertions.assertThrows(NoSuchElementException.class, bfsIt::next);
    stringTree.set(2, "Klein");
    Assertions.assertTrue(stringTree.contains("Klein"));
    Assertions.assertFalse(stringTree.contains("Ground"));
  }

  @Test
  public void depthFirstSearchIteratorTest() {
    stringTree.add("Water");
    TreeGen<String>.Node<String> fire = stringTree.add("Fire");
    stringTree.add("Air");
    TreeGen<String>.Node<String> earth = stringTree.add(fire, "Earth");
    stringTree.add(earth, "Magnets don't work");
    TreeGen<String>.depthFirstSearchIterator dfsIt = stringTree.dfsIterator();

    //iterate
    Assertions.assertThrows(IllegalStateException.class, dfsIt::remove);
    Assertions.assertEquals("Water", dfsIt.next());
    Assertions.assertEquals("Fire", dfsIt.next());
    dfsIt.remove();
    Assertions.assertThrows(IllegalStateException.class, dfsIt::remove);
    Assertions.assertEquals("Earth", dfsIt.next());
    Assertions.assertEquals("Magnets don't work", dfsIt.next());
    Assertions.assertEquals("Air", dfsIt.next());
    Assertions.assertThrows(NoSuchElementException.class, dfsIt::next);
    dfsIt.remove();

    //iterator add
    TreeGen<String>.depthFirstSearchIterator dfsIt1 = stringTree.dfsIterator();
    Assertions.assertEquals("Water", dfsIt1.next());
    Assertions.assertEquals("Earth", dfsIt1.next());
    Assertions.assertThrows(NullPointerException.class, () -> dfsIt1.add(null));
    dfsIt1.add("Math");
    Assertions.assertThrows(IllegalStateException.class, () -> dfsIt1.add("Metal"));
    Assertions.assertThrows(IllegalStateException.class, () -> dfsIt1.set("Meth"));
    Assertions.assertEquals("Math", dfsIt1.next());
    Assertions.assertTrue(stringTree.contains("Math"));
    dfsIt1.set("Ground");
    Assertions.assertTrue(stringTree.contains("Ground"));
    Assertions.assertEquals("Magnets don't work", dfsIt1.next());
    Assertions.assertThrows(NoSuchElementException.class, dfsIt1::next);
    stringTree.set(3, "Klein");
    Assertions.assertTrue(stringTree.contains("Klein"));
    Assertions.assertFalse(stringTree.contains("Ground"));
  }

  @Test
  public void equalsTest() {
    TreeGen<Integer> smallIntTree = new TreeGen<>();
    for (int i = 0; i < 10; i++) {
      intTree.add(i);
      smallIntTree.add(i);
    }
    Assertions.assertEquals(smallIntTree, intTree);
    intTree.add(11);
    smallIntTree.add(12);
    Assertions.assertNotEquals(intTree, null);
    Assertions.assertNotEquals(intTree, "ABC");
    Assertions.assertNotEquals(smallIntTree, intTree);
  }

  @Test
  public void comodificationTest() {
    stringTree.add("A");
    TreeGen<String>.Node<String> nodeB = stringTree.add("B");
    stringTree.add("C");
    stringTree.add(nodeB, "D");

    TreeGen<String>.breadthFirstSearchIterator bfsIt = stringTree.bfsIterator();
    bfsIt.next();
    bfsIt.next();
    stringTree.remove(nodeB);
    Assertions.assertThrows(ConcurrentModificationException.class, bfsIt::remove);
    Assertions.assertThrows(ConcurrentModificationException.class, () -> bfsIt.add("B"));

    TreeGen<String>.depthFirstSearchIterator dfsIt = stringTree.dfsIterator();
    dfsIt.next();
    dfsIt.remove();
    stringTree.add("Z");
    Assertions.assertThrows(ConcurrentModificationException.class, dfsIt::remove);
  }

  @Test
  public void trySplitTest(){
    stringTree.add("A");
    Assertions.assertNull(stringTree.spliterator().trySplit());
    stringTree.add("B");
    stringTree.add("C");

    Spliterator<String> split1 = stringTree.spliterator();
    Spliterator<String> split2 = split1.trySplit();
    for (int i = 0; i < 2; i++) {
      Assertions.assertTrue(split1.tryAdvance(x -> {}));
    }
    Assertions.assertFalse(split1.tryAdvance(x -> {}));
    for (int i = 0; i < 1; i++) {
      Assertions.assertTrue(split2.tryAdvance(x -> {}));
    }
    Assertions.assertFalse(split2.tryAdvance(x -> {}));
  }
  @Test
  public void streamTest() {
    stringTree.add("A");
    TreeGen<String>.Node<String> nodeB = stringTree.add("B");
    stringTree.add("C");
    TreeGen<String>.Node<String> nodeAb = stringTree.add(nodeB, "AB");
    stringTree.add(nodeB, "CB");
    stringTree.add(nodeAb, "BAB");
    stringTree.add(nodeAb, "DAB");

    @SuppressWarnings("SimplifyStreamApiCallChains")
    List<String> list = stringTree.stream()
        .filter(x -> x.contains("B") && !x.equals("B"))
        .sorted()
        .collect(Collectors.toList());

    Assertions.assertEquals(4, list.size());
    Assertions.assertEquals("AB", list.get(0));
    Assertions.assertEquals("BAB", list.get(1));
    Assertions.assertEquals("CB", list.get(2));
    Assertions.assertEquals("DAB", list.get(3));
  }
}