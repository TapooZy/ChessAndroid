package com.example.chess;

public class Rook extends Piece{
    public boolean didMove;

    public Rook (char color, int row, int col){
        super(color, 'r', row, col, "Rook");
        didMove = false;
    }

    public void setDidMove() {
        this.didMove = true;
    }

    public boolean getDidMove() {
        return didMove;
    }

    public Queue<Integer> getPossibleMoves(Board board, boolean checkCheck) {
        int row1, col1;
        Board newBoard;
        Queue<Integer> moves = new Queue<>();
        int[][] directions = {{1, 0},{-1,0},{0,1},{0,-1}};
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
        size = moves.getSize();
        for (int i = 0; i < size; i++) {
            availableMove = moves.remove();
            if (row == availableMove[0] && col == availableMove[1]){
                board.getBoard()[row][col] = new Rook(color, row, col);
                board.getBoard()[this.row][this.col] = null;
            }
        }
    }

    @Override
    public void testMove(Board board, int row, int col){
        board.getBoard()[row][col] = new Rook(color, row, col);
        setDidMove();
        board.getBoard()[this.row][this.col] = null;
    }
}
