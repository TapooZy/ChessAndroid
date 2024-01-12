package com.example.chess;

import java.util.Scanner;

import java.util.Scanner;

public class Queen extends Piece{

    public Queen(char color, int row, int col){
        super(color, 'q', row, col, "Queen");
    }

    public Queue<Integer> getPossibleMoves(Board board) {
        Queue<Integer> moves = new Queue<>();
        Board newBoard = board.clone();
        newBoard.getBoard()[row][col] = new Rook(color, row, col);
        Queue<Integer> RookMoves = newBoard.getBoard()[row][col].getPossibleMoves(newBoard);
        newBoard.getBoard()[row][col] = new Bishop(color, row, col);
        Queue<Integer> BishopMoves = newBoard.getBoard()[row][col].getPossibleMoves(newBoard);
        int RookMovesSize = RookMoves.getSize();
        int[] individualMove;
        for (int i = 0; i < RookMovesSize; i++) {
            individualMove = RookMoves.remove();
            moves.insert(individualMove[0], individualMove[1]);
        }
        int BishopMovesSize = BishopMoves.getSize();
        for (int i = 0; i < BishopMovesSize; i++) {
            individualMove = BishopMoves.remove();
            moves.insert(individualMove[0], individualMove[1]);
        }
        return moves;
    }
    @Override
    public boolean move(Board board, int row, int col){
        int size;
        int[] availableMove;
        Queue<Integer> moves = board.getBoard()[this.row][this.col].getPossibleMoves(board);
        size = moves.getSize();
        for (int i = 0; i < size; i++) {
            availableMove = moves.remove();
            if (row == availableMove[0] && col == availableMove[1]){
                board.getBoard()[row][col] = this;
                board.getBoard()[this.row][this.col] = null;
                this.setRow(row);
                this.setCol(col);
                for (int k = 0; k < 7; k++) {
                    for (int j = 0; j < 7; j++) {
                        if (board.getBoard()[k][j] != null){
                            if (board.getBoard()[k][j].getWasFirstMove()){
                                board.getBoard()[k][j].setWasFirstMove(false);
                            }
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }
}
