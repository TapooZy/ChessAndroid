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

    public Queue<Integer> getAllMoves(Piece piece){
        int[] individualMove;
        Queue<Integer> allMoves = new Queue<>();
        Queue<Integer> pieceMoves;
        char wantedColor;
        if (piece.getColor() == 'b'){
            wantedColor = 'w';
        }
        else {
            wantedColor = 'b';
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] != null) {
                    if (board[i][j].getColor() == wantedColor) {
                        pieceMoves = board[i][j].getPossibleMoves(this, true);
                        for (int k = 0; k < pieceMoves.getSize(); k++)
                        {
                            individualMove = pieceMoves.remove();
                            allMoves.insert(individualMove[0], individualMove[1]);
                        }
                    }
                }
            }
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] != null) {
                    if (board[i][j].getColor() == wantedColor) {
                        pieceMoves = board[i][j].getPossibleMoves(this, true);
                        for (int k = 0; k < pieceMoves.getSize(); k++) {
                            individualMove = pieceMoves.remove();
                            allMoves.insert(individualMove[0], individualMove[1]);
                        }
                    }
                }
            }
        }
        return allMoves;
    }

    public void printBoard(){
        System.out.print("|  ");
        for (int i = 0; i < 8; i++) {
            System.out.print("| " + i);
        }
        System.out.println("|");
        for (int i = 0; i < 8; i++) {
            System.out.print( "| " + i);
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == null){
                    System.out.print("|  ");
                }
                else System.out.print("|" + board[i][j].getColor() + board[i][j].getLetter());
            }
            System.out.println("|");
        }
    }

    public int[] findKing(Board board, char color){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = board.getBoard()[i][j];
                if (piece.getLetter() == 'K' && piece.getColor() == color){
                    int[] coords = {i,j};
                    return coords;
                }
            }
        }
        return null;
    }

    public boolean isInCheck (Board board, Piece piece) {
        Queue<Integer> allMoves = board.getAllMoves(board.getBoard()[piece.getRow()][piece.getCol()]);
        int[] individualMove;
        for (int i = 0; i < allMoves.getSize(); i++) {
            individualMove = allMoves.remove();
            if (individualMove[0] == piece.getRow() && individualMove[1] == piece.getCol()) {
                return true;
            }
        }
        return false;
    }
}
