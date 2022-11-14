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
        firstBuffer = secondBuffer;
        secondBuffer = new char[singleSize];
    }

    private void checkIdx(int idx){
        if(idx < 0 || idx >= singleSize * 2){
            throw new IndexOutOfBoundsException();
        }
    }
}
