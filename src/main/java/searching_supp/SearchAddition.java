package searching_supp;

import java.util.Hashtable;

public class SearchAddition {

    private static class Pair<K, V>{
        K i;
        V j;

        Pair(K i, V j){
            this.i = i;
            this.j = j;
        }

        @Override
        public String toString() {
            return "(" + i + "," + j + ")";
        }
    }

    private static class HashTable{
        Double[] values;
        int[] indexes;
        int M;

        HashTable(int capacity){
            values = new Double[2*capacity];
            indexes = new int[2*capacity];
            M = capacity;
        }

        public int hash(Double n){
            return (n.hashCode() & 0x7FFFFFFF)%M;
        }

        public int tryInsert(Double n){
            int hash = hash(n);
            while (values[hash] != null){
                if (Double.compare(values[hash], n) == 0) return indexes[hash]; // compare equality
                hash++;
            }
            return -1;
        }

        public void insert(Double n, Integer index){
            int hash = hash(n);
            while (values[hash] != null){
                if (Double.compare(values[hash], n) == 0) return;
                hash++;
            }
            values[hash] = n;
            indexes[hash] = index;
        }
    }

    public static Pair<Integer, Integer> checkSum(double[] arr, double value){
        HashTable t = new HashTable(arr.length);
        for (int i = 0; i < arr.length; i++) {
            t.insert(value - arr[i], i);
            int j = t.tryInsert(arr[i]);
            if (j >= 0) return new Pair<>(Math.min(i, j), Math.max(i,j));
        }
        return null;
    }

    public static void main(String[] args) {
        double[] arr = new double[]{1.5, 2.7, 0.5, 4.1, 2.5, 1.5, 0.8};
        System.out.println(checkSum(arr, 4.6));
    }
}
