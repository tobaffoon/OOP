package ru.nsu.amazyar;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

public class GraphAlgorithms {

    public static <V, E extends Number> Optional<List<Pair<Vertex<V>, E>>> sortFrom(
        @NotNull Graph<V, E> graph, Vertex<V> start) {

        List<Edge<E>> edges = graph.getEdges();
        Map<Vertex<V>, Double> minDistance =
            graph.getVertices().stream()
                .collect(Collectors.toMap(Function.identity(),
                    ignored -> Double.POSITIVE_INFINITY));

        minDistance.replace(start, (double) 0);

        for (int i = 0; i < graph.verticesCount(); i++) {
            for (Edge<E> edge : edges) {
                Vertex<V> from = (Vertex<V>) edge.vertexFrom();
                Vertex<V> to = (Vertex<V>) edge.vertexTo();
                if (minDistance.get(from) + (Double) edge.getWeight() < minDistance.get(to)) {
                    minDistance.replace(to, minDistance.get(from) + (Double) edge.getWeight());
                }
            }
        }

        for (Edge<E> edge : edges) {
            Vertex<V> from = (Vertex<V>) edge.vertexFrom();
            Vertex<V> to = (Vertex<V>) edge.vertexTo();
            if (minDistance.get(from) + (Double) edge.getWeight() < minDistance.get(to)) {
                return Optional.empty();
            }
        }

        List<Pair<Vertex<V>, E>> res =
            minDistance.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.naturalOrder()))
                .map(entry -> new Pair<>(entry.getKey(), (E) entry.getValue()))
                .collect(Collectors.toList());

        return Optional.of(res);
    }

}
