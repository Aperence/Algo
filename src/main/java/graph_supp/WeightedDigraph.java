package graph_supp;

import java.util.*;

public class WeightedDigraph{

    List<DirectedEdges>[] vertices;
    int n_edges;

    WeightedDigraph(int N){
        vertices = new List[N];
        for (int i = 0; i < N; i++) {
            vertices[i]= new LinkedList<>();
        }
        n_edges = 0;
    }

    /**
     * The number of vertices
     */
    public int V(){
        return vertices.length;
    }

    /**
     * The number of edges
     */
    public int E(){
        return n_edges;
    }

    /**
     * Add the edge v->w
     */
    public void addEdgeWeight(int v, int w, int weight){
        vertices[v].add(new DirectedEdges(v, w, weight));
    }


    public Iterable<DirectedEdges> adj(int v){
        return vertices[v];
    }

    /**
     * A copy of the digraph with all edges reversed
     */
    public WeightedDigraph reverse(){
        WeightedDigraph ret = new WeightedDigraph(V());
        for (int i = 0; i < V(); i++) {
            for (DirectedEdges d : adj(i)){
                ret.addEdgeWeight(d.to, d.from, d.weight);
            }
        }
        return ret;
    }
}
class DirectedEdges{

    int from;
    int to;
    int weight;

    DirectedEdges(int from, int to, int weight){
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    public int from(){
        return from;
    }

    public int to(){
        return to;
    }

    public int getWeight(){
        return weight;
    }

    @Override
    public String toString() {
        return from + "->" + to;
    }
}

