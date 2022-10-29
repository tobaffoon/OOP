package ru.nsu.amazyar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

public class GraphAlgorithms {

    public static <V, E extends Number> HashMap<Vertex<V>, Double> sortFrom(
        @NotNull Graph<V, E> graph, Vertex<V> start) {

        List<Edge<E>> edges = graph.getEdges();
        Map<Vertex<V>, Double> minDistance =
            graph.getVertices().stream()
                .collect(Collectors.toMap(Function.identity(),
                    ignored -> Double.POSITIVE_INFINITY));

        minDistance.replace(start, (double)0);

        for (int i = 0; i < graph.verticesCount(); i++) {
            for (Edge<E> edge : edges) {
                Vertex<V> from = (Vertex<V>) edge.vertexFrom();
                Vertex<V> to = (Vertex<V>) edge.vertexTo();
                if (minDistance.get(from) + edge.getWeight().doubleValue() < minDistance.get(to)) {
                    minDistance.replace(to, minDistance.get(from) + edge.getWeight().doubleValue());
                }
            }
        }

        for (Edge<E> edge : edges) {
            Vertex<V> from = (Vertex<V>) edge.vertexFrom();
            Vertex<V> to = (Vertex<V>) edge.vertexTo();
            if (minDistance.get(from) + edge.getWeight().doubleValue() < minDistance.get(to)) {
                return null;
            }
        }

        return (HashMap<Vertex<V>, Double>) minDistance;
    }

}
