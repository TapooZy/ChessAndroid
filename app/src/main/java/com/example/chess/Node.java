package com.example.chess;

public class Node<Location> {
    public Location from;
    public Location to;
    private Node <Location> next;

    public Node(Location from, Location to)
    {
        this.from = from;
        this.to = to;
        this.next=null;
    }

    public Node(Location from, Location to, Node<Location> next)
    {
        this.from = from;
        this.to = to;
        this.next=next;
    }

    public Node<Location> getNext()
    {
        return next;
    }

    public Location getFrom()
    {
        return from;
    }

    public Location getTo() {
        return to;
    }

    public boolean hasNext()
    {
        return this.next != null;
    }

    public void setNext(Node<Location> next)
    {
        this.next = next;
    }

//    public String toString()
//    {
//        return  this.value.toString();
//
//    }
}
