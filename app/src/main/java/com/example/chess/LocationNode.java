package com.example.chess;

public class LocationNode<Location> {
    public Location from;
    public Location to;
    private LocationNode<Location> next;

    public LocationNode(Location from, Location to)
    {
        this.from = from;
        this.to = to;
        this.next=null;
    }

    public LocationNode(Location from, Location to, LocationNode<Location> next)
    {
        this.from = from;
        this.to = to;
        this.next=next;
    }

    public LocationNode<Location> getNext()
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

    public void setNext(LocationNode<Location> next)
    {
        this.next = next;
    }

//    public String toString()
//    {
//        return  this.value.toString();
//
//    }
}
