package ru.nsu.amazyar;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
    public static <V, E extends Number> List<Vertex<V>> sortFrom(Graph<V, E> graph,
        Vertex<V> start) {
        if (graph == null || start == null) {
            throw new NullPointerException();
        }
        List<Edge<E>> edges = graph.getEdges();

        //initialisation
        for (Vertex<V> vertex : graph.getVertices()) {
            vertex.sortDistance = Double.POSITIVE_INFINITY;
        }
        start.sortDistance = 0.0;

        //Bellman–Ford main loop
        for (int i = 0; i < graph.verticesCount(); i++) {
            for (Edge<E> edge : edges) {
                Vertex<V> from = (Vertex<V>) edge.vertexFrom();
                Vertex<V> to = (Vertex<V>) edge.vertexTo();
                if (from.sortDistance + edge.getWeight().doubleValue() < to.sortDistance) {
                    to.sortDistance = from.sortDistance + edge.getWeight().doubleValue();
                }
            }
        }

        //Bellman–Ford check for negative cycles
        for (Edge<E> edge : edges) {
            Vertex<V> from = (Vertex<V>) edge.vertexFrom();
            Vertex<V> to = (Vertex<V>) edge.vertexTo();
            if (from.sortDistance + edge.getWeight().doubleValue() < to.sortDistance) {
                return null;
            }
        }

        //not before actual path calculation to ensure that negative cycle fail
        for (Vertex<V> vertex : graph.getVertices()) {
            vertex.wasSorted = true;
        }

        return graph.getVertices().stream()
            .sorted(Comparator.comparingDouble(vert -> vert.sortDistance))
            .collect(Collectors.toList());
    }

}
