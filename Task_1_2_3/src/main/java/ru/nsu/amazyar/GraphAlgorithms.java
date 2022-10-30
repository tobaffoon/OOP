package ru.nsu.amazyar;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class GraphAlgorithms {

    public static <V, E extends Number> List<Vertex<V>> sortFrom(Graph<V, E> graph,
        Vertex<V> start) {
        if (graph == null || start == null) {
            throw new NullPointerException();
        }
        List<Edge<E>> edges = graph.getEdges();

        for (Vertex<V> vertex : graph.getVertices()) {
            vertex.sortDistance = Double.POSITIVE_INFINITY;
        }
        start.sortDistance = 0.0;

        for (int i = 0; i < graph.verticesCount(); i++) {
            for (Edge<E> edge : edges) {
                Vertex<V> from = (Vertex<V>) edge.vertexFrom();
                Vertex<V> to = (Vertex<V>) edge.vertexTo();
                if (from.sortDistance + edge.getWeight().doubleValue() < to.sortDistance) {
                    to.sortDistance = from.sortDistance + edge.getWeight().doubleValue();
                }
            }
        }

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
