package ru.nsu.amazyar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class GraphReader {
    public enum GraphRepresentation{
        ADJACENCY_MATRIX,
        INCIDENCE_MATRIX,
        ADJACENCY_LIST
    }

    public static Graph<String,Double> readGraph(String filename, GraphReader.GraphRepresentation graphType)
        throws IOException {
        File file = new File(filename);
        if(!file.isFile()){
            throw new FileNotFoundException("Couldn't open file");
        }
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        try {
            line = reader.readLine();
        }catch (IOException e){
            throw new IllegalStateException("Empty file");
        }

        Graph<String, Double> graph;
        HashMap<Integer, String> vertexNumbering = new HashMap<>();
        switch (graphType){
            case ADJACENCY_LIST -> graph = new AdjacencyListsGraph<>();
            case INCIDENCE_MATRIX -> graph = new IncidenceMatrixGraph<>();
            case ADJACENCY_MATRIX -> graph = new AdjacencyMatrixGraph<>();
            default -> throw new IllegalStateException("Unknown representation type");
        }

        String[] edgesLists = line.split(" ");
        int vertexCount = 0;
        for (String edgesList : edgesLists) {
            graph.addVertex(edgesList);
            vertexNumbering.put(vertexCount++, edgesList);
        }

        int i;
        String[] multiEdges;
        for (i = 0; ; i++) {
            line = reader.readLine();
            if(line == null){
                break;
            }
            edgesLists = line.split(" ");
            if(edgesLists.length != vertexCount){
                throw new IllegalStateException("Incorrect table size");
            }

            for (int j = 0; j < edgesLists.length; j++) {
                multiEdges = edgesLists[j].split(",");
                for (String edge : multiEdges) {
                    if (edge.equals("-")) {
                        continue;
                    }
                    Vertex<String> from = graph.findVertex(vertexNumbering.get(i));
                    Vertex<String> to = graph.findVertex(vertexNumbering.get(j));
                    graph.addEdge(Double.parseDouble(edge), from, to);
                }
            }
        }

        if(i != vertexCount){
            throw new IllegalStateException("Incorrect table size");
        }
        return graph;
    }
}
