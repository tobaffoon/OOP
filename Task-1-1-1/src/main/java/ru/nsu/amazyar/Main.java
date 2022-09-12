package ru.nsu.amazyar;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int num = sc.nextInt();
        int[] a = new int[num];
        for (int i = 0; i < num; i++) {
            a[i] = sc.nextInt();
        }
        BinHeap bh = new BinHeap();
        bh.heapSort(a);
        for (Integer integer : a) {
            System.out.print(integer + " ");
        }
    }
}
