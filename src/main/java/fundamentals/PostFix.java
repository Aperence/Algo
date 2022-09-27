package fundamentals;

import java.util.EmptyStackException;
import java.util.Stack;
import java.util.StringTokenizer;

public class PostFix {

    static public int evaluate(String s){
        StringTokenizer tokens = new StringTokenizer(s);
        Stack<Integer> values = new Stack<>();
        try {
            while (tokens.hasMoreTokens()) {
                String token = tokens.nextToken();
                if ("+".equals(token)) {
                    int v1 = values.pop();
                    values.push(v1 + values.pop());
                } else if ("-".equals(token)) {
                    int v1 = values.pop();
                    values.push(v1 - values.pop());
                } else if ("*".equals(token)) {
                    int v1 = values.pop();
                    values.push(v1 * values.pop());
                } else if ("/".equals(token)) {
                    int v1 = values.pop();
                    values.push(v1 / values.pop());
                } else {
                    values.push(Integer.parseInt(token));
                }
            }
        }catch (EmptyStackException e){
            throw new ArithmeticException();
        }
        if (values.size() != 1) throw new ArithmeticException();
        return values.pop();
    }

    public static void main(String[] args) {
        System.out.println(evaluate("2 3 1 * + 9 *")); // 45
        System.out.println(evaluate("4 20 + 3 5 1 * * +")); // 39
        try{
            System.out.println(evaluate("20 * 10 + 5"));
        }catch (ArithmeticException e){
            System.out.println("OK");
        }
    }
}



