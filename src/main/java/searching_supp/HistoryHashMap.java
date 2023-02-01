package searching_supp;

import java.util.HashMap;

class NodeHistory<K, V>{
    K key;
    V value;
    NodeHistory<K, V> prev, next;

    NodeHistory(K k, V v){
        key = k;
        value = v;
        prev = null;
        next = null;
    }

}

class DoubleLinkedList<K, V>{
    NodeHistory<K, V> first, last;

    DoubleLinkedList(){
        first = null;
        last = null;
    }

    public NodeHistory<K, V> putLast(K key, V value){
        NodeHistory<K, V> n = new NodeHistory<>(key, value);
        if (first == null){
            first = n;
            last = n;
            return n;
        }
        n.prev = last;
        last.next = n;
        last = n;
        return n;
    }

    public NodeHistory<K, V> removeOldest(){
        NodeHistory<K, V> ret = first;
        first = first.next;
        if (first == null){
           last = null;
        }else{
           first.prev = null;
        }

        return ret;
    }

    public void updateNode(NodeHistory<K, V> n){
        if (n == last) return;
        if (n.next != null) n.next.prev = n.prev;
        if (n.prev != null) n.prev.next = n.next;
        if (n == first) first = first.next;

        n.prev = last;
        last.next = n;
        n.next = null;
        last = n;
    }
}

public class HistoryHashMap<K, V>{

    final int CAPACITY = 3;
    HashMap<K, NodeHistory<K, V>> map = new HashMap<>(CAPACITY);
    DoubleLinkedList<K, V> dl = new DoubleLinkedList<>();
    int nb_elems = 0;

    public boolean isFull(){
        return nb_elems >= CAPACITY;
    }

    public void put(K k, V v){
        if (isFull()){
            NodeHistory<K, V> old = dl.removeOldest();
            map.remove(old.key);
            nb_elems--;
        }
        nb_elems++;
        NodeHistory<K, V> n = dl.putLast(k, v);
        map.put(k, n);
    }

    public V get(K k){
        NodeHistory<K, V> n = map.get(k);

        if (n == null) return null;

        dl.updateNode(n);

        return n.value;
    }

    public static void main(String[] args) {
        HistoryHashMap<Integer, Integer> map = new HistoryHashMap<>();

        map.put(1, 1);
        map.put(2, 2);
        map.put(3,3);

        System.out.println(map.get(1));  // 1
        map.put(4,4);
        System.out.println(map.get(2));  // null
        System.out.println(map.get(3));  // 3
        System.out.println(map.get(1));  // 1
        map.put(5,5);
        System.out.println(map.get(4));  // null
    }
}
