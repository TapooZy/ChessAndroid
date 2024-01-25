package com.example.chess;

public class EngineTree {
    public Engine engine;
    public EngineTree[] roots;

    public EngineTree(Engine engine, EngineTree[] roots){
        this.engine = engine;
        this.roots = roots;
    }

    public EngineTree(Engine engine){
        this.engine = engine;
        this.roots = null;
    }

    public void setRoots(EngineTree[] root, int index) {
        roots = root;
    }
}
