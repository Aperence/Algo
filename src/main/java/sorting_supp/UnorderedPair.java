package sorting_supp;

import java.util.Arrays;

public class UnorderedPair {

    public static int countPairNaive(int[] arr){
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i+1; j < arr.length; j++) {
                if (arr[j] < arr[i]) sum++;
            }
        }
        return sum;
    }

    public static int merge(int[] arr, int[] aux, int lo, int mid,  int hi){
        int i = lo;
        int j = mid+1;
        int count = 0;
        int index = lo;
        for (int k = lo; k <= hi; k++) {
            aux[k] = arr[k];
        }
        while (i <= mid || j <= hi){
            if (i>mid) arr[index++] = aux[j++];
            else if (j>hi) arr[index++] = aux[i++];
            else if (aux[i] < aux[j]) arr[index++] = aux[i++];
            else{
                count += mid - i + 1;
                // numbers of elements greater while they should be lesser
                // left = [1,3,4] right = [2,5,6]
                // when 2 must be inserted, we must increment count by 2 bc 2 < 3 && 2 < 4
                arr[index++] = aux[j++];
            }
        }
        return count;
    }


    public static int countPairEfficient(int[] arr, int[] aux, int lo, int hi){
        if (lo >= hi) return 0;

        int mid = (lo+hi)/2;
        int left = countPairEfficient(arr, aux, lo, mid);
        int right = countPairEfficient(arr, aux, mid+1, hi);
        int union = merge(arr, aux, lo, mid, hi);
        return union+left+right;
    }

    public static int countPairEfficient(int[] arr){
        int[] cpy = Arrays.copyOf(arr, arr.length);
        int[] aux = new int[arr.length];
        return countPairEfficient(cpy, aux, 0, arr.length-1);
    }

    public static void main(String[] args) {
        int[] arr = new int[]{1,3,2, 7,5,6,4,8};

        System.out.println(countPairNaive(arr));
        System.out.println(countPairEfficient(arr));
    }
}
