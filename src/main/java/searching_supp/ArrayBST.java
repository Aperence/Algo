package searching_supp;

import java.util.ArrayList;


/**
 *  We study a BST representation using an arrayList internal representation.
 *  Rather than using a Linked-Node Data-Structure, the left/right children
 *  will be encoded with indices in array lists.
 *  More exactly, in this data-structure each node
 *  is represented by an index i (that correspond to the ith added element)
 *  The left  node of node i, if any, is located
 *        at index idxLeftNode.get(i) otherwise idxLeftNode.get(i) == NONE
 *  The right node of node i, if any is located
 *       at index idxRightNode.get(i) otherwise idxRightNode.get(i) == NONE
 *
 *  The tree below is the result of putting (key,value) pairs (12,A),(15,B),(5,C),(8,d),(1,E) (in this order)
 *
 *                12(A)
 *                / \
 *               /   \
 *             5(C)  15(B)
 *             / \
 *          1(E)  8(D)
 *
 *   The state of internal array list after those put operations is
 *    values:        A, B, C, D, E
 *    keys:        12, 15, 5, 8, 1
 *    idxLeftNode:  2, -1, 4,-1,-1
 *    idxRightNode: 1, -1, 3,-1,-1
 *
 *  You can implement the get method before the put method if you prefer since
 *  the two methods will be graded separately.
 *
 *  You cannot add of change the fields already declared, nor change
 *  the signatures of the methods in this
 *  class but feel free to add methods if needed.
 *
 */
public class ArrayBST<Key extends Comparable<Key>, Value> {

    // The next four array lists should always have exactly equal size after a put

    public ArrayList<Key> keys;
    public ArrayList<Value> values;

    public ArrayList<Integer> idxLeftNode; // idxLeftNode[i] = index of left node of i
    public ArrayList<Integer> idxRightNode; // idxRightNode[i] = index of right node of i

    final int NONE = -1;

    public ArrayBST() {
        keys = new ArrayList<>();
        values = new ArrayList<>();
        idxLeftNode = new ArrayList<>();
        idxRightNode = new ArrayList<>();
    }

    public boolean put(int index, Key key, Value val) {
        // tree is empty
        if (index >= keys.size()){
            keys.add(key);
            values.add(val);
            idxLeftNode.add(NONE);
            idxRightNode.add(NONE);
            return true;
        }
        int newIndex = 0;
        boolean left = true;
        if (key.compareTo(keys.get(index)) == 0){
            values.set(index, val);
            return false;
        }
        else if (key.compareTo(keys.get(index)) < 0) newIndex = idxLeftNode.get(index);
        else { newIndex = idxRightNode.get(index); left = false; }
        // reached a leaf in the tree
        if (newIndex == NONE){
            if (left) idxLeftNode.set(index, keys.size());
            else idxRightNode.set(index, keys.size());
            // will add the value in the list
            return put(keys.size()+1, key, val);
        }
        return put(newIndex, key, val);
    }

    /**
     * Insert the entry in the BST, replace the value if the key is already present
     * @param key a key that is present or not in the BST
     * @param val the value that must be attached to this key
     * @return true if the key was added, false if already present and the value has simply been erased
     */
    public boolean put(Key key, Value val) {
        return put(0, key, val);
    }

    public Value get(int index, Key key){
        // tree is empty
        if (index >= keys.size()) return null;
        // end of the tree
        if (index == NONE) return null;
        if (key.compareTo(keys.get(index)) == 0) return values.get(index);
        // search in left
        else if (key.compareTo(keys.get(index)) < 0) return get(idxLeftNode.get(index), key);
        // search in right
        else return get(idxRightNode.get(index), key);
    }

    /**
     * Return the value attached to this key, null if the key is not present
     * @param key
     * @return the value attached to this key, null if the key is not present
     */
    public Value get(Key key) {
        return get(0, key);
    }

}
