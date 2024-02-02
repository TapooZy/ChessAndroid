package com.example.chess;

public class LocationMove<Location> {
    public Location from;
    public Location to;
    private LocationMove<Location> next;

    public LocationMove(Location from, Location to)
    {
        this.from = from;
        this.to = to;
        this.next=null;
    }

    public LocationMove(Location from, Location to, LocationMove<Location> next)
    {
        this.from = from;
        this.to = to;
        this.next=next;
    }

    public LocationMove<Location> getNext()
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

    public void setNext(LocationMove<Location> next)
    {
        this.next = next;
    }

//    public String toString()
//    {
//        return  this.value.toString();
//
//    }
}
