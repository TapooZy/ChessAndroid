package com.example.chess;

public class Knight extends Piece{

    public Knight(char color,  int row, int col){
        super(color, 'k', row, col);
    }

    @Override
    public Queue<Integer> getPossibleMoves(Board board, boolean checkCheck) {
        int row1, col1;
        Board newBoard;
        Queue<Integer> moves = new Queue<>();
        int[][] directions = {{2, 1},{2,-1},{-2,1},{-2,-1}, {1, 2}, {1, -2}, {-1, 2}, {-1, -2}};
        for (int i = 0; i < 8; i++) {
            row1 = row + directions[i][0];
            col1 = col + directions[i][1];
            if (row1 >= 0 && row1 <= 7 && col1 >= 0 && col1 <= 7) {
                Piece piece = board.getBoard()[row1][col1];
                if (piece == null){
                    if (checkCheck) {
                        newBoard = board.clone();
                        move(newBoard, row1, col1);
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
                        move(newBoard, row1, col1);
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
        return moves;
    }
    @Override
    public void move(Board board, int row, int col){
        board.getBoard()[row][col] = new Knight(color, row, col);
        board.getBoard()[this.row][this.col] = null;
    }
}
