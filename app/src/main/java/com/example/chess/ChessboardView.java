package com.example.chess;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class ChessboardView extends View {
    private static final int GREEN_COLOR = Color.rgb(119, 149, 86);
    private static final int WHITE_COLOR = Color.rgb(236, 235, 235);

    private Board board = new Board();

    private Paint paint;
    private int boardSize = 8; // 8x8 chessboard
    private int squareSize;
    private int selectedRow = -1;
    private int selectedCol = -1;

    public ChessboardView(Context context) {
        super(context);
        board.startGame();
        init();
    }

    public ChessboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        board.startGame();
        init();
    }

    private void init() {
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        squareSize = getWidth() / boardSize;
        int startPoint = (getHeight()-getWidth())/2;
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                int color = ((row + col) % 2 == 0) ? WHITE_COLOR : GREEN_COLOR;
                drawSquare(canvas, col * squareSize, startPoint + row * squareSize, squareSize, color);
                Piece p = board.getBoard()[row][col];
                if (p instanceof Bishop) {
                    if (row == 0) {
                        drawPiece(canvas, col * squareSize, startPoint , squareSize, R.drawable.black_bishop);
                    }
                    else drawPiece(canvas, col * squareSize, startPoint + row * squareSize, squareSize, R.drawable.white_bishop);
                }
                if (p instanceof Pawn){
                    if (row == 1){
                        drawPiece(canvas, col * squareSize, startPoint + row * squareSize, squareSize, R.drawable.black_pawn);
                    }
                    else if (row == 6){
                        drawPiece(canvas, col * squareSize, startPoint + row * squareSize, squareSize, R.drawable.white_pawn);
                    }
                }
                if (p instanceof Rook){
                    if (row == 0){
                        drawPiece(canvas, col * squareSize, startPoint , squareSize, R.drawable.black_rook);
                    }
                    else drawPiece(canvas, col * squareSize, startPoint + row * squareSize, squareSize, R.drawable.white_rook);
                }
                if (p instanceof King){
                    if (row == 0){
                        drawPiece(canvas, col * squareSize, startPoint , squareSize, R.drawable.black_king);
                    }
                    else drawPiece(canvas, col * squareSize, startPoint + row * squareSize, squareSize, R.drawable.white_king);
                }
                if (p instanceof Knight){
                    if (row == 0){
                        drawPiece(canvas, col * squareSize, startPoint , squareSize, R.drawable.black_knight);
                    }
                    else drawPiece(canvas, col * squareSize, startPoint + row * squareSize, squareSize, R.drawable.white_knight);
                }
                if (p instanceof Queen){
                    if (row == 0){
                        drawPiece(canvas, col * squareSize, startPoint, squareSize, R.drawable.black_queen);
                    }
                    else drawPiece(canvas, col * squareSize, startPoint + row * squareSize, squareSize, R.drawable.white_queen);
                }
            }

            if (selectedRow != -1 && selectedCol != -1) {
                paint.setColor(Color.RED);
                int left = selectedCol * squareSize;
                int top = selectedRow * squareSize;
                int right = left + squareSize;
                int bottom = top + squareSize;
                canvas.drawRect(left, top, right, bottom, paint);
            }
        }
    }

    private void drawSquare(Canvas canvas, float left, float top, float size, int color) {
        paint.setColor(color);
        canvas.drawRect(left, top, left + size, top + size, paint);

    }

    private void drawPiece(Canvas canvas, int left, int top, int size, int piece){
        Drawable d = getResources().getDrawable(piece, null);
        d.setBounds(left, top, left + size, top + size);
        d.draw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // Calculate the row and column based on touch coordinates
                int touchedRow = (int) (y / squareSize);
                int touchedCol = (int) (x / squareSize);

                // Update the selected row and column
                selectedRow = touchedRow;
                selectedCol = touchedCol;

                // Trigger redraw
                invalidate();
                break;
        }

        return true;
    }
}

