package graphs;


/**
 * In this exercise, we revisit the GlobalWarming class from the sorting package.
 * You are still given a matrix of altitude in parameter of the constructor, with a water level.
 * All the entries whose altitude is under, or equal to, the water level are submerged while the other constitute small islands.
 *
 * For example let us assume that the water level is 3 and the altitude matrix is the following
 *
 *      | 1 | 3 | 3 | 1 | 3 |
 *      | 4 | 2 | 2 | 4 | 5 |
 *      | 4 | 4 | 1 | 4 | 2 |
 *      | 1 | 4 | 2 | 3 | 6 |
 *      | 1 | 1 | 1 | 6 | 3 |
 * 
 * If we replace the submerged entries by _, it gives the following matrix
 *
 *      | _ | _ | _ | _ | _ |
 *      | 4 | _ | _ | 4 | 5 |
 *      | 4 | 4 | _ | 4 | _ |
 *      | _ | 4 | _ | _ | 6 |
 *      | _ | _ | _ | 6 | _ |
 *
 * The goal is to implmets two methods that can answer the following questions:
 *      1) Are to entries on the same island?
 *      2) How many island is there
 *
 * Two entries above the water level are connected if they are next to each other on
 * the same row or the same column. They are **not** connected **in diagonal**.
 * Beware that the methods must run in O(1) time complexity, at the cost of a pre-processing in the constructor.
 * To help you, you'll find a `Point` class in the utils package which identified an entry of the grid.
 * Carefully read the expected time complexity of the different methods.
 */
public class GlobalWarming {


    boolean[] marked;
    int[] edgeTo;
    int N;
    int M;

    int count = 0;

    /**
     * Constructor. The run time of this method is expected to be in 
     * O(n x log(n)) with n the number of entry in the altitude matrix.
     *
     * @param altitude the matrix of altitude
     * @param waterLevel the water level under which the entries are submerged
     */
    public GlobalWarming(int [][] altitude, int waterLevel) {
        N = altitude.length;
        M = altitude[0].length;
        marked = new boolean[N*M];
        edgeTo = new int[N*M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                int index = index(i, j);
                if (!marked[index] && altitude[i][j]>waterLevel) { count++; edgeTo[index] = index; dfs(i,j,waterLevel, altitude);}
            }
        }
    }

    public int index(int i, int j){return i*M+j;}

    public boolean inLimit(int i, int hi){
        return i>=0 && i<hi;
    }

    public void dfs(int i, int j, int waterLevel, int[][] altitude){
        int index = index(i,j);
        marked[index] = true;
        if (inLimit(i+1, N) && altitude[i+1][j] > waterLevel && !marked[index(i+1,j)]) { edgeTo[index(i+1,j)] = edgeTo[index]; dfs(i+1, j, waterLevel, altitude);}

        if (inLimit(i-1, N) && altitude[i-1][j] > waterLevel && !marked[index(i-1,j)]) { edgeTo[index(i-1,j)] = edgeTo[index]; dfs(i-1, j, waterLevel, altitude);}

        if (inLimit(j+1, M) && altitude[i][j+1] > waterLevel && !marked[index(i, j+1)]) { edgeTo[index(i,j+1)] = edgeTo[index]; dfs(i, j+1, waterLevel, altitude);}

        if (inLimit(j-1, M) && altitude[i][j-1] > waterLevel && !marked[index(i,j-1)]) { edgeTo[index(i,j-1)] = edgeTo[index]; dfs(i, j-1, waterLevel, altitude);}
    }

    /**
     * Returns the number of island
     *
     * Expected time complexity O(1)
     */
    public int nbIslands() {
         return count;
    }

    /**
     * Return true if p1 is on the same island than p2, false otherwise
     *
     * Expected time complexity: O(1)
     *
     * @param p1 the first point to compare
     * @param p2 the second point to compare
     */
    public boolean onSameIsland(Point p1, Point p2) {
         return edgeTo[index(p1.x, p1.y)] == edgeTo[index(p2.x, p2.y)];
    }


    /**
     * This class represent a point in a 2-dimension discrete plane. This is used, for instance, to
     * identified cells of a grid
     */
    static class Point {

        private final int x;
        private final int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof Point) {
                Point p = (Point) o;
                return p.x == this.x && p.y == this.y;
            }
            return false;
        }
    }

    public static void main(String[] args) {
        int [][] altitude = new int[][] {{1, 3, 3, 1, 3},
                {4, 2, 2, 4, 5},
                {4, 4, 1, 4, 2},
                {1, 4, 2, 3, 6},
                {1, 1, 1, 6, 3}};

        GlobalWarming gw = new GlobalWarming(altitude, 3);
        System.out.println(gw.count);
    }
}
