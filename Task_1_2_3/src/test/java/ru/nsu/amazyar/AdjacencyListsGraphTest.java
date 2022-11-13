package ru.nsu.amazyar;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.amazyar.GraphReader.GraphRepresentation;

class AdjacencyListsGraphTest {

    Graph<String, Double> sampleGraph;

    @BeforeEach
    public void initSampleGraph() {
        try {
            sampleGraph = GraphReader.readGraph("sampleGraphs/adjacency_matrix.txt",
                GraphRepresentation.ADJACENCY_LIST);
        } catch (IOException e) {
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
        sampleGraph.removeEdge(1.0, vert4, sampleGraph.findVertex("1"));
        Assertions.assertFalse(sampleGraph.getEdges().contains(rmEdge1));

        Edge<Double> rmEdge2 = sampleGraph.addEdge(1.0, vert4, sampleGraph.findVertex("2"));
        sampleGraph.removeEdge(rmEdge2);
        Assertions.assertFalse(sampleGraph.getEdges().contains(rmEdge2));

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
        Vertex<String> vert1 = sampleGraph.findVertex("1");
        Vertex<String> vert2 = sampleGraph.findVertex("2");
        Vertex<String> vert3 = sampleGraph.findVertex("3");

        Set<Entry<Vertex<String>, Double>> entries = GraphAlgorithms.sortFrom(sampleGraph, start).entrySet();
        Iterator<Entry<Vertex<String>, Double>> setIterator = entries.iterator();

        Entry<Vertex<String>, Double> currentEntry = setIterator.next();
        Assertions.assertEquals(vert2, currentEntry.getKey());
        Assertions.assertEquals(-4.0, currentEntry.getValue());

        currentEntry = setIterator.next();
        Assertions.assertEquals(vert3, currentEntry.getKey());
        Assertions.assertEquals(-2.0, currentEntry.getValue());

        currentEntry = setIterator.next();
        Assertions.assertEquals(vert1, currentEntry.getKey());
        Assertions.assertEquals(-1.0, currentEntry.getValue());

        currentEntry = setIterator.next();
        Assertions.assertEquals(start, currentEntry.getKey());
        Assertions.assertEquals(0.0, currentEntry.getValue());
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
        Assertions.assertThrows(NullPointerException.class,
            () -> GraphAlgorithms.sortFrom(null, null));
    }

    @Test
    public void ambiguousVertexTest() {
        Assertions.assertThrows(IllegalStateException.class,
            () -> sampleGraph.addVertex(sampleGraph.getVertices().get(0).getValue()));
    }

    @Test
    public void equalTest() {
        //simple cases
        Assertions.assertEquals(sampleGraph, sampleGraph);
        Assertions.assertNotEquals(sampleGraph, null);

        //same source
        Graph<String, Double> anotherGraph;
        try {
            anotherGraph = GraphReader.readGraph("sampleGraphs/adjacency_matrix.txt",
                GraphRepresentation.ADJACENCY_LIST);
        } catch (IOException e) {
            anotherGraph = new AdjacencyListsGraph<>();
        }
        Assertions.assertEquals(sampleGraph, anotherGraph);

        //vertex equality
        Vertex<String> vertex0 = anotherGraph.addVertex("silent");
        Assertions.assertNotEquals(null, vertex0);
        Assertions.assertNotEquals("silent", vertex0);

        //different size of vertices lists
        Assertions.assertNotEquals(sampleGraph, anotherGraph);

        Vertex<String> vertex1 = anotherGraph.addVertex("white");
        //edge equality
        Edge<Double> sampleEdge = anotherGraph.addEdge(0.0, vertex0, vertex1);
        Assertions.assertNotEquals("silent", sampleEdge);
        Assertions.assertNotEquals(null, sampleEdge);

        //different size of edges lists
        Vertex<String> vertex2 = sampleGraph.addVertex(vertex0.getValue());
        Vertex<String> vertex3 = sampleGraph.addVertex(vertex1.getValue());
        Assertions.assertNotEquals(sampleGraph, anotherGraph);

        //different edges
        Edge<Double> anotherSampleEdge = sampleGraph.addEdge(0.1, vertex2, vertex3);
        Assertions.assertNotEquals(sampleGraph, anotherGraph);

        //different vertices
        sampleGraph.addEdge(0.0, vertex2, vertex3);
        sampleGraph.removeEdge(anotherSampleEdge);
        sampleGraph.findVertex(vertex1.getValue()).setValue(vertex1.getValue() + "end");
        Assertions.assertNotEquals(sampleGraph, anotherGraph);

        //same
        sampleGraph.findVertex(vertex1.getValue() + "end").setValue(vertex1.getValue());
        Assertions.assertEquals(sampleGraph, anotherGraph);
    }
}