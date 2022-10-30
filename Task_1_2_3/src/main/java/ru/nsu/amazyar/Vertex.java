package ru.nsu.amazyar;

/**
 * Vertex of a graph
 *
 * @param <E> Vertex value type
 */
public class Vertex<E> {

    private E value;
    //protected to use in sort algorithm
    protected double sortDistance;
    protected boolean wasSorted;

    public Vertex(E value) {
        this.value = value;
        sortDistance = Double.POSITIVE_INFINITY;
        this.wasSorted = false;
    }

    public E getValue() {
        return value;
    }

    public void setValue(E value) {
        this.value = value;
    }
    public double getSortDistance() throws IllegalAccessException {
        if(wasSorted){
            return sortDistance;
        }
        throw new IllegalAccessException("No sorting occurred");
    }
}
