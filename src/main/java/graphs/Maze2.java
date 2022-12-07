package graphs;


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
public class Maze2 {

    public static class Node implements Comparable{
        int dist;
        int x;
        int y;
        Node parent;

        Node(int x, int y, int d, Node p){this.x = x; this.y = y;dist = d; parent = p;}

        public boolean isTarget(int x, int y){
            return this.x == x && this.y==y;
        }

        @Override
        public int compareTo(Object o) {
            return dist - ((Node) o).dist;
        }
    }

    public static boolean inLimit(int x, int lower, int upper){
        return x >= lower && x < upper;
    }
    public static Iterable<Integer> shortestPath(int[][] maze, int x1, int y1, int x2, int y2) {
        // dijkstra implementation
        int N = maze.length;
        int M = maze[0].length;
        if (!inLimit(x1, 0, N)
                || !inLimit(x2, 0, N)
                || !inLimit(y1, 0, M)
                || !inLimit(y2, 0, M)
                || maze[x1][y1] == 1 || maze[x2][y2] == 1) return new LinkedList<>();
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(new Node(x1, y1, 0, null));
        boolean[] marked = new boolean[N*M];
        while (!pq.isEmpty()){
            Node n = pq.remove();
            if (n.isTarget(x2, y2)){
                LinkedList<Integer> ret = new LinkedList<>();
                while (n != null){
                    ret.addFirst(ind(n.x, n.y, M));
                    n = n.parent;
                }
                return ret;
            }
            if (marked[ind(n.x, n.y, M)]) continue;
            marked[ind(n.x, n.y, M)] = true;

            int newX = n.x + 1;
            if (newX >=0 && newX < N && maze[newX][n.y] != 1)
                pq.add(new Node(newX, n.y, n.dist + 1, n));

            newX = n.x - 1;
            if (newX >=0 && newX < N && maze[newX][n.y] != 1)
                pq.add(new Node(newX, n.y, n.dist + 1, n));


            int newY = n.y + 1;
            if (newY >= 0 && newY < M && maze[n.x][newY] != 1)
                pq.add(new Node(n.x, newY, n.dist + 1, n));

            newY = n.y - 1;
            if (newY >= 0 && newY < M && maze[n.x][newY] != 1)
                pq.add(new Node(n.x, newY, n.dist + 1, n));

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
        int[][] maze2 = new int[][]{
                {0, 0, 0, 1, 0, 0, 0},
                {1, 1, 0, 0, 0, 1, 0}
        };
        int[][] maze1 = new int[][]{
                {0, 0, 0, 0, 0, 0, 0},
                {1, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 1, 0},
                {0, 1, 1, 1, 1, 1, 1},
                {0, 0, 0, 0, 1, 0, 0},
                {1, 1, 0, 0, 1, 0, 0},
                {0, 0, 0, 0, 1, 0, 0}
        };

        for (int w : shortestPath(maze1, 1, 0, 6, 0)){
            System.out.println(row(w, maze1[0].length) + "," + col(w, maze1[0].length));
        }
    }

}
