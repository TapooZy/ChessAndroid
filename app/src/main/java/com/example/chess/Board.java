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

    public Queue<Integer> getDifferentColorMoves(Piece piece){
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
                        pieceMoves = board[i][j].getPossibleMoves(this);
                        int size = pieceMoves.getSize();
                        for (int k = 0; k < size; k++)
                        {
                            individualMove = pieceMoves.remove();
                            allMoves.insert(individualMove[0], individualMove[1]);
                        }
                    }
                }
            }
        }
        return allMoves;
    }

    public Queue<Integer> getSameColorMoves(Piece piece){
        int[] individualMove;
        Queue<Integer> allMoves = new Queue<>();
        Queue<Integer> pieceMoves;
        char wantedColor = piece.color;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] != null) {
                    if (board[i][j].getColor() == wantedColor) {
                        pieceMoves = board[i][j].getPossibleMoves(this);
                        int size = pieceMoves.getSize();
                        for (int k = 0; k < size; k++)
                        {
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

    public Piece findKing(char color){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] != null){
                    if (board[i][j].getLetter() == 'K' && board[i][j].getColor() == color) {
                        return board[i][j];
                    }
                }
            }
        }
        return null;
    }
    public String convertBoardToString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = board[i][j];
                stringBuilder.append(piece.getColor()+ piece.getLetter()).append(" ");
            }
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }

    public boolean isInCheck (Piece piece) {
        Queue<Integer> allMoves = getDifferentColorMoves(piece);
        int[] individualMove;
        int size = allMoves.getSize();
        for (int i = 0; i < size; i++) {
            individualMove = allMoves.remove();
            if (individualMove[0] == piece.getRow() && individualMove[1] == piece.getCol()) {
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
