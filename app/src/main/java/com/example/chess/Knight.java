package com.example.chess;

import java.util.Scanner;

public class Knight extends Piece{

    public Knight(char color,  int row, int col){
        super(color, 'k', row, col, "Knight");
    }

    @Override
    public Queue<Integer> getPossibleMoves(Board board) {
        int row1, col1;
        Queue<Integer> moves = new Queue<>();
        int[][] directions = {{2, 1},{2,-1},{-2,1},{-2,-1}, {1, 2}, {1, -2}, {-1, 2}, {-1, -2}};
        for (int i = 0; i < 8; i++) {
            row1 = row + directions[i][0];
            col1 = col + directions[i][1];
            Piece piece = board.getBoard()[row1][col1];
            if (piece == null){
                moves.insert(row1, col1);
            }
            else if (piece.getColor() != color){
                moves.insert(row1, col1);
                continue;
            }
            else continue;
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
