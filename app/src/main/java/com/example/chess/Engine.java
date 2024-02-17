package com.example.chess;

import android.util.Log;

public class Engine {
    private Board board;
    private int[] enPassantLocation;
    private MoveNode<Move> history = new MoveNode<Move>(null);
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

    public void deMove(){
        MoveNode<Move> curr;
        curr = history;
        Piece piece;
        board.startGame();
        if (curr != null){
            if (curr.getNext() != null){
                if (curr.getNext().getNext() != null) {
                    while (curr.getNext().getNext() != null) {
                        piece = board.getBoard()[curr.move.from.row][curr.move.from.col];
                        if (piece.letter == 'p') {
                            ((Pawn) piece).pawnMove(board, curr.move.to.row, curr.move.to.col, curr.move.enPassant_location);
                        } else {
                            piece.move(board, curr.move.to.row, curr.move.to.col);
                        }
                        curr = curr.getNext();
                    }
                    enPassantLocation = curr.getNext().move.enPassant_location;
                    curr.setNext(null);
                }
                if (curr.move == null){
                    Log.d("hi", "hi");
                }
                piece = board.getBoard()[curr.move.from.row][curr.move.from.col];
                if (piece.letter == 'p') {
                    ((Pawn) piece).pawnMove(board, curr.move.to.row, curr.move.to.col, curr.move.enPassant_location);
                } else {
                    piece.move(board, curr.move.to.row, curr.move.to.col);
                }
                curr.setNext(null);
            }
            else{
                history = new MoveNode<>(null);
            }
        }
    }

    public int evaluate(){
        int whiteSum = 0, blackSum = 0;
        Piece piece;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                piece = board.getBoard()[i][j];
                if (piece != null){
                    if (piece.color == 'w'){
                        whiteSum += piece.value;
                    }
                    else {
                        blackSum += piece.value;
                    }
                }
            }
        }
        return whiteSum - blackSum;
    }
}
