package com.example.chess;

import android.util.Log;

public class Pawn extends Piece{
    public Pawn(char color, int row, int col){
       super(color, 'p', row, col);
    }

    public Queue<Integer> getPossibleMoves(Board board, boolean checkCheck) {
        return null;
    }

    public Queue<Integer> getPawnPossibleMoves(Board board, int[] enPassantLocation, boolean checkCheck) {
        int row1, col1;
        Queue<Integer> moves = new Queue<>();
        Board newBoard;
        if (color == 'b') {
            row1 = row - 1;
            if (board.getBoard()[row1][col] == null) { // 1 step
                if (checkCheck) {
                    newBoard = board.clone();
                    pawnMove(newBoard, row1, col, enPassantLocation);
                    if (newBoard.canMove(this)) {
                        moves.insert(row1, col);
                    }
                } else {
                    moves.insert(row1, col);
                }
            }
            if (!didMove) {
                row1 = row - 2;
                if (board.getBoard()[row1][col] == null) { // 2 steps
                    if (checkCheck) {
                        newBoard = board.clone();
                        pawnMove(newBoard, row1, col, enPassantLocation);
                        if (newBoard.canMove(this)) {
                            moves.insert(row1, col);
                        }
                    } else {
                        moves.insert(row1, col);
                    }
                }
            }
            row1 = row - 1;
            col1 = col + 1;
            if (row1 < 8 && col1 < 8) {
                if (board.getBoard()[row1][col1] != null) { // eat right
                    if (board.getBoard()[row1][col1].color != this.color) {
                        if (checkCheck) {
                            newBoard = board.clone();
                            pawnMove(newBoard, row1, col1, enPassantLocation);
                            if (newBoard.canMove(this)) {
                                moves.insert(row1, col1);
                            }
                        } else {
                            moves.insert(row1, col1);
                        }
                    }
                }
            }
            row1 = row - 1;
            col1 = col - 1;
            if (row1 < 8 && col1 > -1) {
                if (board.getBoard()[row1][col1] != null) { // eat left
                    if (board.getBoard()[row1][col1].color != this.color) {
                        if (checkCheck) {
                            newBoard = board.clone();
                            pawnMove(newBoard, row1, col1, enPassantLocation);
                            if (newBoard.canMove(this)) {
                                moves.insert(row1, col1);
                            }
                        } else {
                            moves.insert(row1, col1);
                        }
                    }
                }
            }
            if (row == 3) {
                if (canEnPassantLeft(board, enPassantLocation, checkCheck)) {
                    moves.insert(row - 1, col - 1);
                }
                if (canEnPassantRight(board, enPassantLocation, checkCheck)) {
                    moves.insert(row - 1, col + 1);
                }
            }
        } else {
            row1 = row + 1;
            if (board.getBoard()[row1][col] == null) { // 1 step
                if (checkCheck) {
                    newBoard = board.clone();
                    pawnMove(newBoard, row1, col, enPassantLocation);
                    if (newBoard.canMove(this)) {
                        moves.insert(row1, col);
                    }
                } else {
                    moves.insert(row1, col);
                }
                if (!didMove) {
                    row1 = row + 2;
                    if (board.getBoard()[row1][col] == null) { // 2 steps
                        if (checkCheck) {
                            newBoard = board.clone();
                            pawnMove(newBoard, row1, col, enPassantLocation);
                            if (newBoard.canMove(this)) {
                                moves.insert(row1, col);
                            }
                        } else {
                            moves.insert(row1, col);
                        }
                    }
                }
            }
            row1 = row + 1;
            col1 = col + 1;
            if (row1 > -1 && col1 < 8) {
                if (board.getBoard()[row1][col1] != null) { // eat right
                    if (board.getBoard()[row1][col1].color != this.color) {
                        if (checkCheck) {
                            newBoard = board.clone();
                            pawnMove(newBoard, row1, col1, enPassantLocation);
                            if (newBoard.canMove(this)) {
                                moves.insert(row1, col1);
                            }
                        } else {
                            moves.insert(row1, col1);
                        }
                    }
                }
            }
            row1 = row + 1;
            col1 = col - 1;
            if (row1 > -1 && col1 > -1) {
                if (board.getBoard()[row1][col1] != null) { // eat left
                    if (board.getBoard()[row1][col1].color != this.color) {
                        if (checkCheck) {
                            newBoard = board.clone();
                            pawnMove(newBoard, row1, col1, enPassantLocation);
                            if (newBoard.canMove(this)) {
                                moves.insert(row1, col1);
                            }
                        } else {
                            moves.insert(row1, col1);
                        }
                    }
                }
            }
            if (row == 4) {
                if (canEnPassantLeft(board, enPassantLocation, checkCheck)) {
                    moves.insert(row + 1, col - 1);
                }
                if (canEnPassantRight(board, enPassantLocation, checkCheck)) {
                    moves.insert(row + 1, col + 1);
                }
            }
        }
        return moves;
    }

    public boolean canEnPassantLeft(Board board, int[] enPassantLocation, boolean checkCheck){
        Board newBoard;
        if (col - 1 > -1){
            if (enPassantLocation != null) {
                if (enPassantLocation[0] == row && enPassantLocation[1] == col - 1) {
                    if (color == 'b') {
                        if (board.getBoard()[row - 1][col - 1] == null) {
                            if (checkCheck) {
                                newBoard = board.clone();
                                pawnMove(newBoard, row+1, col-1, enPassantLocation);
                                return newBoard.canMove(this);
                            } else {
                                return true;
                            }
                        }
                    } else {
                        if (board.getBoard()[row + 1][col - 1] == null) {
                            if (checkCheck) {
                                newBoard = board.clone();
                                pawnMove(newBoard, row-1, col-1, enPassantLocation);
                                return newBoard.canMove(this);
                            } else {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean canEnPassantRight(Board board, int[] enPassantLocation, boolean checkCheck){
        Board newBoard;
        if (col + 1 < 8) {
            if (enPassantLocation != null) {
                if (enPassantLocation[0] == row && enPassantLocation[1] == col + 1) {
                    if (color == 'b') {
                        if (board.getBoard()[row - 1][col + 1] == null) {
                            if (checkCheck) {
                                newBoard = board.clone();
                                pawnMove(newBoard, row+1, col+1, enPassantLocation);
                                return newBoard.canMove(this);
                            } else {
                                return true;
                            }
                        }
                    } else {
                        if (board.getBoard()[row + 1][col + 1] == null) {
                            if (checkCheck) {
                                newBoard = board.clone();
                                pawnMove(newBoard, row-1, col+1, enPassantLocation);
                                return newBoard.canMove(this);
                            } else {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    public void move(Board board, int row, int col){
    }

    public void pawnMove(Board board, int row, int col, int[] enPassantLocation){
        Pawn pawn = new Pawn(color, row, col);
        board.getBoard()[row][col] = pawn;
        pawn.didMove = true;
        board.getBoard()[this.row][this.col] = null;
        if (enPassantLocation != null){
            if (color == 'b'){
                if (enPassantLocation[0] == row+1 && enPassantLocation[1] == col){
                    board.getBoard()[row+1][col] = null;
                }
            }
            else {
                if (enPassantLocation[0] == row-1 && enPassantLocation[1] == col) {
                    board.getBoard()[row - 1][col] = null;
                }
            }
        }
    }
}
