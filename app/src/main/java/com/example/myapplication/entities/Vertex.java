package com.example.myapplication.entities;

public class Vertex {

    private int x;
    private int y;
    private int radius;

    private int number;

    public Vertex(int x, int y, int radius, int number) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.number = number;
    }

    public Vertex(int number){
        this.number = number;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Vertex{" +
                "x=" + x +
                ", y=" + y +
                ", radius=" + radius +
                ", number=" + number +
                '}';
    }
}
