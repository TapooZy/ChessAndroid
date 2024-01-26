package com.example.chess;
abstract public class Piece {
    protected char color;
    protected char letter;
    protected int row;
    protected int col;
    protected boolean didMove;
    protected int value;
    public Piece(char color, char letter, int row, int col, int value) {
        this.color = color;
        this.letter = letter;
        this.row = row;
        this.col = col;
        this.value = value;
        didMove = false;
    }
    public abstract Queue<Location> getPossibleMoves(Board board, boolean checkCheck);

    public abstract void move(Board board, int row, int col);

    public abstract Piece clone();
}
