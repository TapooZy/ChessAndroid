package com.example.chess;

import java.util.Scanner;

import java.util.Scanner;

public class Queen extends Piece{

    public Queen(char color, int row, int col){
        super(color, 'q', row, col);
    }

    public Queue<Integer> getPossibleMoves(Board board, boolean checkChecked) {
        Queue<Integer> moves = new Queue<>();
        Board newBoard = board.clone();
        newBoard.getBoard()[row][col] = new Rook(color, row, col);
        Queue<Integer> RookMoves = newBoard.getBoard()[row][col].getPossibleMoves(newBoard, true);
        newBoard.getBoard()[row][col] = new Bishop(color, row, col);
        Queue<Integer> BishopMoves = newBoard.getBoard()[row][col].getPossibleMoves(newBoard, true);
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
    public void move(Board board, int row, int col){
        board.getBoard()[row][col] = new Queen(color, row, col);
        board.getBoard()[this.row][this.col] = null;

    }
}
