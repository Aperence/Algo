package graph_supp;

import utils.Graph;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class NegativeCycle {

    private static boolean[] visited;
    private static boolean[] onStack;
    private static DirectedEdges[] edgeTo;

    private LinkedList<DirectedEdges> cycle = null;

    NegativeCycle(WeightedDigraph g){
        visited = new boolean[g.V()];
        onStack = new boolean[g.V()];
        edgeTo = new DirectedEdges[g.V()];
        for (int i = 0; i < g.V(); i++) {
            edgeTo[i] = null;
        }
        for (int i = 0; i < g.V(); i++) {
            if (!visited[i])
                dfs(g, i);
        }
    }

    public boolean hasCycle(){
        return cycle != null;
    }

    public Iterable<DirectedEdges> cycle(){
        if (hasCycle())
            return cycle;
        return null;
    }

    public void dfs(WeightedDigraph g, int v){
        visited[v] = true;
        onStack[v] = true;
        for (DirectedEdges e : g.adj(v)){
            int w = e.to();
            if (cycle != null) return;
            else if (!visited[w]){
                edgeTo[w] = e;
                dfs(g, w);
            }
            else if (onStack[w] && isNegative(v, e.weight)){
                cycle = new LinkedList<>();
                DirectedEdges runner = edgeTo[v];
                cycle.add(e);
                while(runner != null){
                    cycle.addFirst(runner);
                    v = runner.from();
                    runner = edgeTo[v];
                }
            }
        }
        onStack[v] = false;
    }

    private boolean isNegative(int v, int weight){
        if (edgeTo[v] == null) return weight < 0;
        DirectedEdges e = edgeTo[v];
        int w = e.from();
        return isNegative(w, weight + e.weight);
    }

    public static void main(String[] args) {
        WeightedDigraph g = new WeightedDigraph(4);

        g.addEdgeWeight(0, 3, 12);
        g.addEdgeWeight(0 , 2, 5);
        g.addEdgeWeight(0, 1, -13);
        g.addEdgeWeight(1, 3, -4);
        g.addEdgeWeight(3, 0, -2);

        NegativeCycle c = new NegativeCycle(g);
        System.out.println(c.cycle());
    }
}
