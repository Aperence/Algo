package graphs;

import javax.sound.midi.Soundbank;
import java.util.*;

public class GlobalWarmingPaths {

    int waterLevel;
    int [][] altitude;
    int hiX;
    int hiY;

    public GlobalWarmingPaths(int[][] altitude, int waterLevel) {
        this.waterLevel = waterLevel;
        this.altitude = altitude;
        hiX = altitude.length;
        hiY = altitude[0].length;
    }

    public boolean inBorder(int x, int lo, int hi){
        return x >= lo && x < hi;
    }


    public List<Point> shortestPath(Point p1, Point p2) {
        // expected time complexity O(n^2)
        PriorityQueue<Point> pq = new PriorityQueue<>();
        BitSet bs = new BitSet(altitude.length * altitude[0].length);
        if (altitude[p1.x][p1.y] > waterLevel) pq.add(p1);
        while(!pq.isEmpty()){
            Point p = pq.remove();
            if (p2.equals(p)){
                LinkedList<Point> temp = new LinkedList<>();
                while(p != null){
                    temp.addFirst(p);
                    p = p.parent;
                }
                List<Point> ret = new ArrayList<>();
                ret.addAll(temp);
                return ret;
            }
            if (bs.get(p.getIndex(hiY))) continue;
            if (inBorder(p.x + 1, 0, hiX) && altitude[p.x+1][p.y] > waterLevel) pq.add(new Point(p.x + 1, p.y, p.dist+1, p));

            if (inBorder(p.x - 1, 0, hiX) && altitude[p.x-1][p.y] > waterLevel) pq.add(new Point(p.x - 1, p.y, p.dist+1, p));

            if (inBorder(p.y + 1, 0, hiY) && altitude[p.x][p.y+1] > waterLevel) pq.add(new Point(p.x, p.y + 1, p.dist+1, p));

            if (inBorder(p.y - 1, 0, hiY) && altitude[p.x][p.y-1] > waterLevel) pq.add(new Point(p.x, p.y - 1, p.dist+1, p));
            bs.set(p.getIndex(hiY), true);
        }
        return null;
    }

    /**
     * This class represent a point in a 2-dimension discrete plane. This is used, for instance, to
     * identified cells of a grid
     */
    static class Point implements Comparable{
        private final int x;
        private final int y;
        private final int dist;
        private Point parent;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
            dist = 0;
        }

        public Point(int x, int y, int d, Point p) {
            this.x = x;
            this.y = y;
            dist = d;
            parent = p;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getIndex(int length){
            return x*length+y;
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof Point) {
                Point p = (Point) o;
                return p.x == this.x && p.y == this.y;
            }
            return false;
        }

        @Override
        public int compareTo(Object o) {
            return dist - ((Point) o).dist;
        }

        @Override
        public String toString() {
            return "Point{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }

    public static void main(String[] args) {
        int[][] matrix = new int[][]{
                {0, 0, 0, 0, 0, 1, 1, 1, 0, 0},
                {0, 1, 0, 0, 0, 1, 0, 1, 1, 1},
                {0, 0, 0, 0, 0, 1, 0, 0, 1, 0},
                {0, 1, 0, 0, 0, 1, 0, 1, 1, 0},
                {0, 1, 0, 0, 0, 1, 1, 1, 1, 1},
                {0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 1, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };

        GlobalWarmingPaths warming = new GlobalWarmingPaths(matrix, 0);

        List<Point> res = warming.shortestPath(new Point(4,5),new Point(1,7));

        for (Point p:
             res) {
            System.out.println(p);
        }
    }
}
