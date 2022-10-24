package ru.nsu.amazyar;

import java.util.List;

public interface Graph<V, E extends Number>{
    //TODO check if Vertex or Edge is in graph before doing anything
    Vertex<V> addVertex(V newValue);
    void removeVertex(Vertex<V> rmVertex);
    List<Vertex<V>> getVertices();

    Edge<E> addEdge(E newValue, Vertex<V> from, Vertex<V> to);
    void removeEdge(Edge<E> rmEdge);

    List<Edge<E>> getEdges();

    int verticesCount();
}
