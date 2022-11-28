package ru.nsu.amazyar;

import java.util.List;

/**
 * Most general graph interface.
 *
 * @param <V> Type of object assigned to vertices
 * @param <E> Type of object assigned to edges
 */
public interface Graph<V, E extends Number> {

    /**
     * Add vertex to the graph.
     *
     * @return New Vertex
     * @throws IllegalStateException if vertex with given value already exists in the graph
     */
    Vertex<V> addVertex(V newValue) throws IllegalStateException;

    /**
     * Remove vertex from the graph. If graph doesn't have this vertex nothing happens
     */
    void removeVertex(Vertex<V> rmVertex);

    List<Vertex<V>> getVertices();

    /**
     * Finds vertex with provided value.
     *
     * @return Found vertex or null if it wasn't found
     */
    Vertex<V> findVertex(V value);

    /**
     * Add edge with provided parameters.
     *
     * @return New Edge
     */
    Edge<E> addEdge(E weight, Vertex<V> from, Vertex<V> to);

    /**
     * Remove edge from the graph. If graph doesn't have this edge nothing happens
     */
    void removeEdge(Edge<E> rmEdge);

    void removeEdge(E weight, Vertex<V> from, Vertex<V> to);

    List<Edge<E>> getEdges();

    int verticesCount();
}
