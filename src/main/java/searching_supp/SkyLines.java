package searching_supp;

import java.util.*;

public class SkyLines {

    public static void put(TreeMap<Integer, Integer> counts, Integer i){
        counts.putIfAbsent(i, 0);
        counts.replace(i, counts.get(i) + 1);
    }

    public static int getMax(TreeMap<Integer, Integer> counts){
        return counts.lastKey();
    }

    public static int removeMax(TreeMap<Integer, Integer> counts){
        int max = getMax(counts);
        counts.replace(max, counts.get(max) - 1);
        if (counts.get(max) == 0) counts.remove(max);
        return max;
    }

    public static int remove(TreeMap<Integer, Integer> counts, int i){
        int value = counts.get(i);
        counts.replace(i, counts.get(i) - 1);
        if (counts.get(i) == 0) counts.remove(i);
        return value;
    }

    public static List<Interval> horizon(List<Interval> buildings){
        /* TO DO */
        buildings.sort(Interval::compareTo);
        List<Interval> ret = new LinkedList<>();
        TreeMap<Integer, LinkedList<Integer>> tree = new TreeMap<>();
        for (Interval i : buildings) {
            tree.putIfAbsent(i.lo, new LinkedList<>());
            tree.get(i.lo).addFirst(i.height);  // ensure that the additions are done first

            tree.putIfAbsent(i.hi, new LinkedList<>());
            tree.get(i.hi).addLast(-i.height);  // ensure that the subtractions are done after adding
        }

        Integer start = null;

        TreeMap<Integer, Integer> counts = new TreeMap<>();
        for (Map.Entry<Integer, LinkedList<Integer>> e: tree.entrySet()) {
            int index = e.getKey();
            for (int height : e.getValue()) {
                if (start == null){
                    start = e.getKey();
                    put(counts, height);
                    continue;
                }
                // if there is only one height left, with a count of 1 and height is negative
                // => remove the last height => end of building
                if (counts.size() == 1 && counts.get(getMax(counts)) == 1 && height < 0){
                    ret.add(new Interval(start, index, removeMax(counts)));
                    start = null;
                    continue;
                }
                if (height < 0 && height == -getMax(counts)){
                    int temp_max = removeMax(counts);
                    if (temp_max == getMax(counts)) continue;
                    ret.add(new Interval(start, index, temp_max));
                    start = e.getKey();
                    continue;
                }
                if (height < 0) remove(counts, -height);
                if (height > 0 && getMax(counts) < height){
                    ret.add(new Interval(start, index, getMax(counts)));
                    start = e.getKey();
                }
                if (height > 0) put(counts, height);
            }
        }
        return ret;
    }
}

class Interval implements Comparable{
    int lo;
    int hi;
    int height;

    Interval(int a, int b, int h){
        lo = a;
        hi = b;
        height = h;
    }

    @Override
    public int compareTo(Object o) {
        if (!(o instanceof Interval)) throw new UnsupportedOperationException();
        Interval that = (Interval) o;
        if (lo == that.lo) return hi - that.hi;
        return lo - that.lo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Interval)) return false;
        Interval interval = (Interval) o;
        return lo == interval.lo && hi == interval.hi && height == interval.height;
    }

    @Override
    public String toString() {
        return "[" + lo + ", " + hi + "]" + height;
    }
}
