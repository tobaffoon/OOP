package ru.nsu.amazyar;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.Stack;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Ordinary tree generic collection.
 *
 * @param <E> type of elements of tree
 */
public class Tree<E> implements Iterable<E> {

  /**
   * Root-node of tree.
   */
  private E value;
  private Tree<E> parent;
  private ArrayList<Tree<E>> children;
  private int size;

  private boolean sizeModified = false;

  private long modCount = 0;

  /**
   * Constructor for empty tree.
   */
  public Tree() {
    value = null;
    parent = null;
    children = new ArrayList<>();
    size = 0;
  }

  /**
   * Constructor for singleton tree.
   *
   * @param nodeValue Value of the only element
   */
  public Tree(E nodeValue) {
    if (nodeValue == null) {
      throw new NullPointerException();
    }
    value = nodeValue;
    parent = null;
    children = new ArrayList<>();
    size = 1;
  }

  /**
   * Changes value of a node.
   *
   * @param node  Node whose value will change
   * @param value New value of the node
   */
  public void set(Tree<E> node, E value) {
    if (node == null || value == null) {
      throw new NullPointerException();
    }

    ++modCount;
    node.value = value;
  }

  /**
   * Changes value of a node by index.
   *
   * @param index    Index of a node whose value will change
   * @param newValue New value of the node
   */
  public void set(int index, E newValue) {
    this.checkElementIndex(index);
    breadthFirstSearchIterator it = bfsIterator();
    for (int i = 0; i <= index; i++) {
      it.next();
    }
    it.set(newValue);
  }

  /**
   * Add child to parentNode.
   *
   * @param parentNode Node to which we add child
   * @param e          Value of new node
   * @return Created node
   */
  public Tree<E> add(Tree<E> parentNode, E e) {
    if (parentNode == null || e == null) {
      throw new NullPointerException();
    } else {
      sizeModified = true;
      ++modCount;
      Tree<E> newChild = new Tree<>(e);
      newChild.parent = parentNode;
      parentNode.children.add(newChild);
      return newChild;
    }
  }

  /**
   * Add child to root.
   *
   * @param e Value of new node
   * @return Created node
   */
  public Tree<E> add(E e) {
    if (this.isEmpty()) {
      value = e;
      return this;
    } else {
      return add(this, e);
    }
  }

  /**
   * Makes Tree empty.
   */
  public void clear() {
    value = null;
    parent = null;
    children = new ArrayList<>();
    size = 0;
    ++modCount;
  }

  private boolean isElementIndex(int index) {
    return index >= 0 && index < this.size();
  }

  private void checkElementIndex(int index) {
    if (!isElementIndex(index)) {
      throw new IndexOutOfBoundsException();
    }
  }

  /**
   * Get size of tree.
   *
   * @return size of tree
   */
  public int size() {
    checkEmptyTree();
    if (sizeModified) {
      sizeModified = false;
      size = updateSize();
      return size;
    } else {
      return size;
    }
  }

  private int updateSize() {
    if (this.children.isEmpty()) {
      return 1;
    } else {
      int subTreeSize = 0;
      for (Tree<E> child : children) {
        subTreeSize += child.updateSize();
      }
      return 1 + subTreeSize;
    }
  }

  /**
   * Check if tree is empty.
   *
   * @return true if tree is empty, false if it is not
   */
  public boolean isEmpty() {
    return value == null;
  }

  /**
   * Check if node is a leaf
   * @return True if node is leaf, false otherwise
   */
  public boolean isLeaf(){
    return this.children.isEmpty() && this.value != null;
  }

  private void checkEmptyTree() {
    if (isEmpty()) {
      throw new IllegalStateException();
    }
  }

  public E getValue(){
    checkEmptyTree();
    return this.value;
  }

  /**
   * Check if tree contains object.
   *
   * @param o Object to look for
   * @return true if o is in tree, false otherwise
   */
  public boolean contains(Object o) {
    if (o == null) {
      return false;
    }

    for (E e : this) {
      if (o.equals(e)) {
        return true;
      }
    }
    return false;
  }

