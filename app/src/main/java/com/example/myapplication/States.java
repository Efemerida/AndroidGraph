package com.example.myapplication;

public enum States {

    ADD_POINT(0),
    ADD_LINE(1),
    DELETE_LINE(2),
    DELETE_POINT(3);

    public int position;

    States (int position){
        this.position = position;
    }

    public static States getState(int position){
        if(position==0) return ADD_POINT;
        if(position==1) return ADD_LINE;
        if(position==3) return DELETE_POINT;
        if(position==2) return DELETE_LINE;
        return null;
    }

}
