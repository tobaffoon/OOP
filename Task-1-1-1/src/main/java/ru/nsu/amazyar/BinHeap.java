package ru.nsu.amazyar;

public class BinHeap{
    private int[] tree;
    private int binLength = 0;
    private void createTree(int [] arr){
        int length = arr.length;
        tree = new int[length];
        this.binLength = length;
        for (int i = 0; i < length; i++) {
            tree[i] = arr[i];
            siftUp(i);
        }
    }
    private int getLength(){
        return this.binLength;
    }
    private int getEl(int idx){
        return this.tree[idx];
    }
    private void setEl(int idx, int newValue){
        this.tree[idx] = newValue;
    }
    private void swap(int idxA, int idxB){
        int temp = this.getEl(idxA);
        this.setEl(idxA, this.getEl(idxB));
        this.setEl(idxB, temp);
    }
    //ensures that children are bigger than their parents
    private void siftUp(int idx){
        if(idx == 0) return;
        int dadIdx = (idx + 1) / 2 - 1;
        if(this.getEl(idx) < this.getEl(dadIdx)){
            this.swap(idx, dadIdx);
            siftUp(dadIdx);
        }
    }
    //ensures that parents are smaller than their children
    private void siftDown(int idx){
        int smallest = idx;
        int leftChild = 2*idx + 1;
        int rightChild = 2*idx + 2;

        if(leftChild < this.getLength() && this.getEl(leftChild) < this.getEl(smallest)){
            smallest = leftChild;
        }

        if(rightChild < this.getLength() && this.getEl(rightChild) < this.getEl(smallest)){
            smallest = rightChild;
        }

        if(smallest != idx){
            this.swap(smallest, idx);
            siftDown(smallest);
        }
    }
    public void heapSort(int [] arr){
        this.createTree(arr);
        int length = this.getLength();
        for (int i = 0; i < length; i++) {
            arr[i] = this.getEl(0);
            this.swap(0, length - i - 1);
            this.binLength--;
            siftDown(0);
        }
    }
}
