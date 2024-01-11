package com.example.chess;

import java.util.*;
abstract public class Piece {
    protected char color;
    protected char letter;
    protected int row;
    protected int col;
    protected boolean didMove;
    protected boolean wasFirstMove;

    public Piece(char color, char letter, int row, int col) {
        this.color = color;
        this.letter = letter;
        this.row = row;
        this.col = col;
        this.didMove = false;
        this.wasFirstMove = false;
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

    public boolean getDidMove()
    {
        return didMove;
    }

    public boolean getWasFirstMove() {
        return wasFirstMove;
    }

    public void setWasFirstMove(boolean wasFirstMove) {
        this.wasFirstMove = wasFirstMove;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public abstract Queue<Integer> getPossibleMoves(Board board, boolean isAllMoves);

    public abstract void move(Board board);
}
