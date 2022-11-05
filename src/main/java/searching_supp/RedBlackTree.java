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

    private boolean isEmpty(){
        return root == null;
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
        n.color = !n.color;
        n.left.color = !n.left.color;
        n.right.color = !n.right.color;
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

    public I get(Node n, K key){
        if (n==null) return null;
        if (key.compareTo(n.key) == 0) return n.value;
        else if (key.compareTo(n.key) < 0) return get(n.left, key);
        else return get(n.right, key);
    }

    public I get(K key){
        return get(root, key);
    }

    private Node min(Node n) {
        if (n==null) return null;
        if (n.left == null) return n;
        return min(n.left);
    }

    private Node moveRedLeft(Node n){
        flipColor(n);
        if (isRed(n.right.left)){
            n.right = rotateRight(n.right);
            n = rotateLeft(n);
        }
        return n;
    }

    private Node moveRedRight(Node n){
        flipColor(n);
        if (!isRed(n.left.left))
            n = rotateRight(n);
        return n;
    }

    private Node balance(Node n){
        if (isRed(n.right)) n = rotateLeft(n);
        //if (isRed(n.right) && !isRed(n.left)) n = rotateLeft(n);
        if (isRed(n.left) && isRed(n.left.left)) n = rotateRight(n);
        if (isRed(n.right) && isRed(n.left)) flipColor(n);
        return n;
    }

    private Node deleteMin(Node n) {
        if (n.left == null) return null;
        if (!isRed(n.left) && !isRed(n.left.left))
            n = moveRedLeft(n);
        n.left = deleteMin(n.left);
        return balance(n);
    }


    public Node delete(Node n, K key) {
        if (key.compareTo(n.key) < 0) {
            if (!isRed(n.left) && !isRed(n.left.left))
                n = moveRedLeft(n);
            n.left = delete(n.left, key);
        } else {
            if (isRed(n.left))
                n = rotateRight(n);
            if (key.compareTo(n.key) == 0 && n.right == null)
                return null;
            if (!isRed(n.right) && !isRed(n.right.left))
                n = moveRedRight(n);
            if (key.compareTo(n.key) == 0){
                n.value = get(n.right, min(n.right).key);
                n.key = min(n.right).key;
                n.right = deleteMin(n.right);
            }
            else n.right = delete(n.right, key);
        }
        return balance(n);
    }

    public void delete(K key){
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;
        root = delete(root, key);
        if (!isEmpty()) root.color = BLACK;
    }

    public void inorder(Node n, StringBuilder sb){
        if (n==null) return;

        sb.append("(");
        inorder(n.left, sb);
        sb.append(")");
        sb.append(n.key);
        sb.append("(");
        inorder(n.right, sb);
        sb.append(")");
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

}