  private void reassignChildren(Tree<E> oldParent, Tree<E> newParent) {
    /* NOTE: child isn't removed from children list of oldParent because reassignChildren is called
     * only when oldParent is removed from the tree, so it is not needed to modify it
     */
    for (Tree<E> child : oldParent.children) {
      newParent.children.add(child);
      child.parent = newParent;
    }
  }

  /**
   * Remove element by index.
   *
   * @param index index of element
   * @return value of remove element
   */
  public E remove(int index) {
    this.checkElementIndex(index);
    Iterator<E> it = iterator();
    E returnValue;
    for (int i = 0; i < index; i++) {
      it.next();
    }
    returnValue = it.next();
    it.remove();
    return returnValue;
  }

  /**
   * Remove specific element from tree. If this element is root, then one of its child becomes root,
   * this means that child's value and child's children is assigned to root. Tree becomes empty if
   * no children. If this element isn't root, its children are assigned to element's parent
   *
   * @param rmNode Node to be removed
   * @return removed node
   */
  public E remove(Tree<E> rmNode) {
    if (rmNode == null) {
      throw new NullPointerException();
    }

    ++modCount;
    sizeModified = true;
    if (rmNode == this) {
      //delete first child from children list and make it root
      if (!rmNode.children.isEmpty()) {
        Tree<E> newRoot = rmNode.children.remove(0);
        rmNode.value = newRoot.value;
        reassignChildren(newRoot, rmNode);
      } else {
        rmNode.clear();
      }
    } else {
      rmNode.parent.children.remove(rmNode);
      if (rmNode.children != null) {
        reassignChildren(rmNode, rmNode.parent);
      }
    }

    return rmNode.value;
  }

  /**
   * BFS iterator as simple iterator.
   *
   * @return iterator
   */
  public Iterator<E> iterator() {
    checkEmptyTree();
    return new breadthFirstSearchIterator();
  }

  public Iterator<Tree<E>> treeIterator() {
    checkEmptyTree();
    return new bfsTreeIterator();
  }

  /**
   * BFS iterator.
   *
   * @return iterator
   */
  public breadthFirstSearchIterator bfsIterator() {
    checkEmptyTree();
    return new breadthFirstSearchIterator();
  }

  /**
   * DFS iterator as simple iterator.
   *
   * @return iterator
   */
  public depthFirstSearchIterator dfsIterator() {
    checkEmptyTree();
    return new depthFirstSearchIterator();
  }

  /**
   * Iterator using Breadth First Search.
   */
  public class breadthFirstSearchIterator implements Iterator<E> {

    private Tree<E> lastRet;
    private final LinkedList<Tree<E>> queue;
    private long expectedModCount;

    /**
     * Constructor initiating BFS.
     */
    public breadthFirstSearchIterator() {
      lastRet = null;
      queue = new LinkedList<>();
      queue.add(Tree.this);
      expectedModCount = Tree.this.modCount;
    }

    /**
     * Checks if any element left.
     */
    public boolean hasNext() {
      return !queue.isEmpty();
    }

    /**
     * Return next element. Also adds its child to the queue
     *
     * @return next element
     */


    @SuppressWarnings("null")
    public E next() {
      checkForComodification();
      if (!this.hasNext()) {
        throw new NoSuchElementException();
      } else {
        lastRet = queue.poll();
        this.addChildren(lastRet);
        return lastRet.value;
      }
    }

    /**
     * Remove last element. If {@link #next} has not been called before,
     * {@code IllegalStateException} is thrown
     */
    public void remove() {
      checkForComodification();
      if (lastRet == null) {
        throw new IllegalStateException();
      }

      Tree.this.remove(lastRet);
      lastRet = null;
      ++expectedModCount;
    }

    /**
     * Change value of last node.
     *
     * @param value New Value
     */
    public void set(E value) {
      checkForComodification();
      if (lastRet == null) {
        throw new IllegalStateException();
      }

      Tree.this.set(lastRet, value);
      ++expectedModCount;
    }


