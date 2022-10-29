package ru.nsu.amazyar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AdjacencyListsGraph <V, E extends Number> implements Graph<V, E>{
    private final Map<Vertex<V>, List<Edge<E>>> matrix;

    public AdjacencyListsGraph() {
        matrix = new HashMap<>();
    }

    @Override
    public Vertex<V> addVertex(V newValue) {
        if(newValue == null){
            throw new NullPointerException();
        }
        Vertex<V> newVertex = new Vertex<>(newValue);
        matrix.put(newVertex, new ArrayList<>());
        return newVertex;
    }

    @Override
    public void removeVertex(Vertex<V> rmVertex) {
        if(rmVertex == null){
            throw new NullPointerException();
        }
        matrix.remove(rmVertex);
        for (Vertex<V> vertex : this.getVertices()) {
            matrix.get(vertex).removeIf(edge -> edge.vertexTo() == rmVertex);
        }
    }

    @Override
    public List<Vertex<V>> getVertices() {
        return matrix.keySet().stream().collect(Collectors.toList());
    }

    @Override
    public Vertex<V> findVertex(V value) {
        if(value == null){
            throw new NullPointerException();
        }
        for (Vertex<V> v : matrix.keySet()) {
            if (v.getValue().equals(value)) {
                return v;
            }
        }
        return null;
    }

    @Override
    public Edge<E> addEdge(E weight, Vertex<V> from, Vertex<V> to) {
        if(weight == null || from == null || to == null){
            throw new NullPointerException();
        }
        Edge<E> newEdge = new Edge<>(weight, from, to);
        matrix.get(from).add(newEdge);
        return newEdge;
    }

    @Override
    public void removeEdge(Edge<E> rmEdge) {
        if(rmEdge == null){
            throw new NullPointerException();
        }
        matrix.get(rmEdge.vertexFrom()).remove(rmEdge);
    }

    @Override
    public void removeEdge(E weight, Vertex<V> from, Vertex<V> to) {
        if(weight == null || from == null || to == null){
            throw new NullPointerException();
        }
        matrix.get(from).removeIf(edge -> edge.vertexTo() == to && edge.getWeight().equals(weight));
    }

    @Override
    public List<Edge<E>> getEdges() {
        return matrix.values().stream().flatMap(List::stream).collect(Collectors.toList());
    }

    @Override
    public int verticesCount() {
        return matrix.size();
    }
}
