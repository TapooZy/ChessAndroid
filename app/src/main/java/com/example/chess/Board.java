package com.example.chess;
public class Board {
    private final Piece[][] board;

    public Board(){
        this.board = new Piece[8][8];
        for (int i = 0; i < 8; i++) { // row
            for (int j = 0; j < 8; j++) { // column
                this.board[i][j] = null;
            }
        }
    }

    public Piece[][] getBoard() {
        return board;
    }

    public void startGame(){
        board[0][0] = new Rook('b', 0, 0);
        board[0][1] = new Knight('b', 0, 1);
        board[0][2] = new Bishop('b', 0, 2);
        board[0][3] = new Queen('b', 0, 3);
        board[0][4] = new King('b', 0, 4);
        board[0][5] = new Bishop('b', 0, 5);
        board[0][6] = new Knight('b', 0, 6);
        board[0][7] = new Rook('b', 0, 7);

        for (int i = 0; i < 8; i++) {
            board[1][i] = new Pawn('b', 1, i);
        }

        board[7][0] = new Rook('w', 7, 0);
        board[7][1] = new Knight('w', 7, 1);
        board[7][2] = new Bishop('w', 7, 2);
        board[7][3] = new Queen('w', 7, 3);
        board[7][4] = new King('w', 7, 4);
        board[7][5] = new Bishop('w', 7, 5);
        board[7][6] = new Knight('w', 7, 6);
        board[7][7] = new Rook('w', 7, 7);

        for (int i = 0; i < 8; i++) {
            board[6][i] = new Pawn('w', 6, i);
        }
    }

    public Queue<Integer> getColorMoves(char color, boolean different){
        int[] individualMove;
        Queue<Integer> allMoves = new Queue<>();
        Queue<Integer> pieceMoves;
        char wantedColor;
        if (different) {
            if (color == 'b') {
                wantedColor = 'w';
            } else {
                wantedColor = 'b';
            }
        }
        else wantedColor = color;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] != null) {
                    if (board[i][j].color == wantedColor) {
                        if (board[i][j] instanceof Pawn){
                            pieceMoves = ((Pawn) board[i][j]).getPawnPossibleMoves(this, null, false);
                        }
                        else {
                            pieceMoves = board[i][j].getPossibleMoves(this, false);
                        }
                        if (pieceMoves != null) {
                            int size = pieceMoves.getSize();
                            for (int k = 0; k < size; k++) {
                                individualMove = pieceMoves.remove();
                                allMoves.insert(individualMove[0], individualMove[1]);
                            }
                        }
                    }
                }
            }
        }
        return allMoves;
    }

    public Queue<Integer> getEndMoves(char color){
        int[] individualMove;
        Queue<Integer> allMoves = new Queue<>();
        Queue<Integer> pieceMoves;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] != null) {
                    if (board[i][j].color == color) {
                        if (board[i][j] instanceof Pawn){
                            pieceMoves = ((Pawn) board[i][j]).getPawnPossibleMoves(this, null, true);
                        }
                        else {
                            pieceMoves = board[i][j].getPossibleMoves(this, true);
                        }
                        if (pieceMoves != null) {
                            int size = pieceMoves.getSize();
                            for (int k = 0; k < size; k++) {
                                individualMove = pieceMoves.remove();
                                allMoves.insert(individualMove[0], individualMove[1]);
                            }
                        }
                    }
                }
            }
        }
        return allMoves;
    }

    public Piece findPiece(char pieceClassName, char color, int row, int col){
        if (row != -1){
            for (int i = 0; i < 8; i++) {
                if (board[row][i] != null) {
                    if (board[row][i].letter == pieceClassName) {
                        if (board[row][i].color == color) {
                            return board[row][i];
                        }
                    }
                }
            }
        }
        else if (col != -1){
            for (int i = 0; i < 8; i++) {
                if (board[i][col] != null) {
                    if (board[i][col].letter == pieceClassName) {
                        if (board[i][col].color == color) {
                            return board[i][col];
                        }
                    }
                }
            }
        }
        else {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (board[i][j] != null) {
                        if (board[i][j].letter == pieceClassName) {
                            if (board[i][j].color == color) {
                                return board[i][j];
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public boolean canMove(Piece piece){
        Piece king = findKing(piece.color);
        if (king == null){
            return false;
        }
        Queue<Integer> allMoves = getColorMoves(piece.color, true);
        int size = allMoves.getSize();
        int[] individualMove;
        for (int i = 0; i < size; i++) {
            individualMove = allMoves.remove();
            if (individualMove[0] == king.row && individualMove[1] == king.col) {
                return false;
            }
        }
        return true;
    }

    public Piece findKing(char color){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] != null){
                    if (board[i][j] instanceof King && board[i][j].color == color) {
                        return board[i][j];
                    }
                }
            }
        }
        return null;
    }
    public boolean isInCheck (char color) {
        Queue<Integer> allMoves = getColorMoves(color, true);
        Piece king = findKing(color);
        if (king == null){
            return false;
        }
        int[] individualMove;
        int size = allMoves.getSize();
        for (int i = 0; i < size; i++) {
            individualMove = allMoves.remove();
            if (individualMove[0] == king.row && individualMove[1] == king.col) {
                return true;
            }
        }
        return false;
    }

    public Board clone(){
        Board newBoard = new Board();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                newBoard.getBoard()[i][j] = board[i][j];
            }
        }
        return newBoard;
    }
}
