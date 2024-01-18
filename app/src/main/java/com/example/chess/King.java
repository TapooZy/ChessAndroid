package com.example.chess;

import android.util.Log;

public class King extends Piece {
    public boolean didMove;

    public King(char color, int row, int col) {
        super(color, 'K', row, col, "King");
        didMove = false;
    }

    public void setDidMove() {
        this.didMove = true;
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
                else if (piece.getColor() != color){
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
        Queue<Integer> allMoves = board.getColorMoves(this.getColor(), true);
        Log.d("go to", "its here");
        int[] individualMove;
        if (board.getBoard()[row][7] != null)
        {
            if (board.getBoard()[row][7].getLetter() == 'r')
            {
                Rook rook = (Rook) board.getBoard()[row][7];
                if (!rook.getDidMove())
                {
                    for (int i = 0; i < allMoves.getSize(); i++)
                    {
                        individualMove = allMoves.remove();
                        for (int col = 5; col < 7; col++)
                        {
                            if (board.getBoard()[row][col] != null)
                            {
                                return false;
                            }
                            if (individualMove[0] == row && individualMove[1] == col)
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
        Queue<Integer> allMoves = board.getColorMoves(this.getColor(), true);
        int[] individualMove;
        if (board.getBoard()[row][0] != null)
        {
            if (board.getBoard()[row][0].getLetter() == 'r')
            {
                Rook rook = (Rook) board.getBoard()[row][0];
                if (!rook.getDidMove())
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
                King king = new King(color, row, col);
                king.setDidMove();
                board.getBoard()[row][col] = king;
                board.getBoard()[this.row][this.col] = null;
            }
        }
    }

    @Override
    public void testMove(Board board, int row, int col){
        King king = new King(color, row, col);
        king.setDidMove();
        board.getBoard()[row][col] = king;
        board.getBoard()[this.row][this.col] = null;
    }
}
