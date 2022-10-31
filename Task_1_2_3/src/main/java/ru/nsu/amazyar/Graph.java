package ru.nsu.amazyar;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

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

    default <V1, E1 extends Number> boolean equals(Graph<V1, E1> graph1){
        if(graph1 == null){
           return false;
        }
        if(this.getVertices().size() != graph1.getVertices().size()
            || this.getEdges().size() != graph1.getEdges().size()){
            return false;
        }
        HashSet<V> thisVertices =
            (HashSet<V>) this.getVertices().stream().map(Vertex::getValue).collect(Collectors.toSet());
        HashSet<V> graph1Vertices =
            (HashSet<V>) graph1.getVertices().stream().map(Vertex::getValue).collect(Collectors.toSet());
        HashSet<E> thisEdges =
            (HashSet<E>) this.getEdges().stream().map(Edge::getWeight).collect(Collectors.toSet());
        HashSet<E> graph1Edges =
            (HashSet<E>) graph1.getEdges().stream().map(Edge::getWeight).collect(Collectors.toSet());
        return thisVertices.containsAll(graph1Vertices) && graph1Vertices.containsAll(thisVertices)
            && thisEdges.containsAll(graph1Edges) && graph1Edges.containsAll(thisEdges);
    }
}
