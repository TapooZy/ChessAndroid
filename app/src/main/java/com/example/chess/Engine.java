package com.example.chess;

import java.util.*;

public class Engine {
    private Board board;
    private int[] enPassantLocation;

    public Engine(){
        this.board = new Board();
        board.startGame();
    }

    public int[] getEnPassantLocation() {
        return enPassantLocation;
    }

    public void setEnPassantLocation(int[] enPassantLocation) {
        this.enPassantLocation = enPassantLocation;
    }

    public void game(){
        int Piece, Move, row, col;
        String color = "White";
        char c = 'w';
        Piece piece;
        Scanner in = new Scanner(System.in);
        while(board.findKing('b') != null && board.findKing('w') != null){
            board.printBoard();
            System.out.println(color + " move");
            while (true) {
                System.out.println("Which piece do you want to move");
                Piece = in.nextInt();
                row = Piece / 10;
                col = Piece % 10;
                if (board.getBoard()[row][col] != null){
                    if (board.getBoard()[row][col].getColor() == c) {
                        piece = board.getBoard()[row][col];
                        break;
                    }
                    else {
                        System.out.println("Wrong color");
                    }
                }
                else {
                    System.out.println("This square is empty");
                }
            }
//            while (true) {
//                System.out.println("Where");
//                Move = in.nextInt();
//                row = Move / 10;
//                col = Move % 10;
//                if (piece.move(board, row, col)){
//                    break;
//                }
//                else{
//                    System.out.println("This is an illegal move");
//                }
//            }
            if (color.equals("White")){
                color = "Black";
                c = 'b';
            }
            else {
                color = "White";
                c = 'w';
            }
        }
    }

    public Board getBoard() {
        return board;
    }
}
