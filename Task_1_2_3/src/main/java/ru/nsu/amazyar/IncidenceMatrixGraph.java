package ru.nsu.amazyar;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

public class IncidenceMatrixGraph<V, E extends Number> implements Graph<V, E> {

    private final Map<Vertex<V>, Map<Edge<E>, EdgeDirection>> matrix;

    private enum EdgeDirection {
        IN,
        OUT,
        LOOP,
        NO_EDGE
    }

    public IncidenceMatrixGraph() {
        matrix = new HashMap<>();
    }

    @Override
    public Vertex<V> addVertex(@NotNull V newValue) {
        Vertex<V> newVertex = new Vertex<>(newValue);
        matrix.put(newVertex, new HashMap<>());
        for (Edge<E> edge : this.getEdges()) {
            matrix.get(newVertex).put(edge, EdgeDirection.NO_EDGE);
        }
        return newVertex;
    }

    @Override
    public void removeVertex(@NotNull Vertex<V> rmVertex) {
        matrix.remove(rmVertex);
        for (Vertex<V> vertex : this.getVertices()) {
            matrix.get(vertex).keySet()
                .removeIf(edge -> edge.vertexFrom() == rmVertex || edge.vertexTo() == rmVertex);
        }
    }

    @Override
    public List<Vertex<V>> getVertices() {
        return this.matrix.keySet().stream().collect(Collectors.toList());
    }

    @Override
    public Vertex<V> findVertex(@NotNull V value) {
        for (Vertex<V> v : matrix.keySet()) {
            if (v.getValue().equals(value)) {
                return v;
            }
        }
        return null;
    }

    @Override
    public Edge<E> addEdge(@NotNull E weight, Vertex<V> from, Vertex<V> to) {
        Edge<E> newEdge = new Edge<>(weight, from, to);
        matrix.values().forEach(map -> map.put(newEdge, EdgeDirection.NO_EDGE));
        if (from == to) {
            matrix.get(from).put(newEdge, EdgeDirection.LOOP);
        } else {
            matrix.get(from).put(newEdge, EdgeDirection.OUT);
            matrix.get(to).put(newEdge, EdgeDirection.IN);
        }
        return newEdge;
    }

    @Override
    public void removeEdge(@NotNull Edge<E> rmEdge) {
        matrix.values().forEach(map -> map.remove(rmEdge));
    }

    public void removeEdge(@NotNull E weight, Vertex<V> from, Vertex<V> to) {
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
        if (this.verticesCount() == 0) {
            return null;
        }

        return matrix.getOrDefault(this.getVertices().get(0), null).keySet().stream().collect(
            Collectors.toList());
    }

    @Override
    public int verticesCount() {
        return matrix.size();
    }
}
