package com.company;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;


abstract class GraphOnMatrix<Vertex> implements IGraph<Vertex> {
    protected static final class Edge<Vertex> {
        private Vertex to;

        protected Edge(Vertex to) {
            if (to == null) {
                throw new RuntimeException("Could not create edge to null-vertex");
            }
            this.to = to;
        }

        public Vertex to() {
            return to;
        }
    }

    Map<Vertex, Set<Edge<Vertex>>> vertices = new LinkedHashMap<>();

    @Override
    public void addVertex(Vertex vertex) {
        if (vertex == null) {
            throw new RuntimeException("Vertex could not be null");
        }
        vertices.put(vertex, new HashSet<>());
    }

    @Override
    public void addEdge(Vertex from, Vertex to) {
        doAddEdge(from, to);
    }

    protected abstract void doAddEdge(Vertex from, Vertex to);

    @Override
    public List<Vertex> getPath(Vertex vertex1, Vertex vertex2) {
        return getAllPaths(vertex1, vertex2).stream().min(Comparator.comparingInt(List::size)).orElse(Collections.emptyList());
    }

    public List<List<Vertex>> getAllPaths(Vertex vertex1, Vertex vertex2) {
        if (!vertices.containsKey(vertex1)) {
            throw new RuntimeException("Vertex \"from\" does not exist");
        }
        if (!vertices.containsKey(vertex2)) {
            throw new RuntimeException("Vertex \"to\" does not exist");
        }

        List<List<Vertex>> found = new ArrayList<>();
        List<Vertex> res = new ArrayList<>();
        // quick case - the same vertex or neighbours
        if (vertex1.equals(vertex2)) {
            res.add(vertex1);
            found.add(res);
        } else if (vertices.get(vertex1).stream().map(Edge::to).anyMatch(v -> v.equals(vertex2))) {
            res.add(vertex1);
            res.add(vertex2);
            found.add(res);
        } else {
            findPath(vertex1, vertex2, new ArrayList<>(), found);
        }
        return found;
    }

    private List<Vertex> findPath(Vertex vertex1, Vertex target, List<Vertex> visited, List<List<Vertex>> found) {
        List<Vertex> fromEdges = vertices.get(vertex1).stream()
                .map(Edge::to).filter(v -> !visited.contains(v)).collect(Collectors.toList());

        List<Vertex> res = null;
        for (Vertex vertex12 : fromEdges) {
            if (vertex12.equals(target)) {
                res = new ArrayList<>(visited);
                res.add(vertex1);
                res.add(vertex12);
            } else {
                visited.add(vertex1);

                res = findPath(vertex12, target, visited, found);
                if (res != null) {
                    found.add(res);
                    res = null;
                }
                visited.remove(vertex1);
            }
        }

        return res;
    }


    private List<Integer> getAllMatrPath(Long[][] matr, int from, int to, Integer[] path, List<List<Integer>> foundPath) {
        List<Integer> res = null;
        for (int i = 0; i < matr[from].length; i++) {
            if (matr[from][i] != null) {
                // the edge does exist
                int size = getSize(path);
                if (i == to) {
                    // path is found
                    path[size] = from;
                    path[size + 1] = to;
                    res = new ArrayList<>(Arrays.asList(Arrays.copyOf(path, size)));
                    foundPath.add(res);
                    return res;
                }
                else {
                    path[size] = from;
                    res = getAllMatrPath(matr, from, to, path, foundPath);
                    if (res != null) {
                        foundPath.add(res);
                        res = null;
                    }
                    path[size] = null;
                }
            }
        }
        return res;
    }

    public List<List<Vertex>> getAllMatrPaths(Vertex vertex1, Vertex vertex2) {
        if (!vertices.containsKey(vertex1)) {
            throw new RuntimeException("Vertex \"from\" does not exist");
        }
        if (!vertices.containsKey(vertex2)) {
            throw new RuntimeException("Vertex \"to\" does not exist");
        }
        Long[][] matr = convertToMatr();
        int from = getIndex(vertex1);
        int to = getIndex(vertex2);

        List<List<Integer>> found = new ArrayList<>();
        List<Integer> res = new ArrayList<>();
        // quick case - the same vertex or neighbours
        if (from == to) {
            res.add(from);
            found.add(res);
        } else if (vertices.get(vertex1).stream().map(Edge::to).anyMatch(v -> v.equals(vertex2))) {
            res.add(from);
            res.add(to);
            found.add(res);
        } else {
            getAllMatrPath(matr, from, to, new Integer[vertices.size()], found);
        }
        return found.stream().map(this::convertFromMatr).collect(Collectors.toList());
    }

    private Long[][] convertToMatr () {
        Long[][] res = new Long[vertices.size()][vertices.size()];
        Vertex[] froms = (Vertex[])vertices.keySet().toArray();

        for (int from = 0; from < froms.length; from++) {
            Set<Vertex> tos = vertices.get(froms[from]).stream().map(Edge::to).collect(Collectors.toSet());
                // find vertex by number
                for (int i = 0; i < froms.length; i++) {
                    if (tos.contains(froms[i])) {
                        res[from][i] = 1L;
                        break;
                }
            }
        }
        return res;
    }

    private List<Vertex> convertFromMatr(List<Integer> path) {
        Vertex[] vertexes = vertices.keySet().toArray((Vertex[]) new Object[0]);
        List<Vertex> res = new ArrayList<>();

        for (Integer i : path) {
            res.add(vertexes[i]);
        }
        return res;
    }

    private int getIndex(Vertex v) {
        Object[] o = vertices.keySet().toArray();
        for (int i = 0; i < o.length; i++) {
            if (o[i].equals(v)) {
                return i;
            }
        }
        return -1;
    }

    private int getSize(Integer[] path) {
        int size;
        for (size = path.length - 1; size >= 0; size--) {
            if (path[size] != null) {
                break;
            }
        }
        return size;
    }
}
