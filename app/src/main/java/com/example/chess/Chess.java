package com.example.chess;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.GridLayout;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.Toast;

import org.checkerframework.checker.units.qual.K;

public class Chess extends AppCompatActivity {
    Dialog promotionDialog;
    ImageView queen, knight, rook, bishop;
    GridLayout chessBoard;
    Engine engine = new Engine();
    Board board = engine.getBoard();
    Piece piece;
    int screenWidth, screenHeight, whiteId, greenId, promotionCol;
    char nextMoveColor = 'w';
    Piece whiteKing = board.findKing('w'), blackKing = board.findKing('b');
    boolean wasClickedOnAPiece;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chess);
        ImageView from_green = new ImageView(this);
        ImageView from_white = new ImageView(this);
        from_green.setBackgroundColor(getResources().getColor(R.color.can_move_to_from_green));
        from_white.setBackgroundColor(getResources().getColor(R.color.can_move_to_from_white));
        ColorDrawable whiteColor = (ColorDrawable) from_white.getBackground();
        whiteId = whiteColor.getColor();
        ColorDrawable greenColor = (ColorDrawable) from_green.getBackground();
        greenId = greenColor.getColor();
        chessBoard = findViewById(R.id.chessBoard);
        showBoard(false);
    }

    private void showBoard(boolean isLoadGame) {
        // Get the width of the screen in pixels
        DisplayMetrics displayMetrics = new DisplayMetrics(); // DisplayMetrics instance to store display information
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics); // Retrieve display metrics
        screenWidth = displayMetrics.widthPixels; // Extract the width of the screen in pixels
        for (int i = 7; i > -1; i--) {
            for (int j = 7; j > -1; j--) {
                int row = i;  // Store the current row
                int col = j;  // Store the current column

                // Create an ImageView for each square on the chessboard
                ImageView imageView = new ImageView(this);

                // Set layout parameters for the ImageView based on screen width
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = screenWidth / 8; // Divide the screen width into 8 equal parts for each square
                params.height = screenWidth / 8;
                imageView.setLayoutParams(params);
                imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

                // Determine the background color for each square
                int backgroundColor = (i + j) % 2 == 0 ? getResources().getColor(R.color.light_square) : getResources().getColor(R.color.dark_square);
                imageView.setBackgroundColor(backgroundColor);

                // Get the piece at the current board position
                Piece p = board.getBoard()[i][j];

                // Set the appropriate image resource based on the type and color of the piece
                if (p instanceof Bishop) {
                    if (p.color == 'b') {
                        imageView.setImageResource(R.drawable.black_bishop);
                    } else {
                        imageView.setImageResource(R.drawable.white_bishop);
                    }
                }
                if (p instanceof Pawn) {
                    if (p.color == 'b') {
                        imageView.setImageResource(R.drawable.black_pawn);
                    } else {
                        imageView.setImageResource(R.drawable.white_pawn);
                    }
                }
                if (p instanceof Rook) {
                    if (p.color == 'b') {
                        imageView.setImageResource(R.drawable.black_rook);
                    } else {
                        imageView.setImageResource(R.drawable.white_rook);
                    }
                }
                if (p instanceof King) {
                    if (p.color == 'b') {
                        imageView.setImageResource(R.drawable.black_king);
                    } else {
                        imageView.setImageResource(R.drawable.white_king);
                    }
                }
                if (p instanceof Knight) {
                    if (p.color == 'b') {
                        imageView.setImageResource(R.drawable.black_knight);
                    } else {
                        imageView.setImageResource(R.drawable.white_knight);
                    }
                }
                if (p instanceof Queen) {
                    if (p.color == 'b') {
                        imageView.setImageResource(R.drawable.black_queen);
                    } else {
                        imageView.setImageResource(R.drawable.white_queen);
                    }
                }

                if (!isLoadGame) {
                    // Set a click listener for each ImageView
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Handle the first click event
                            onSquareClicked(row, col, wasClickedOnAPiece);
                        }
                    });
                }

                // Add the ImageView to the chessboard GridLayout
                chessBoard.addView(imageView);
            }
        }
    }

    private void onSquareClicked(int row, int col, boolean wasClickedOnAPiece) {
        if (wasClickedOnAPiece) {
            View square = chessBoard.getChildAt(row * 8 + col);
            ColorDrawable squareColor = (ColorDrawable) square.getBackground();
            int squareId = squareColor.getColor();
            if (squareId == whiteId || squareId == greenId) {
                if (piece instanceof Pawn) {
                    int[] cords = {row, col};
                    int pieceRow = piece.row;
                    ((Pawn) piece).pawnMove(board, row, col, engine.getEnPassantLocation());
                    if (row == 7 && piece.color == 'b'){
                        blackPawnPromotion(col);
                    }
                    int dist = pieceRow - row;
                    dist *= dist;
                    if (dist == 4) {
                        engine.setEnPassantLocation(cords);
                    } else {
                        engine.setEnPassantLocation(null);
                    }
                } else {
                    piece.move(board, row, col);
                    engine.setEnPassantLocation(null);
                }
                if (nextMoveColor == 'w'){
                    whiteKing = board.findKing('w');
                }
                else {
                    blackKing = board.findKing('b');
                }
                this.wasClickedOnAPiece = false;
                setNextMoveColor();
                chessBoard.removeAllViews();
                showBoard(false);
                Queue<Integer> allMoves = board.getEndMoves(nextMoveColor);
                if (allMoves.getSize() == 0 && board.isInCheck(nextMoveColor)) {
                    if (nextMoveColor == 'b') {
                        Toast.makeText(this, "White won", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(this, "Black won", Toast.LENGTH_LONG).show();
                    }
                }
                else if (allMoves.getSize() == 0 && !board.isInCheck(nextMoveColor)){
                    Toast.makeText(this, "Stalemate, no one won", Toast.LENGTH_LONG).show();
                }
                return;
            }
            piece = board.getBoard()[row][col];
            if (piece != null) {
                if (piece.color == nextMoveColor) {
                    onSquareClicked(row, col, false);
                } else {
                    chessBoard.removeAllViews();
                    showBoard(false);
                    this.wasClickedOnAPiece = false;
                }
            } else {
                chessBoard.removeAllViews();
                showBoard(false);
                this.wasClickedOnAPiece = false;
            }
        } else {
            Queue<Integer> moves;
            piece = board.getBoard()[row][col];
            if (piece == null) {
                Toast.makeText(this, "This square is empty", Toast.LENGTH_SHORT).show();
                chessBoard.removeAllViews();
                showBoard(false);
                return;
            }
            this.wasClickedOnAPiece = true;
            if (piece.color != nextMoveColor) {
                Toast.makeText(this, "Wrong piece color, pick again", Toast.LENGTH_SHORT).show();
                return;
            }
            if (piece instanceof Pawn) {
                moves = (((Pawn) piece).getPawnPossibleMoves(board, engine.getEnPassantLocation(), true));
            } else {
                moves = piece.getPossibleMoves(board, true);
            }
            chessBoard.removeAllViews();
            showBoard(false);
            int size = moves.getSize();
            int[] individual_move;
            if (size == 0) {
                Toast.makeText(this, "this piece has no moves", Toast.LENGTH_SHORT).show();
            }
            for (int i = 0; i < size; i++) {
                individual_move = moves.remove();
                View square_to_color = chessBoard.getChildAt(individual_move[0] * 8 + individual_move[1]);
                if ((individual_move[0] + individual_move[1]) % 2 == 0) {
                    square_to_color.setBackgroundColor(getResources().getColor(R.color.can_move_to_from_white));
                } else {
                    square_to_color.setBackgroundColor(getResources().getColor(R.color.can_move_to_from_green));
                }
            }
        }
    }

    public void setNextMoveColor() {
        if (nextMoveColor == 'w') {
            nextMoveColor = 'b';
        } else {
            nextMoveColor = 'w';
        }
    }

    public void blackPawnPromotion(int col){
        Dialog d = new Dialog(this);
        d.setContentView(R.layout.custom_dialog_black);
        d.setCancelable(false);
        promotionDialog = d;
        promotionCol = col;
        queen = d.findViewById(R.id.ibQueen);
        knight = d.findViewById(R.id.ibKnight);
        rook = d.findViewById(R.id.ibRook);
        bishop = d.findViewById(R.id.ibBishop);

        queen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                board.getBoard()[7][promotionCol] = new Queen('b', 7, promotionCol);
                chessBoard.removeAllViews();
                showBoard(false);
                promotionDialog.cancel();
            }
        });

        knight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                board.getBoard()[7][promotionCol] = new Knight('b', 7, promotionCol);
                chessBoard.removeAllViews();
                showBoard(false);
                promotionDialog.cancel();
            }
        });

        rook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                board.getBoard()[7][promotionCol] = new Rook('b', 7, promotionCol);
                board.getBoard()[7][promotionCol].didMove = true;
                chessBoard.removeAllViews();
                showBoard(false);
                promotionDialog.cancel();
            }
        });

        bishop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                board.getBoard()[7][promotionCol] = new Bishop('b', 7, promotionCol);
                chessBoard.removeAllViews();
                showBoard(false);
                promotionDialog.cancel();
            }
        });
        d.show();
    }

    public void loadGame(String moves) {
        int row, col, times = 0;
        Queue<Integer> pieceMoves;
        while (moves.length() != 0) {
            if (times % 2 == 0) {
                moves = moves.substring(2);
            }
            if (moves.charAt(0) == 'B') {
                if ((int) moves.charAt(1) == 120){
                    Bishop bishop = (Bishop) board.findPiece('b', nextMoveColor, -1, -1);
                    col = (int) moves.charAt(2) - 97;
                    row = (int) moves.charAt(3) - 49;
                    int[] move = {row, col};
                    pieceMoves = bishop.getPossibleMoves(board, true);
                    if (pieceMoves.isInsideQueue(move)){
                        bishop.move(board, row, col);
                        times++;
                    }
                    if (moves.charAt(4) == '+' || moves.charAt(4) == '#'){
                        moves = moves.substring(5);
                    }
                    else {
                        moves = moves.substring(4);
                    }
                }
//                else if ((int) moves.charAt(1) > 48 && (int) moves.charAt(1) < 57){
//                    Bishop bishop = (Bishop) board.findPiece('b', nextMoveColor, )
//                }
            }
//            else if (moves.charAt(0) == 'N') {
//                Knight knight;
//                if (moves.charAt(1) != 'x') {
//                    int row = (int) moves.charAt(2) - 48;
//                    int col = (int) moves.charAt(1) - 97;
//                    int[] move = {row, col};
//                    Queue<Integer> pieceMoves = knight.getPossibleMoves(board, true);
//                    if (pieceMoves.inInsideQueue(move)) {
//                        knight.move(board, row, col);
//                        setNextMoveColor();
//                    } else {
//                        board.getBoard()[knight.row][knight.col] = null;
//                        knight = (Knight) board.findPiece('k', nextMoveColor);
//                        pieceMoves = knight.getPossibleMoves(board, true);
//                        if (pieceMoves.inInsideQueue(move)) {
//                            knight.move(board, row, col);
//                            setNextMoveColor();
//                        }
//                    }
//                }
//                int row = (int) moves.charAt(3) - 48;
//                int col = (int) moves.charAt(2) - 97;
//                int[] move = {row, col};
//                Queue<Integer> pieceMoves = knight.getPossibleMoves(board, true);
//                if (pieceMoves.inInsideQueue(move)) {
//                    knight.move(board, row, col);
//                    setNextMoveColor();
//                }
//            } else if (moves.charAt(0) == 'Q') {
//                Queen queen = (Queen) board.findPiece('q', nextMoveColor);
//                if (moves.charAt(1) != 'x') {
//                    int row = (int) moves.charAt(2) - 48;
//                    int col = (int) moves.charAt(1) - 97;
//                    int[] move = {row, col};
//                    Queue<Integer> pieceMoves = queen.getPossibleMoves(board, true);
//                    if (pieceMoves.inInsideQueue(move)) {
//                        queen.move(board, row, col);
//                        setNextMoveColor();
//                    } else {
//                        board.getBoard()[queen.row][queen.col] = null;
//                        queen = (Queen) board.findPiece('q', nextMoveColor);
//                        pieceMoves = queen.getPossibleMoves(board, true);
//                        if (pieceMoves.inInsideQueue(move)) {
//                            queen.move(board, row, col);
//                            setNextMoveColor();
//                        }
//                    }
//                }
//                int row = (int) moves.charAt(3) - 48;
//                int col = (int) moves.charAt(2) - 97;
//                int[] move = {row, col};
//                Queue<Integer> pieceMoves = queen.getPossibleMoves(board, true);
//                if (pieceMoves.inInsideQueue(move)) {
//                    queen.move(board, row, col);
//                    setNextMoveColor();
//                }
//            } else if (moves.charAt(0) == 'R') {
//                Rook rook = (Rook) board.findPiece('r', nextMoveColor);
//                if (moves.charAt(1) != 'x') {
//                    int row = (int) moves.charAt(2) - 48;
//                    int col = (int) moves.charAt(1) - 97;
//                    int[] move = {row, col};
//                    Queue<Integer> pieceMoves = rook.getPossibleMoves(board, true);
//                    if (pieceMoves.inInsideQueue(move)) {
//                        rook.move(board, row, col);
//                        setNextMoveColor();
//                    } else {
//                        board.getBoard()[rook.row][rook.col] = null;
//                        rook = (Rook) board.findPiece('r', nextMoveColor);
//                        pieceMoves = rook.getPossibleMoves(board, true);
//                        if (pieceMoves.inInsideQueue(move)) {
//                            rook.move(board, row, col);
//                            setNextMoveColor();
//                        }
//                    }
//                }
//                else {
//                    int row = (int) moves.charAt(3) - 48;
//                    int col = (int) moves.charAt(2) - 97;
//                    int[] move = {row, col};
//                    Queue<Integer> pieceMoves = rook.getPossibleMoves(board, true);
//                    if (pieceMoves.inInsideQueue(move)) {
//                        rook.move(board, row, col);
//                        setNextMoveColor();
//                    }
//                }
//            } else if (moves.charAt(0) == 'K' || moves.charAt(0) == 'O') {
//                King king = (King) board.findPiece('K', nextMoveColor);
//                if (moves.charAt(1) != 'x') {
//                    if (moves.charAt(0) == 'O') {
//                        if (moves.length() > 4) {
//                            if (moves.charAt(4) == 'O') {
//                                int row = king.row;
//                                int col = king.col - 2;
//                                int[] move = {row, col};
//                                Queue<Integer> pieceMoves = king.getPossibleMoves(board, true);
//                                if (pieceMoves.inInsideQueue(move)) {
//                                    king.move(board, row, col);
//                                    setNextMoveColor();
//                                }
//                            }
//                            else {
//                                int row = king.row;
//                                int col = king.col + 2;
//                                int[] move = {row, col};
//                                Queue<Integer> pieceMoves = king.getPossibleMoves(board, true);
//                                if (pieceMoves.inInsideQueue(move)) {
//                                    king.move(board, row, col);
//                                    setNextMoveColor();
//                                }
//                            }
//                        }
//                        else {
//                            int row = king.row;
//                            int col = king.col + 2;
//                            int[] move = {row, col};
//                            Queue<Integer> pieceMoves = king.getPossibleMoves(board, true);
//                            if (pieceMoves.inInsideQueue(move)) {
//                                king.move(board, row, col);
//                                setNextMoveColor();
//                            }
//                        }
//                    }
//                    else {
//                        int row = (int) moves.charAt(2) - 48;
//                        int col = (int) moves.charAt(1) - 97;
//                        int[] move = {row, col};
//                        Queue<Integer> pieceMoves = king.getPossibleMoves(board, true);
//                        if (pieceMoves.inInsideQueue(move)) {
//                            king.move(board, row, col);
//                            setNextMoveColor();
//                        }
//                    }
//                }
//                else {
//                    int row = (int) moves.charAt(3) - 48;
//                    int col = (int) moves.charAt(2) - 97;
//                    int[] move = {row, col};
//                    Queue<Integer> pieceMoves = king.getPossibleMoves(board, true);
//                    if (pieceMoves.inInsideQueue(move)) {
//                        king.move(board, row, col);
//                        setNextMoveColor();
//                    }
//                }
//            }
        }
    }
}
