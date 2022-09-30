package sorting_supp;

import java.util.Arrays;

public class Quick3Way {

    public static void exchange(Comparable[] array, int i, int j){
        Comparable temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static void sort(Comparable[] array, int low, int high){
        if (low >=  high) return;
        int lo = low;
        int hi = high;
        int eq = low+1;
        Comparable value = array[lo];
        while (eq <= hi){
            if (array[eq].compareTo(value) == 0) eq++;
            else if (array[eq].compareTo(value) < 0){
                exchange(array, eq++, lo++);
            }else{
                exchange(array, eq, hi--);
            }
        }

        sort(array, low, lo);
        sort(array, eq, high);
    }

    public static void sort(Comparable[] array){
        sort(array, 0, array.length-1);
    }

    public static void main(String[] args) {
        Integer [] a = new Integer[]{2,5,1,2,2,2,3,6,4, 4, 6, 7, 9, 1};
        sort(a);
        System.out.println(Arrays.toString(a));
    }
}
