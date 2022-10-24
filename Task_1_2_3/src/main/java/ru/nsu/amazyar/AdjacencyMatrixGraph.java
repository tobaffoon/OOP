package ru.nsu.amazyar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

public class AdjacencyMatrixGraph <V, E extends Number> implements Graph<V, E>{
    private Map<Vertex<V>, Pair<Vertex<V>, List<Edge<E>>>> matrix;

    public AdjacencyMatrixGraph() {
        matrix = new HashMap<>();
    }

    @Override
    public Vertex<V> addVertex(@NotNull V newValue) {
        Vertex<V> newVertex = new Vertex<>(newValue);
        matrix.put(newVertex, new Pair<>(newVertex, new ArrayList<>()));
        for (Vertex<V> friend : this.getVertices()) {
            matrix.put(newVertex, new Pair<>(friend, new ArrayList<>()));
            matrix.put(friend, new Pair<>(newVertex, new ArrayList<>()));
        }
        return newVertex;
    }

    @Override
    public void removeVertex(@NotNull Vertex<V> rmVertex) {
        matrix.keySet().remove(rmVertex);
        matrix.entrySet().removeIf(entry -> entry.getValue().getFirst().equals(rmVertex));
    }

    @Override
    public List<Vertex<V>> getVertices() {
        return matrix.keySet().stream().collect(Collectors.toList());
    }

    @Override
    public Edge<E> addEdge(@NotNull E weight, Vertex<V> from, Vertex<V> to) {
        Edge<E> newEdge = new Edge<>(weight, from, to);
        matrix.get(from).getSecond().add(newEdge);
        return newEdge;
    }

    @Override
    public void removeEdge(@NotNull Edge<E> rmEdge) {
        matrix.get(rmEdge.vertexFrom()).getSecond().remove(rmEdge);
    }

    @Override
    public List<Edge<E>> getEdges() {
        return matrix.values().stream().flatMap(pair -> pair.getSecond().stream()).collect(Collectors.toList());
    }

    @Override
    public int verticesCount() {
        return matrix.size();
    }
}
