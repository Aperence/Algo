package searching;

public class LinearProbingHashST<Key, Value> {
    private static final int INIT_CAPACITY = 4;

    // Please do not add/remove variables/modify their visibility.
    protected int n;           // number of key-value pairs in the symbol table
    protected int m;           // size of linear probing table
    protected Key[] keys;      // the keys
    protected Value[] vals;    // the values


    public LinearProbingHashST() {
        this(INIT_CAPACITY);
    }

    public LinearProbingHashST(int capacity) {
        m = capacity;
        n = 0;
        keys = (Key[]) new Object[m];
        vals = (Value[]) new Object[m];
    }

    public int size() {
        return n;
    }

    public int capacity() {
        return m;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean contains(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
        return get(key) != null;
    }

    // hash function for keys - returns value between 0 and M-1
    protected int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % m;
    }

    /**
     * resizes the hash table to the given capacity by re-hashing all of the keys
     */
    private void resize(int capacity) {
        m = capacity;
        Key[] newKeys = (Key[]) new Object[capacity];
        Value[] newVals = (Value[]) new Object[capacity];
        for (int i = 0; i < keys.length; i++) {
            if (keys[i] != null) put(newKeys, newVals, keys[i], vals[i]);
        }
        keys = newKeys;
        vals = newVals;
    }

    public void put(Key[] array, Value[] arrayVal, Key key, Value val) {
        int index = hash(key);
        while(array[index] != null && !key.equals(array[index])) index = (index+1)%m;
        array[index] = key;
        arrayVal[index] = val;
    }

    /**
     * Inserts the specified key-value pair into the symbol table, overwriting the old
     * value with the new value if the symbol table already contains the specified key.
     * The load factor should never exceed 50% so make sure to resize correctly
     *
     * @param key the key
     * @param val the value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void put(Key key, Value val) {
        if (key == null) throw new IllegalArgumentException();
        if (isEmpty() || get(key) == null) n++;
        if (n > capacity()/2) resize(capacity()*2);
        put(keys, vals, key, val);
    }

    /**
     * Returns the value associated with the specified key.
     *
     * @param key the key
     * @return the value associated with {@code key};
     * {@code null} if no such value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public Value get(Key key) {
        if (key == null) throw new IllegalArgumentException();
        if (isEmpty()) return null;
        int hash = hash(key);
        while(keys[hash] != null && !key.equals(keys[hash])) hash = (hash+1)%m;
        return vals[hash];
    }

    /**
     * Returns all keys in this symbol table as an array
     */
    public Object[] keys() {
        Object[] exportKeys = new Object[n];
        int j = 0;
        for (int i = 0; i < m; i++)
            if (keys[i] != null) exportKeys[j++] = keys[i];
        return exportKeys;
    }

}
