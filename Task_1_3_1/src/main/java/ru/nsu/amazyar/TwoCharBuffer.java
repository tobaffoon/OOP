package ru.nsu.amazyar;

/**
 * Class with two char buffers. Used to operate two buffers effectively
 */
public class TwoCharBuffer {

    private final int singleSize;
    private char[] firstBuffer;
    private char[] secondBuffer;

    public TwoCharBuffer(int size) {
        singleSize = size;
        firstBuffer = new char[singleSize];
        secondBuffer = new char[singleSize];
    }

    public char[] getFirstBuffer() {
        return firstBuffer;
    }

    public char[] getSecondBuffer() {
        return secondBuffer;
    }

    /**
     * Gets value from buffers. Gets value from first buffer if idx is smaller than its size and
     * from second buffer otherwise
     *
     * @throws IndexOutOfBoundsException when idx is bigger then buffers size
     */
    public int get(int idx) {
        checkIdx(idx);
        if (idx < singleSize) {
            return firstBuffer[idx];
        } else {
            idx -= singleSize;
            return secondBuffer[idx];
        }
    }

    /**
     * Sets value in buffers. Sets value in first buffer if idx is smaller than its size and from
     * second buffer otherwise
     *
     * @throws IndexOutOfBoundsException when idx is bigger then buffers size
     */
    public void set(int idx, char value) {
        checkIdx(idx);
        if (idx < singleSize) {
            firstBuffer[idx] = value;
        } else {
            idx -= singleSize;
            secondBuffer[idx] = value;
        }
    }

    /**
     * Switches buffers with each other.
     */
    public void switchBuffers() {
        char[] temp = firstBuffer;
        firstBuffer = secondBuffer;
        secondBuffer = temp;
    }

    private void checkIdx(int idx) {
        if (idx < 0 || idx >= singleSize * 2) {
            throw new IndexOutOfBoundsException();
        }
    }
}
