package testp;

import com.company.IGraph;
import com.company.UndirectedGraph;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        MyVertex[] v = new MyVertex[11];

        for (int i = 0; i < v.length; i++) {
            v[i] = new MyVertex(i);
        }

        IGraph g = new UndirectedGraph();

        for (int i = 0; i < v.length; i++) {
            g.addVertex(v[i]);
        }

        addEdgeIntoGraph(g, v, 1, 2);
        addEdgeIntoGraph(g, v, 1, 4);
        addEdgeIntoGraph(g, v, 2, 10);
        addEdgeIntoGraph(g, v, 4, 9);
        addEdgeIntoGraph(g, v, 4, 10);
        addEdgeIntoGraph(g, v, 5, 6);
        addEdgeIntoGraph(g, v, 5, 7);
        addEdgeIntoGraph(g, v, 7, 9);
        addEdgeIntoGraph(g, v, 8, 9);
        addEdgeIntoGraph(g, v, 9, 10);

        List<Object> res = g.getPath(v[1], v[6]);
        System.out.println(Arrays.toString(res.toArray()));
    }

    public static void addEdgeIntoGraph (IGraph graph, MyVertex[] v, int vert1, int vert2 ) {
        graph.addEdge(v[vert1], v[vert2]);
    }
}
