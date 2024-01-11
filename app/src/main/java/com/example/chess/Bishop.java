package com.example.chess;

import java.util.Scanner;

public class Bishop extends Piece {

    public Bishop(char color, int row, int col) {
        super(color, 'b', row, col);
    }

    @Override
    public Queue<Integer> getPossibleMoves(Board board, boolean isAllMoves) {
        Queue<Integer> moves = new Queue<>();
        int row1, col1;
        row1 = row - 1;
        col1 = col - 1;
        while (row1 >= 0 && col1 >= 0) { // top left
            if (board.getBoard()[row1][col1] == null){
                moves.insert(row1, col1);
                row1--;
                col1--;
            } else if (board.getBoard()[row1][col1].getColor() != color) {
                moves.insert(row1, col1);
                break;
            } else break;
        }
        row1 = row - 1;
        col1 = col + 1;
        while (row1 >= 0 && col1 <= 7) { // top right
            if (board.getBoard()[row1][col1] == null) {
                moves.insert(row1, col1);
                row1--;
                col1++;
            } else if (board.getBoard()[row1][col1].getColor() != color) {
                moves.insert(row1, col1);
                break;
            } else break;
        }
        row1 = row + 1;
        col1 = col + 1;
        while (row1 <= 7 && col1 <= 7) { // bottom right
            if (board.getBoard()[row1][col1] == null) {
                moves.insert(row1, col1);
                row1++;
                col1++;
            } else if (board.getBoard()[row1][col1].getColor() != color) {
                moves.insert(row1, col1);
                break;
            } else break;
        }
        row1 = row + 1;
        col1 = col - 1;
        while (row1 <= 7 && col1 >= 0) { // bottom left
            if (board.getBoard()[row1][col1] == null) {
                moves.insert(row1, col1);
                row1++;
                col1--;
            } else if (board.getBoard()[row1][col1].getColor() != color) {
                moves.insert(row1, col1);
                break;
            } else break;
        }
        return moves;
    }

    @Override
    public void move(Board board){
        Scanner in = new Scanner(System.in);
        int rowCol, desirableRow, desirableCol, size;
        boolean isAvailableMove = false;
        rowCol = in.nextInt();
        desirableRow = rowCol/10;
        desirableCol = rowCol%10;
        int[] desirableMove = {desirableRow, desirableCol};
        int[] availableMove;
        Queue<Integer> moves = board.getBoard()[this.row][this.col].getPossibleMoves(board, false);
        size = moves.getSize();
        for (int i = 0; i < size; i++) {
            availableMove = moves.remove();
            if (desirableMove[0] == availableMove[0] && desirableMove[1] == availableMove[1]){
                board.getBoard()[desirableRow][desirableCol] = this;
                board.getBoard()[this.row][this.col] = null;
                isAvailableMove = true;
                board.printBoard();
                for (int k = 0; k < 7; i++) {
                    for (int j = 0; j < 7; j++) {
                        if (board.getBoard()[k][j] != null){
                            if (board.getBoard()[k][j].getWasFirstMove()){
                                board.getBoard()[k][j].setWasFirstMove(false);
                            }
                        }
                    }
                }
                break;
            }
        }
        if (!isAvailableMove){
            System.out.println("This is not a possible move");
        }
    }
}
