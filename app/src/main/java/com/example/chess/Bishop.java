package com.example.chess;

public class Bishop extends Piece {

    public Bishop(char color, int row, int col) {
        super(color, 'b', row, col, 3);
    }

    @Override
    public Queue<Location> getPossibleMoves(Board board, boolean checkCheck) {
        Queue<Location> moves = new Queue<>();
        Location from = new Location(row, col);
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
                        move(newBoard, row1, col1);
                        if (newBoard.canMove(this)){
                            Location to = new Location(row1, col1);
                            moves.insert(from, to);
                        }
                    }
                    else {
                        Location to = new Location(row1, col1);
                        moves.insert(from, to);
                    }
                }
                else if (piece.color != color){
                    if (checkCheck) {
                        newBoard = board.clone();
                        move(newBoard, row1, col1);
                        if (newBoard.canMove(this)){
                            Location to = new Location(row1, col1);
                            moves.insert(from, to);
                        }
                    }
                    else {
                        Location to = new Location(row1, col1);
                        moves.insert(from, to);
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
    public Move move(Board board, int row, int col){
        Move move = new Move(new Location(this.row, this.col), new Location(row, col), null, board);
        board.getBoard()[row][col] = new Bishop(color, row, col);
        board.getBoard()[this.row][this.col] = null;
        return move;
    }

    @Override
    public Piece clone(){
        return new Bishop(color, row, col);
    }
}
