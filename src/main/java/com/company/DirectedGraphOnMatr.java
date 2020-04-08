package com.company;

public final class DirectedGraphOnMatr<Vertex> extends GraphOnMatrix<Vertex> {
    @Override
    protected void doAddEdge(Vertex from, Vertex to) {
        if (!vertices.containsKey(from) || !vertices.containsKey(to)) {
            throw new RuntimeException("Could not add edge because not all vertices do exist");
        }
        if (from.equals(to)) {
            throw new RuntimeException("Could not add edge to the same vertex");
        }

        vertices.get(from).add(new Edge<>(to));
    }
}
