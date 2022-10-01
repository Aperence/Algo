package sorting_supp;

public class LinkedList {

    Node first;
    int size;

    private class Node implements Comparable{
        Node next = null;
        Node previous = null;
        int value;

        Node(int value){
            this.value = value;
        }

        Node(int value, Node next, Node previous){
            this.value = value;
            this.next = next;
            this.previous = previous;
        }

        @Override
        public String toString() {
            if (next == null) return value + "";
            return value + " " + next.toString();
        }

        public void push(Node n){
            if (next == null) next = n;
            else next.push(n);
        }

        @Override
        public int compareTo(Object o) {
            if (o instanceof Node){
                return value - ((Node) o ).value;
            }
            return -1;
        }
    }

    @Override
    public String toString() {
        return "[" + first.toString() + "]";
    }

    public void addValues(int[] values){
        Node n = null;
        for (int i = values.length-1; i >=0 ; i--) {
            n = new Node(values[i], n, null);
        }
        first = n;
        for (int i = 0; i < values.length; i++) {
            if (n.next != null){
                n.next.previous = n;
            }
            n = n.next;
        }
    }

    public Node[] partition(Node n){
        Node pivot = n;
        n = n.next;
        Node left = null;
        Node right = null;
        while (n != null){
            Node temp = n.next;
            if (n.compareTo(pivot)<0){
                n.next = left;
                left = n;
            }else{
                n.next = right;
                right = n;
            }
            n = temp;
        }
        return new Node[]{left, pivot, right};
    }

    public Node sort(Node n){
        if (n == null) return null;
        Node[] list = partition(n);
        Node left = sort(list[0]);
        Node right = sort(list[2]);
        Node middle = list[1];
        middle.next = right;
        if (left == null) left = middle;
        else left.push(middle);
        return left;
    }

    public void sort(){
        first = sort(first);
        Node runner = first;
        runner.previous = null;
        while (runner.next != null){
            runner.next.previous = runner;
            runner = runner.next;
        }
    }

    public static void main(String[] args) {
        LinkedList l =new LinkedList();
        int[] values = {1,4,5,2,3,0,7,8,9};
        l.addValues(values);
        l.sort();
        System.out.println(l);
    }

}
