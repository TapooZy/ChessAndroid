package com.example.chess;

public class EngineTree {
    public Engine engine;
    public MoveNode<Move>[] leaves;
    public int evaluation;

    public EngineTree(Engine engine, MoveNode<Move>[] roots){
        this.engine = engine;
        this.leaves = roots;
    }

    public EngineTree(Engine engine){
        this.engine = engine;
        this.leaves = null;
    }

    public void setLeaves(MoveNode<Move>[] leaves) {
        this.leaves = leaves;
    }

//    public int[] levels(){
//        int levelsCount = 0;
//        EngineTree engineTree = this;
//        while (engineTree.leaves != null){
//            levelsCount ++;
//            Piece piece = engine.getBoard().getBoard()[leaves[0].from.row][leaves[0].from.col];
//            if (piece.letter == 'p') {
//                ((Pawn) piece).pawnMove(engine.getBoard(), leaves[0].to.row, leaves[0].to.col, engine.getEnPassantLocation());
//            }
//            else {
//                piece.move(engine.getBoard(), leaves[0].to.row, leaves[0].to.col);
//            }
//        }
//        int[] levels = new int[levelsCount+1];
//        levels = recCount(levels, 0);
//        return levels;
//    }

//    public int[] recCount(int[] levels, int currentLevel){
//        levels[currentLevel] += 1;
//        if (this.leaves != null) {
//            int length = this.leaves.length;
//            for (int i = 0; i < length; i++) {
//                levels = this.leaves[i].recCount(levels, currentLevel + 1);
//            }
//        }
//        return levels;
//    }

    public String toString(int[] levels){
        String s = "[";
        for (int i = 0; i < levels.length; i++) {
            s += "(" + i + ": " + levels[i] + ")";
        }
        s += "]";
        return s;
    }

//    public void makeTree(EngineTree root, int depth, char color){
//        if (depth == 0){
//            return;
//        }
//        Piece piece;
//        char nextColor;
//        if (color == 'w'){
//            nextColor = 'b';
//        }
//        else {
//            nextColor = 'w';
//        }
//        int allMovesSize = root.leaves.length;
//        Move[] leaves = new Move[allMovesSize];
//        for (int i = 0; i < allMovesSize; i++) {
//            Engine newEngine = root.engine.clone();
//            piece = newEngine.getBoard().getBoard()[root.leaves[i].from.row][root.leaves[i].from.col];
//            if (piece.letter == 'p'){
//                ((Pawn) piece).pawnMove(newEngine.getBoard(), root.leaves[i].to.row, root.leaves[i].to.col, newEngine.getEnPassantLocation());
//            }
//            else {
//                piece.move(newEngine.getBoard(), root.leaves[i].to.row, root.leaves[i].to.col);
//            }
//            EngineTree leaf_i = new EngineTree(newEngine);
//            leaves[i] = leaf_i;
//            makeTree(leaf_i, depth - 1, nextColor);
//        }
//        root.setLeaves(leaves);
//    }
}
