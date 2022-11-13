package ru.nsu.amazyar;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Class with static algorithms on graphs.
 */
public class GraphAlgorithms {

    /**
     * Sorts vertices by minimal distance from set vertex For determining minimal distance uses.
     * Bellman–Ford algorithm
     *
     * @return Sorted ArrayList of vertices with set distances or null if graph has negative cycles
     */
    public static <V, E extends Number> LinkedHashMap<Vertex<V>, Double> sortFrom(Graph<V, E> graph,
        Vertex<V> start) {
        if (graph == null || start == null) {
            throw new NullPointerException();
        }
        List<Edge<E>> edges = graph.getEdges();

        HashMap<Vertex<V>, Double> minDistance = new HashMap<>();
        //initialisation
        for (Vertex<V> vertex : graph.getVertices()) {
            minDistance.put(vertex, Double.POSITIVE_INFINITY);
        }
        minDistance.put(start, 0.0);

        //Bellman–Ford main loop
        for (int i = 0; i < graph.verticesCount(); i++) {
            for (Edge<E> edge : edges) {
                Vertex<V> from = (Vertex<V>) edge.vertexFrom();
                Vertex<V> to = (Vertex<V>) edge.vertexTo();
                if (minDistance.get(from) + edge.getWeight().doubleValue() < minDistance.get(to)) {
                    minDistance.put(to, minDistance.get(from) + edge.getWeight().doubleValue());
                }
            }
        }

        //Bellman–Ford check for negative cycles
        for (Edge<E> edge : edges) {
            Vertex<V> from = (Vertex<V>) edge.vertexFrom();
            Vertex<V> to = (Vertex<V>) edge.vertexTo();
            if (minDistance.get(from) + edge.getWeight().doubleValue() < minDistance.get(to)) {
                return null;
            }
        }

        LinkedHashMap<Vertex<V>, Double> resMap = new LinkedHashMap<>();
        minDistance.entrySet().stream()
            .sorted(Comparator.comparingDouble(Map.Entry::getValue))                //sort vertices
            .forEachOrdered(entry -> resMap.put(entry.getKey(), entry.getValue())); //order them
        return resMap;
    }

}
