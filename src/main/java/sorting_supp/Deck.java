package sorting_supp;

import java.util.Arrays;

public class Deck {

    int[] values;
    int size;

    Deck(int[] v){
        values = Arrays.copyOf(v, v.length);
        size = values.length;
    }

    public void swap(){
        int temp = values[0];
        values[0] = values[1];
        values[1] = temp;
    }

    public int compare(){
        return values[0] - values[1];
    }

    public void pushBack(){
        int value = values[0];
        for (int i = 1; i < values.length; i++) {  // should be implemented with a linked list with last and first;
            values[i-1] = values[i];
        }
        values[values.length-1] = value;
    }

    @Override
    public String toString() {
        return Arrays.toString(values);
    }

    public void sort(){
        for (int i = 0; i < size-1; i++) {
            for (int j = 0; j < size-i; j++) {
                if (compare() > 0) swap();
                pushBack();
            }
            for (int j = 0; j < i; j++) {
                pushBack();
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = {4,2,3,5,1,8, 7, 6};
        Deck d = new Deck(arr);
        d.sort();
        System.out.println(d.toString());
    }
}
