package sorting;

import java.util.Arrays;

/**
 * Let a be an array of integers. In this exercise we are interested in finding
 * the two entries i and j such that a[i] + a[j] is the closest from a target x.
 * In other words, there are no entries k,l such that |x - (a[i] + a[j])| > |x - (a[k] + a[l])|.
 * Note that we can have i = j.
 * Your program must return the values a[i] and a[j].
 *
 * For example let a = [5, 10, 1, 75, 150, 151, 155, 18, 75, 50, 30]
 *
 * then for x = 20, your program must return the array [10, 10],
 *      for x = 153 you must return [1, 151] and
 *      for x = 170 you must return [18, 151]
 */
public class ClosestPair {

    public static int closest(int[] arr, int value, int lo, int hi){
        while (lo < hi){
            int middle = (lo+hi)/2;
            if (arr[middle] == value) return middle;
            else if (arr[middle] < value) lo = middle+1;
            else hi = middle;
        }
        if (hi == 0) return hi;
        if (hi == arr.length) return hi-1;
        if (Math.abs(arr[hi] - value) < Math.abs(arr[hi-1] - value)) return hi;
        else return hi-1;
    }

    /**
      * Finds the pair of integers which sums up to the closest value of x
      *
      * @param a the array in which the value are looked for
      * @param x the target value for the sum
      */
    public static int[] closestPair(int [] a, int x) {
        int[] cpy = Arrays.copyOf(a, a.length);
        Arrays.sort(cpy);
        int[] arr = new int[]{cpy[0], cpy[0]};
        int closestVal = 2*cpy[0];
        for (int i = 0; i < cpy.length; i++) {
            int index = closest(cpy, x - cpy[i], 0, cpy.length);
            if (Math.abs(x-closestVal) > Math.abs(x-cpy[i]-cpy[index])){
                arr[0] = cpy[i];
                arr[1] = cpy[index];
                closestVal = cpy[i]+cpy[index];
            }
        }
        Arrays.sort(arr);
        return arr;
    }

    public static void main(String[] args) {
        int [] a = new int[]{5, 10, 1, 75, 150, 151, 155, 18, 75, 50, 30};

        int x = 13;

        int [] res = ClosestPair.closestPair(a,x);
        System.out.println(Arrays.toString(res));
    }
}
