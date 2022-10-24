package ru.nsu.amazyar;

/**
 * Oriented edge in a graph
 * @param <E> Edge weight value type
 */
public class Edge <E extends Number>{
    private E weight;
    private final Vertex<?> from;
    private final Vertex<?> to;

    public Edge(E weight, Vertex<?> from, Vertex<?> to) {
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
}