    /**
     * Adds a child of last returned node. If next has not been called before,
     * {@code IllegalStateException} is thrown
     *
     * @param newValue Value of new child
     */
    public void add(E newValue) {
      checkForComodification();
      if (lastRet == null) {
        throw new IllegalStateException();
      }

      Tree<E> newNode = Tree.this.add(lastRet, newValue);
      queue.add(newNode); //add last because last returned node added its children in the end
      lastRet = null;
      ++expectedModCount;
    }

    private void addChildren(Tree<E> node) {
      queue.addAll(node.children);
    }

    private void checkForComodification() {
      if (expectedModCount != Tree.this.modCount) {
        throw new ConcurrentModificationException();
      }
    }
  }

  /**
   * Iterator on nodes of tree using Breadth First Search.
   */
  public class bfsTreeIterator implements Iterator<Tree<E>> {

    private Tree<E> lastRet;
    private final LinkedList<Tree<E>> queue;
    private long expectedModCount;

    /**
     * Constructor initiating BFS.
     */
    public bfsTreeIterator() {
      lastRet = null;
      queue = new LinkedList<>();
      queue.add(Tree.this);
      expectedModCount = Tree.this.modCount;
    }

    /**
     * Checks if any element left.
     */
    public boolean hasNext() {
      return !queue.isEmpty();
    }

    /**
     * Return next element. Also adds its child to the queue
     *
     * @return next element
     */


    @SuppressWarnings("null")
    public Tree<E> next() {
      checkForComodification();
      if (!this.hasNext()) {
        throw new NoSuchElementException();
      } else {
        lastRet = queue.poll();
        this.addChildren(lastRet);
        return lastRet;
      }
    }

    /**
     * Remove last element. If {@link #next} has not been called before,
     * {@code IllegalStateException} is thrown
     */
    public void remove() {
      checkForComodification();
      if (lastRet == null) {
        throw new IllegalStateException();
      }

      Tree.this.remove(lastRet);
      lastRet = null;
      ++expectedModCount;
    }

    private void addChildren(Tree<E> node) {
      queue.addAll(node.children);
    }

    private void checkForComodification() {
      if (expectedModCount != Tree.this.modCount) {
        throw new ConcurrentModificationException();
      }
    }
  }

  /**
   * Iterator using Depth First Search.
   */
  public class depthFirstSearchIterator implements Iterator<E> {

    private Tree<E> lastRet;
    private final Stack<Tree<E>> stack;
    private long expectedModCount;

    /**
     * Constructor initiating DFS.
     */
    public depthFirstSearchIterator() {
      stack = new Stack<>();
      stack.push(Tree.this);
      lastRet = null;
      expectedModCount = Tree.this.modCount;
    }

    /**
     * Checks if any element left.
     */
    public boolean hasNext() {
      return !stack.empty();
    }

    /**
     * Return next element. Also adds its child to the stack in reverse order, to preserve left
     * orientation
     *
     * @return next element
     */
    public E next() {
      checkForComodification();
      if (!this.hasNext()) {
        throw new NoSuchElementException();
      } else {
        Tree<E> currentNode = stack.pop();

        for (int i = currentNode.children.size() - 1; i >= 0; i--) {
          stack.push(currentNode.children.get(i));
        }

        lastRet = currentNode;
        return currentNode.value;
      }
    }

    /**
     * Remove last element. If next has not been called before, {@code IllegalStateException} is
     * thrown
     */
    public void remove() {
      checkForComodification();
      if (lastRet == null) {
        throw new IllegalStateException();
      }

      Tree.this.remove(lastRet);
      lastRet = null;
      ++expectedModCount;
    }

    /**
     * Change value of last node.
     *
     * @param value New value
     */
    public void set(E value) {
      checkForComodification();
      if (lastRet == null) {
        throw new IllegalStateException();
      }

      Tree.this.set(lastRet, value);
      ++expectedModCount;
    }

    /**
     * Adds a child of last node. If next has not been called before, {@code IllegalStateException}
     * is thrown
     *
     * @param newValue Value of new child
     */
    public void add(E newValue) {
      checkForComodification();
      if (lastRet == null) {
        throw new IllegalStateException();
      }

      Tree<E> newNode = Tree.this.add(lastRet, newValue);
      stack.push(newNode);
      lastRet = null;
      ++expectedModCount;
    }

