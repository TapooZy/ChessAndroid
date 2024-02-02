package com.example.chess;

public class Move {
    public Piece eatenPiece;
    public Location from;
    public Location to;
    public int[] enPassant_location;
    public boolean is_promotion_move;


    public Move(Location from, Location to, int[] enPassant_location, Board board) {
        this.eatenPiece = board.getBoard()[to.row][to.col];
        this.from = from;
        this.to = to;
        this.enPassant_location = enPassant_location;
        if (board.getBoard()[from.row][from.col].letter == 'p' && to.row == 7 && board.getBoard()[from.row][from.col].color == 'w'){
            this.is_promotion_move = true;
        } else if (board.getBoard()[from.row][from.col].letter == 'p' && to.row == 0 && board.getBoard()[from.row][from.col].letter == 'b'){
            this.is_promotion_move = true;
        }
        else this.is_promotion_move = false;
    }

    public Move(Location from, Location to, int[] enPassant_location, Board board, Piece eatenPiece) {
        this.eatenPiece = eatenPiece;
        this.from = from;
        this.to = to;
        this.enPassant_location = enPassant_location;
        if (board.getBoard()[to.row][to.col].letter == 'p' && to.row == 7 && board.getBoard()[to.row][to.col].color == 'w'){
            this.is_promotion_move = true;
        } else if (board.getBoard()[to.row][to.col].letter == 'p' && to.row == 0 && board.getBoard()[to.row][to.col].letter == 'b'){
            this.is_promotion_move = true;
        }
        else this.is_promotion_move = false;
    }
}
