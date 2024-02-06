package com.example.chess;

public class Queen extends Piece{

    public Queen(char color, int row, int col){
        super(color, 'q', row, col, 5);
    }

    public Queue<Location> getPossibleMoves(Board board, boolean checkChecked) {
        Queue<Location> moves = new Queue<>();
        Board newBoard = board.clone();
        newBoard.getBoard()[row][col] = new Rook(color, row, col);
        Queue<Location> RookMoves = newBoard.getBoard()[row][col].getPossibleMoves(newBoard, true);
        newBoard.getBoard()[row][col] = new Bishop(color, row, col);
        Queue<Location> BishopMoves = newBoard.getBoard()[row][col].getPossibleMoves(newBoard, true);
        int RookMovesSize = RookMoves.getSize();
        LocationNode<Location> individualMove;
        for (int i = 0; i < RookMovesSize; i++) {
            individualMove = RookMoves.remove();
            moves.insert(individualMove.getFrom(), individualMove.getTo());
        }
        int BishopMovesSize = BishopMoves.getSize();
        for (int i = 0; i < BishopMovesSize; i++) {
            individualMove = BishopMoves.remove();
            moves.insert(individualMove.getFrom(), individualMove.getTo());
        }
        return moves;
    }
    @Override
    public Move move(Board board, int row, int col){
        Move move = new Move(new Location(this.row, this.col), new Location(row, col), null, board);
        board.getBoard()[row][col] = new Queen(color, row, col);
        board.getBoard()[this.row][this.col] = null;
        return move;
    }

    @Override
    public Piece clone(){
        return new Queen(color, row, col);
    }
}
