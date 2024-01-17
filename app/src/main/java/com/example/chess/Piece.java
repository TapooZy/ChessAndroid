package com.example.chess;
abstract public class Piece {
    protected char color;
    protected char letter;
    protected int row;
    protected int col;
    protected String name;
    public Piece(char color, char letter, int row, int col, String name) {
        this.color = color;
        this.letter = letter;
        this.row = row;
        this.col = col;
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public char getLetter() {
        return letter;
    }

    public char getColor() {
        return color;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }


    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public abstract Queue<Integer> getPossibleMoves(Board board, boolean checkCheck);

    public abstract void move(Board board, int row, int col);

    public abstract void testMove(Board board, int row, int col);
}