    private void checkForComodification() {
      if (expectedModCount != Tree.this.modCount) {
        throw new ConcurrentModificationException();
      }
    }
  }


  /**
   * Check if this and o are equal. Compares nodes' value at same index. Index does not represent
   * position
   */
  public boolean equals(Object o) {
    if (o == null) {
      return false;
    }
    if (o.getClass() != this.getClass()) {
      return false;
    }

    @SuppressWarnings("unchecked") Tree<E> oTree = (Tree<E>) o;

    Iterator<E> it1 = this.iterator();
    Iterator<E> it2 = oTree.iterator();

    while (it1.hasNext() && it2.hasNext()) {
      if (!it1.next().equals(it2.next())) {
        return false;
      }
    }
    return !it1.hasNext() && !it2.hasNext();
  }

  /**
   * Tree spliterator for stream. Uses BFS-ordered array as source
   *
   * @return Spliterator of {@code this}
   */
  public Spliterator<E> spliterator() {
    Iterator<E> iterator = Tree.this.iterator();
    ArrayList<E> nodeOrder = new ArrayList<>();
    while (iterator.hasNext()) {
      nodeOrder.add(iterator.next());
    }

    class TreeSpliterator implements Spliterator<E> {

      private int start;
      private final int end;

      public TreeSpliterator() {
        super();
        start = 0;
        end = nodeOrder.size();
      }

      private TreeSpliterator(int start, int end) {
        super();
        this.start = start;
        this.end = end;
      }

      public boolean tryAdvance(Consumer<? super E> action) {
        if (start >= end) {
          return false;
        }

        E nextElement = nodeOrder.get(start);
        action.accept(nextElement);
        start++;
        return true;
      }

      public long estimateSize() {
        return end - start;
      }

      public Spliterator<E> trySplit() {
        if (end - start < 2) {
          return null;
        }
        int middle = (end - start) / 2;
        Spliterator<E> secondSplit = new TreeSpliterator(start, middle);
        this.start = middle;
        return secondSplit;
      }

      public int characteristics() {
        return this.IMMUTABLE | this.NONNULL | this.SIZED | this.SUBSIZED;
      }
    }
    return new TreeSpliterator();
  }

  public Stream<E> stream() {
    return StreamSupport.stream(this.spliterator(), false);
  }

  /**
   * Tree spliterator for stream. Uses BFS-ordered array as source
   *
   * @return Spliterator of {@code this}
   */
  public Spliterator<Tree<E>> treeSpliterator() {
    Iterator<Tree<E>> treeIterator = this.treeIterator();
    ArrayList<Tree<E>> nodeOrder = new ArrayList<>();
    while (treeIterator.hasNext()) {
      nodeOrder.add(treeIterator.next());
    }

    class TreeSpliterator implements Spliterator<Tree<E>> {

      private int start;
      private final int end;

      public TreeSpliterator() {
        super();
        start = 0;
        end = nodeOrder.size();
      }

      private TreeSpliterator(int start, int end) {
        super();
        this.start = start;
        this.end = end;
      }

      public boolean tryAdvance(Consumer<? super Tree<E>> action) {
        if (start >= end) {
          return false;
        }

        Tree<E> nextNode = nodeOrder.get(start);
        action.accept(nextNode);
        start++;
        return true;
      }

      public long estimateSize() {
        return end - start;
      }

      public Spliterator<Tree<E>> trySplit() {
        if (end - start < 2) {
          return null;
        }
        int middle = (end - start) / 2;
        Spliterator<Tree<E>> secondSplit = new TreeSpliterator(start, middle);
        this.start = middle;
        return secondSplit;
      }

      public int characteristics() {
        return this.IMMUTABLE | this.NONNULL | this.SIZED | this.SUBSIZED;
      }
    }
    return new TreeSpliterator();
  }

  public Stream<Tree<E>> treeStream() {
    return StreamSupport.stream(this.treeSpliterator(), false);
  }
}
