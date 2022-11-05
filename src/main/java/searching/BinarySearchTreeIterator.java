package searching;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * In this exercise, we are interested in implementing an iterator (BSTIterator) for a Binary Search Tree (BST).
 * The iterator must traverse the tree in-order. This means that for each node, the left sub-tree is visited, then the node and finally the right sub-tree.
 *
 *  For example, consider the following tree
 *
 *                              12
 *                              |
 *                 8 ------------------------ 18
 *                  |                          |
 *           3 ------------ 11       14 -------------- 20
 *                          |        |
 *                     9 ---         --- 15
 *
 *
 * The iterator visits the nodes in this order: 3, 8, 9, 11, 12, 14, 15, 18, 20
 * We ask you to complete the BSTIterator class, which must implement the Iterator interface.
 *
 * The BSTNode are generic over their key (the integers in the example above) and implement the 
 * BinaryNode and KeyNode interface available in the utils package.
 */
public class BinarySearchTreeIterator<Key extends Comparable<Key>> implements Iterable<Key> {

    private BSTNode<Key> root;
    long nOp = 0;

    public BinarySearchTreeIterator() { };

    /**
     * Returns the size of the tree
     */
    public int size() {
        return this.size(root);
    }

    /**
     * Returns the size the subtree rooted at node
     *
     * @param node the root of the subtree
     */
    private int size(BSTNode<Key> node) {
        if (node == null) {
            return 0;
        }
        return node.getSize();
    }

    public Key minKey(BSTNode<Key> n){
        if (n.left == null) return n.key;
        return minKey(n.left);
    }

    public Key minKey(){
        if (root == null) return null;
        return minKey(root);
    }

    public Key leastKey(BSTNode<Key> n, Key v,  Key ceil){
        if (n == null) return ceil;
        int cmp = v.compareTo(n.key);
        if (cmp < 0){
            if (ceil == null || ceil.compareTo(n.key) > 0) return leastKey(n.left, v, n.key);
            else return leastKey(n.left, v, ceil);
        }else return leastKey(n.right, v, ceil);
    }

    public Key leastKey(Key k){
        return leastKey(root, k, null);
    }

    /**
     * Adds a value in the tree
     *
     * @param key the value to add
     */
    public void put(Key key) {
        nOp++; this.root = put(this.root, key);
    }

    /**
     * Adds a value in a subtree of the tree
     *
     * @param node the root of the subtree
     * @param key the value to add
     */
    private BSTNode<Key> put(BSTNode<Key> node, Key key) {
        if (node == null) {
            return new BSTNode<>(key, 1);
        }
        int cmp = key.compareTo(node.getKey());
        if (cmp < 0) {
            node.setLeft(put(node.getLeft(), key));
        } else if (cmp > 0) {
            node.setRight(put(node.getRight(), key));
        }
        node.setSize(1 + size(node.getLeft()) + size(node.getRight()));
        return node;
    }

    @Override
    public Iterator<Key> iterator() {
        return new BSTIterator2();
    }

    private class BSTIterator implements Iterator<Key> {

        Key[] list = (Key []) new Comparable[size()];
        int runner = 0;
        int size = size();
        int count = 0;

        BSTIterator(){
            inorder(root);
        }

        public void inorder(BSTNode<Key> Node){
            if (Node == null) return;
            inorder(Node.left);
            list[count++] = Node.key;
            inorder(Node.right);
        }

        @Override
        public boolean hasNext() {
            if (size != size()) throw new ConcurrentModificationException();
            return runner < list.length;
        }

        @Override
        public Key next() {
            if (!hasNext()) throw new NoSuchElementException();
            if (size != size()) throw new ConcurrentModificationException();
            return list[runner++];
        }
    }

    class BSTIterator2 implements Iterator<Key>{

        long nbOp = nOp;
        BSTNode<Key> runner = root;
        Stack<BSTNode<Key>> s = new Stack<>();

        @Override
        public boolean hasNext() {
            if (nbOp != nOp) throw new ConcurrentModificationException();
            return !s.isEmpty() || runner != null;
        }

        @Override
        public Key next() {
            if (!hasNext()) throw new NoSuchElementException();
            while(runner != null){
                s.push(runner);
                runner = runner.left;
            }
            runner = s.pop();
            Key ret = runner.key;
            runner = runner.right;
            return ret;
        }
    }

    class BSTNode<K extends Comparable<K>> {

        private BSTNode<K> left;
        private BSTNode<K> right;
        private K key;
        private int size;

        public BSTNode(K key) {
            this.left = null;
            this.right = null;
            this.key = key;
            this.size = 0;
        }

        public BSTNode(K key, int size) {
            this.left = null;
            this.right = null;
            this.key = key;
            this.size = size;
        }

        public BSTNode<K> getLeft() {
            return this.left;
        }

        @SuppressWarnings("unchecked")
        public void setLeft(BSTNode<K> node) {
            this.left = node;
        }

        public BSTNode<K> getRight() {
            return this.right;
        }

        @SuppressWarnings("unchecked")
        public void setRight(BSTNode<K> node) {
            this.right = node;
        }

        public K getKey() {
            return this.key;
        }

        public void setKey(K newKey) {
            this.key = newKey;
        }

        public int getSize() {
            return this.size;
        }

        public void setSize(int newSize) {
            this.size = newSize;
        }

        /**
         * Adds a new value in the subtree rooted a this node
         */
        public void add(K key) {
            if (key.compareTo(this.key) > 0) {
                if (this.right == null) {
                    this.right = new BSTNode<>(key);
                } else {
                    this.right.add(key);
                }
            } else {
                if (this.left == null) {
                    this.left = new BSTNode<>(key);
                } else {
                    this.left.add(key);
                }
            }
        }
    }

    public static void main(String[] args) {
        BinarySearchTreeIterator<Integer> tree = new BinarySearchTreeIterator<>();
        int[] values = new int[]{12, 8, 18, 3, 11, 14, 20, 9, 15};
        for (int v : values) {
            tree.put(v);
        }
        System.out.println(tree.leastKey(18));
    }
}

