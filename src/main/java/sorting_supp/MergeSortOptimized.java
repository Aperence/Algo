package sorting_supp;

import java.util.Arrays;

/**
 * Hello world!
 */
public final class MergeSortOptimized {

    public static final int THRESHOLD = 4;

    public static void merge(Comparable[] arr, Comparable[] aux, int lo, int mid, int hi){
        int i = lo; int j = mid+1;

        for (int k = lo; k <= hi; k++) {
            if (i > mid)                            arr[k] = aux[j++];
            else if (j > hi)                        arr[k] = aux[i++];
            else if (aux[i].compareTo(aux[j]) <= 0) arr[k] = aux[i++];
            else                                    arr[k] = aux[j++];
        }
    }

    public static void insertionSort(Comparable[] arr, int lo, int hi){
        for (int i = lo+1; i <= hi; i++) {
            int j = i - 1;
            Comparable value = arr[i];
            while (j >= lo && arr[j].compareTo(value)>0){
                arr[j+1] = arr[j--];
            }
            arr[j+1] = value;
        }
    }

    public static void sortHelp(Comparable[] arr, Comparable[] aux, int lo, int hi){
        if (hi - lo <= THRESHOLD){
            insertionSort(arr, lo, hi);
            return;
        } 

        int mid = (lo+hi)/2;
        sortHelp(aux, arr, lo, mid);
        sortHelp(aux, arr, mid+1, hi);
        //if (arr[mid].compareTo(arr[mid+1]) < 0) return;  // is already sorted => can be only done when not interleaving aux and arr
        merge(arr, aux, lo, mid, hi);
    }
    
    public static void sort(Comparable[] arr){
        Comparable[] aux = Arrays.copyOf(arr, arr.length);
        sortHelp(arr, aux, 0, arr.length-1);
    }

    /**
     * Says hello to the world.
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        Integer[] arr = new Integer[]{4,5,1,2,9,7,2,2,0,8,8,52, 51};
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
