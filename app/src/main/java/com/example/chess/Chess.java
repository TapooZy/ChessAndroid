package com.example.chess;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
    ImageView from_green;
    ImageView from_white;
    boolean wasClickedOnAPiece;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chess);
        from_green = new ImageView(this);
        from_white = new ImageView(this);
        from_green.setBackgroundColor(getResources().getColor(R.color.can_move_to_from_green));
        from_white.setBackgroundColor(getResources().getColor(R.color.can_move_to_from_white));

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
                        Queue<Integer> moves = onSquareClicked(row, col, wasClickedOnAPiece);

                        // Handle the second click event (call another method or write additional code)
//                        onSecondClick(row, col, moves, piece);
                    }
                });

                // Add the ImageView to the chessboard GridLayout
                chessBoard.addView(imageView);
            }
        }
    }

    private Queue<Integer> onSquareClicked(int row, int col, boolean wasClickedOnAPiece) {
        Log.d("onclick", "entered onclick");
        if (wasClickedOnAPiece){
            View square = chessBoard.getChildAt(row * 8 + col);
            ColorDrawable squareColor = (ColorDrawable) square.getBackground();
            int squareId = squareColor.getColor();
            ColorDrawable whiteColor = (ColorDrawable) from_white.getBackground();
            int whiteId = whiteColor.getColor();
            ColorDrawable greenColor = (ColorDrawable) from_green.getBackground();
            int greenId = greenColor.getColor();
            if (squareId == whiteId || squareId == greenId){
                piece.move(board, row, col);
                this.wasClickedOnAPiece = false;
                chessBoard.removeAllViews();
                showBoard();
                return  null;
            }
            piece = board.getBoard()[row][col];
            if (piece != null){
                return onSquareClicked(row, col, false);
            }
            else{
                this.wasClickedOnAPiece = false;
                return null;
            }
        }
        else {
            piece = board.getBoard()[row][col];
            if (piece == null) {
                Toast.makeText(this, "This square is empty", Toast.LENGTH_SHORT).show();
                wasClickedOnAPiece = false;
                chessBoard.removeAllViews();
                showBoard();
                return null;
            }
            this.wasClickedOnAPiece = true;
            Queue<Integer> moves = piece.getPossibleMoves(board);
            if (moves.getSize() == 0) {
                Toast.makeText(this, "no moves", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, moves.toString(), Toast.LENGTH_SHORT).show();
            }
            chessBoard.removeAllViews();
            showBoard();
            int size = moves.getSize();
            int[] individual_move;
            for (int i = 0; i < size; i++) {
                individual_move = moves.remove();
                View square_to_color = chessBoard.getChildAt(individual_move[0] * 8 + individual_move[1]);
                if ((individual_move[0] + individual_move[1]) % 2 == 0) {
                    square_to_color.setBackgroundColor(getResources().getColor(R.color.can_move_to_from_white));
                } else {
                    square_to_color.setBackgroundColor(getResources().getColor(R.color.can_move_to_from_green));
                }
            }
            return moves;
        }
    }

    private void onSecondClick(int row, int col, Queue<Integer> moves, Piece p){
        int[] individual_move;
        int size = moves.getSize();
        for (int i = 0; i < size; i++) {
            individual_move = moves.remove();
            if (individual_move[0] == row && individual_move[1] == col){
                p.move(board, row, col);
            }
        }
        chessBoard.removeAllViews();
        showBoard();
    }
}
