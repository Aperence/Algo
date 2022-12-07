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
public class Maze {

    static class Node{
        int x;
        int y;
        Node parent;

        Node(int x, int y, Node parent){
            this.x = x;
            this.y = y;
            this.parent = parent;
        }
    }

    public static Iterable<Integer> shortestPath(int[][] maze, int x1, int y1, int x2, int y2) {
        int n = maze.length;
        int m = maze[0].length;
        if (x1 < 0 || x1 >= n || y1 < 0 || y1 >= m || maze[x1][y1] != 0) return new LinkedList<>();
        if (x2 < 0 || x2 >= n || y1 < 0 || y2 >= m || maze[x2][y2] != 0) return new LinkedList<>();
        boolean[] visited = new boolean[n*m];
        Queue<Node> q = new ArrayDeque<>();
        q.add(new Node(x1, y1, null));
        LinkedList<Integer> ret = new LinkedList<>();
        while(!q.isEmpty()){
            Node temp = q.remove();
            int x = temp.x;
            int y = temp.y;
            if (x < 0 || x >= n) continue;
            if (y < 0 || y >= m) continue;
            if (maze[x][y] != 0) continue;
            if (visited[ind(x, y, m)]) continue;
            visited[ind(x, y, m)] = true;
            if (x == x2 && y == y2){
                while(temp != null){
                    ret.addFirst(ind(temp.x, temp.y, m));
                    temp = temp.parent;
                }
                return ret;
            }
            q.add(new Node(x-1, y, temp));
            q.add(new Node(x, y-1, temp));
            q.add(new Node(x+1, y, temp));
            q.add(new Node(x, y+1, temp));
        }
        return ret;
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

        for (int w : shortestPath(maze1, 0, 0, 6, 0)){
            System.out.println(row(w, maze1[0].length) + "," + col(w, maze1[0].length));
        }
    }

}
