package fundamentals;

import java.util.ArrayDeque;
import java.util.EmptyStackException;
import java.util.Queue;

/**
 * Author: Pierre Schaus and Auguste Burlats
 * Implement the abstract data type stack using two queues
 * You are not allowed to modify or add the instance variables,
 * only the body of the methods
 */
public class StackWithTwoQueues<E> {

    Queue<E> queue1;
    Queue<E> queue2;

    public StackWithTwoQueues() {
        queue1 = new ArrayDeque();
        queue2 = new ArrayDeque();
    }

    /**
     * Looks at the object at the top of this stack
     * without removing it from the stack
     */
    public boolean empty() {
         return queue1.isEmpty() && queue2.isEmpty();
    }

    public void check(){
        if (!queue2.isEmpty()) return;
        E[] temp = (E[]) new Object[queue1.size()];
        int n =  queue1.size();
        for (int i = 0; i < n; i++) {
            temp[i] = queue1.remove();
        }
        for (int i = temp.length-1; i >= 0; i--) {
            queue2.add(temp[i]);
        }
    }

    /**
     * Returns the first element of the stack, without removing it from the stack
     *
     * @throws EmptyStackException if the stack is empty
     */
    public E peek() throws EmptyStackException {
         if (empty()) throw new EmptyStackException();
         check();
         return queue2.peek();
    }

    /**
     * Remove the first element of the stack and returns it
     *
     * @throws EmptyStackException if the stack is empty
     */
    public E pop() throws EmptyStackException {
        if (empty()) throw new EmptyStackException();
        check();
        return queue2.remove();
    }

    /**
     * Adds an element to the stack
     *
     * @param item the item to add
     */
    public void push(E item) {
        queue1.add(item);
    }

    public static void main(String[] args) {
        StackWithTwoQueues<Integer> st = new StackWithTwoQueues<>();
        for (int i = 0; i < 100; i++) {
            st.push(i);
        }
        for (int i = 0; i < 100; i++) {
            System.out.println(st.pop());
        }
    }

}
