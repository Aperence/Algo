package graph_supp;

import java.util.*;

public class BellmanFordAlgorithm {

    int[] distTo;
    boolean[] onQueue;
    int[] parent;

    Iterable<DirectedEdges> cycle;


    BellmanFordAlgorithm(WeightedDigraph g, int source){
        distTo = new int[g.V()];
        onQueue = new boolean[g.V()];
        parent = new int[g.V()];
        NegativeCycle nc = new NegativeCycle(g);
        if (nc.hasCycle()){
            cycle = nc.cycle();
            return;
        }
        bellmanFord(g, source);
    }

    public void bellmanFord(WeightedDigraph g, int source){
        Queue<Integer> q = new ArrayDeque();
        for (int i = 0; i < g.V(); i++) {
            parent[i] = i;
            distTo[i] = Integer.MAX_VALUE;
        }
        distTo[source] = 0;
        parent[source] = source;
        onQueue[source] = true;
        q.add(source);

        while (!q.isEmpty()){
            Integer v = q.remove();
            onQueue[v] = false;
            relax(g, v, q);
        }
    }

    public void relax(WeightedDigraph g, int v, Queue<Integer> q){
        for (DirectedEdges e : g.adj(v)){
            int other = e.to() == v ? e.from() : e.to();
            if (distTo[other] > distTo[v] + e.weight){
                distTo[other] = distTo[v] + e.weight;
                parent[other] = v;
                if (!onQueue[other]) {
                    onQueue[other] = true;
                    q.add(other);
                }
            }
        }
    }

    public Iterable<Integer> getPath(int v){
        if (cycle != null) return null;
        List<Integer> ret = new LinkedList<>();
        while(v != parent[v]){
            ret.add(v);
            v = parent[v];
        }
        ret.add(v);
        return ret;
    }

    public Iterable<DirectedEdges> cycle(){
        if (cycle != null) return cycle;
        return null;
    }

    public static void main(String[] args) {
        WeightedDigraph g = new WeightedDigraph(4);

        g.addEdgeWeight(0 , 2, 5);
        g.addEdgeWeight(0, 1, -13);
        g.addEdgeWeight(1, 3, -4);
        g.addEdgeWeight(3, 1, -2);

        BellmanFordAlgorithm bmf = new BellmanFordAlgorithm(g, 0);
        System.out.println(bmf.cycle());
        System.out.println(bmf.getPath(3));
    }
}
