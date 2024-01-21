package com.example.chess;

public class Queue<Integer> {

    private Node<Integer> first;
    private Node <Integer> last;
    private int size;

    public Queue()
    {
        this.first = null;
        this.last = null;
        this.size = 0;
    }

    public void insert (int row, int col)
    {
        Node<Integer> temp = new  Node <Integer> (row, col);
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

    public int[] remove()
    {
        int row = this.first.getRow();
        int col = this.first.getCol();
        this.first = this.first.getNext();
        if(this.first == this.last)
            this.last = null;
        int[] arr = {row, col};
        size--;
        return arr;
    }

    public boolean isInsideQueue(int[] arr){
        int queueSize = size;
        for (int i = 0; i < queueSize; i++) {
            int[] move = remove();
            if (arr[0] == move[0] && arr[1] == move[1]){
                return true;
            }
        }
        return false;
    }

    public int getSize() {
        return size;
    }
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        Node<Integer> current = first;

        result.append("Queue: [");

        while (current != null) {
            result.append("(").append(current.getRow()).append(", ").append(current.getCol()).append(")");

            if (current.getNext() != null) {
                result.append(", ");
            }

            current = current.getNext();
        }

        result.append("]");

        return result.toString();
    }
}