package sorting_supp;

import java.util.Arrays;

/**
 * @author Aperence
 */

public class StableSort{

    private static class Wrapper implements Comparable{
        Comparable item;
        int index;

        Wrapper(Comparable item, int index){
            this.item = item;
            this.index = index;
        }

        @Override
        public int compareTo(Object o) {
            if (! (o instanceof Wrapper)) return -1;
            Wrapper w = (Wrapper) o;
            int res = item.compareTo(w.item);
            if (res != 0) return res;
            return index - w.index;
        }
    }

    public static void exch(Comparable[] array, int i, int j){
        Comparable temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }


    public static int partition(Comparable[] array, int lo, int hi){
        int i = lo;
        Comparable value = array[hi];
        for (int k = lo; k < hi; k++) {
            if (array[k].compareTo(value) < 0){
                exch(array, i++, k);
            }
        }
        exch(array, i, hi);
        return i;
    }

    public static void sort( Comparable[] array, int lo, int hi){
        if (lo >= hi) return;
        int k = partition(array, lo, hi);

        sort(array, lo, k-1);
        sort(array, k+1, hi);
    }

    // implementation of a stable quick sort
    public static void sort( Comparable[] array){
        Wrapper[] ret = new Wrapper[array.length];
        for (int i = 0; i < array.length; i++) {
            ret[i] = new Wrapper(array[i], i);  // create array of wrapper
        }
        sort(ret, 0, array.length-1);
        for (int i = 0; i < array.length; i++) {
            array[i] = ret[i].item;            // copy items back in original array
        }
    }

    public static void unstableSort(Comparable[] array){
        sort(array, 0, array.length - 1);
    }

    public static class Person implements Comparable{
        static int counter;

        int id;
        String name;

        Person(String name){
            this.name = name;
            id = counter++;
        }

        @Override
        public String toString() {
            return name + "(" + id + ")";
        }

        @Override
        public int compareTo(Object o) {
            return name.compareTo(((Person) o).name );
        }
    }

    public static void main(String[] args) {
        Person[] arr = {new Person("John"), new Person("Harry"), new Person("John"), new Person("Max"), new Person("Leon"), new Person("Leon"), new Person("Leon") , new Person("John"), new Person("John")};
        Person.counter = 0;
        Person[] arr2 = {new Person("John"), new Person("Harry"), new Person("John"), new Person("Max"), new Person("Leon"), new Person("Leon"), new Person("Leon") , new Person("John"), new Person("John")};
        System.out.println(Arrays.toString(arr));
        sort(arr);
        unstableSort(arr2);
        System.out.println(Arrays.toString(arr));
        System.out.println(Arrays.toString(arr2));
    }
}
