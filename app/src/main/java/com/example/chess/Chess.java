package com.example.chess;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.Toast;
public class Chess extends AppCompatActivity {
    Dialog promotionDialog;
    ImageView queen, knight, rook, bishop;
    GridLayout chessBoard;
    Engine engine = new Engine();
    Board board = engine.getBoard();
    Piece piece;
    int screenWidth, whiteId, greenId, promotionCol;
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
        wasClickedOnAPiece = false;
    }

    private void showBoard(boolean isLoadGame) {
        // Get the width of the screen in pixels
        DisplayMetrics displayMetrics = new DisplayMetrics(); // DisplayMetrics instance to store display information
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics); // Retrieve display metrics
        screenWidth = displayMetrics.widthPixels; // Extract the width of the screen in pixels
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
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
        Log.d("board", "row: " + row + " col: " + col);
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
            Log.d("piece", "row: " + piece.row + " col: " + piece.col + " color: " + piece.color + " letter: " + piece.letter);
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
                if (moves.charAt(1) == 'x'){
                    Bishop bishop = (Bishop) board.findPiece('b', nextMoveColor, -1, -1);
                    col = (int) moves.charAt(2) - 97;
                    row = (int) moves.charAt(3) - 49;
                    int[] move = {row, col};
                    pieceMoves = bishop.getPossibleMoves(board, true);
                    Board newBoard = board.clone();
                    while (!pieceMoves.isInsideQueue(move)){
                        newBoard = newBoard.clone();
                        newBoard.getBoard()[bishop.row][bishop.col] = null;
                        bishop = (Bishop) newBoard.findPiece('b', nextMoveColor, -1, -1);
                        pieceMoves = bishop.getPossibleMoves(board, true);
                    }
                    bishop.move(board, row, col);
                    times++;
                    if (moves.charAt(4) == '+' || moves.charAt(4) == '#'){
                        moves = moves.substring(5);
                    }
                    else {
                        moves = moves.substring(4);
                    }
                }
                else if ((int) moves.charAt(1) > 48 && (int) moves.charAt(1) < 57){
                    Bishop bishop = (Bishop) board.findPiece('b', nextMoveColor, (int) moves.charAt(1) - 49, -1);
                    if (moves.charAt(2) == 'x'){
                        col = moves.charAt(3) - 97;
                        row = moves.charAt(4) - 49;
                        int[] move = {row, col};
                        pieceMoves = bishop.getPossibleMoves(board, true);
                        if (pieceMoves.isInsideQueue(move)){
                            bishop.move(board, row, col);
                            times++;
                        }
                        if (moves.charAt(5) == '+' || moves.charAt(5) == '#'){
                            moves = moves.substring(6);
                        }
                        else {
                            moves = moves.substring(5);
                        }
                    }
                    else {
                        col = moves.charAt(2) - 97;
                        row = moves.charAt(3) - 49;
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
                }
                else if ((int) moves.charAt(1) > 96 && (int) moves.charAt(1) < 105){
                    Bishop bishop = (Bishop) board.findPiece('b', nextMoveColor, -1, (int) moves.charAt(1) - 97);
                    if (moves.charAt(2) == 'x'){
                        col = moves.charAt(3) - 97;
                        row = moves.charAt(4) - 49;
                        int[] move = {row, col};
                        pieceMoves = bishop.getPossibleMoves(board, true);
                        if (pieceMoves.isInsideQueue(move)){
                            bishop.move(board, row, col);
                            times++;
                        }
                        if (moves.charAt(5) == '+' || moves.charAt(5) == '#'){
                            moves = moves.substring(6);
                        }
                        else {
                            moves = moves.substring(5);
                        }
                    }
                    else {
                        col = moves.charAt(2) - 97;
                        row = moves.charAt(3) - 49;
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
                }
                else {
                    Bishop bishop = (Bishop) board.findPiece('b', nextMoveColor, -1, -1);
                    col = moves.charAt(1) - 97;
                    row = moves.charAt(2) - 49;
                    int[] move = {row, col};
                    pieceMoves = bishop.getPossibleMoves(board, true);
                    if (pieceMoves.isInsideQueue(move)) {
                        bishop.move(board, row, col);
                        times++;
                    }
                    if (moves.charAt(4) == '+' || moves.charAt(4) == '#') {
                        moves = moves.substring(5);
                    } else {
                        moves = moves.substring(4);
                    }
                }
            }
            else if (moves.charAt(0) == 'N'){
                if (moves.charAt(1) == 'x'){
                    Knight knight = (Knight) board.findPiece('k', nextMoveColor, -1, -1);
                    col = (int) moves.charAt(2) - 97;
                    row = (int) moves.charAt(3) - 49;
                    int[] move = {row, col};
                    pieceMoves = knight.getPossibleMoves(board, true);
                    Board newBoard = board.clone();
                    while (!pieceMoves.isInsideQueue(move)){
                        newBoard = newBoard.clone();
                        newBoard.getBoard()[knight.row][knight.col] = null;
                        knight = (Knight) newBoard.findPiece('k', nextMoveColor, -1, -1);
                        pieceMoves = knight.getPossibleMoves(board, true);
                    }
                    knight.move(board, row, col);
                    times++;
                    if (moves.charAt(4) == '+' || moves.charAt(4) == '#'){
                        moves = moves.substring(5);
                    }
                    else {
                        moves = moves.substring(4);
                    }
                }
                else if ((int) moves.charAt(1) > 48 && (int) moves.charAt(1) < 57){
                    Knight knight = (Knight) board.findPiece('k', nextMoveColor, (int) moves.charAt(1) - 49, -1);
                    if (moves.charAt(2) == 'x'){
                        col = moves.charAt(3) - 97;
                        row = moves.charAt(4) - 49;
                        int[] move = {row, col};
                        pieceMoves = knight.getPossibleMoves(board, true);
                        if (pieceMoves.isInsideQueue(move)){
                            knight.move(board, row, col);
                            times++;
                        }
                        if (moves.charAt(5) == '+' || moves.charAt(5) == '#'){
                            moves = moves.substring(6);
                        }
                        else {
                            moves = moves.substring(5);
                        }
                    }
                    else {
                        col = moves.charAt(2) - 97;
                        row = moves.charAt(3) - 49;
                        int[] move = {row, col};
                        pieceMoves = knight.getPossibleMoves(board, true);
                        if (pieceMoves.isInsideQueue(move)){
                            knight.move(board, row, col);
                            times++;
                        }
                        if (moves.charAt(4) == '+' || moves.charAt(4) == '#'){
                            moves = moves.substring(5);
                        }
                        else {
                            moves = moves.substring(4);
                        }
                    }
                }
                else if ((int) moves.charAt(1) > 96 && (int) moves.charAt(1) < 105){
                    Knight knight = (Knight) board.findPiece('k', nextMoveColor, -1, (int) moves.charAt(1) - 97);
                    if (moves.charAt(2) == 'x'){
                        col = moves.charAt(3) - 97;
                        row = moves.charAt(4) - 49;
                        int[] move = {row, col};
                        pieceMoves = knight.getPossibleMoves(board, true);
                        if (pieceMoves.isInsideQueue(move)){
                            knight.move(board, row, col);
                            times++;
                        }
                        if (moves.charAt(5) == '+' || moves.charAt(5) == '#'){
                            moves = moves.substring(6);
                        }
                        else {
                            moves = moves.substring(5);
                        }
                    }
                    else {
                        col = moves.charAt(2) - 97;
                        row = moves.charAt(3) - 49;
                        int[] move = {row, col};
                        pieceMoves = knight.getPossibleMoves(board, true);
                        if (pieceMoves.isInsideQueue(move)){
                            knight.move(board, row, col);
                            times++;
                        }
                        if (moves.charAt(4) == '+' || moves.charAt(4) == '#'){
                            moves = moves.substring(5);
                        }
                        else {
                            moves = moves.substring(4);
                        }
                    }
                }
                else {
                    Knight knight = (Knight) board.findPiece('k', nextMoveColor, -1, -1);
                    col = moves.charAt(1) - 97;
                    row = moves.charAt(2) - 49;
                    int[] move = {row, col};
                    pieceMoves = knight.getPossibleMoves(board, true);
                    if (pieceMoves.isInsideQueue(move)) {
                        knight.move(board, row, col);
                        times++;
                    }
                    if (moves.charAt(4) == '+' || moves.charAt(4) == '#') {
                        moves = moves.substring(5);
                    } else {
                        moves = moves.substring(4);
                    }
                }
            }
            else if (moves.charAt(0) == 'Q') {
                if (moves.charAt(1) == 'x') {
                    Queen queen = (Queen) board.findPiece('q', nextMoveColor, -1, -1);
                    col = (int) moves.charAt(2) - 97;
                    row = (int) moves.charAt(3) - 49;
                    int[] move = {row, col};
                    pieceMoves = queen.getPossibleMoves(board, true);
                    Board newBoard = board.clone();
                    while (!pieceMoves.isInsideQueue(move)){
                        newBoard = newBoard.clone();
                        newBoard.getBoard()[queen.row][queen.col] = null;
                        queen = (Queen) newBoard.findPiece('q', nextMoveColor, -1, -1);
                        pieceMoves = queen.getPossibleMoves(board, true);
                    }
                    queen.move(board, row, col);
                    times++;
                    if (moves.charAt(4) == '+' || moves.charAt(4) == '#') {
                        moves = moves.substring(5);
                    } else {
                        moves = moves.substring(4);
                    }
                } else if ((int) moves.charAt(1) > 48 && (int) moves.charAt(1) < 57) {
                    Queen queen = (Queen) board.findPiece('q', nextMoveColor, (int) moves.charAt(1) - 49, -1);
                    if (moves.charAt(2) == 'x') {
                        col = moves.charAt(3) - 97;
                        row = moves.charAt(4) - 49;
                        int[] move = {row, col};
                        pieceMoves = queen.getPossibleMoves(board, true);
                        if (pieceMoves.isInsideQueue(move)) {
                            queen.move(board, row, col);
                            times++;
                        }
                        if (moves.charAt(5) == '+' || moves.charAt(5) == '#') {
                            moves = moves.substring(6);
                        } else {
                            moves = moves.substring(5);
                        }
                    } else {
                        col = moves.charAt(2) - 97;
                        row = moves.charAt(3) - 49;
                        int[] move = {row, col};
                        pieceMoves = queen.getPossibleMoves(board, true);
                        if (pieceMoves.isInsideQueue(move)) {
                            queen.move(board, row, col);
                            times++;
                        }
                        if (moves.charAt(4) == '+' || moves.charAt(4) == '#') {
                            moves = moves.substring(5);
                        } else {
                            moves = moves.substring(4);
                        }
                    }
                } else if ((int) moves.charAt(1) > 96 && (int) moves.charAt(1) < 105) {
                    Queen queen = (Queen) board.findPiece('q', nextMoveColor, -1, (int) moves.charAt(1) - 97);
                    if (moves.charAt(2) == 'x') {
                        col = moves.charAt(3) - 97;
                        row = moves.charAt(4) - 49;
                        int[] move = {row, col};
                        pieceMoves = queen.getPossibleMoves(board, true);
                        if (pieceMoves.isInsideQueue(move)) {
                            queen.move(board, row, col);
                            times++;
                        }
                        if (moves.charAt(5) == '+' || moves.charAt(5) == '#') {
                            moves = moves.substring(6);
                        } else {
                            moves = moves.substring(5);
                        }
                    } else {
                        col = moves.charAt(2) - 97;
                        row = moves.charAt(3) - 49;
                        int[] move = {row, col};
                        pieceMoves = queen.getPossibleMoves(board, true);
                        if (pieceMoves.isInsideQueue(move)) {
                            queen.move(board, row, col);
                            times++;
                        }
                        if (moves.charAt(4) == '+' || moves.charAt(4) == '#') {
                            moves = moves.substring(5);
                        } else {
                            moves = moves.substring(4);
                        }
                    }
                } else {
                    Queen queen = (Queen) board.findPiece('q', nextMoveColor, -1, -1);
                    col = moves.charAt(1) - 97;
                    row = moves.charAt(2) - 49;
                    int[] move = {row, col};
                    pieceMoves = queen.getPossibleMoves(board, true);
                    if (pieceMoves.isInsideQueue(move)) {
                        queen.move(board, row, col);
                        times++;
                    }
                    if (moves.charAt(4) == '+' || moves.charAt(4) == '#') {
                        moves = moves.substring(5);
                    } else {
                        moves = moves.substring(4);
                    }
                }
            }
            else if (moves.charAt(0) == 'R'){
                if (moves.charAt(1) == 'x'){
                    Rook rook = (Rook) board.findPiece('r', nextMoveColor, -1, -1);
                    col = (int) moves.charAt(2) - 97;
                    row = (int) moves.charAt(3) - 49;
                    int[] move = {row, col};
                    pieceMoves = rook.getPossibleMoves(board, true);
                    Board newBoard = board.clone();
                    while (!pieceMoves.isInsideQueue(move)){
                        newBoard = newBoard.clone();
                        newBoard.getBoard()[rook.row][rook.col] = null;
                        rook = (Rook) newBoard.findPiece('r', nextMoveColor, -1, -1);
                        pieceMoves = rook.getPossibleMoves(board, true);
                    }
                    rook.move(board, row, col);
                    times++;
                    if (moves.charAt(4) == '+' || moves.charAt(4) == '#'){
                        moves = moves.substring(5);
                    }
                    else {
                        moves = moves.substring(4);
                    }
                }
                else if ((int) moves.charAt(1) > 48 && (int) moves.charAt(1) < 57){
                    Rook rook = (Rook) board.findPiece('r', nextMoveColor, (int) moves.charAt(1) - 49, -1);
                    if (moves.charAt(2) == 'x'){
                        col = moves.charAt(3) - 97;
                        row = moves.charAt(4) - 49;
                        int[] move = {row, col};
                        pieceMoves = rook.getPossibleMoves(board, true);
                        if (pieceMoves.isInsideQueue(move)){
                            rook.move(board, row, col);
                            times++;
                        }
                        if (moves.charAt(5) == '+' || moves.charAt(5) == '#'){
                            moves = moves.substring(6);
                        }
                        else {
                            moves = moves.substring(5);
                        }
                    }
                    else {
                        col = moves.charAt(2) - 97;
                        row = moves.charAt(3) - 49;
                        int[] move = {row, col};
                        pieceMoves = rook.getPossibleMoves(board, true);
                        if (pieceMoves.isInsideQueue(move)){
                            rook.move(board, row, col);
                            times++;
                        }
                        if (moves.charAt(4) == '+' || moves.charAt(4) == '#'){
                            moves = moves.substring(5);
                        }
                        else {
                            moves = moves.substring(4);
                        }
                    }
                }
                else if ((int) moves.charAt(1) > 96 && (int) moves.charAt(1) < 105){
                    Rook rook = (Rook) board.findPiece('r', nextMoveColor, -1, (int) moves.charAt(1) - 97);
                    if (moves.charAt(2) == 'x'){
                        col = moves.charAt(3) - 97;
                        row = moves.charAt(4) - 49;
                        int[] move = {row, col};
                        pieceMoves = rook.getPossibleMoves(board, true);
                        if (pieceMoves.isInsideQueue(move)){
                            rook.move(board, row, col);
                            times++;
                        }
                        if (moves.charAt(5) == '+' || moves.charAt(5) == '#'){
                            moves = moves.substring(6);
                        }
                        else {
                            moves = moves.substring(5);
                        }
                    }
                    else {
                        col = moves.charAt(2) - 97;
                        row = moves.charAt(3) - 49;
                        int[] move = {row, col};
                        pieceMoves = rook.getPossibleMoves(board, true);
                        if (pieceMoves.isInsideQueue(move)){
                            rook.move(board, row, col);
                            times++;
                        }
                        if (moves.charAt(4) == '+' || moves.charAt(4) == '#'){
                            moves = moves.substring(5);
                        }
                        else {
                            moves = moves.substring(4);
                        }
                    }
                }
                else {
                        Rook rook = (Rook) board.findPiece('q', nextMoveColor, -1, -1);
                    col = moves.charAt(1) - 97;
                    row = moves.charAt(2) - 49;
                    int[] move = {row, col};
                    pieceMoves = rook.getPossibleMoves(board, true);
                    if (pieceMoves.isInsideQueue(move)) {
                        rook.move(board, row, col);
                        times++;
                    }
                    if (moves.charAt(4) == '+' || moves.charAt(4) == '#') {
                        moves = moves.substring(5);
                    } else {
                        moves = moves.substring(4);
                    }
                }
            }
            else if (moves.charAt(0) == 'K' || moves.charAt(0) == 'O') {
                King king = (King) board.findPiece('K', nextMoveColor, -1, -1);
                if (moves.charAt(0) == 'K') {
                    if (moves.charAt(1) == 'x') {
                        col = (int) moves.charAt(2) - 97;
                        row = (int) moves.charAt(3) - 49;
                        int[] move = {row, col};
                        pieceMoves = king.getPossibleMoves(board, true);
                        if (pieceMoves.isInsideQueue(move)) {
                            king.move(board, row, col);
                            times++;
                        }
                    }
                    else {
                        col = moves.charAt(2) - 97;
                        row = moves.charAt(3) - 49;
                        int[] move = {row, col};
                        pieceMoves = king.getPossibleMoves(board, true);
                        if (pieceMoves.isInsideQueue(move)) {
                            king.move(board, row, col);
                            times++;
                        }
                    }
                }
                else {
                    if (moves.length() > 4){
                        if (moves.charAt(2) == 'O' && moves.charAt(4) == 'O'){
                            col = king.col - 2;
                            row = king.row;
                            int[] move = {row, col};
                            pieceMoves = king.getPossibleMoves(board, true);
                            if (pieceMoves.isInsideQueue(move)) {
                                king.move(board, row, col);
                                times++;
                            }
                        }
                        else {
                            col = king.col + 2;
                            row = king.row;
                            int[] move = {row, col};
                            pieceMoves = king.getPossibleMoves(board, true);
                            if (pieceMoves.isInsideQueue(move)) {
                                king.move(board, row, col);
                                times++;
                            }
                        }
                    }
                    else {
                        col = king.col + 2;
                        row = king.row;
                        int[] move = {row, col};
                        pieceMoves = king.getPossibleMoves(board, true);
                        if (pieceMoves.isInsideQueue(move)) {
                            king.move(board, row, col);
                            times++;
                        }
                    }
                }
                if (moves.charAt(4) == '+' || moves.charAt(4) == '#') {
                    moves = moves.substring(5);
                } else {
                    moves = moves.substring(4);
                }
            }
            else {
                if (moves.charAt(1) == 'x'){
                    Pawn pawn = (Pawn) board.findPiece('p', nextMoveColor, -1, -1);
                    col = (int) moves.charAt(2) - 97;
                    row = (int) moves.charAt(3) - 49;
                    int[] move = {row, col};
                    pieceMoves = pawn.getPossibleMoves(board, true);
                    Board newBoard = board.clone();
                    while (!pieceMoves.isInsideQueue(move)){
                        newBoard = newBoard.clone();
                        newBoard.getBoard()[pawn.row][pawn.col] = null;
                        pawn = (Pawn) newBoard.findPiece('p', nextMoveColor, -1, -1);
                        pieceMoves = pawn.getPossibleMoves(board, true);
                    }
                    pawn.move(board, row, col);
                    times++;
                    if (moves.charAt(4) == '+' || moves.charAt(4) == '#'){
                        moves = moves.substring(5);
                    }
                    else if (moves.length() > 9){
                        if (moves.charAt(7) == 'p'){
                            if (moves.charAt(9) == '#' || moves.charAt(9) == '+'){
                                moves = moves.substring(10);
                            }
                            else {
                                moves = moves.substring(9);
                            }
                        }
                    }
                    else {
                        moves = moves.substring(4);
                    }
                }
            }
        }
    }
}
