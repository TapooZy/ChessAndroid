package com.example.chess;
public class Queue<Location>{

    private LocationMove<Location> first;
    private LocationMove<Location> last;
    private int size;

    public Queue()
    {
        this.first = null;
        this.last = null;
        this.size = 0;
    }

    public void insert (Location from, Location to)
    {
        LocationMove<Location> temp = new LocationMove<>(from, to);
        if (this.first == null){
            this.first = temp;
            this.last = temp;
        }
        else if (this.first == this.last){
            this.last = temp;
            this.first.setNext(this.last);
        }
        else {
            this.last.setNext(temp);
            this.last = temp;
        }
        size++;
    }

    public LocationMove<Location> remove(){
        LocationMove<Location> thisFirst = first;
        this.first = this.first.getNext();
        if(this.first == this.last)
            this.last = null;
        size--;
        return thisFirst;
    }

//    public boolean isInsideQueue(Location location){
//        int queueSize = size;
//        for (int i = 0; i < queueSize; i++) {
//            Location[] move = remove();
//            if (location == move[1]){
//                return true;
//            }
//        }
//        return false;
//    }

    public int getSize() {
        return size;
    }
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        LocationMove<Location> current = first;

        result.append("Queue: [");

        while (current != null) {
            result.append("(").append(current.getFrom()).append(", ").append(current.getTo()).append(")");

            if (current.getNext() != null) {
                result.append(", ");
            }

            current = current.getNext();
        }

        result.append("]");

        return result.toString();
    }
}