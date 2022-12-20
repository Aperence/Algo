package graph_supp;

import utils.Graph;

public class NodeRemoval {

    static int minimumNode;
    static boolean visited[];
    public static int removeNode(Graph g){
        visited = new boolean[g.V()];
        minimumNode = -1;
        for (int i = 0; i < g.V(); i++) {
            if (!visited[i])
                dfs(g, i);
        }
        return minimumNode;
    }

    public static void dfs(Graph g, int v){
        visited[v] = true;
        boolean allVisited = true;
        for (int w : g.adj(v)){
            if (!visited[w]){
                allVisited = false;
                dfs(g, w);
            }
        }
        if (allVisited) minimumNode = v;
    }

}
