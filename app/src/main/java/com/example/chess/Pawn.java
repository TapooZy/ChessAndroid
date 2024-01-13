package com.example.chess;

import android.util.Log;
import android.widget.Toast;

import java.util.Scanner;
import java.util.Scanner;

public class Pawn extends Piece{
    boolean didMove;

    public Pawn(char color, int row, int col){
       super(color, 'p', row, col, "Pawn");
       didMove = false;
    }

    public Queue<Integer> getPossibleMoves(Board board) {
        return null;
    }

    public Queue<Integer> getPawnPossibleMoves(Board board, int[] enPassantLocation){
        int row1, col1;
        Queue<Integer> moves = new Queue<>();
        if (color == 'b') {
            row1 = row +1;
            if (row1 < 8){
                if (board.getBoard()[row1][col] == null) { // 1 step
                    moves.insert(row1, col);
                    if (!didMove) {
                        row1 = row + 2;
                        if (board.getBoard()[row1][col] == null) { // 2 steps
                            moves.insert(row1, col);
                        }
                    }
                }
            }
            row1 = row +1;
            col1 = col +1;
            if (row1 < 8 && col1 < 8){
                if (board.getBoard()[row1][col1] != null){ // eat right
                    if (board.getBoard()[row1][col1].getColor() != this.color) {
                        moves.insert(row1, col1);
                    }
                }
            }
            row1 = row +1;
            col1 = col -1;
            if (row1 < 8 && col1 > -1){
                if (board.getBoard()[row1][col1] != null){ // eat left
                    if (board.getBoard()[row1][col1].getColor() != this.color) {
                        moves.insert(row1, col1);
                    }
                }
            }
            if (row == 4){
                if (canEnPassantLeft(board, enPassantLocation)) {
                    moves.insert(row + 1, col - 1);
                }
                if (canEnPassantRight(board, enPassantLocation)) {
                    moves.insert(row + 1, col + 1);
                }
            }
        }
        else {
            row1 = row - 1;
            if (board.getBoard()[row1][col] == null) { // 1 step
                moves.insert(row1, col);
                if (!didMove) {
                    row1 = row - 2;
                    if (board.getBoard()[row1][col] == null) { // 2 steps
                        moves.insert(row1, col);
                    }
                }
            }
            row1 = row - 1;
            col1 = col + 1;
            if (row1 > -1 && col1 < 8) {
                if (board.getBoard()[row1][col1] != null) { // eat right
                    if (board.getBoard()[row1][col1].getColor() != this.color) {
                        moves.insert(row1, col1);
                    }
                }
            }
            row1 = row - 1;
            col1 = col - 1;
            if (row1 > -1 && col1 > -1) {
                if (board.getBoard()[row1][col1] != null) { // eat left
                    if (board.getBoard()[row1][col1].getColor() != this.color) {
                        moves.insert(row1, col1);
                    }
                }
            }
            if (row == 3){
                if (canEnPassantLeft(board, enPassantLocation)) {
                    moves.insert(row - 1, col - 1);
                }
                if (canEnPassantRight(board, enPassantLocation)) {
                    moves.insert(row - 1, col + 1);
                }
            }
        }
        return moves;
    }

    public boolean canEnPassantLeft(Board board, int[] enPassantLocation){
        if (col - 1 > -1){
            if (enPassantLocation != null) {
                if (enPassantLocation[0] == row && enPassantLocation[1] == col - 1) {
                    if (color == 'b') {
                        if (board.getBoard()[row + 1][col - 1] == null) {
                            return true;
                        }
                    } else {
                        if (board.getBoard()[row - 1][col - 1] == null) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean canEnPassantRight(Board board, int[] enPassantLocation){
        if (col + 1 < 8){
            if (enPassantLocation != null) {
                if (enPassantLocation[0] == row && enPassantLocation[1] == col + 1) {
                    if (color == 'b') {
                        if (board.getBoard()[row + 1][col + 1] == null) {
                            return true;
                        }
                    } else {
                        if (board.getBoard()[row - 1][col + 1] == null) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    @Override
    public void move(Board board, int row, int col){
    }

    public void pawnMove(Board board, int row, int col, int[] enPassantLocation){
        int size;
        int[] availableMove;
        Queue<Integer> moves = this.getPawnPossibleMoves(board, enPassantLocation);
        size = moves.getSize();
        for (int i = 0; i < size; i++) {
            availableMove = moves.remove();
            if (row == availableMove[0] && col == availableMove[1]){
                board.getBoard()[row][col] = this;
                if (enPassantLocation != null){
                    if (color == 'b'){
                        if (enPassantLocation[0] == row-1 && enPassantLocation[1] == col){
                            board.getBoard()[row-1][col] = null;
                        }
                    }
                    else {
                        if (enPassantLocation[0] == row+1 && enPassantLocation[1] == col) {
                            Log.d("move", "enPassant");
                            board.getBoard()[row + 1][col] = null;
                        }
                    }
                }
                board.getBoard()[this.row][this.col] = null;
                this.setRow(row);
                this.setCol(col);
                didMove = true;
                break;
            }
        }
    }
}
