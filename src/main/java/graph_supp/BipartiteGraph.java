package graph_supp;

import utils.Graph;

import java.util.HashSet;

public class BipartiteGraph {

    enum COLOR{
        WHITE,
        BLACK
    }

    COLOR[] colors;
    HashSet<Integer> firstGroup;
    HashSet<Integer> secondGroup;
    boolean Bipartite;
    boolean[] visited;

    BipartiteGraph(Graph g){
        colors = new COLOR[g.V()];
        visited = new boolean[g.V()];
        firstGroup = new HashSet<>();
        secondGroup = new HashSet<>();
        Bipartite = true;
        for (int i = 0; i < g.V(); i++) {
            if (!visited[i])
                dfs(g, i, COLOR.BLACK);
                if (!Bipartite) return;
        }
    }

    public static COLOR opposite(COLOR c){
        return c == COLOR.WHITE ? COLOR.BLACK : COLOR.WHITE;
    }

    public void dfs(Graph g, int v, COLOR color){
        visited[v] = true;
        colors[v] = color;
        if (color == COLOR.WHITE) firstGroup.add(v);
        else secondGroup.add(v);
        for (Integer w : g.adj(v)){
            if (!Bipartite) return;
            if (colors[w] == colors[v])
                Bipartite = false;
            if (!visited[w])
                dfs(g, w, opposite(color));
        }
    }

    public boolean isBipartite(){
        return Bipartite;
    }

    public Iterable<Integer> firstSet(){
        return firstGroup;
    }

    public Iterable<Integer> secondSet(){
        return secondGroup;
    }

    public static void main(String[] args) {
        Graph g = new Graph(4);
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(2, 3);
        g.addEdge(3, 0);

        BipartiteGraph b = new BipartiteGraph(g);
        System.out.println(b.isBipartite());
        if (b.isBipartite()){
            System.out.println(b.firstSet());
            System.out.println(b.secondSet());
        }
    }
}
