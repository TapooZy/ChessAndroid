package com.example.chess;

import java.util.Scanner;
import java.util.Scanner;

public class Pawn extends Piece{

    public Pawn(char color, int row, int col){
        super(color, 'p', row, col, "Pawn");
    }

    public Queue<Integer> getPossibleMoves(Board board) {
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
                if (canEnPassantLeft(board)) {
                    moves.insert(row + 1, col - 1);
                }
                if (canEnPassantRight(board)) {
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
                if (canEnPassantLeft(board)) {
                    moves.insert(row - 1, col - 1);
                }
                if (canEnPassantRight(board)) {
                    moves.insert(row - 1, col + 1);
                }
            }
        }
        return moves;
    }

    public boolean canEnPassantLeft(Board board){
        if (col - 1 > -1){
            if (board.getBoard()[row][col - 1] != null){
                if (board.getBoard()[row][col - 1].getLetter() == 'p'){
                    if (board.getBoard()[row][col - 1].getColor() != this.color){
                        if (board.getBoard()[row][col - 1].wasFirstMove) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean canEnPassantRight(Board board){
        if (col + 1 < 8){
            if (board.getBoard()[row][col + 1] != null){
                if (board.getBoard()[row][col + 1].getLetter() == 'p'){
                    if (board.getBoard()[row][col + 1].getColor() != this.color){
                        if (board.getBoard()[row][col + 1].wasFirstMove) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    @Override
    public boolean move(Board board, int row, int col){
        int size, formerRow, formerCol;
        int[] availableMove;
        Queue<Integer> moves = board.getBoard()[this.row][this.col].getPossibleMoves(board);
        size = moves.getSize();
        for (int i = 0; i < size; i++) {
            availableMove = moves.remove();
            if (row == availableMove[0] && col == availableMove[1]){
                board.getBoard()[row][col] = this;
                formerRow = this.row;
                formerCol = this.col;
                if ((formerCol == col - 1 || formerCol == col + 1) && color == 'w'){
                    board.getBoard()[row+1][col] = null;
                }
                else if ((formerCol == col - 1 || formerCol == col + 1) && color == 'b'){
                    board.getBoard()[row-1][col] = null;
                }
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
                if (this.row == formerRow + 2 || this.row == formerRow - 2){
                    this.setWasFirstMove(true);
                }
                return true;
            }
        }
        return false;
    }
}
