package sorting_supp;

/**
 * @author Aperence
 */
public class Insert
{

    // return the index where value should be inserted
    public static int insert(int[] arr, int value){
        int lo = 0;
        int hi = arr.length;
        while (lo < hi){
            int middle = (lo+hi)/2;
            if (arr[middle] >= value) hi = middle;
            else lo = middle+1;
        }
        return hi;
    }

    public static void main(String[] args) {
        int[] arr = {1,3,4};
        System.out.println(insert(arr, 5));
    }
}
