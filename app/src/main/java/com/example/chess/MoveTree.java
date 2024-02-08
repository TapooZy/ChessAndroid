package com.example.chess;

public class MoveTree {
    public Move move;
    public MoveTree[] leaves;
    public Integer evaluation;

    public MoveTree(Move move, MoveTree[] roots){
        this.move = move;
        this.leaves = roots;
    }

    public MoveTree(Move move){
        this.move = move;
        this.leaves = null;
    }

    public void setLeaves(MoveTree[] leaves) {
        this.leaves = leaves;
    }

    public int[] levels(){
        int levelsCount = 0;
        MoveTree moveTree = this;
        while (moveTree.leaves != null){
            levelsCount ++;
            moveTree = moveTree.leaves[0];
        }
        int[] levels = new int[levelsCount+1];
        levels = recCount(levels, 0);
        return levels;
    }

    public int[] recCount(int[] levels, int currentLevel){
        levels[currentLevel] += 1;
        if (this.leaves != null) {
            int length = this.leaves.length;
            for (int i = 0; i < length; i++) {
                levels = this.leaves[i].recCount(levels, currentLevel + 1);
            }
        }
        return levels;
    }

    public String toString(int[] levels){
        String s = "[";
        for (int i = 0; i < levels.length; i++) {
            s += "(" + i + ": " + levels[i] + ")";
        }
        s += "]";
        return s;
    }

    public void makeTree(MoveTree moveTree, int depth, char color, Engine engine){
        if (depth == 0){
            evaluation = engine.evaluate();
            engine.deMove();
            return;
        }
        char nextColor;
        if (color == 'w'){
            nextColor = 'b';
        }
        else {
            nextColor = 'w';
        }
        Board board = engine.getBoard();
        Queue<Location> allMoves = board.getColorMoves(color, false);
        int allMovesSize = allMoves.getSize(), dist;
        LocationNode<Location> move;
        MoveNode<Move> historyCurr = engine.getHistory(), newNode = null;
        MoveTree[] leaves = new MoveTree[allMovesSize];
        for (int i = 0; i < allMovesSize; i++) {
//            if (moveTree.move == null){
//                curr = null;
//            }
//            else {
//                newNode = moveTree.move.clone();
//                curr = newNode;
//            }
            int[] enPassantLocation = new int[2];
            move = allMoves.remove();
            while (historyCurr.getNext() != null){
                historyCurr = historyCurr.getNext();
            }
            if (board.getBoard()[move.from.row][move.from.col].letter == 'p'){
                ((Pawn) board.getBoard()[move.from.row][move.from.col]).pawnMove(engine.getBoard(), move.to.row, move.to.col, historyCurr.move.enPassant_location);
                dist = move.from.row - move.to.row;
                dist *= dist;
                if (dist == 4){
                    enPassantLocation[0] = move.to.row;
                    enPassantLocation[1] = move.to.col;
                }
                else {
                    enPassantLocation = null;
                }
            }
            else {
                board.getBoard()[move.from.row][move.from.col].move(engine.getBoard(), move.to.row, move.to.col);
                enPassantLocation = null;
            }
            historyCurr.setNext(new MoveNode<Move>(new Move(move.from, move.to, enPassantLocation, board)));
            leaves[i] = new MoveTree(new Move(move.from, move.to, enPassantLocation, board));
            makeTree(leaves[i], depth - 1, nextColor, engine);
        }
        moveTree.setLeaves(leaves);
        engine.deMove();
    }

    public Board createBoard(MoveNode<Move> moves){
        Board board = new Board();
        board.startGame();
        Piece piece;
        MoveNode<Move> curr = moves;
        while (curr != null){
            piece = board.getBoard()[curr.move.from.row][curr.move.from.col];
            if (piece.letter == 'p'){
                ((Pawn) piece).pawnMove(board, curr.move.to.row, curr.move.to.col, curr.move.enPassant_location);
            }
            else {
                piece.move(board, curr.move.to.row, curr.move.to.col);
            }
            curr = curr.getNext();
        }
        return board;
    }
}
