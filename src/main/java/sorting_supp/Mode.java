package sorting_supp;

import java.util.Arrays;

/**
 * @author Aperence
 */
public class Mode {

    // search for the mode of an array
    public static int mode(int[] array){
        // O(nlog(n))
        int[] cpy = Arrays.copyOf(array, array.length);
        Arrays.sort(cpy);
        int count = 1;
        int value = cpy[0];
        int maxValue = cpy[0];
        int maxCount = 1;
        for (int i = 1; i < array.length; i++) {
            if (cpy[i] != value){
                value = cpy[i];
                count = 1;
            }
            else{
                count++;
            }
            if (maxCount < count){
                maxValue = value;
                maxCount = count;
            }
        }
        return maxValue;
    }

    public static int mode(int[] arr, int max){
        // O(n) => but must know the max value and shouldn't be too high
        int[] count = new int[max+1];
        for (int i = 0; i < arr.length; i++) {
            count[arr[i]] += 1;
        }
        int maxValue = count[0];
        int index = 0;
        for (int i = 1; i < count.length; i++) {
            if (count[i] > maxValue){
                maxValue = count[i];
                index = i;
            }
        }
        return index;
    }

    public static void main(String[] args) {
        int[] arr = new int[]{1,2,3,2,2,1,3, 1};
        System.out.println(mode(arr, 3));
        System.out.println(mode(arr));
    }
}
