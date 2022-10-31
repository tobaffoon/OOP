package ru.nsu.amazyar;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class GraphReader {
    public enum GraphRepresentation{
        ADJACENCY_MATRIX,
        INCIDENCE_MATRIX,
        ADJACENCY_LIST
    }

    public static Graph<String,Double> readGraph(String path, GraphRepresentation graphType) throws IOException {
        Path paths = Paths.get(path);
        return readGraph(paths, graphType);
    }

    public static Graph<String,Double> readGraph(Path path, GraphRepresentation graphType) throws IOException {
        BufferedReader reader = new BufferedReader(Files.newBufferedReader(path));

        String line = reader.readLine();
        if(line == null){
            throw new IllegalStateException("Empty file");
        }

        Graph<String, Double> graph;
        HashMap<Integer, String> vertexNumbering = new HashMap<>();
        switch (graphType){
            case ADJACENCY_LIST:
                graph = new AdjacencyListsGraph<>();
                break;
            case INCIDENCE_MATRIX:
                graph = new IncidenceMatrixGraph<>();
                break;
            case ADJACENCY_MATRIX:
                graph = new AdjacencyMatrixGraph<>();
                break;
            default:
                graph = new AdjacencyMatrixGraph<>();
        }

        String[] argsLists = line.trim().split(" +");//add skip any number of spaces
        int vertexCount = 0;
        for (String argsList : argsLists) {
            graph.addVertex(argsList);
            vertexNumbering.put(vertexCount++, argsList);
        }

        int i;
        String[] multiEdges;
        for (i = 0; ; i++) {
            line = reader.readLine();
            if(line == null){
                break;
            }
            if(i >= vertexCount){
                throw new IllegalStateException("Incorrect table size");
            }
            argsLists = line.trim().split(" +");
            if(argsLists.length != vertexCount){
                throw new IllegalStateException("Incorrect table size");
            }

            for (int j = 0; j < argsLists.length; j++) {
                multiEdges = argsLists[j].split(",");
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
        if(i < vertexCount){
            throw new IllegalStateException("Incorrect table size");
        }
        return graph;
    }
}
