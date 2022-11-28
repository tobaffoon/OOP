package ru.nsu.amazyar;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Graph implementation on adjacency matrix.
 */
public class AdjacencyMatrixGraph<V, E extends Number> implements Graph<V, E> {

    private final Map<Vertex<V>, Map<Vertex<V>, List<Edge<E>>>> matrix;

    public AdjacencyMatrixGraph() {
        matrix = new HashMap<>();
    }

    @Override
    public Vertex<V> addVertex(V newValue) {
        if (newValue == null) {
            throw new NullPointerException();
        }
        //Check for already existing vertices
        //Needed to ensure "find" method unambiguity
        if (this.findVertex(newValue) != null) {
            throw new IllegalStateException("Vertex ambiguity is not allowed");
        }
        Vertex<V> newVertex = new Vertex<>(newValue);
        matrix.put(newVertex, new HashMap<>());
        for (Vertex<V> friend : this.getVertices()) {
            matrix.get(newVertex).put(friend, new ArrayList<>());   //get Map of other vertices
            matrix.get(friend).put(newVertex, new ArrayList<>());   //and put existing ones there
        }
        return newVertex;
    }

    @Override
    public void removeVertex(Vertex<V> rmVertex) {
        if (rmVertex == null) {
            throw new NullPointerException();
        }
        matrix.remove(rmVertex);
        for (Vertex<V> vertex : this.getVertices()) {
            matrix.get(vertex)
                .remove(rmVertex);    //remove vertex from all lists of other vertices
        }
    }

    @Override
    public List<Vertex<V>> getVertices() {
        return new ArrayList<>(this.matrix.keySet());
    }

    @Override
    public Vertex<V> findVertex(V value) {
        if (value == null) {
            throw new NullPointerException();
        }
        //keySet contains vertices of graph
        for (Vertex<V> v : matrix.keySet()) {
            if (v.getValue().equals(value)) {
                return v;
            }
        }
        return null;
    }

    @Override
    public Edge<E> addEdge(E weight, Vertex<V> from, Vertex<V> to) {
        if (weight == null || from == null || to == null) {
            throw new NullPointerException();
        }
        Edge<E> newEdge = new Edge<>(weight, from, to);
        matrix.get(from).get(to).add(newEdge);
        return newEdge;
    }

    @Override
    public void removeEdge(Edge<E> rmEdge) {
        if (rmEdge == null) {
            throw new NullPointerException();
        }
        matrix.get(rmEdge.vertexFrom()).get(rmEdge.vertexTo()).remove(rmEdge);
    }

    @Override
    public void removeEdge(E weight, Vertex<V> from, Vertex<V> to) {
        if (weight == null || from == null || to == null) {
            throw new NullPointerException();
        }
        matrix.get(from).get(to).removeIf(
            edge -> edge.vertexFrom() == from && edge.vertexTo() == to && edge.getWeight()
                .equals(weight));
    }

    @Override
    public List<Edge<E>> getEdges() {
        //HashMap -> Stream<HashMap> -> Stream<List<Edge>> -> Stream<Edge> -> List<Edge>
        return matrix.values().stream()
            .flatMap(map -> map.values().stream().flatMap(Collection::stream))
            .collect(Collectors.toList());
    }

    @Override
    public int verticesCount() {
        return matrix.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AdjacencyMatrixGraph<V, E> graph1 = (AdjacencyMatrixGraph<V, E>) o;
        if (this.getVertices().size() != graph1.getVertices().size()
            || this.getEdges().size() != graph1.getEdges().size()) {
            return false;
        }

        for (Vertex<V> vert : graph1.getVertices()) {
            if (!this.getVertices().contains(vert)) {
                return false;
            }
        }
        for (Edge<E> edge : graph1.getEdges()) {
            if (!this.getEdges().contains(edge)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(matrix);
    }
}
