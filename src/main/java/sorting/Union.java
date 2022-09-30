package sorting;


import java.util.Arrays;
import java.util.Stack;

/**
 * Author Pierre Schaus
 *
 * Given an array of (closed) intervals, you are asked to implement the union operation.
 * This operation will return the minimal array of sorted intervals covering exactly the union
 * of the points covered by the input intervals.
 * For example, the union of intervals [7,9],[5,8],[2,4] is [2,4],[5,9].
 * The Interval class allowing to store the intervals is provided
 * to you.
 *
 */
public class Union {

    /**
     * A class representing an interval with two integers. Hence the interval is
     * [min, max].
     */
    public static class Interval implements Comparable<Union.Interval> {

        public final int min;
        public final int max;

        public Interval(int min, int max) {
            assert(min <= max);
            this.min = min;
            this.max = max;
        }

        @Override
        public boolean equals(Object obj) {
            return ((Union.Interval) obj).min == min && ((Union.Interval) obj).max == max;
        }

        @Override
        public String toString() {
            return "["+min+","+max+"]";
        }

        @Override
        public int compareTo(Union.Interval o) {
            if (min < o.min) return -1;
            else if (min == o.min) return max - o.max;
            else return 1;
        }

        public boolean intersect(Union.Interval o){
            return (o.min <= max && min <= o.max) || (min <= o.max && max >= o.min);
        }

        public Interval union(Union.Interval o){
            return new Interval(Math.min(o.min, min), Math.max(o.max, max));
        }
    }

    /**
     * Returns the union of the intervals given in parameters.
     * This is the minimal array of (sorted) intervals covering
     * exactly the same points than the intervals in parameter.
     * 
     * @param intervals the intervals to unite.
     */
    public static Interval[] union(Interval[] intervals) {
        Interval[] ret = Arrays.copyOf(intervals, intervals.length);
        Arrays.sort(ret);
        Stack<Interval> s = new Stack<>();
        s.push(ret[0]);
        boolean lastAdded = false;
        for (int i = 1; i < ret.length; i++) {
            if (s.peek().intersect(ret[i])){
                s.push(s.pop().union(ret[i]));
            }else{
                s.push(ret[i]);
            }
        }
        return s.toArray(new Interval[s.size()]);

    }

}
