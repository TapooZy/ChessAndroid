package com.example.chess;

public class Engine {
    private Board board;
    private int[] enPassantLocation;
    private MoveNode<Move> history = new MoveNode<>(null);
    public Engine(){
        this.board = new Board();
        board.startGame();
    }

    public Engine(Board board){
        this.board = board;
    }
    public Engine(Board board, int[] enPassantLocation) {
        this.board = board;
        this.enPassantLocation = enPassantLocation;
    }

    public int[] getEnPassantLocation() {
        return enPassantLocation;
    }

    public void setEnPassantLocation(int[] enPassantLocation) {
        this.enPassantLocation = enPassantLocation;
    }

    public Board getBoard() {
        return board;
    }

    public MoveNode<Move> getHistory() {
        return history;
    }

    public Engine clone(){
        return new Engine(board.clone(), enPassantLocation);
    }
}
