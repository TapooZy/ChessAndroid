package com.example.chess;
abstract public class Piece {
    protected char color;
    protected char letter;
    protected int row;
    protected int col;
    protected boolean didMove;
    public Piece(char color, char letter, int row, int col) {
        this.color = color;
        this.letter = letter;
        this.row = row;
        this.col = col;
        didMove = false;
    }
    public abstract Queue<Integer> getPossibleMoves(Board board, boolean checkCheck);

    public abstract void move(Board board, int row, int col);
}
