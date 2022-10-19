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
        Node(int x, int y, int h, int g, graphs.Maze.Node p){this.x = x; this.y = y; this.f = h+g; this.parent = p;};

        @Override
        public int compareTo(Object o) {
            return f - ((graphs.Maze.Node) o).f ;
        }

        @Override
        public String toString() {
            return "(" + x + "," + y + ")";
        }
    }
    public static Iterable<Integer> shortestPath(int[][] maze, int x1, int y1, int x2, int y2) {
        // TODO
        PriorityQueue<Node> pq = new PriorityQueue<>();
        BitSet visited = new BitSet(maze.length);
        pq.add(new Node(x1, y1, 0, Math.abs(x2-x1) + Math.abs(y2-y1), null));
        while(!pq.isEmpty()){
            Node x = pq.remove();
            visited.set(ind(x.x, x.y, maze.length));
            if (x.x == x2 && x.y == y2){
                LinkedList<Integer> ret = new LinkedList<>();
                while(x.parent != null){
                    ret.add(ind(x.x, x.y, maze.length));
                    x = x.parent;
                }
                ret.add(ind(x.x, x.y, maze.length));
                LinkedList<Integer> temp =new LinkedList<>();
                for (int i : ret) temp.addFirst(i);
                return temp;
            }

            for (int i = -1; i <= 1 ; i+=2) {
                if (x.x + i >= 0 && x.x+i < maze.length && maze[x.x+i][x.y] != 1 && !visited.get(ind(x.x + i, x.y, maze.length)))
                    pq.add(new Node(x.x+i, x.y, Math.abs(x.x+i - x1) + Math.abs(x.y - y1), Math.abs(x.x+i - x2) + Math.abs(x.y - y2), x));
            }

            for (int j = -1; j <= 1 ; j+=2) {
                if (x.y + j >= 0 && x.y + j< maze[0].length && maze[x.x][x.y+j] != 1 && !visited.get(ind(x.x,  x.y + j, maze.length)))
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
        for (int w : shortestPath(maze1, 2, 3, 4, 3)){
            System.out.println(row(w, maze1.length) + "," + col(w, maze1.length));
        }
    }

}

