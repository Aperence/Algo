package searching_supp;

public class RedBlackTree<K extends Comparable, I> {

    Node root = null;
    private static final boolean RED = true;
    private static final boolean BLACK = false;
    private class Node<K>{
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
        n.color = BLACK;
        n.left.color = BLACK;
        n.right.color = BLACK;
    }

    public Node put(Node n, K key, I value){
        if (n == null) return new Node(key, value, null, null);

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

    public static void main(String[] args) {
        RedBlackTree<Integer, String> rbt = new RedBlackTree<>();
        rbt.put(2, "John");
        rbt.put(1, "Tim");
        rbt.put(3, "Tommy");
        rbt.put(4, "Jimmy");
        rbt.put(0, "Jim");
        rbt.inorder();
    }
}
