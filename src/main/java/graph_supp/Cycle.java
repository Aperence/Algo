package graph_supp;

import utils.Graph;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class Cycle {

    private static boolean[] visited;
    private static int[] edgeTo;

    private List<Integer> cycle = null;

    Cycle(Graph g){
        visited = new boolean[g.V()];
        edgeTo = new int[g.V()];
        for (int i = 0; i < g.V(); i++) {
            edgeTo[i] = i;
        }
        for (int i = 0; i < g.V(); i++) {
            if (!visited[i])
                dfs(g, -1, i);
        }
    }

    public Iterable<Integer> cycle(){
        return cycle;
    }

    public void dfs(Graph g, int parent, int v){
        visited[v] = true;
        for (Integer w : g.adj(v)){
            if (cycle != null){
                return;
            }
            if (!visited[w]){
                edgeTo[w] = v;
                dfs(g, v, w);
            }
            else if (w != parent){
                cycle = new LinkedList<>();
                cycle.add(w);
                while(edgeTo[v] != v){
                    cycle.add(v);
                    v = edgeTo[v];
                }
                cycle.add(w);
            }
        }
    }

    public static void main(String[] args) {
        Graph g = new Graph(4);
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(2, 3);
        //g.addEdge(3, 0);

        Cycle c = new Cycle(g);
        System.out.println(c.cycle());
    }
}
