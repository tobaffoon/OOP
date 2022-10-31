package ru.nsu.amazyar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AdjacencyListsGraph <V, E extends Number> implements Graph<V, E>{
    private final Map<Vertex<V>, HashMap<Vertex<V>, ArrayList<Edge<E>>>> adjacencyList;

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
        adjacencyList.put(newVertex, new HashMap<>());
        return newVertex;
    }

    @Override
    public void removeVertex(Vertex<V> rmVertex) {
        if(rmVertex == null){
            throw new NullPointerException();
        }
        adjacencyList.remove(rmVertex);
        for (Vertex<V> vertex : this.getVertices()) {
            adjacencyList.get(vertex).remove(rmVertex);
        }
    }

    @Override
    public List<Vertex<V>> getVertices() {
        return new ArrayList<>(adjacencyList.keySet());
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
        adjacencyList.get(from).computeIfAbsent(to, ignore -> new ArrayList<>()).add(newEdge);
        return newEdge;
    }

    @Override
    public void removeEdge(Edge<E> rmEdge) {
        if(rmEdge == null){
            throw new NullPointerException();
        }
        adjacencyList.get(rmEdge.vertexFrom()).get(rmEdge.vertexTo()).remove(rmEdge);
    }

    @Override
    public void removeEdge(E weight, Vertex<V> from, Vertex<V> to) {
        if(weight == null || from == null || to == null){
            throw new NullPointerException();
        }
        adjacencyList.get(from).get(to).removeIf(edge -> edge.getWeight().equals(weight));
    }

    @Override
    public List<Edge<E>> getEdges() {
        return adjacencyList.values().stream().flatMap(map -> map.values().stream().flatMap(List::stream)).collect(Collectors.toList());
    }

    @Override
    public int verticesCount() {
        return adjacencyList.size();
    }
}
