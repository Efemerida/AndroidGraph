package com.example.myapplication.entities;

import android.util.Log;

import java.util.List;

public class Graph {

    public List<Edge> edgeList;
    public List<Vertex> vertices;


    public static Graph loadGraph(String string){
        Graph graph = new Graph();
        String[] edges = string.split("\n");
        for(String edge:edges){
            Edge edgeTmp = new Edge();
            String[] edgeStr = edge.split(" ");
            Log.d("taggg", "graph is " + edgeStr[0]);
            Vertex vertex1 = new Vertex(Integer.parseInt(edgeStr[0]));
            Vertex vertex2 = new Vertex(Integer.parseInt(edgeStr[1]));
            edgeTmp.setVertex1(vertex1);
            edgeTmp.setVertex2(vertex2);
            edgeTmp.setWeight(Integer.parseInt(edgeStr[2]));
            graph.edgeList.add(edgeTmp);
        }
        return graph;

    }

    @Override
    public String toString() {
        return "Graph{" +
                "edgeList=" + edgeList +
                ", vertices=" + vertices +
                '}';
    }
}
