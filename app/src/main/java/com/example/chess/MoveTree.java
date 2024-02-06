package com.example.chess;

public class MoveTree {
    public MoveNode<Move> move;
    public MoveTree[] leaves;
    public int evaluation;

    public MoveTree(MoveNode<Move> move, MoveTree[] roots){
        this.move = move;
        this.leaves = roots;
    }

    public MoveTree(MoveNode<Move> move){
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
            moveTree = leaves[0];
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

    public void makeTree(MoveTree moveTree, int depth, char color){
        if (depth == 0){
            return;
        }
        char nextColor;
        if (color == 'w'){
            nextColor = 'b';
        }
        else {
            nextColor = 'w';
        }
        Board board = createBoard(moveTree.move);
        Queue<Location> allMoves = board.getColorMoves(color, false);
        int allMovesSize = allMoves.getSize(), dist;
        LocationNode<Location> move;
        MoveNode<Move> curr = moveTree.move;
        MoveTree[] leaves = new MoveTree[allMovesSize];
        for (int i = 0; i < allMovesSize; i++) {
            int[] enPassantLocation = new int[2];
            move = allMoves.remove();
            if (board.getBoard()[move.from.row][move.from.col].letter == 'p'){
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
                enPassantLocation = null;
            }
            if (curr == null){
                leaves[i] = new MoveTree(new MoveNode<Move>(new Move(move.from, move.to, enPassantLocation, board)));
            }
            else {
                while (curr.getNext() != null){
                    curr = curr.getNext();
                }
                curr.setNext(new MoveNode<Move>(new Move(move.from, move.to, enPassantLocation, board)));
                leaves[i] = new MoveTree(moveTree.move);
            }
            makeTree(leaves[i], depth - 1, nextColor);
        }
        moveTree.setLeaves(leaves);
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
