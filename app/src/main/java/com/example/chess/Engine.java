package com.example.chess;

import java.util.*;

public class Engine {
    private Board board;
    private int[] enPassantLocation;
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

    public Engine clone(){
        return new Engine(board.clone(), enPassantLocation.clone());
    }
}
