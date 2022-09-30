package sorting_supp;

import java.util.Arrays;
import java.util.Random;

public class Boxing {

    public static void main(String[] args) {
        Random rng = new Random();
        int n = 10000000;
        int[] array = new int[n];
        Integer[] array2 = new Integer[n];
        for (int i = 0; i < n; i++) {
            int r = rng.nextInt();;
            array[i] = r;
            array2[i] = r;
        }
        long start = System.currentTimeMillis();
        Arrays.sort(array);
        long end  = System.currentTimeMillis() - start;
        start = System.currentTimeMillis();
        Arrays.sort(array2);
        long end2 = System.currentTimeMillis() - start;

        System.out.println("int : " + ((double) end)/1000.0 + ", Integer : " + ((double) end2)/1000.0);
    }
}
