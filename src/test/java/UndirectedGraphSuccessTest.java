import com.company.IGraph;
import com.company.UndirectedGraph;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import testp.MyVertex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UndirectedGraphSuccessTest {
    private UndirectedGraph<MyVertex> g;
    private MyVertex[] v;

    @Before
    public void setUp() {
        v = new MyVertex[11];

        for (int i = 0; i < v.length; i++) {
            v[i] = new MyVertex(i);
        }

        g = new UndirectedGraph<>();

        for (int i = 0; i < v.length; i++) {
            g.addVertex(v[i]);
        }
    }

    @Test
    public void emptyPathTest() {

        addEdgeIntoGraph(g, v, 1, 2);
        addEdgeIntoGraph(g, v, 1, 4);
        addEdgeIntoGraph(g, v, 5, 6);
        addEdgeIntoGraph(g, v, 5, 7);
        addEdgeIntoGraph(g, v, 7, 9);
        addEdgeIntoGraph(g, v, 8, 9);
        addEdgeIntoGraph(g, v, 9, 10);

        // 1 -> 3
        List<List<MyVertex>> actual = g.getAllPaths(v[1], v[3]);
        Assert.assertTrue(actual.isEmpty());
        // 3 -> 1
        actual = g.getAllPaths(v[3], v[1]);
        Assert.assertTrue(actual.isEmpty());

        // 1 -> 6
        actual = g.getAllPaths(v[1], v[6]);
        Assert.assertTrue(actual.isEmpty());
        // 6 -> 1
        actual = g.getAllPaths(v[6], v[1]);
        Assert.assertTrue(actual.isEmpty());

        // 3 -> 6
        actual = g.getAllPaths(v[3], v[6]);
        Assert.assertTrue(actual.isEmpty());
    }

    @Test
    public void singleRouteTest() {
        fillEdges();
        List<List<MyVertex>> actual;
        for (int i = 0; i < v.length; i++) {
            // 1 -> 6
            actual = g.getAllPaths(v[i], v[i]);
            Assert.assertEquals(1, actual.size());
        }
    }

    @Test
    public void findPathTest1() {

        fillEdges();

        // 1 -> 6
        List<List<MyVertex>> actual = g.getAllPaths(v[1], v[6]);
        System.out.println(Arrays.toString(actual.toArray()));
//[MyVertex{i=1}, MyVertex{i=4}, MyVertex{i=9}, MyVertex{i=7}, MyVertex{i=5}, MyVertex{i=6}],
        List<List<MyVertex>> allExpected = new ArrayList<>();
        List<MyVertex> expected1 = new ArrayList<>();
        expected1.add(v[1]);
        expected1.add(v[4]);
        expected1.add(v[9]);
        expected1.add(v[7]);
        expected1.add(v[5]);
        expected1.add(v[6]);

//[MyVertex{i=1}, MyVertex{i=4}, MyVertex{i=10}, MyVertex{i=9}, MyVertex{i=7}, MyVertex{i=5}, MyVertex{i=6}],
        List<MyVertex> expected2 = new ArrayList<>();
        expected2.add(v[1]);
        expected2.add(v[4]);
        expected2.add(v[10]);
        expected2.add(v[9]);
        expected2.add(v[7]);
        expected2.add(v[5]);
        expected2.add(v[6]);

//[MyVertex{i=1}, MyVertex{i=2}, MyVertex{i=10}, MyVertex{i=4}, MyVertex{i=9}, MyVertex{i=7}, MyVertex{i=5}, MyVertex{i=6}],
        List<MyVertex> expected3 = new ArrayList<>();
        expected3.add(v[1]);
        expected3.add(v[2]);
        expected3.add(v[10]);
        expected3.add(v[4]);
        expected3.add(v[9]);
        expected3.add(v[7]);
        expected3.add(v[5]);
        expected3.add(v[6]);

//[MyVertex{i=1}, MyVertex{i=2}, MyVertex{i=10}, MyVertex{i=9}, MyVertex{i=7}, MyVertex{i=5}, MyVertex{i=6}]
        List<MyVertex> expected4 = new ArrayList<>();
        expected4.add(v[1]);
        expected4.add(v[2]);
        expected4.add(v[10]);
        expected4.add(v[9]);
        expected4.add(v[7]);
        expected4.add(v[5]);
        expected4.add(v[6]);

        allExpected.add(expected1);
        allExpected.add(expected2);
        allExpected.add(expected3);
        allExpected.add(expected4);

        Assert.assertEquals(allExpected.size(), actual.size());

        for (List<MyVertex> l : allExpected) {
            boolean flag = false;
            for (int i = 0; i < actual.size(); i++) {
                if (compareArrays(l.toArray(), actual.get(i).toArray())) {
                    actual.remove(i);
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                Assert.fail(Arrays.toString(l.toArray()) + " not found");
            }
        }

        List<MyVertex> bestWay = g.getPath(v[1], v[6]);
        Assert.assertTrue(compareArrays(bestWay.toArray(), expected1.toArray()));
    }

    private boolean compareArrays(Object[] o1, Object[] o2) {
        if (o1.length != o2.length) {
            return false;
        }
        for (int i = 0; i < o1.length; i++) {
            if (!o1[i].equals(o2[i])) {
                return false;
            }
        }
        return true;
    }

    private void addEdgeIntoGraph(IGraph<MyVertex> graph, MyVertex[] v, int vert1, int vert2) {
        graph.addEdge(v[vert1], v[vert2]);
    }

    private void fillEdges() {
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
    }
}
