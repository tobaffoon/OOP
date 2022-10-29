package ru.nsu.amazyar;

import java.util.List;

public interface Graph<V, E extends Number> {
    Vertex<V> addVertex(V newValue);
    void removeVertex(Vertex<V> rmVertex);
    List<Vertex<V>> getVertices();
    Vertex<V> findVertex(V value);

    Edge<E> addEdge(E weight, Vertex<V> from, Vertex<V> to);
    void removeEdge(Edge<E> rmEdge);
    void removeEdge(E weight, Vertex<V> from, Vertex<V> to);

    List<Edge<E>> getEdges();

    int verticesCount();
}
