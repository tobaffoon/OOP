package ru.nsu.amazyar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Graph implementation on incidence matrix.
 */
public class IncidenceMatrixGraph<V, E extends Number> implements Graph<V, E> {

    private final Map<Vertex<V>, Map<Edge<E>, EdgeDirection>> matrix;
    //edges is used to getEdges to avoid using any key and getting value (map containing all edges as keys)
    //because keySet may be empty and map methods return Sets which are hard to get single element from
    private final List<Edge<E>> edges;

    private enum EdgeDirection {
        IN,
        OUT,
        LOOP,
        NO_EDGE
    }

    public IncidenceMatrixGraph() {
        matrix = new HashMap<>();
        edges = new ArrayList<>();
    }

    @Override
    public Vertex<V> addVertex(V newValue) throws IllegalStateException {
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

        //Add all existing edges incidence to newVertex
        for (Edge<E> edge : this.getEdges()) {
            matrix.get(newVertex).put(edge, EdgeDirection.NO_EDGE);
        }
        return newVertex;
    }

    @Override
    public void removeVertex(Vertex<V> rmVertex) {
        if (rmVertex == null) {
            throw new NullPointerException();
        }
        matrix.remove(rmVertex);
        //removes entry if edge goes in or out from the vertex
        for (Vertex<V> vertex : this.getVertices()) {
            matrix.get(vertex).entrySet()
                .removeIf(entry -> entry.getValue() != EdgeDirection.NO_EDGE);
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

        //put entry of newEdge incident to no vertex
        matrix.values().forEach(map -> map.put(newEdge, EdgeDirection.NO_EDGE));
        //put (rewrite) loop edge
        if (from == to) {
            matrix.get(from).put(newEdge, EdgeDirection.LOOP);
        }
        //put (rewrite) regular edge
        else {
            matrix.get(from).put(newEdge, EdgeDirection.OUT);
            matrix.get(to).put(newEdge, EdgeDirection.IN);
        }
        edges.add(newEdge);
        return newEdge;
    }

    @Override
    public void removeEdge(Edge<E> rmEdge) {
        if (rmEdge == null) {
            throw new NullPointerException();
        }
        //remove edge from all incidence lists (whole matrix)
        matrix.values().forEach(map -> map.remove(rmEdge));
        edges.remove(rmEdge);
    }

    public void removeEdge(E weight, Vertex<V> from, Vertex<V> to) {
        if (weight == null || from == null || to == null) {
            throw new NullPointerException();
        }
        //find first edge fitting the criteria and remove if it is present
        matrix.get(from).keySet().stream()
            .filter(edge -> edge.vertexFrom() == from
                && edge.vertexTo() == to
                && edge.getWeight().equals(weight))
            .findFirst()
            .ifPresentOrElse(this::removeEdge, () -> {
            });
    }

    @Override
    public List<Edge<E>> getEdges() {
        return this.edges;
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

        IncidenceMatrixGraph<V, E> graph1 = (IncidenceMatrixGraph<V, E>) o;
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
