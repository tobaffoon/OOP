package ru.nsu.amazyar;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.amazyar.GraphReader.GraphRepresentation;

class GraphReaderTest {

    @Test
    public void badFilesTest() {
        Assertions.assertThrows(NoSuchFileException.class,
            () -> GraphReader.readGraph("i_dont_exist", null));
        Assertions.assertThrows(IllegalStateException.class,
            () -> GraphReader.readGraph("sampleGraphs/empty_file.txt",
                GraphRepresentation.ADJACENCY_MATRIX));
        Assertions.assertThrows(NullPointerException.class,
            () -> GraphReader.readGraph("sampleGraphs/adjacency_matrix_bad.txt", null));
        Assertions.assertThrows(IllegalStateException.class,
            () -> GraphReader.readGraph("sampleGraphs/adjacency_matrix_bad.txt",
                GraphRepresentation.ADJACENCY_MATRIX));
        Assertions.assertThrows(IllegalStateException.class,
            () -> GraphReader.readGraph("sampleGraphs/adjacency_matrix_bad1.txt",
                GraphRepresentation.ADJACENCY_MATRIX));
        Assertions.assertThrows(IllegalStateException.class,
            () -> GraphReader.readGraph("sampleGraphs/adjacency_matrix_half_empty.txt",
                GraphRepresentation.ADJACENCY_MATRIX));
        Assertions.assertThrows(IllegalStateException.class,
            () -> GraphReader.readGraph("sampleGraphs/adjacency_matrix_too_big.txt",
                GraphRepresentation.ADJACENCY_MATRIX));
    }

    @Test
    public void incidenceMatrixReadTest() {
        Graph<String, Double> referenceGraph = new IncidenceMatrixGraph<>();
        Vertex<String> vert1 = referenceGraph.addVertex("1");
        Vertex<String> vert2 = referenceGraph.addVertex("2");
        Vertex<String> vert3 = referenceGraph.addVertex("3");
        referenceGraph.addEdge(-3.0, vert1, vert2);
        referenceGraph.addEdge(3.0, vert2, vert1);
        referenceGraph.addEdge(1.0, vert2, vert3);
        referenceGraph.addEdge(2.0, vert2, vert3);
        referenceGraph.addEdge(1.0, vert3, vert3);
        referenceGraph.addEdge(1.0, vert3, vert1);
        referenceGraph.addEdge(1.0, vert1, vert3);

        try {
            Assertions.assertTrue(referenceGraph.equals(
                GraphReader.readGraph(Paths.get("sampleGraphs/adjacency_matrix.txt"),
                    GraphRepresentation.INCIDENCE_MATRIX)));
        } catch (IOException ignored) {

        }
    }

    @Test
    public void adjacencyMatrixReadTest() {
        Graph<String, Double> referenceGraph = new AdjacencyMatrixGraph<>();
        Vertex<String> vert1 = referenceGraph.addVertex("1");
        Vertex<String> vert2 = referenceGraph.addVertex("2");
        Vertex<String> vert3 = referenceGraph.addVertex("3");
        referenceGraph.addEdge(-3.0, vert1, vert2);
        referenceGraph.addEdge(3.0, vert2, vert1);
        referenceGraph.addEdge(1.0, vert2, vert3);
        referenceGraph.addEdge(2.0, vert2, vert3);
        referenceGraph.addEdge(1.0, vert3, vert3);
        referenceGraph.addEdge(1.0, vert3, vert1);
        referenceGraph.addEdge(1.0, vert1, vert3);

        try {
            Assertions.assertTrue(referenceGraph.equals(
                GraphReader.readGraph(Paths.get("sampleGraphs/adjacency_matrix.txt"),
                    GraphRepresentation.ADJACENCY_MATRIX)));
        } catch (IOException ignored) {

        }
    }

    @Test
    public void adjacencyListsReadTest() {
        Graph<String, Double> referenceGraph = new AdjacencyListsGraph<>();
        Vertex<String> vert1 = referenceGraph.addVertex("1");
        Vertex<String> vert2 = referenceGraph.addVertex("2");
        Vertex<String> vert3 = referenceGraph.addVertex("3");
        referenceGraph.addEdge(-3.0, vert1, vert2);
        referenceGraph.addEdge(3.0, vert2, vert1);
        referenceGraph.addEdge(1.0, vert2, vert3);
        referenceGraph.addEdge(2.0, vert2, vert3);
        referenceGraph.addEdge(1.0, vert3, vert3);
        referenceGraph.addEdge(1.0, vert3, vert1);
        referenceGraph.addEdge(1.0, vert1, vert3);

        try {
            Assertions.assertTrue(referenceGraph.equals(
                GraphReader.readGraph(Paths.get("sampleGraphs/adjacency_matrix.txt"),
                    GraphRepresentation.ADJACENCY_LIST)));
        } catch (IOException ignored) {

        }
    }
}