package ru.nsu.amazyar;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

/**
 * Reads graph from a file.
 */
public class GraphReader {

    /**
     * Implementation to use to store the graph.
     */
    public enum GraphRepresentation {
        ADJACENCY_MATRIX,
        INCIDENCE_MATRIX,
        ADJACENCY_LIST
    }

    /**
     * Returns graph from a file with directory path.
     */
    public static Graph<String, Double> readGraph(String path, GraphRepresentation graphType)
        throws IOException {
        Path paths = Paths.get(path);
        return readGraph(paths, graphType);
    }

    /**
     * Returns graph from a file with directory path.
     */
    public static Graph<String, Double> readGraph(Path path, GraphRepresentation graphType)
        throws IOException {
        BufferedReader reader = new BufferedReader(Files.newBufferedReader(path));

        //scan line with vertices names
        String line = reader.readLine();
        if (line == null) {
            throw new IllegalStateException("Empty file");
        }

        //if file is not empty - initialise new graph and vertex numbering
        Graph<String, Double> graph;
        HashMap<Integer, String> vertexNumbering = new HashMap<>();
        switch (graphType) {
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

        //parse line of vertices names
        String[] names = line.trim().split(" +"); //add skip any number of spaces
        int vertexCount = 0;
        for (String name : names) {
            graph.addVertex(name);
            vertexNumbering.put(vertexCount++, name);
        }

        //read lines of edges
        String[] multiEdges;
        String[] edgesList;
        int i;
        for (i = 0; ; i++) {
            //scan one line
            line = reader.readLine();
            if (line == null) {
                break;
            }

            //check that number of rows isn't too big
            if (i >= vertexCount) {
                throw new IllegalStateException("Incorrect table size");
            }

            //separate lists of multiEdges in line
            edgesList = line.trim().split(" +");
            //check that number of columns is correct
            if (edgesList.length != vertexCount) {
                throw new IllegalStateException("Incorrect table size");
            }

            //parse multiEdges in given list of multiEdges
            for (int j = 0; j < names.length; j++) {
                multiEdges = edgesList[j].split(",");
                for (String edge : multiEdges) {
                    if (edge.equals("-")) {
                        continue;
                    }
                    //from and to vertices are deducted from current table position
                    //i (row) and j (column) values
                    Vertex<String> from = graph.findVertex(vertexNumbering.get(i));
                    Vertex<String> to = graph.findVertex(vertexNumbering.get(j));
                    graph.addEdge(Double.parseDouble(edge), from, to);
                }
            }
        }

        //check that there were enough rows
        if (i < vertexCount) {
            throw new IllegalStateException("Incorrect table size");
        }
        return graph;
    }
}
