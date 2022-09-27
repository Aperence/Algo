package sorting;

import java.util.Arrays;
import java.util.Random;

public class ShellSort {

    public static boolean less(Comparable[] arr, int i, int j){
        return arr[i].compareTo(arr[j]) < 0;
    }

    public static void exchange(Comparable[] arr, int i, int j){
        Comparable temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void sort(Comparable[] arr){
        int k = 1;
        int N = arr.length;
        while(k < N/3) k = 3*k+1;
        while (k >=1){
            for (int i = k; i < N; i++) {
                for (int j = i; j >= k && less(arr, j, j-k); j -= k) {
                    exchange(arr, j, j-k);
                }
            }
            k /= 3;
        }

    }

    public static void main(String[] args) {
        int n = 10000000;
        Random rng = new Random();
        Integer[] arr = new Integer[n];
        for (int i = 0; i < n; i++) {
            arr[i] = rng.nextInt(100);
        }

        Arrays.sort(arr);

    }
}
