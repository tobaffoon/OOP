package ru.nsu.amazyar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AdjacencyListsGraph <V, E extends Number> implements Graph<V, E>{
    private final Map<Vertex<V>, List<Edge<E>>> adjacencyList;

    public AdjacencyListsGraph() {
        adjacencyList = new HashMap<>();
    }

    @Override
    public Vertex<V> addVertex(V newValue) {
        if(newValue == null){
            throw new NullPointerException();
        }
        //Check for already existing vertices
        //Needed to ensure "find" method unambiguity
        if(this.findVertex(newValue) != null){
            throw new IllegalStateException("Vertex ambiguity is not allowed");
        }
        Vertex<V> newVertex = new Vertex<>(newValue);
        adjacencyList.put(newVertex, new ArrayList<>());
        return newVertex;
    }

    @Override
    public void removeVertex(Vertex<V> rmVertex) {
        if(rmVertex == null){
            throw new NullPointerException();
        }
        adjacencyList.remove(rmVertex);
        for (Vertex<V> vertex : this.getVertices()) {
            adjacencyList.get(vertex).removeIf(edge -> edge.vertexTo() == rmVertex);
        }
    }

    @Override
    public List<Vertex<V>> getVertices() {
        return adjacencyList.keySet().stream().collect(Collectors.toList());
    }

    @Override
    public Vertex<V> findVertex(V value) {
        if(value == null){
            throw new NullPointerException();
        }
        for (Vertex<V> v : adjacencyList.keySet()) {
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
        adjacencyList.get(from).add(newEdge);
        return newEdge;
    }

    @Override
    public void removeEdge(Edge<E> rmEdge) {
        if(rmEdge == null){
            throw new NullPointerException();
        }
        adjacencyList.get(rmEdge.vertexFrom()).remove(rmEdge);
    }

    @Override
    public void removeEdge(E weight, Vertex<V> from, Vertex<V> to) {
        if(weight == null || from == null || to == null){
            throw new NullPointerException();
        }
        adjacencyList.get(from).removeIf(edge -> edge.vertexTo() == to && edge.getWeight().equals(weight));
    }

    @Override
    public List<Edge<E>> getEdges() {
        return adjacencyList.values().stream().flatMap(List::stream).collect(Collectors.toList());
    }

    @Override
    public int verticesCount() {
        return adjacencyList.size();
    }
}
