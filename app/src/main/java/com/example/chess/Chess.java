package com.example.chess;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.BadParcelableException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.Toast;

import java.sql.SQLData;

public class Chess extends AppCompatActivity {
    GridLayout chessBoard;
    Engine engine = new Engine();
    Board board = engine.getBoard();
    Piece piece;
    char nextMoveColor = 'w';
    int whiteId;
    int greenId;
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
        showBoard();
    }

    private void showBoard() {
        // Get the width of the screen in pixels
        DisplayMetrics displayMetrics = new DisplayMetrics(); // DisplayMetrics instance to store display information
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics); // Retrieve display metrics
        int screenWidth = displayMetrics.widthPixels; // Extract the width of the screen in pixels
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                final int row = i;  // Store the current row
                final int col = j;  // Store the current column

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
                    if (p.getColor() == 'b') {
                        imageView.setImageResource(R.drawable.black_bishop);
                    } else {
                        imageView.setImageResource(R.drawable.white_bishop);
                    }
                }
                if (p instanceof Pawn) {
                    if (p.getColor() == 'b') {
                        imageView.setImageResource(R.drawable.black_pawn);
                    } else {
                        imageView.setImageResource(R.drawable.white_pawn);
                    }
                }
                if (p instanceof Rook) {
                    if (p.getColor() == 'b') {
                        imageView.setImageResource(R.drawable.black_rook);
                    } else {
                        imageView.setImageResource(R.drawable.white_rook);
                    }
                }
                if (p instanceof King) {
                    if (p.getColor() == 'b') {
                        imageView.setImageResource(R.drawable.black_king);
                    } else {
                        imageView.setImageResource(R.drawable.white_king);
                    }
                }
                if (p instanceof Knight) {
                    if (p.getColor() == 'b') {
                        imageView.setImageResource(R.drawable.black_knight);
                    } else {
                        imageView.setImageResource(R.drawable.white_knight);
                    }
                }
                if (p instanceof Queen) {
                    if (p.getColor() == 'b') {
                        imageView.setImageResource(R.drawable.black_queen);
                    } else {
                        imageView.setImageResource(R.drawable.white_queen);
                    }
                }

                // Set a click listener for each ImageView
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Handle the first click event
                        onSquareClicked(row, col, wasClickedOnAPiece);
                    }
                });

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
                showBoard();
                Queue<Integer> allMoves = board.getColorMoves(nextMoveColor, false);
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
                if (piece.getColor() == nextMoveColor) {
                    onSquareClicked(row, col, false);
                } else {
                    chessBoard.removeAllViews();
                    showBoard();
                    this.wasClickedOnAPiece = false;
                }
            } else {
                chessBoard.removeAllViews();
                showBoard();
                this.wasClickedOnAPiece = false;
            }
        } else {
            Queue<Integer> moves;
            piece = board.getBoard()[row][col];
            if (piece == null) {
                Toast.makeText(this, "This square is empty", Toast.LENGTH_SHORT).show();
                chessBoard.removeAllViews();
                showBoard();
                return;
            }
            this.wasClickedOnAPiece = true;
            if (piece.getColor() != nextMoveColor) {
                Toast.makeText(this, "Wrong piece color, pick again", Toast.LENGTH_SHORT).show();
                return;
            }
            if (piece instanceof Pawn) {
                moves = (((Pawn) piece).getPawnPossibleMoves(board, engine.getEnPassantLocation(), true));
            } else {
                moves = piece.getPossibleMoves(board, true);
            }
            chessBoard.removeAllViews();
            showBoard();
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
}
