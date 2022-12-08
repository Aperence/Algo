package graphs;


import java.util.ArrayList;
import java.util.List;

/**
 *Implement the WeightedDigraph.java interface in the WeightedDigraph.java class using an adjacency list
 * data structure to represent directed graphs.
 */
public class Digraph {

    List<Integer>[] adj;
    int V = 0;
    int E = 0;
    public Digraph(int V) {
        adj = new List[V];
        for (int i = 0; i < V; i++) {
            adj[i] = new ArrayList<>();
        }
        this.V = V;
    }

    /**
     * The number of vertices
     */
    public int V() {
        return V;
    }

    /**
     * The number of edges
     */
    public int E() {
         return E;
    }

    /**
     * Add the edge v->w
     */
    public void addEdge(int v, int w) {
        E++;
        adj[v].add(w);
    }

    /**
     * The nodes adjacent to node v
     * that is the nodes w such that there is an edge v->w
     */
    public Iterable<Integer> adj(int v) {
        return adj[v];
    }

    /**
     * A copy of the digraph with all edges reversed
     */
    public Digraph reverse() {
        Digraph dg = new Digraph(V);
        for (int v = 0; v < V; v++) {
            for (int w : adj(v))
                dg.addEdge(w, v);
        }
        return dg;
    }

}
