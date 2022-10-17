package sorting_supp;

import java.util.PriorityQueue;
import java.util.Random;

public class ThirdSmallest {

    public static void swap(int[] arr, int i, int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static int thirdSmallest(int[] arr){
        PriorityQueue<Integer> pq = new PriorityQueue<>((o1, o2)->-o1.compareTo(o2));
        for (int i = 0; i < arr.length; i++) {
            pq.add(arr[i]);
            if (pq.size()==4) pq.poll();
        }
        return pq.poll();
    }

    public static void main(String[] args) {
        int [] arr = new int[100000000];
        Random rng = new Random();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }
        for (int i = 0; i < arr.length; i++) {
            swap(arr, i, rng.nextInt(arr.length-1));
        }
        System.out.println(thirdSmallest(arr));
    }
}
