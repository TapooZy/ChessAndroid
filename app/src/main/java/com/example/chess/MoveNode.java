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
    public void setNext(MoveNode<Move> next)
    {
        this.next = next;
    }

    public String toString(){
        String s = "", enPassant = "";
        MoveNode<Move> curr = this;
        curr = curr.getNext();
        while (curr != null) {
            s += "(from: " + curr.move.from.toString() + ", to: " + curr.move.to.toString() + ", enPassant: " + enPassant + ")";
            curr = curr.getNext();
        }
        return s;
    }

    public MoveNode<Move> clone(){
        MoveNode<Move> curr = this, newNode = new MoveNode<Move>(curr.move), next, newCurr = newNode;
        curr = curr.getNext();
        while (curr != null){
            next = new MoveNode<Move>(curr.move);
            newCurr.setNext(next);
            newCurr = newCurr.getNext();
            curr = curr.getNext();
        }
        return newNode;
    }
}
