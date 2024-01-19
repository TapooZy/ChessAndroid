package com.example.chess;

import android.util.Log;

public class King extends Piece {

    public King(char color, int row, int col) {
        super(color, 'K', row, col);
    }
    public Queue<Integer> getPossibleMoves(Board board, boolean checkCheck) {
        int row1, col1;
        Queue<Integer> moves = new Queue<>();
        Board newBoard;
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
        for (int i = 0; i < 8; i++) {
            row1 = row + directions[i][0];
            col1 = col + directions[i][1];
            if (row1 >= 0 && row1 <= 7 && col1 >= 0 && col1 <= 7) {
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
                }
            }
        }
        if (checkCheck) {
            if (canCastleLeft(board)){
            moves.insert(row, col-2);
            }
            if (canCastleRight(board)) {
                moves.insert(row, col + 2);
            }
        }
        return moves;

    }

    public boolean canCastleRight (Board board)
    {
        Queue<Integer> allMoves = board.getColorMoves(this.color, true);
        int[] individualMove;
        if (board.getBoard()[row][7] != null)
        {
            if (board.getBoard()[row][7] instanceof Rook)
            {
                if (!board.getBoard()[row][7].didMove && !this.didMove)
                {
                    for (int i = 0; i < allMoves.getSize(); i++)
                    {
                        individualMove = allMoves.remove();
                        for (int col = 5; col < 7; col++)
                        {
                            if (board.getBoard()[row][col] != null || (individualMove[0] == row && individualMove[1] == col))
                            {
                                return false;
                            }
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public boolean canCastleLeft (Board board){
        Queue<Integer> allMoves = board.getColorMoves(this.color, true);
        Log.d("didMove", "" + color + " " + this.didMove);
        int[] individualMove;
        if (board.getBoard()[row][0] != null)
        {
            if (board.getBoard()[row][0] instanceof Rook)
            {
                if (!board.getBoard()[row][0].didMove && !this.didMove)
                {
                    for (int i = 0; i < allMoves.getSize(); i++)
                    {
                        individualMove = allMoves.remove();
                        for (int col = 3; col > 0; col--)
                        {
                            if (board.getBoard()[row][col] != null || (individualMove[0] == row && individualMove[1] == col))
                            {
                                return false;
                            }
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }
    @Override
    public void move(Board board, int row, int col){
        int size;
        int[] availableMove;
        Queue<Integer> moves = getPossibleMoves(board, true);
        size = moves.getSize();
        for (int i = 0; i < size; i++) {
            availableMove = moves.remove();
            if (row == availableMove[0] && col == availableMove[1]){
                if (col == this.col + 2){
                    King king = new King(color, row, col);
                    king.didMove = true;
                    board.getBoard()[row][col] = king;
                    board.getBoard()[this.row][this.col] = null;
                    Rook rook = new Rook(color, row, col-1);
                    board.getBoard()[this.row][7] = null;
                    board.getBoard()[row][col-1] = rook;
                    rook.didMove = true;
                }
                else if (col == this.col - 2){
                    King king = new King(color, row, col);
                    king.didMove = true;
                    board.getBoard()[row][col] = king;
                    board.getBoard()[this.row][this.col] = null;
                    Rook rook = new Rook(color, row, col+1);
                    board.getBoard()[this.row][0] = null;
                    board.getBoard()[row][col+1] = rook;
                    rook.didMove = true;
                }
                else {
                    King king = new King(color, row, col);
                    king.didMove = true;
                    board.getBoard()[row][col] = king;
                    board.getBoard()[this.row][this.col] = null;
                }
            }
        }
        Log.d("didMove", "" + board.getBoard()[row][col].didMove);
    }

    @Override
    public void testMove(Board board, int row, int col){
        King king = new King(color, row, col);
        king.didMove = true;
        board.getBoard()[row][col] = king;
        board.getBoard()[this.row][this.col] = null;
    }
}
