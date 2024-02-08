package com.example.chess;

public class EngineTree {
    Engine engine;
    MoveTree[] sons;

    public EngineTree(Engine engine){
        this.engine = engine;
    }

    public void makeTree(int depth){
        Queue<Location> allMoves = engine.getBoard().getColorMoves('b', false);
        int allMovesSize = allMoves.getSize(), dist;
        LocationNode<Location> move;
        for (int i = 0; i < allMovesSize; i++) {
            int[] enPassant_location = new int[2];
            move = allMoves.remove();
            if (engine.getBoard().getBoard()[move.to.row][move.to.col].letter == 'p'){
                dist = move.from.row - move.to.row;
                dist *= dist;
                if (dist == 4){
                    enPassant_location[0] = move.to.row;
                    enPassant_location[1] = move.to.col;
                }
            }
            else {
                enPassant_location = null;
            }
            sons[i] = new MoveTree(new Move(move.from, move.to, enPassant_location, engine.getBoard()));
            sons[i].makeTree(sons[i], depth - 1, 'b', engine);
        }
    }
}
