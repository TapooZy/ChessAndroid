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
        String s = "", enPassant = "";
        MoveNode<Move> curr = this;
        curr = curr.getNext();
        while (curr != null) {
            if (curr.move.enPassant_location == null){
                enPassant = "null";
            }
            else {
                enPassant = "(" + curr.move.enPassant_location[0] + ", " + curr.move.enPassant_location[1] + ")";
            }
            s += "(from: " + curr.move.from.toString() + ", to: " + curr.move.to.toString() + ", enPassant: " + enPassant + ")";
            curr = curr.getNext();
        }
        return s;
    }
}
