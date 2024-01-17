package com.example.chess;

import java.util.*;

public class Engine {
    private Board board;
    private int[] enPassantLocation;
    private Piece king;
    public Engine(){
        this.board = new Board();
        board.startGame();
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
}
