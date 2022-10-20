package searching_supp;

import java.util.Arrays;
import java.util.Random;
import java.util.StringTokenizer;

public class BinarySearchST<Key extends Comparable<Key>, Value> {

    private class Node<Key extends Comparable<Key>, Value>{
        Key k;
        Value v;
        Node<Key, Value> left;
        Node<Key, Value> right;

        Node(Key k, Value v, Node<Key, Value> l, Node<Key, Value> r){
            this.k = k;
            this.v = v;
            left = l;
            right = r;
        }
    }

    private Node<Key, Value> root = null;
    private Node<Key, Value> lastAdded = null;

    private int size = 0;

    public Value search(Key k, Node<Key, Value> n){
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

    public void inOrder(Key[] arr, Node<Key, Value> n, int[] counter){
        if (n == null) return;
        inOrder(arr, n.left, counter);
        arr[counter[0]++] = n.k;
        inOrder(arr, n.right, counter);
    }

    public void inorder(Node<Key, Value> n, StringBuilder sb){
        if (n==null) return;

        sb.append("(");
        inorder(n.left, sb);
        sb.append(")");
        sb.append(n.k);
        sb.append("(");
        inorder(n.right, sb);
        sb.append(")");
    }

    public String inorder(){
        StringBuilder sb = new StringBuilder();
        inorder(root, sb);
        return sb.toString();
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

    public Node<Integer, Integer> nodeFromString(StringTokenizer s){
        if (!s.hasMoreTokens()) return null;
        String c = s.nextToken();
        Node<Integer, Integer> ret = new Node(null, null, null, null);
        if (c.equals(")")) return null;
        if (c.equals("(")) {ret.left = nodeFromString(s); c = s.nextToken();}

        StringBuilder sb = new StringBuilder();
        while(!c.equals(")") && !c.equals("(") && s.hasMoreTokens()){
            sb.append(c);
            c = s.nextToken();
        }
        ret.k = Integer.parseInt(sb.toString());
        ret.v = Integer.parseInt(sb.toString());
        ret.right = nodeFromString(s);
        return ret;
    }


    public static BinarySearchST<Integer, Integer> treeFromString(String s){
        BinarySearchST<Integer, Integer> st = new BinarySearchST<>();
        String[] strings = s.split("");
        s = String.join(",", strings);
        StringTokenizer tokens = new StringTokenizer(s, ",");
        st.root = st.nodeFromString(tokens);
        return st;
    }

    public static void main(String[] args) {
        BinarySearchST<Integer, Integer> st = new BinarySearchST();
        for (int i = 0; i < 10 ; i++) {
            st.put(i, i);
        }
        String s = st.inorder();
        System.out.println(s);
        BinarySearchST<Integer, Integer> st2 = treeFromString(s);
        System.out.println(st2.inorder());
    }
}
