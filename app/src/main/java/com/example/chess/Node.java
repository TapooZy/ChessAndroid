package com.example.chess;

public class Node<Integer> {
    private int row;
    private int col;
    private Node <Integer> next;

    public Node(int row, int col)
    {
        this.row=row;
        this.col=col;
        this.next=null;
    }

    public Node(int row, int col, Node<Integer> next)
    {
        this.row=row;
        this.col=col;
        this.next=next;
    }

    public Node<Integer> getNext()
    {
        return next;
    }

    public int getRow()
    {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean hasNext()
    {
        return this.next != null;
    }

    public void setNext(Node<Integer> next)
    {
        this.next = next;
    }

//    public String toString()
//    {
//        return  this.value.toString();
//
//    }
}
