package graph_supp;

import java.util.*;

/**
 * We are interested in solving a maze represented by a matrix of integers 0-1 of size nxm.
 * This matrix is a two-dimensional array. An entry equal to '1' means that there is a wall and therefore this position is not accessible,
 * while '0' means that the position is free.
 * We ask you to write a Java code to discover the shortest path between two coordinates on this matrix from (x1, y1) to (x2, y2).
 * The moves can only be vertical or horizontal (not diagonal), one step at a time.
 * The result of the path is an Iterable of coordinates from the origin to the destination.
 * These coordinates are represented by integers between 0 and n * m-1, where an integer 'a' represents the position x =a/m and y=a%m.
 * If the start or end position is a wall or if there is no path, an empty Iterable must be returned.
 * The same applies if there is no path between the origin and the destination.
 */
public class AStar{

    public static class Node implements Comparable{
        int x;
        int y;
        int f;
        Node parent;
        Node(int x, int y, int h, int g, Node p){this.x = x; this.y = y; this.f = h+g; this.parent = p;};

        @Override
        public int compareTo(Object o) {
            return f - ((Node) o).f ;
        }

        @Override
        public String toString() {
            return "(" + x + "," + y + ")";
        }
    }

    public static boolean inLimit(int x, int lower, int upper){
        return x >= lower && x < upper;
    }
    public static Iterable<Integer> shortestPath(int[][] maze, int x1, int y1, int x2, int y2) {
        int N = maze.length;
        int M = maze[0].length;
        if (!inLimit(x1, 0, N)
                || !inLimit(x2, 0, N)
                || !inLimit(y1, 0, M)
                || !inLimit(y2, 0, M)
                || maze[x1][y1] == 1 || maze[x2][y2] == 1) return new LinkedList<>();

        PriorityQueue<Node> pq = new PriorityQueue<>();
        BitSet visited = new BitSet(N);
        pq.add(new Node(x1, y1, 0, Math.abs(x2-x1) + Math.abs(y2-y1), null));
        while(!pq.isEmpty()){
            Node x = pq.remove();
            if (visited.get(ind(x.x, x.y, M))) continue;
            visited.set(ind(x.x, x.y, M));
            if (x.x == x2 && x.y == y2){
                LinkedList<Integer> ret = new LinkedList<>();
                while(x != null){
                    ret.addFirst(ind(x.x, x.y, maze[0].length));
                    x = x.parent;
                }
                return ret;
            }

            for (int i = -1; i <= 1 ; i+=2) {
                if (inLimit(x.x+i, 0, N) && maze[x.x+i][x.y] != 1 && !visited.get(ind(x.x + i, x.y, M)))
                    pq.add(new Node(x.x+i, x.y, Math.abs(x.x+i - x1) + Math.abs(x.y - y1), Math.abs(x.x+i - x2) + Math.abs(x.y - y2), x));
            }

            for (int j = -1; j <= 1 ; j+=2) {
                if (inLimit(x.y + j, 0, M) && maze[x.x][x.y+j] != 1 && !visited.get(ind(x.x,  x.y + j, M)))
                    pq.add(new Node(x.x, x.y+j, Math.abs(x.x - x1) + Math.abs(x.y+j - y1), Math.abs(x.x - x2) + Math.abs(x.y+j - y2), x));
            }
        }
        return new LinkedList<>();
    }

    public static int ind(int x, int y, int lg) {
        return x * lg + y;
    }

    public static int row(int pos, int mCols) {
        return pos / mCols;
    }

    public static int col(int pos, int mCols) {
        return pos % mCols;
    }

    public static void main(String[] args) {
        int[][] maze1 = new int[][]{
                {0, 0, 0, 0, 0, 0, 0},
                {1, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 1, 0},
                {0, 1, 1, 1, 1, 1, 1},
                {0, 0, 0, 0, 1, 0, 0},
                {1, 1, 0, 0, 1, 0, 0},
                {0, 0, 0, 0, 1, 0, 0}
        };
        for (int w : shortestPath(maze1, 0, 0, 6, 0)){
            System.out.println(row(w, maze1[0].length) + "," + col(w, maze1[0].length));
        }
    }

}

