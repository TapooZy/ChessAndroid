package com.example.chess;

public class Location {
    public int row;
    public int col;

    public Location(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public String toString(){
        return "(" + row + ", " + col + ")";
    }
}
