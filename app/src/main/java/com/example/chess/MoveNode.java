package com.example.chess;

public class MoveNode<Move> {

    public com.example.chess.Move move;
    private MoveNode<Move> next;

    public MoveNode(com.example.chess.Move move, MoveNode<Move> next) {
        this.move = move;
        this.next = next;
    }

    public MoveNode(com.example.chess.Move move) {
        this.move = move;
        this.next = null;
    }
    public MoveNode<Move> getNext()
    {
        return next;
    }
    public boolean hasNext()
    {
        return this.next != null;
    }

    public void setNext(MoveNode<Move> next)
    {
        this.next = next;
    }

    public String toString(){
        String s = "";
        MoveNode<Move> curr = this;
        while (curr != null) {
            s += "(from: " + curr.move.from.toString() + ", to: " + curr.move.to.toString() + ")";
            curr = curr.getNext();
        }
        return s;
    }
}