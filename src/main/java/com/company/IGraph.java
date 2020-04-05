package com.company;

import java.util.List;

public interface IGraph<Vertex> {
    void addVertex(Vertex vertex);

    void addEdge(Vertex from, Vertex to);

    List<Vertex> getPath(Vertex vertex1, Vertex vertex2);
}
