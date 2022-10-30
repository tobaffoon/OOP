package ru.nsu.amazyar;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.amazyar.GraphReader.GraphRepresentation;

class AdjacencyListsGraphTest {

    Graph<String, Double> sampleGraph;

    @BeforeEach
    public void initSampleGraph() {
        try{
            sampleGraph = GraphReader.readGraph("sampleGraphs/adjacency_matrix.txt",
                GraphRepresentation.ADJACENCY_LIST);
        }catch (IOException e){
            sampleGraph = new AdjacencyListsGraph<>();
        }
    }

    @Test
    public void removeTest() {
        //init graph
        Assertions.assertEquals(3, sampleGraph.verticesCount());

        //remove operations
        Vertex<String> vert4 = sampleGraph.addVertex("4");
        sampleGraph.addEdge(1.0, vert4, sampleGraph.findVertex("3"));
        Edge<Double> rmEdge1 = sampleGraph.addEdge(1.0, vert4, sampleGraph.findVertex("1"));
        Edge<Double> rmEdge2 = sampleGraph.addEdge(1.0, vert4, sampleGraph.findVertex("2"));

        sampleGraph.removeEdge(rmEdge2);
        Assertions.assertFalse(sampleGraph.getEdges().contains(rmEdge2));
        sampleGraph.removeEdge(1.0, vert4, sampleGraph.findVertex("1"));
        Assertions.assertFalse(sampleGraph.getEdges().contains(rmEdge1));

        Assertions.assertNotNull(sampleGraph.findVertex("4"));
        sampleGraph.removeVertex(vert4);
        Assertions.assertNull(sampleGraph.findVertex("4"));
    }

    @Test
    public void getSetTest() {
        List<Edge<Double>> edgeList = sampleGraph.getEdges();

        for (Vertex<String> v : sampleGraph.getVertices()) {
            v.setValue("0");
        }
        for (Vertex<String> v : sampleGraph.getVertices()) {
            Assertions.assertEquals("0", v.getValue());
        }

        for (Edge<Double> e : edgeList) {
            e.setWeight(0.0);
        }
        for (Edge<Double> e : sampleGraph.getEdges()) {
            Assertions.assertEquals(0.0, e.getWeight());
        }
    }

    @Test
    public void minDistanceTest() {
        Vertex<String> start = sampleGraph.addVertex("0");
        sampleGraph.addEdge(0.0, start, sampleGraph.findVertex("1"));
        Assertions.assertNull(GraphAlgorithms.sortFrom(sampleGraph, start));

        sampleGraph.removeEdge(-10.0, sampleGraph.findVertex("2"), sampleGraph.findVertex("3"));
        sampleGraph.removeEdge(1.0, sampleGraph.findVertex("2"), sampleGraph.findVertex("3"));
        Map<Vertex<String>, Double> mapSort = GraphAlgorithms.sortFrom(sampleGraph, start);

        Map<Vertex<String>, Double> refSort = new HashMap<>();
        refSort.put(start, 0.0);
        refSort.put(sampleGraph.findVertex("1"), 0.0);
        refSort.put(sampleGraph.findVertex("2"), -3.0);
        refSort.put(sampleGraph.findVertex("3"), -1.0);

        Assertions.assertEquals(refSort, mapSort);
    }

    @Test
    public void nullTest() {
        Graph<Integer, Integer> emptyGraph = new AdjacencyMatrixGraph<>();
        Assertions.assertTrue(emptyGraph.getVertices().isEmpty());
        Assertions.assertTrue(emptyGraph.getEdges().isEmpty());
        Assertions.assertNull(emptyGraph.findVertex(2));
        Assertions.assertThrows(NullPointerException.class, () -> sampleGraph.addVertex(null));
        Assertions.assertThrows(NullPointerException.class, () -> sampleGraph.removeVertex(null));
        Assertions.assertThrows(NullPointerException.class, () -> sampleGraph.findVertex(null));
        Assertions.assertThrows(NullPointerException.class,
            () -> sampleGraph.addEdge(null, null, null));
        Assertions.assertThrows(NullPointerException.class, () -> sampleGraph.removeEdge(null));
        Assertions.assertThrows(NullPointerException.class,
            () -> sampleGraph.removeEdge(null, null, null));
    }
}