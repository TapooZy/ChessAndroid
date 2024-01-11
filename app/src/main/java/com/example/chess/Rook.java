package com.example.chess;

import java.util.Scanner;

public class Rook extends Piece{

    public Rook (char color, int row, int col){
        super(color, 'r', row, col);
    }

    public Queue<Integer> getPossibleMoves(Board board, boolean isAllMoves) {
        int row1, col1;
        Queue<Integer> moves = new Queue<>();
        row1 = row -1;
        while (row1 >= 0){ // up
            if (board.getBoard()[row1][col] == null) {
                moves.insert(row1, col);
                row1--;
            }
            else if (board.getBoard()[row1][col].getColor() != color){
                moves.insert(row1, col);
                break;
            } else if (isAllMoves){
                moves.insert(row1, col);
            }
            else break;
        }
        col1 = col +1;
        while (col1 <= 7){ // right
            if (board.getBoard()[row][col1] == null) {
                moves.insert(row, col1);
                col1++;
            }
            else if (board.getBoard()[row][col1].getColor() != color){
                moves.insert(row, col1);
                break;
            } else if (isAllMoves){
                moves.insert(row, col1);
            }
            else break;
        }
        row1 = row +1;
        while (row1 <= 7){ // down
            if (board.getBoard()[row1][col] == null) {
                moves.insert(row1, col);
                row1++;
            }
            else if (board.getBoard()[row1][col].getColor() != color){
                moves.insert(row1, col);
                break;
            } else if (isAllMoves){
                moves.insert(row1, col);
            }
            else break;
        }
        col1 = col -1;
        while (col1 >= 0){ // left
            if (board.getBoard()[row][col1] == null) {
                moves.insert(row, col1);
                col1--;
            }
            else if (board.getBoard()[row][col1].getColor() != color){
                moves.insert(row, col1);
                break;
            } else if (isAllMoves){
                moves.insert(row, col1);
            }
            else break;
        }
        return moves;
    }

    @Override
    public void move(Board board){
        Scanner in = new Scanner(System.in);
        int rowCol, desirableRow, desirableCol, size, formerRow, formerCol;
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
                formerRow = this.row;
                formerCol = this.col;
                setRow(desirableRow);
                setCol(desirableCol);
                board.getBoard()[formerRow][formerCol] = null;
                if (!didMove){
                    didMove = true;
                }
                board.getBoard()[desirableRow][desirableCol] = this;
                board.printBoard();
                for (int k = 0; k < 7; k++) {
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
        System.out.println("This is not a possible move");
    }
}
