package searching_supp;

public class UniformSearch {

    static public int search(int[] array, int value){
        int lo = 0;
        int hi = array.length-1;
        if (value >= array.length) return -1;
        while (lo <= hi){
            int pos = ((array.length-1)*(value - lo))/(hi - lo);
            if (array[pos] == value) return pos;
            else if (array[pos] < value) lo = pos + 1;
            else hi = pos - 1;
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] arr = new int[500];
        for (int i = 0; i < 500; i++) {
            arr[i] = i;
        }
        System.out.println(search(arr, 0) + "");
    }
}
