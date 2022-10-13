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
public class TreeGen<E> implements Iterable<E> {

  /**
   * Root-node of tree.
   */
  private Node<E> root;

  /**
   * Flag to know if {@link #root} size should be updated.
   */
  private boolean sizeModified = false;

  private long modCount = 0;

  /**
   * Constructor for empty tree.
   */
  public TreeGen() {
    root = null;
  }

  /**
   * Constructor for singleton tree.
   *
   * @param rootValue Value of the only element
   */
  public TreeGen(E rootValue) {
    if (rootValue == null) {
      throw new NullPointerException();
    }
    root = new Node<>(rootValue);
  }

  /**
   * Changes value of a node.
   *
   * @param node  Node whose value will change
   * @param value New value of the node
   */
  public void set(Node<E> node, E value) {
    if (node == null || value == null) {
      throw new NullPointerException();
    }

    ++modCount;
    node.rootValue = value;
  }

  /**
   * Changes value of a node by index.
   *
   * @param index Index of a node whose value will change
   * @param value New value of the node
   */
  public void set(int index, E value) {
    this.checkElementIndex(index);
    breadthFirstSearchIterator it = bfsIterator();
    for (int i = 0; i <= index; i++) {
      it.next();
    }
    it.set(value);
  }

  /**
   * Add child to parentNode.
   *
   * @param parentNode Node to which we add child
   * @param e          Value of new node
   * @return Created node
   */
  public Node<E> add(Node<E> parentNode, E e) {
    if (parentNode == null || e == null) {
      throw new NullPointerException();
    } else {
      sizeModified = true;
      ++modCount;
      if (parentNode.children == null) {
        parentNode.children = new ArrayList<>();
      }
      Node<E> newChild = new Node<>(e);
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
  public Node<E> add(E e) {
    if (this.isEmpty()) {
      root = new Node<>(e);
      return root;
    } else {
      return add(root, e);
    }
  }

  /**
   * Makes Tree empty.
   */
  public void clear() {
    root = null;
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
      return root.updateSize();
    } else {
      return root.size;
    }
  }

  /**
   * Check if tree is empty.
   *
   * @return true if tree is empty, false if it is not
   */
  public boolean isEmpty() {
    return root == null;
  }

  private void checkEmptyTree() {
    if (isEmpty()) {
      throw new IllegalStateException();
    }
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

  private void reassignChildren(Node<E> oldParent, Node<E> newParent) {
    if (newParent.children == null) {
      newParent.children = new ArrayList<>();
    }

    for (Node<E> child : oldParent.children) {
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
    E returnValue = null;
    for (int i = 0; i <= index; i++) {
      returnValue = it.next();
    }
    it.remove();
    return returnValue;
  }

  /**
   * Remove specific element from tree. If this element is root, then one of its child becomes root,
   * tree becomes empty if no children If this element isn't root, its children are assigned to
   * element's parent
   *
   * @param rmNode Node to be removed
   * @return removed node
   */
  public Node<E> remove(Node<E> rmNode) {
    if (rmNode == null) {
      throw new NullPointerException();
    }

    ++modCount;
    sizeModified = true;
    if (rmNode == root) {
      //delete first child from children list and make it root
      if (rmNode.children != null) {
        Node<E> newRoot = rmNode.children.remove(0);
        reassignChildren(rmNode, newRoot);
        root = newRoot;
      } else {
        root = null;
      }
    } else {
      rmNode.parent.children.remove(rmNode);
      if (rmNode.children != null) {
        Node<E> newFather = rmNode.parent;
        reassignChildren(rmNode, newFather);
      }
    }

    return rmNode;
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

    private Node<E> lastRet;
    private final LinkedList<Node<E>> queue;
    private int cursor;
    private long expectedModCount;

    /**
     * Constructor initiating BFS.
     */
    public breadthFirstSearchIterator() {
      lastRet = null;
      queue = new LinkedList<>();
      queue.add(TreeGen.this.root);
      cursor = 0;
      expectedModCount = TreeGen.this.modCount;
    }

    /**
     * Checks if any element left.
     */
    public boolean hasNext() {
      return cursor < queue.size();
    }

    /**
     * Return next element. Also adds its child to the queue
     *
     * @return next element
     */
    public E next() {
      checkForComodification();
      if (!this.hasNext()) {
        throw new NoSuchElementException();
      } else {
        lastRet = queue.get(cursor++);
        this.addChildren(lastRet);
        return lastRet.rootValue;
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

      TreeGen.this.remove(lastRet);
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

      TreeGen.this.set(lastRet, value);
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

      Node<E> newNode = TreeGen.this.add(lastRet, newValue);
      queue.add(newNode); //add last because last returned node added its children in the end
      lastRet = null;
      ++expectedModCount;
    }

    private void addChildren(Node<E> node) {
      if (node.children != null) {
        queue.addAll(node.children);
      }
    }

    private void checkForComodification() {
      if (expectedModCount != TreeGen.this.modCount) {
        throw new ConcurrentModificationException();
      }
    }
  }

  /**
   * Iterator using Depth First Search.
   */
  public class depthFirstSearchIterator implements Iterator<E> {

    private Node<E> lastRet;
    private final Stack<Node<E>> stack;
    private long expectedModCount;

    /**
     * Constructor initiating DFS.
     */
    public depthFirstSearchIterator() {
      stack = new Stack<>();
      stack.push(TreeGen.this.root);
      lastRet = null;
      expectedModCount = TreeGen.this.modCount;
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
        Node<E> currentNode = stack.pop();

        if (currentNode.children != null) {
          for (int i = currentNode.children.size() - 1; i >= 0; i--) {
            stack.push(currentNode.children.get(i));
          }
        }

        lastRet = currentNode;
        return currentNode.rootValue;
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

      TreeGen.this.remove(lastRet);
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

      TreeGen.this.set(lastRet, value);
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

      Node<E> newNode = TreeGen.this.add(lastRet, newValue);
      stack.push(newNode);
      lastRet = null;
      ++expectedModCount;
    }

    private void checkForComodification() {
      if (expectedModCount != TreeGen.this.modCount) {
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

    @SuppressWarnings("unchecked") TreeGen<E> oTree = (TreeGen<E>) o;

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
   * Vertex of tree.
   */
  public class Node<T extends E> {

    private T rootValue;
    private Node<T> parent = null;
    private ArrayList<Node<T>> children = null;
    private int size = 1;

    /**
     * Constructor with node value.
     *
     * @param rootValue Node value
     */
    public Node(T rootValue) {
      this.rootValue = rootValue;
    }

    private int updateSize() {
      if (this.children != null) {
        for (Node<T> child : children) {
          this.size += child.updateSize();
        }
      }
      return this.size;
    }
  }

  /**
   * Tree spliterator for stream. Uses BFS-ordered array as source
   *
   * @return Spliterator of {@code this}
   */
  public Spliterator<E> spliterator() {
    Iterator<E> iterator = TreeGen.this.iterator();
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
        nodeOrder.set(start++, nextElement);
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
}
