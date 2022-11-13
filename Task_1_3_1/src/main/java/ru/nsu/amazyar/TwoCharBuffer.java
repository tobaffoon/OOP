package ru.nsu.amazyar;

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
        int normalIdx = normalizeIndex(idx);
        if(normalIdx < singleSize){
            return firstBuffer[normalIdx];
        }
        else{
            normalIdx -= singleSize;
            return secondBuffer[normalIdx];
        }
    }

    public void set(int idx, char value){
        int normalIdx = normalizeIndex(idx);
        if(normalIdx < singleSize){
            firstBuffer[normalIdx] = value;
        }
        else{
            normalIdx -= singleSize;
            secondBuffer[normalIdx] = value;
        }
    }

    public void switchBuffers(){
        firstBuffer = secondBuffer;
        secondBuffer = new char[singleSize];
    }

    private int normalizeIndex(int idx){
        if(idx < 0){
            throw new IndexOutOfBoundsException();
        }
        return idx % (singleSize * 2);
    }
}
