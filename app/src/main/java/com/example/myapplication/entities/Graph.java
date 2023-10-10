package com.example.myapplication.entities;

import android.util.Log;

import com.example.myapplication.BlankFragment;
import com.example.myapplication.MainActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Graph {

    public List<Edge> edgeList = new ArrayList<>();
    public Set<Vertex> vertices = new HashSet<>();


    public static Graph loadGraph(String string){
        Graph graph = new Graph();
        MainActivity.MyView view = BlankFragment.view;
        int width = view.getWidth();
        int height = view.getHeight();
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

        for(Edge edge: graph.edgeList){
            int w = (int) (Math.random() * (width - 1) + 1);
            int h = (int) (Math.random() * (height - 1) + 1);
            edge.getVertex1().setX(w);
            edge.getVertex1().setY(h);
            graph.vertices.add(edge.getVertex1  ());
            w = (int) (Math.random() * (width - 1) + 1);
            h = (int) (Math.random() * (height - 1) + 1);
            edge.getVertex2().setX(w);
            edge.getVertex2().setY(h);
            graph.vertices.add(edge.getVertex2());
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

    public int getCountEdge(){
        return edgeList.size();
    }

    public int getCountVertex(){
        return vertices.size();
    }

    public boolean isAdjacent(Vertex vertex1, Vertex vertex2){
        for(Edge edge: edgeList){
            if((edge.getVertex1().equals(vertex1) && edge.getVertex2().equals(vertex2)) ||
                    (edge.getVertex2().equals(vertex1) && edge.getVertex1().equals(vertex2)))
                return true;
        }
        return false;
    }

    public Edge getEdgeByAdjacent(Vertex vertex1, Vertex vertex2){
        for(Edge edge: edgeList){
            if((edge.getVertex1().equals(vertex1) && edge.getVertex2().equals(vertex2)) ||
                    (edge.getVertex2().equals(vertex1) && edge.getVertex1().equals(vertex2)))
                return edge;
        }
        return null;
    }

    public int getWeight(Vertex vertex1, Vertex vertex2){
        Edge edge = getEdgeByAdjacent(vertex1, vertex2);
        if(edge!= null) return edge.getWeight();
        return 0;
    }
}
