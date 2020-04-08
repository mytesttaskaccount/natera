package com.company;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


abstract class Graph<Vertex> implements IGraph<Vertex> {
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

    Map<Vertex, Set<Edge<Vertex>>> vertices = new HashMap<>();

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
}
