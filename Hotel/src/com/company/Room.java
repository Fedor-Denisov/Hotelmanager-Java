package com.company;

import java.util.ArrayList;

public class Room {
    int Number;
    int freeBeds;
    int Maximum;
    int square;
    double Ver;
    ArrayList<Human> guests = new ArrayList<>();

    public Room(int Number, int freeBeds, int Maximum, int square, double Ver) {
        this.Number = Number;
        this.freeBeds = freeBeds;
        this.Maximum = Maximum;
        this.square = square;
        this.Ver = Ver;
    }

    @Override
    public String toString() {
        return "Number " + Number + " | " + "Area " + square + " | " + " Busy " + guests.size() + "/" + Maximum + " | " + "guests" + guests.toString();
    }
}