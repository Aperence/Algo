package fundamentals;


import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Stack;

public class HammingProblem {

    public static Queue<Integer> times(int n){
        Queue<Integer> ret = new ArrayDeque<>();
        ret.add(n);
        return ret;
    }

    public static Stack<Integer> hammingSuite(int n){
        Queue<Integer> two = times(2);
        Queue<Integer> three = times(3);
        Queue<Integer> five = times(5);

        Stack<Integer> ret = new Stack<>();
        ret.push(1);

        while(two.peek() < n || three.peek() < n || five.peek() < n){
            int temp;
            if (two.peek() <= three.peek() && two.peek() <= five.peek())
                temp = two.remove();
            else if (three.peek() <= two.peek() && three.peek() <= five.peek())
                temp = three.remove();
            else
                temp = five.remove();
            if (ret.peek() == temp) continue;
            ret.push(temp);
            two.add(temp*2);
            three.add(temp*3);
            five.add(temp*5);
        }

        return ret;
    }

    public static void main(String[] args) {
        Stack<Integer> ret = hammingSuite(10000);
        System.out.println(ret.size());
        System.out.println(ret);
    }
}
