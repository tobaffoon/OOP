package ru.nsu.amazyar;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.amazyar.GraphReader.GraphRepresentation;

class IncidenceMatrixGraphTest {

    Graph<String, Double> sampleGraph;

    @BeforeEach
    public void initSampleGraph() {
        try{
            sampleGraph = GraphReader.readGraph("sampleGraphs/adjacency_matrix.txt",
                GraphRepresentation.INCIDENCE_MATRIX);
        }catch (IOException e){
            sampleGraph = new IncidenceMatrixGraph<>();
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
        sampleGraph.addEdge(-1.0, start, sampleGraph.findVertex("1"));
        Assertions.assertNull(GraphAlgorithms.sortFrom(sampleGraph, start));

        sampleGraph.removeEdge(-10.0, sampleGraph.findVertex("2"), sampleGraph.findVertex("3"));
        sampleGraph.removeEdge(1.0, sampleGraph.findVertex("2"), sampleGraph.findVertex("3"));

        List<Vertex<String>> mapSort = GraphAlgorithms.sortFrom(sampleGraph, start);

        Vertex<String> vert1 = sampleGraph.findVertex("1");
        Vertex<String> vert2 = sampleGraph.findVertex("2");
        Vertex<String> vert3 = sampleGraph.findVertex("3");
        Assertions.assertEquals(vert2, mapSort.get(0));
        Assertions.assertEquals(-4.0, mapSort.get(0).sortDistance);
        Assertions.assertEquals(vert3, mapSort.get(1));
        Assertions.assertEquals(-2.0, mapSort.get(1).sortDistance);
        Assertions.assertEquals(vert1, mapSort.get(2));
        Assertions.assertEquals(-1.0, mapSort.get(2).sortDistance);
        Assertions.assertEquals(start, mapSort.get(3));
        Assertions.assertEquals(0.0, mapSort.get(3).sortDistance);
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

    @Test
    public void ambiguousVertexTest(){
        Assertions.assertThrows(IllegalStateException.class, () -> sampleGraph.addVertex(sampleGraph.getVertices().get(0).getValue()));
    }
}