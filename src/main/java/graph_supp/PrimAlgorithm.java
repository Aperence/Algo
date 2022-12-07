package graph_supp;

import utils.Digraph;

import java.util.*;

public class PrimAlgorithm {

    boolean[] visited;
    DirectedEdges[] parent;
    int[] dist;

    PrimAlgorithm(DigraphImpl g, int source){
        visited = new boolean[g.V()];
        parent = new DirectedEdges[g.V()];
        dist = new int[g.V()];
        primLoop(g, source);
    }

    void primLoop(DigraphImpl g, int v){
        for (int i = 0; i < g.V(); i++) {
            dist[i] = Integer.MAX_VALUE;
        }
        dist[v] = 0;
        visited[v] = true;
        parent[v] = null;
        PriorityQueue<DirectedEdges> pq = new PriorityQueue<>((d1, d2) -> d1.getWeight() - d2.getWeight());
        relax(g, v, pq);

        while (!pq.isEmpty()){
            DirectedEdges d = pq.poll();

            if (visited[d.to()] && visited[d.from()]) continue;
            else if (visited[d.to()]) relax(g, d.from(), pq);
            else relax(g, d.to(), pq);
        }
    }

    void relax(DigraphImpl g, int v, PriorityQueue<DirectedEdges> pq){
        visited[v] = true;
        for (DirectedEdges d : g.adj2(v)){
            int other = d.to() == v ? d.from() : d.to();
            if (dist[v] + d.weight < dist[other]){
                dist[other] = dist[v] + d.weight;
                parent[other] = d;
                pq.add(d);
            }
        }
    }

    public Iterable<DirectedEdges> MST(){
        return Arrays.asList(parent);
    }

    public static void main(String[] args) {
        DigraphImpl g = new DigraphImpl(4);

        g.addEdgeWeight(0, 3, 12);
        g.addEdgeWeight(0 , 2, 5);
        g.addEdgeWeight(0, 1, 5);
        g.addEdgeWeight(1, 3, 1);

        PrimAlgorithm p = new PrimAlgorithm(g, 0);
        for (DirectedEdges d: p.MST()) {
            System.out.println(d);
        }
    }
}
