package com.example.chess;

public class Move {
    public Piece eatenPiece;
    public Location from;
    public Location to;
    public boolean is_enPassant_move;
    public boolean is_promotion_move;


    public Move(Location from, Location to, boolean is_enPassant_move, Board board) {
        this.eatenPiece = board.getBoard()[to.row][to.col];
        this.from = from;
        this.to = to;
        this.is_enPassant_move = is_enPassant_move;
        if (board.getBoard()[to.row][to.col].letter == 'p' && to.row == 7 && board.getBoard()[to.row][to.col].color == 'w'){
            this.is_promotion_move = true;
        } else if (board.getBoard()[to.row][to.col].letter == 'p' && to.row == 0 && board.getBoard()[to.row][to.col].letter == 'b'){
            this.is_promotion_move = true;
        }
        else this.is_promotion_move = false;
    }
}
