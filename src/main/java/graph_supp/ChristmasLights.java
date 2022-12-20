package graph_supp;

import utils.Graph;

import java.util.ArrayDeque;
import java.util.Queue;

public class ChristmasLights {

    static int time;
    public static int minTime(Graph G){
        time = 0;
        bfs(G, 0);
        return time;
    }

    public static void bfs(Graph g, int s){
        int[] distTo = new int[g.V()];
        boolean[] visited = new boolean[g.V()];
        distTo[s] = 0;
        time = 0;
        Queue<Integer> q = new ArrayDeque<>();
        q.add(s);

        while (!q.isEmpty()){
            int v = q.remove();
            visited[v] = true;
            for (int w : g.adj(v)){
                if (!visited[w]){
                    q.add(w);
                    distTo[w] = distTo[v] + 1;
                    if (distTo[w] > time) time = distTo[w];
                }
            }
        }
    }

    public static void main(String[] args) {
        Graph g = new Graph(4);
        g.addEdge(0, 1);
        g.addEdge(0,2);
        g.addEdge(2, 3);

        System.out.println(minTime(g));
    }
}
