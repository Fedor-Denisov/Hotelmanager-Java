package com.company;

public class Human {
    String name;
    String id;
    int days;
    int WantLive;
    double Price;

    public Human(String name, String id, int days, int Wantlive) {
        this.name = name;
        this.id = id;
        this.days = days;
        this.WantLive = Wantlive;
    }

    @Override
    public String toString() {
        return name + " " + id + " " + days + " " + WantLive + "" + +Price;
    }
}