package searching_supp;

import java.util.Arrays;
import java.util.Random;

public class BinarySearchST<Key extends Comparable<Key>, Value> {

    private class Node{
        final Key k;
        Value v;
        Node left;
        Node right;

        Node(Key k, Value v, Node l, Node r){
            this.k = k;
            this.v = v;
            left = l;
            right = r;
        }
    }

    private Node root = null;
    private Node lastAdded = null;

    private int size = 0;

    public Value search(Key k, Node n){
        if (n == null) return null;
        if (n.k.compareTo(k) == 0) return n.v;
        else if (n.k.compareTo(k) < 0) return search(k, n.right);
        else return search(k, n.left);
    }

    public Value search(Key k){
        return search(k, root);
    }

    public int size(){ return size; }

    public Node put(Key k, Value v, Node n){
        if (n == null){ n = new Node(k, v, null, null); lastAdded = n; size++;}
        else if (n.k.compareTo(k) == 0) {n.v = v; lastAdded = n;}
        else if (n.k.compareTo(k) == 0) {n.v = v; lastAdded = n;}
        else if (n.k.compareTo(k) < 0) n.right = put(k, v,  n.right);
        else n.left = put(k, v, n.left);
        return n;
    }

    public void put(Key k, Value v){
        if (lastAdded != null && lastAdded.k.compareTo(k) == 0)  {lastAdded.v = v; return;}
        root = put(k, v, root);
    }

    public void inOrder(Key[] arr, Node n, int[] counter){
        if (n == null) return;
        inOrder(arr, n.left, counter);
        arr[counter[0]++] = n.k;
        inOrder(arr, n.right, counter);
    }

    public boolean isSorted(Key[] arr){
        for (int i = 1; i < arr.length; i++) {
            if (arr[i-1].compareTo(arr[i]) > 0) return false;
        }
        return true;
    }

    public boolean isBST(){
        Key[] keys = (Key[]) new Comparable[size];
        inOrder(keys, root, new int[1]);
        System.out.println(Arrays.toString(keys));
        return isSorted(keys);
    }

    public static void main(String[] args) {
        BinarySearchST<Integer, Integer> st = new BinarySearchST();
        for (int i = 0; i < 100 ; i++) {
            st.put(i, i);
        }
        System.out.println(st.search(53));
        System.out.println(st.search(100));
        for (int i = 0; i < 100; i++) {
            st.put(53,53);
        }
        System.out.println(st.size());
        st.isBST();
    }
}
