package com.example.chess;

import android.nfc.NdefMessage;

public class EngineTree {
    Engine engine;
    MoveTree[] sons;

    public EngineTree(Engine engine){
        this.engine = engine;
    }

    public void makeTree(int depth){
        MoveNode<Move> historyCurr = engine.getHistory();
        while (historyCurr.getNext() != null){
            historyCurr = historyCurr.getNext();
        }
        Queue<Location> allMoves = engine.getBoard().getColorMoves('b', false);
        Piece piece;
        int allMovesSize = allMoves.getSize(), dist;
        sons = new MoveTree[allMovesSize];
        LocationNode<Location> move;
        for (int i = 0; i < allMovesSize; i++) {
            int[] enPassant_location = new int[2];
            move = allMoves.remove();
            piece = engine.getBoard().getBoard()[move.from.row][move.from.col];
            if (piece.letter == 'p'){
                ((Pawn) piece).pawnMove(engine.getBoard(), move.to.row, move.to.col, historyCurr.move.enPassant_location);
                dist = move.from.row - move.to.row;
                dist *= dist;
                if (dist == 4){
                    enPassant_location[0] = move.to.row;
                    enPassant_location[1] = move.to.col;
                }
                else enPassant_location = null;
            }
            else {
                piece.move(engine.getBoard(), move.to.row, move.to.col);
                enPassant_location = null;
            }
            historyCurr.setNext(new MoveNode<Move>(new Move(move.from, move.to, enPassant_location, engine.getBoard())));
            sons[i] = new MoveTree(new Move(move.from, move.to, enPassant_location, engine.getBoard()));
            sons[i].makeTree(sons[i], depth - 1, 'w', engine);
            engine.deMove();
        }
    }
}
