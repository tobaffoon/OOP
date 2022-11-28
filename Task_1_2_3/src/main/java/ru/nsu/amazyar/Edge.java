package ru.nsu.amazyar;

import java.util.Objects;

/**
 * Oriented edge in a graph.
 *
 * @param <E> Edge weight value type
 */
public class Edge<E extends Number> {

    private E weight;
    private final Vertex<?> from;
    private final Vertex<?> to;

    protected Edge(E weight, Vertex<?> from, Vertex<?> to) {
        this.weight = weight;
        this.from = from;
        this.to = to;
    }

    public E getWeight() {
        return weight;
    }

    public void setWeight(E weight) {
        this.weight = weight;
    }

    public Vertex<?> vertexFrom() {
        return from;
    }

    public Vertex<?> vertexTo() {
        return to;
    }

    /**
     * Compares edges by weight and incident vertices.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Edge<?> edge = (Edge<?>) o;
        return weight.equals(edge.weight) && from.equals(edge.from) && to.equals(edge.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(weight, from, to);
    }
}
