package ru.nsu.amazyar;

import java.util.Objects;

/**
 * Vertex of a graph.
 *
 * @param <V> Vertex value type
 */
public class Vertex<V> {

    private V value;
    //protected to use in sort algorithm
    protected double sortDistance;
    protected boolean wasSorted;

    protected Vertex(V value) {
        this.value = value;
        sortDistance = Double.POSITIVE_INFINITY;
        this.wasSorted = false;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    /**
     * Returns minimal distance from some vertex.
     *
     * @return Distance if sorting occurred
     * @throws IllegalAccessException if there was no sorting
     */
    public double getSortDistance() throws IllegalAccessException {
        if (wasSorted) {
            return sortDistance;
        }
        throw new IllegalAccessException("No sorting occurred");
    }

    /**
     * Compares vertices by their value.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Vertex<?> vertex = (Vertex<?>) o;
        return value.equals(vertex.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
