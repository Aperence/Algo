package graphs;

import java.util.*;


/**
 * You are asked to implement the WordTransformationSP class which allows to find the shortest path
 * from a string A to another string B (with the certainty that there is a path from A to B).
 * To do this, we define a rotation(x, y) operation that reverses the order of the letters between the x and y positions (not included).
 * For example, with A=``HAMBURGER``, if we do rotation(A, 4, 8), we get HAMBEGRUR. So you can see that the URGE sub-string
 * has been inverted to EGRU and the rest of the string has remained unchanged: HAMB + ECRU + R = HAMBEGRUR.
 * Let's say that a rotation(x, y) has a cost of y-x. For example going from HAMBURGER to HAMBEGRUR costs 8-4 = 4.
 * The question is what is the minimum cost to reach a string B from A?
 * So you need to implement a public static int minimalCost(String A, String B)
 * function that returns the minimum cost to reach String B from A using the rotation operation.
 */
public class WordTransformationSP {

    /**
     * Rotate the substring between start and end of a given string s
     * eg. s = HAMBURGER, rotation(s,4,8) = HAMBEGRUR i.e. HAMB + EGRU + R
     */
    /**
     * Rotate the substring between start and end of a given string s
     * eg. s = HAMBURGER, rotation(s,4,8) = HAMBEGRUR i.e. HAMB + EGRU + R
     *
     * @param s
     * @param start
     * @param end
     * @return rotated string
     */
    public static String rotation(String s, int start, int end) {
        return s.substring(0, start) + new StringBuilder(s.substring(start, end)).reverse().toString() + s.substring(end);
    }

    public static class Pair implements Comparable{
        String s;
        int cost;

        Pair(String s, int c){ this.s = s; cost = c;}

        @Override
        public int compareTo(Object o) {
            return cost - ((Pair) o).cost;
        }
    }


    /**
     * Compute the minimal cost from string "from" to string "to" representing the shortest path
     *
     * @param from
     * @param to
     * @return
     */
    public static int minimalCost(String from, String to) {
        PriorityQueue<Pair> pq = new PriorityQueue<>();
        HashMap<String, Integer> map = new HashMap<>();
        pq.add(new Pair(from, 0));
        while (!pq.isEmpty()){
            Pair temp = pq.remove();
            if (map.containsKey(temp.s)) continue;
            if (temp.s.compareTo(to) == 0) return temp.cost;

            map.put(temp.s, temp.cost);
            for (int size = 1; size <= to.length(); size++) {
                for (int index = 0; index < to.length() - size + 1; index++) {
                    String n = rotation(temp.s, index, index+size);
                    if (map.containsKey(n)) continue;
                    pq.add(new Pair(n, temp.cost+size));
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        System.out.println(minimalCost("wdaowkl", "aowlwkd"));
    }


}
