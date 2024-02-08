package com.example.chess;

import android.util.Log;

public class Rook extends Piece{

    public Rook (char color, int row, int col){
        super(color, 'r', row, col, 5);
    }

    public Queue<Location> getPossibleMoves(Board board, boolean checkCheck) {
        int row1, col1;
        Board newBoard;
        Queue<Location> moves = new Queue<>();
        Location from = new Location(row, col);
        int[][] directions = {{1, 0},{-1,0},{0,1},{0,-1}};
        for (int i = 0; i < 4; i++) {
            row1 = row + directions[i][0];
            col1 = col + directions[i][1];
            while (row1 >= 0 && row1 <= 7 && col1>= 0 && col1 <= 7){
                Piece piece = board.getBoard()[row1][col1];
                if (piece == null){
                    if (checkCheck) {
                        newBoard = board.clone();
                        move(newBoard, row1, col1);
                        if (newBoard.canMove(this)){
                            Location to = new Location(row1, col1);
                            moves.insert(from, to);
                        }
                    }
                    else {
                        Location to = new Location(row1, col1);
                        moves.insert(from, to);
                    }
                }
                else if (piece.color != color){
                    if (checkCheck) {
                        newBoard = board.clone();
                        move(newBoard, row1, col1);
                        if (newBoard.canMove(this)){
                            Location to = new Location(row1, col1);
                            moves.insert(from, to);
                        }
                    }
                    else {
                        Location to = new Location(row1, col1);
                        moves.insert(from, to);
                    }
                    break;
                }
                else break;
                row1 += directions[i][0];
                col1 += directions[i][1];
            }
        }
        return moves;
    }

    @Override
    public Move move(Board board, int row, int col){
        int initial_row = this.row, initial_col = this.col;
        Rook rook = new Rook(color, row, col);
        rook.didMove = true;
        board.getBoard()[row][col] = rook;
        board.getBoard()[this.row][this.col] = null;
        Move move = new Move(new Location(initial_row, initial_col), new Location(row, col), null, board);
        return move;
    }

    @Override
    public Piece clone(){
        Rook newRook = new Rook(color, row, col);
        newRook.didMove = didMove;
        return newRook;
    }
}
