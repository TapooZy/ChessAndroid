package com.example.chess;

import java.util.Scanner;
public class Pawn extends Piece{

    public Pawn(char color, int row, int col){
        super(color, 'p', row, col);
    }

    public Queue<Integer> getPossibleMoves(Board board, boolean isAllMoves) {
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
                    } else if (isAllMoves) {
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
                    } else if (isAllMoves) {
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
    public void move(Board board){
        Scanner in = new Scanner(System.in);
        int rowCol, desirableRow, desirableCol, size, formerRow, formerCol;
        boolean flag = false;
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
                flag = true;
                if (desirableCol == col - 1){
                    formerCol = col - 1;
                    formerRow = this.row;
                    board.getBoard()[formerRow][formerCol] = null;

                }
                else if (desirableCol == col + 1){
                    formerCol = col + 1;
                    formerRow = this.row;
                    board.getBoard()[formerRow][formerCol] = null;
                }
                else{
                    formerCol = this.col;
                    formerRow = this.row;
                    board.getBoard()[formerRow][formerCol] = null;
                }
                formerCol = col;
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
                if (desirableRow == formerRow + 2 || desirableRow == formerRow - 2){
                    this.setWasFirstMove(true);
                }
                break;
            }
        }
        if (!flag)
            System.out.println("This is not a possible move");
    }
}
