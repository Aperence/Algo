package searching_supp;


import java.util.Arrays;
import java.util.Stack;

public class RedBlackTree<K extends Comparable, I> {

    Node root = null;
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    int size = 0;
    private class Node{
        K key;
        I value;
        Node left;
        Node right;
        boolean color;

        Node(K i, I j , Node l, Node r){
            key = i;
            value = j;
            left = l;
            right = r;
            color = RED;
        }

        @Override
        public String toString() {
            return key + " " + value;
        }
    }

    private Node rotateLeft(Node n){
        Node x = n.right;
        n.right = x.left;
        x.left = n;
        n.color = RED;
        x.color = BLACK;
        return x;
    }

    private Node rotateRight(Node n){
        Node x = n.left;
        n.left = x.right;
        x.right = n;
        n.color = RED;
        x.color = BLACK;
        return x;
    }

    private boolean isRed(Node n){
        if (n == null) return false;
        return n.color == RED;
    }

    private void flipColor(Node n){
        n.color = RED;
        n.left.color = BLACK;
        n.right.color = BLACK;
    }

    public Node put(Node n, K key, I value){
        if (n == null) {size++; return new Node(key, value, null, null);}

        int cmp = key.compareTo(n.key);

        if (cmp == 0) n.value = value;
        else if (cmp < 0) n.left = put(n.left, key, value);
        else n.right = put(n.right, key, value);

        if (isRed(n.right) && !isRed(n.left)) n = rotateLeft(n);
        if (isRed(n.left) && isRed(n.left.left)) n = rotateRight(n);
        if (isRed(n.right) && isRed(n.left)) flipColor(n);

        return n;
    }

    public void put(K key, I value){
        root = put(root, key, value);
        root.color = BLACK;
    }

    public void inorder(Node n){
        if (n==null) return;

        inorder(n.left);
        System.out.println(n);
        inorder(n.right);
    }

    public void inorder(){
        inorder(root);
    }

    public void inOrder(K[] arr, Node n, int[] counter){
        if (n == null) return;
        inOrder(arr, n.left, counter);
        arr[counter[0]++] = n.key;
        inOrder(arr, n.right, counter);
    }

    public boolean isSorted(K[] arr){
        for (int i = 1; i < arr.length; i++) {
            if (arr[i-1].compareTo(arr[i]) > 0) return false;
        }
        return true;
    }

    public boolean isBST(){
        K[] keys = (K[]) new Comparable[size];
        inOrder(keys, root, new int[1]);
        System.out.println(Arrays.toString(keys));
        return isSorted(keys);
    }

    public int isBalanced(Node n){
        if (n == null) return 0;
        int left = isBalanced(n.left);
        int right = isBalanced(n.right);
        if (right<0 || left != right) return -1;
        if (isRed(n)) return left;
        return left + 1;
    }

    public boolean isBalanced(){
        return isBalanced(root) != -1;
    }

    public boolean is23(Node n){
        if (n == null) return true;
        if (isRed(n.right)) return false;
        if (isRed(n.left) && isRed(n.left.left)) return false;
        return is23(n.left) && is23(n.right);
    }

    public boolean is23(){
        return is23(root);
    }

    public boolean isRedBlackTree(){
        return is23() && isBalanced() && isBST();
    }

    public static void main(String[] args) {
        RedBlackTree<Integer, String> rbt = new RedBlackTree<>();
        rbt.put(2, "John");
        rbt.put(1, "Tim");
        rbt.put(3, "Tommy");
        rbt.put(4, "Jimmy");
        rbt.put(0, "Jim");
        rbt.put(6, "John");
        rbt.put(7, "Tim");
        rbt.put(9, "Tommy");
        rbt.inorder();
        System.out.println(rbt.is23());
        System.out.println(rbt.isBalanced());
        System.out.println(rbt.isBST());
    }
}
