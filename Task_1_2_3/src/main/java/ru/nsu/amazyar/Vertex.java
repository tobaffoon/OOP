package ru.nsu.amazyar;

/**
 * Vertex of a graph
 *
 * @param <E> Vertex value type
 */
public class Vertex<E> {

    private E value;

    public Vertex(E value) {
        this.value = value;
    }

    public E getValue() {
        return value;
    }

    public void setValue(E value) {
        this.value = value;
    }
}
