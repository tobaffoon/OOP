package ru.nsu.amazyar;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AdjacencyMatrixGraphTest {
    @Test
    public void SampleTest(){
        Graph<Integer, Integer> sampleGraph = new AdjacencyMatrixGraph<>();
        sampleGraph.addVertex(2);
        Assertions.assertEquals(2, sampleGraph.getVertices().get(0).getValue().intValue());
    }
}