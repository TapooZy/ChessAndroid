package com.example.chess;

public class King extends Piece {

    public King(char color, int row, int col) {
        super(color, 'K', row, col, 1000000000);
    }
    public Queue<Location> getPossibleMoves(Board board, boolean checkCheck) {
        int row1, col1;
        Queue<Location> moves = new Queue<>();
        Board newBoard;
        Location from = new Location(this.row, this.col);
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
        for (int i = 0; i < 8; i++) {
            row1 = row + directions[i][0];
            col1 = col + directions[i][1];
            if (row1 >= 0 && row1 <= 7 && col1 >= 0 && col1 <= 7) {
                Piece piece = board.getBoard()[row1][col1];
                if (piece == null){
                    if (checkCheck) {
                        newBoard = board.clone();
                        move(newBoard, row1, col1);
                        if (newBoard.canMove(this)){
                            Location to = new Location(row1, col1);
                            moves.insert(from, to);
                        }
                    }
                    else {
                        Location to = new Location(row1, col1);
                        moves.insert(from, to);
                    }
                }
                else if (piece.color != color){
                    if (checkCheck) {
                        newBoard = board.clone();
                        move(newBoard, row1, col1);
                        if (newBoard.canMove(this)){
                            Location to = new Location(row1, col1);
                            moves.insert(from, to);
                        }
                    }
                    else {
                        Location to = new Location(row1, col1);
                        moves.insert(from, to);
                    }
                }
            }
        }
        if (checkCheck) {
            if (canCastleLeft(board)){
                Location to = new Location(this.row, col - 2);
                moves.insert(from, to);
            }
            if (canCastleRight(board)) {
                Location to = new Location(row, col + 2);
                moves.insert(from, to);
            }
        }
        return moves;

    }

    public boolean canCastleRight (Board board)
    {
        Queue<Location> allMoves = board.getColorMoves(this.color, true);
        LocationMove<Location> individualMove;
        if (board.getBoard()[row][7] != null)
        {
            if (board.getBoard()[row][7] instanceof Rook)
            {
                if (!board.getBoard()[row][7].didMove && !this.didMove)
                {
                    for (int i = 0; i < allMoves.getSize(); i++)
                    {
                        individualMove = allMoves.remove();
                        for (int col = 5; col < 7; col++)
                        {
                            if (board.getBoard()[row][col] != null || (individualMove.getTo().row == row && individualMove.getTo().col == col))
                            {
                                return false;
                            }
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public boolean canCastleLeft (Board board){
        Queue<Location> allMoves = board.getColorMoves(this.color, true);
        LocationMove<Location> individualMove;
        if (board.getBoard()[row][0] != null)
        {
            if (board.getBoard()[row][0] instanceof Rook)
            {
                if (!board.getBoard()[row][0].didMove && !this.didMove)
                {
                    for (int i = 0; i < allMoves.getSize(); i++)
                    {
                        individualMove = allMoves.remove();
                        for (int col = 3; col > 0; col--)
                        {
                            if (board.getBoard()[row][col] != null || (individualMove.getTo().row == row && individualMove.getTo().col == col))
                            {
                                return false;
                            }
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }
    @Override
    public Move move(Board board, int row, int col){
        if (col == this.col + 2){
            King king = new King(color, row, col);
            king.didMove = true;
            board.getBoard()[row][col] = king;
            board.getBoard()[this.row][this.col] = null;
            Rook rook = new Rook(color, row, col-1);
            board.getBoard()[this.row][7] = null;
            board.getBoard()[row][col-1] = rook;
            rook.didMove = true;
        }
        else if (col == this.col - 2){
            King king = new King(color, row, col);
            king.didMove = true;
            board.getBoard()[row][col] = king;
            board.getBoard()[this.row][this.col] = null;
            Rook rook = new Rook(color, row, col+1);
            board.getBoard()[this.row][0] = null;
            board.getBoard()[row][col+1] = rook;
            rook.didMove = true;
        }
        else {
            King king = new King(color, row, col);
            king.didMove = true;
            board.getBoard()[row][col] = king;
            board.getBoard()[this.row][this.col] = null;
        }
        Move move = new Move(new Location(this.row, this.col), new Location(row, col), false, board);
        return move;
    }

    @Override
    public Piece clone(){
        King newKing = new King(color, row, col);
        newKing.didMove = didMove;
        return newKing;
    }
}
