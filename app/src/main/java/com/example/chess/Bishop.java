package com.example.chess;

import java.util.Scanner;

public class Bishop extends Piece {

    public Bishop(char color, int row, int col) {
        super(color, 'b', row, col, "Bishop");
    }

    @Override
    public Queue<Integer> getPossibleMoves(Board board, boolean checkCheck) {
        Queue<Integer> moves = new Queue<>();
        int row1, col1;
        Board newBoard;
        int[][] directions = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
        for (int i = 0; i < 4; i++) {
            row1 = row + directions[i][0];
            col1 = col + directions[i][1];
            while (row1 >= 0 && row1 <= 7 && col1>= 0 && col1 <= 7){
                Piece piece = board.getBoard()[row1][col1];
                if (piece == null){
                    if (checkCheck) {
                        newBoard = board.clone();
                        testMove(newBoard, row1, col1);
                        if (newBoard.canMove(this)){
                            moves.insert(row1, col1);
                        }
                    }
                    else {
                        moves.insert(row1, col1);
                    }
                }
                else if (piece.color != color){
                    if (checkCheck) {
                        newBoard = board.clone();
                        testMove(newBoard, row1, col1);
                        if (newBoard.canMove(this)){
                            moves.insert(row1, col1);
                        }
                    }
                    else {
                        moves.insert(row1, col1);
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
    public void move(Board board, int row, int col){
        int size;
        int[] availableMove;
        Queue<Integer> moves = getPossibleMoves(board, true);
        if (moves != null) {
            size = moves.getSize();
        }
        else
            size = 0;
        for (int i = 0; i < size; i++) {
            availableMove = moves.remove();
            if (row == availableMove[0] && col == availableMove[1]){
                board.getBoard()[row][col] = new Bishop(color, row, col);
                board.getBoard()[this.row][this.col] = null;
            }
        }
    }

    @Override
    public void testMove(Board board, int row, int col){
        board.getBoard()[row][col] = new Bishop(color, row, col);
        board.getBoard()[this.row][this.col] = null;
    }
}
