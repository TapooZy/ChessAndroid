package com.example.chess;

import java.util.Scanner;

public class Rook extends Piece{

    public Rook (char color, int row, int col){
        super(color, 'r', row, col, "Rook");
    }

    public Queue<Integer> getPossibleMoves(Board board) {
        int row1, col1;
        Queue<Integer> moves = new Queue<>();
//        Piece king = board.findKing(this.color);
        int[][] directions = {{1, 0},{-1,0},{0,1},{0,-1}};
        for (int i = 0; i < 4; i++) {
            row1 = row + directions[i][0];
            col1 = col + directions[i][1];
            while (row1 >= 0 && row1 <= 7 && col1>= 0 && col1 <= 7){
                Piece piece = board.getBoard()[row1][col1];
                if (piece == null){
                    moves.insert(row1, col1);
                }
                else if (piece.getColor() != color){
                    moves.insert(row1, col1);
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
    public void move(Board board, int row, int col){
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
                board.printBoard();
            }
        }
    }
}
