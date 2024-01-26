package com.example.chess;

public class EngineTree {
    public Engine engine;
    public EngineTree[] leaves;

    public EngineTree(Engine engine, EngineTree[] roots){
        this.engine = engine;
        this.leaves = roots;
    }

    public EngineTree(Engine engine){
        this.engine = engine;
        this.leaves = null;
    }

    public void setLeaves(EngineTree[] leaves) {
        this.leaves = leaves;
    }

    public int[] levels(){
        int levelsCount = 0;
        EngineTree engineTree = this;
        while (engineTree.leaves != null){
            levelsCount ++;
            engineTree = engineTree.leaves[0];
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
        return s;
    }
}
