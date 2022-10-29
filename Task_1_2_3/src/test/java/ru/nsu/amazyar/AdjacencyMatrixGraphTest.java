package ru.nsu.amazyar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AdjacencyMatrixGraphTest {
    Graph<Integer, Integer> sampleGraph;
    @BeforeEach
    public void initSampleGraph(){
        sampleGraph = new AdjacencyMatrixGraph<>();
        Vertex<Integer> vert1 = sampleGraph.addVertex(1);
        Vertex<Integer> vert2 = sampleGraph.addVertex(2);
        Vertex<Integer> vert3 = sampleGraph.addVertex(3);
        sampleGraph.addEdge(-3, vert1, vert2);
        sampleGraph.addEdge(1, vert1, vert3);
        sampleGraph.addEdge(3, vert2, vert1);
        sampleGraph.addEdge(1, vert2, vert3);
        sampleGraph.addEdge(2, vert2, vert3);
        sampleGraph.addEdge(1, vert3, vert3);
        sampleGraph.addEdge(1, vert3, vert1);
    }
    @Test
    public void removeTest(){
        //init graph
        Assertions.assertEquals(3, sampleGraph.verticesCount());

        //remove operations
        Vertex<Integer> vert4 = sampleGraph.addVertex(4);
        sampleGraph.addEdge(1, vert4, sampleGraph.findVertex(3));
        Edge<Integer> rmEdge1 = sampleGraph.addEdge(1, vert4, sampleGraph.findVertex(1));
        Edge<Integer> rmEdge2 = sampleGraph.addEdge(1, vert4, sampleGraph.findVertex(2));

        sampleGraph.removeEdge(rmEdge2);
        Assertions.assertFalse(sampleGraph.getEdges().contains(rmEdge2));
        sampleGraph.removeEdge(1, vert4, sampleGraph.findVertex(1));
        Assertions.assertFalse(sampleGraph.getEdges().contains(rmEdge1));

        Assertions.assertNotNull(sampleGraph.findVertex(4));
        sampleGraph.removeVertex(vert4);
        Assertions.assertNull(sampleGraph.findVertex(4));
    }

    @Test
    public void getSetTest(){
        List<Edge<Integer>> edgeList = sampleGraph.getEdges();

        for (Vertex<Integer> v : sampleGraph.getVertices()) {
            v.setValue(0);
        }
        for (Vertex<Integer> v : sampleGraph.getVertices()) {
            Assertions.assertEquals(0, v.getValue());
        }

        for (Edge<Integer> e : edgeList) {
            e.setWeight(0);
        }
        for (Edge<Integer> e : sampleGraph.getEdges()) {
            Assertions.assertEquals(0, e.getWeight());
        }
    }

    @Test
    public void minDistanceTest(){
        Vertex<Integer> start = sampleGraph.addVertex(0);
        sampleGraph.addEdge(0, start, sampleGraph.findVertex(1));
        Assertions.assertNull(GraphAlgorithms.sortFrom(sampleGraph, start));

        sampleGraph.removeEdge(-10, sampleGraph.findVertex(2), sampleGraph.findVertex(3));
        sampleGraph.removeEdge(1, sampleGraph.findVertex(2), sampleGraph.findVertex(3));
        Map<Vertex<Integer>, Double> mapSort = GraphAlgorithms.sortFrom(sampleGraph, start);

        Map<Vertex<Integer>, Double> refSort = new HashMap<>();
        refSort.put(start, (double) 0);
        refSort.put(sampleGraph.findVertex(1), (double) 0);
        refSort.put(sampleGraph.findVertex(2), (double) -3);
        refSort.put(sampleGraph.findVertex(3), (double) -1);

        Assertions.assertEquals(refSort, mapSort);
    }

    @Test
    public void nullTest(){
        Graph<Integer, Integer> emptyGraph = new AdjacencyMatrixGraph<>();
        Assertions.assertTrue(emptyGraph.getVertices().isEmpty());
        Assertions.assertTrue(emptyGraph.getEdges().isEmpty());
        Assertions.assertNull(emptyGraph.findVertex(2));
        Assertions.assertThrows(NullPointerException.class, () -> sampleGraph.addVertex(null));
        Assertions.assertThrows(NullPointerException.class, () -> sampleGraph.removeVertex(null));
        Assertions.assertThrows(NullPointerException.class, () -> sampleGraph.findVertex(null));
        Assertions.assertThrows(NullPointerException.class, () -> sampleGraph.addEdge(null, null, null));
        Assertions.assertThrows(NullPointerException.class, () -> sampleGraph.removeEdge(null));
        Assertions.assertThrows(NullPointerException.class, () -> sampleGraph.removeEdge(null, null, null));
    }
}