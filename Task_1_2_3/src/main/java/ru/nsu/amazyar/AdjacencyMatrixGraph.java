package ru.nsu.amazyar;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

public class AdjacencyMatrixGraph <V, E extends Number> implements Graph<V, E>{
    private final Map<Vertex<V>, Map<Vertex<V>, List<Edge<E>>>> matrix;

    public AdjacencyMatrixGraph() {
        matrix = new HashMap<>();
    }

    @Override
    public Vertex<V> addVertex(@NotNull V newValue) {
        Vertex<V> newVertex = new Vertex<>(newValue);
        matrix.put(newVertex,  new HashMap<>());
        for (Vertex<V> friend : this.getVertices()) {
            matrix.get(newVertex).put(friend, new ArrayList<>());   //get Map of other vertices
            matrix.get(friend).put(newVertex, new ArrayList<>());   //and put existing ones there
        }
        return newVertex;
    }

    @Override
    public void removeVertex(@NotNull Vertex<V> rmVertex) {
        matrix.remove(rmVertex);
        for (Vertex<V> vertex : this.getVertices()) {
            matrix.get(vertex).remove(rmVertex);    //remove vertex from all lists of other vertices
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
        matrix.get(from).get(to).add(newEdge);
        return newEdge;
    }

    @Override
    public void removeEdge(@NotNull Edge<E> rmEdge) {
        matrix.get(rmEdge.vertexFrom()).get(rmEdge.vertexTo()).remove(rmEdge);
    }

    public void removeEdge(@NotNull E weight, Vertex<V> from, Vertex<V> to) {
        matrix.get(from).get(to).removeIf(edge -> edge.vertexFrom() == from && edge.vertexTo() == to && edge.getWeight().equals(weight));
    }

    @Override
    public List<Edge<E>> getEdges() {
        return matrix.values().stream()
            .flatMap(map -> map.values().stream().flatMap(Collection::stream))
            .collect(Collectors.toList());
    }

    @Override
    public int verticesCount() {
        return matrix.size();
    }
}
