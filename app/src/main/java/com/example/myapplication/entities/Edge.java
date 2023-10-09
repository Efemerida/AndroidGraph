package com.example.myapplication.entities;

import java.util.ArrayList;
import java.util.ArrayList;
import java.util.List;

public class Edge {

    private Vertex vertex1;
    private Vertex vertex2;

    private int weight;

    public Edge() {
    }

    public Edge(Vertex vertex1, Vertex vertex2) {
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
    }
    public List<Integer> getPlaceForWeight(){
        List<Integer> res=new ArrayList<>();
        int offset=70;
        double forCalc=offset/
                Math.pow((Math.pow(vertex1.getY()-vertex2.getY(),2))+
                        Math.pow(vertex2.getX()-vertex1.getX(),2),0.5);
        int coord1=(int)((vertex2.getX()+vertex1.getX())/2-forCalc*(vertex1.getY()-vertex2.getY()));

        int coord2=(int)((vertex2.getY()+vertex1.getY())/2-forCalc*(vertex2.getX()-vertex1.getX()));
        res.add(coord1);
        res.add(coord2);
        return res;
    }
    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Vertex getVertex1() {
        return vertex1;
    }

    public void setVertex1(Vertex vertex1) {
        this.vertex1 = vertex1;
    }

    public Vertex getVertex2() {
        return vertex2;
    }

    public void setVertex2(Vertex vertex2) {
        this.vertex2 = vertex2;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "vertex1=" + vertex1 +
                ", vertex2=" + vertex2 +
                ", weight=" + weight +
                '}';
    }
}
