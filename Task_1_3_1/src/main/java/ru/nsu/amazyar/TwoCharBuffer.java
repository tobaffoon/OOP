package ru.nsu.amazyar;

/**
 * Class with two char buffers.
 * Used to operate with two buffers effectively
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

    public int get(int idx){
        checkIdx(idx);
        if(idx < singleSize){
            return firstBuffer[idx];
        }
        else{
            idx -= singleSize;
            return secondBuffer[idx];
        }
    }

    public void set(int idx, char value){
        checkIdx(idx);
        if(idx < singleSize){
            firstBuffer[idx] = value;
        }
        else{
            idx -= singleSize;
            secondBuffer[idx] = value;
        }
    }

    public void switchBuffers(){
        char[] temp = firstBuffer;
        firstBuffer = secondBuffer;
        secondBuffer = temp;
    }

    private void checkIdx(int idx){
        if(idx < 0 || idx >= singleSize * 2){
            throw new IndexOutOfBoundsException();
        }
    }
}
