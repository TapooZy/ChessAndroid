package com.example.chess;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.GridLayout;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.TextView;
import android.widget.Toast;
public class Chess extends AppCompatActivity {
    TextView info;
    Dialog promotionDialog;
    ImageView queen, knight, rook, bishop;
    GridLayout chessBoard;
    CheckBox checkBox;
    Engine engine = new Engine();
    Board board = engine.getBoard();
    Piece piece;
    int screenWidth, whiteId, greenId, promotionCol, timesRemoved = 0;
    char nextMoveColor;
//    Piece whiteKing = board.findKing('w'), blackKing = board.findKing('b');
    boolean wasClickedOnAPiece, isLoadGame, wasChecked = false;
    String moves;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chess);
        nextMoveColor = 'w';
        ImageView from_green = new ImageView(this);
        ImageView from_white = new ImageView(this);
        from_green.setBackgroundColor(getResources().getColor(R.color.can_move_to_from_green));
        from_white.setBackgroundColor(getResources().getColor(R.color.can_move_to_from_white));
        ColorDrawable whiteColor = (ColorDrawable) from_white.getBackground();
        checkBox = findViewById(R.id.cbWhite);
        whiteId = whiteColor.getColor();
        ColorDrawable greenColor = (ColorDrawable) from_green.getBackground();
        greenId = greenColor.getColor();
        info = findViewById(R.id.tvInfo);
        if (nextMoveColor == 'b') {
            info.setText("Next move: black");
        } else {
            info.setText("Next move: white");
        }
        EngineTree engineTree = new EngineTree(engine);
        makeTree(engineTree, 3, nextMoveColor);
        int[] levels = engineTree.levels();
        chessBoard = findViewById(R.id.chessBoard);
//        moves = "1.e4e52.Nf3Nc63.Bb5Nf64.d3d65.c3g66.Nbd2Bg77.Nf1O-O8.Ba4Nd79.Ne3Nc510.Bc2Ne611.h4Ne712.h5d513.hxg6fxg614.exd5Nxd515.Nxd5Qxd516.Bb3Qc617.Qe2Bd718.Be3Kh819.O-O-ORae820.Qf1a521.d4exd422.Nxd4Bxd423.Rxd4Nxd424.Rxh7+Kxh725.Qh1+Kg726.Bh6+Kf627.Qh4+Ke528.Qxd4+";
        if (moves != null) {
            isLoadGame = true;
        } else {
            isLoadGame = false;
        }
        showBoard(isLoadGame);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chessBoard.removeAllViews();
                showBoard(isLoadGame);
            }
        });
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
                Piece p;
                if (checkBox.isChecked()){
                    p = board.getBoard()[7-i][j];
                }
                else {
                    p = board.getBoard()[i][j];
                }

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

                // Set a click listener for each ImageView
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!isLoadGame) {
                            onSquareClicked(row, col, wasClickedOnAPiece, false);
                        }
//                        else {
//                            moves = loadGame(moves);
//                        }
                    }
                });

                // Add the ImageView to the chessboard GridLayout
                chessBoard.addView(imageView);
            }
        }
    }
    private void onSquareClicked(int row, int col, boolean wasClickedOnAPiece, boolean isRec) {
        int newRow = row;
        Log.d("row and col", row + " " + col);
        if (wasClickedOnAPiece) {
            if (checkBox.isChecked()) {
                newRow = 7 - row;
            }
            View square = chessBoard.getChildAt(row * 8 + col);
            ColorDrawable squareColor = (ColorDrawable) square.getBackground();
            int squareId = squareColor.getColor();
            if (squareId == whiteId || squareId == greenId) {
                if (piece.letter == 'p') {
                    int[] cords = {newRow, col};
                    int pieceRow = piece.row;
                    ((Pawn) piece).pawnMove(board, newRow, col, engine.getEnPassantLocation());
                    if (newRow == 0 && piece.color == 'b') {
                        blackPawnPromotion(col);
                    }
                    else if (newRow == 7 && piece.color == 'w'){
                        whitePawnPromotion(col);
                    }
                    int dist = pieceRow - newRow;
                    dist *= dist;
                    if (dist == 4) {
                        engine.setEnPassantLocation(cords);
                    } else {
                        engine.setEnPassantLocation(null);
                    }
                } else {
                    piece.move(board, newRow, col);
                    engine.setEnPassantLocation(null);
                }
//                if (nextMoveColor == 'w') {
//                    whiteKing = board.findKing('w');
//                } else {
//                    blackKing = board.findKing('b');
//                }
                this.wasClickedOnAPiece = false;
                setNextMoveColor();
                if (nextMoveColor == 'b') {
                    info.setText("Next move: black");
                } else {
                    info.setText("Next move: white");
                }
                chessBoard.removeAllViews();
                showBoard(false);
                Queue<Location> allMoves = board.getEndMoves(nextMoveColor);
                if (allMoves.getSize() == 0 && board.isInCheck(nextMoveColor)) {
                    if (nextMoveColor == 'b') {
                        info.setText("Checkmate, white won");
                    } else {
                        info.setText("Checkmate, black won");
                    }
                    board.startGame();
                    chessBoard.removeAllViews();
                    showBoard(false);
                    nextMoveColor = 'w';
                } else if (allMoves.getSize() == 0 && !board.isInCheck(nextMoveColor)) {
                    info.setText("Stalemate, no one won");
                    board.startGame();
                    chessBoard.removeAllViews();
                    showBoard(false);
                    nextMoveColor = 'w';
                }
                Log.d("evaluate", evaluate(board) + "");
                return;
            }
            piece = board.getBoard()[newRow][col];
            if (piece != null) {
                if (piece.color == nextMoveColor) {
                    onSquareClicked(row, col, false, true);
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
            Queue<Location> moves;
            if (!isRec) {
                if (checkBox.isChecked()) {
                    piece = board.getBoard()[7 - row][col];
                } else {
                    piece = board.getBoard()[row][col];
                }
            }
            if (piece == null) {
                Toast.makeText(this, "This square is empty", Toast.LENGTH_SHORT).show();
                chessBoard.removeAllViews();
                showBoard(false);
                return;
            }
            if (piece.color != nextMoveColor) {
                Toast.makeText(this, "Wrong piece color, pick again", Toast.LENGTH_SHORT).show();
                return;
            }
            this.wasClickedOnAPiece = true;
            if (piece instanceof Pawn) {
                moves = (((Pawn) piece).getPawnPossibleMoves(board, engine.getEnPassantLocation(), true));
            } else {
                moves = piece.getPossibleMoves(board, true);
            }
            chessBoard.removeAllViews();
            showBoard(false);
            int size = moves.getSize();
            Node<Location> individual_move;
            if (size == 0) {
                Toast.makeText(this, "this piece has no moves", Toast.LENGTH_SHORT).show();
            }
            for (int i = 0; i < size; i++) {
                individual_move = moves.remove();
                View square_to_color;
                if (checkBox.isChecked()){
                    square_to_color = chessBoard.getChildAt((7-individual_move.getTo().row) * 8 + individual_move.getTo().col);
                }
                else {
                    square_to_color = chessBoard.getChildAt(individual_move.getTo().row * 8 + individual_move.getTo().col);
                }
                if ((individual_move.getTo().row + individual_move.getTo().col) % 2 == 0) {
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

    public void blackPawnPromotion(int col) {
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
                board.getBoard()[0][promotionCol] = new Queen('b', 0, promotionCol);
                chessBoard.removeAllViews();
                showBoard(false);
                promotionDialog.cancel();
            }
        });

        knight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                board.getBoard()[0][promotionCol] = new Knight('b', 0, promotionCol);
                chessBoard.removeAllViews();
                showBoard(false);
                promotionDialog.cancel();
            }
        });

        rook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                board.getBoard()[0][promotionCol] = new Rook('b', 0, promotionCol);
                board.getBoard()[0][promotionCol].didMove = true;
                chessBoard.removeAllViews();
                showBoard(false);
                promotionDialog.cancel();
            }
        });

        bishop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                board.getBoard()[0][promotionCol] = new Bishop('b', 0, promotionCol);
                chessBoard.removeAllViews();
                showBoard(false);
                promotionDialog.cancel();
            }
        });
        d.show();
    }

    public void whitePawnPromotion(int col) {
        Dialog d = new Dialog(this);
        d.setContentView(R.layout.custom_dialog_white);
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
                board.getBoard()[7][promotionCol] = new Queen('w', 7, promotionCol);
                chessBoard.removeAllViews();
                showBoard(false);
                promotionDialog.cancel();
            }
        });

        knight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                board.getBoard()[7][promotionCol] = new Knight('w', 7, promotionCol);
                chessBoard.removeAllViews();
                showBoard(false);
                promotionDialog.cancel();
            }
        });

        rook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                board.getBoard()[7][promotionCol] = new Rook('w', 7, promotionCol);
                board.getBoard()[7][promotionCol].didMove = true;
                chessBoard.removeAllViews();
                showBoard(false);
                promotionDialog.cancel();
            }
        });

        bishop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                board.getBoard()[7][promotionCol] = new Bishop('w', 7, promotionCol);
                chessBoard.removeAllViews();
                showBoard(false);
                promotionDialog.cancel();
            }
        });
        d.show();
    }


    public void makeTree(EngineTree root, int depth, char color){
        if (depth == 0){
            return;
        }
        Piece piece;
        char nextColor;
        if (color == 'w'){
            nextColor = 'b';
        }
        else {
            nextColor = 'w';
        }
        Node<Location> move;
        Queue<Location> allMoves = root.engine.getBoard().getColorMoves(color, false);
        int allMovesSize = allMoves.getSize();
        EngineTree[] leaves = new EngineTree[allMovesSize];
        for (int i = 0; i < allMovesSize; i++) {
            Engine newEngine = root.engine.clone();
            move = allMoves.remove();
            piece = newEngine.getBoard().getBoard()[move.getFrom().row][move.getFrom().col];
            if (piece.letter == 'p'){
                ((Pawn) piece).pawnMove(newEngine.getBoard(), move.getTo().row, move.getTo().col, newEngine.getEnPassantLocation());
            }
            else {
                piece.move(newEngine.getBoard(), move.getTo().row, move.getTo().col);
            }
            EngineTree leaf_i = new EngineTree(newEngine);
            leaves[i] = leaf_i;
            makeTree(leaf_i, depth - 1, nextColor);
        }
        root.setLeaves(leaves);
    }

    public int evaluate(Board board){
        int whiteSum = 0, blackSum = 0;
        Piece piece;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                piece = board.getBoard()[i][j];
                if (piece != null){
                    if (piece.color == 'w'){
                        whiteSum += piece.value;
                    }
                    else {
                        blackSum += piece.value;
                    }
                }
            }
        }
        return whiteSum - blackSum;
    }
/*
    @SuppressLint("SetTextI18n")
    public String loadGame(String moves) {
        int row, col;
        Queue<Location> pieceMoves;
        if (moves.length() != 0) {
            if (nextMoveColor == 'w') {
                timesRemoved++;
                moves = moves.substring(String.valueOf(timesRemoved).length() + 1);
                Log.d("moves after  cut", moves);
            }
            if (moves.charAt(0) == 'B') {
                if (moves.charAt(1) == 'x') {
                    Bishop bishop = (Bishop) board.findPiece('b', nextMoveColor, -1, -1);
                    col = (int) moves.charAt(2) - 97;
                    row = (int) moves.charAt(3) - 49;
                    Location move = new Location(row, col);
                    pieceMoves = bishop.getPossibleMoves(board, true);
                    Board newBoard = board.clone();
                    while (!pieceMoves.isInsideQueue(move)) {
                        newBoard.getBoard()[bishop.row][bishop.col] = null;
                        bishop = (Bishop) newBoard.findPiece('b', nextMoveColor, -1, -1);
                        pieceMoves = bishop.getPossibleMoves(board, true);
                    }
                    bishop.move(board, row, col);
                    chessBoard.removeAllViews();
                    showBoard(isLoadGame);
                    setNextMoveColor();
                    if (nextMoveColor == 'b') {
                        info.setText("Next move: black");
                    } else {
                        info.setText("Next move: white");
                    }
                    if (moves.charAt(4) == '+' || moves.charAt(4) == '#') {
                        moves = moves.substring(5);
                    } else {
                        moves = moves.substring(4);
                    }
                } else if ((int) moves.charAt(1) > 48 && (int) moves.charAt(1) < 57) {
                    Bishop bishop = (Bishop) board.findPiece('b', nextMoveColor, (int) moves.charAt(1) - 49, -1);
                    if (moves.charAt(2) == 'x') {
                        col = moves.charAt(3) - 97;
                        row = moves.charAt(4) - 49;
                        Integer[] move = {row, col};
                        pieceMoves = bishop.getPossibleMoves(board, true);
                        if (pieceMoves.isInsideQueue(move)) {
                            bishop.move(board, row, col);
                            chessBoard.removeAllViews();
                            showBoard(isLoadGame);
                            setNextMoveColor();
                            if (nextMoveColor == 'b') {
                                info.setText("Next move: black");
                            } else {
                                info.setText("Next move: white");
                            }
                        }
                        if (moves.charAt(5) == '+' || moves.charAt(5) == '#') {
                            moves = moves.substring(6);
                        } else {
                            moves = moves.substring(5);
                        }
                    } else {
                        col = moves.charAt(2) - 97;
                        row = moves.charAt(3) - 49;
                        Integer[] move = {row, col};
                        pieceMoves = bishop.getPossibleMoves(board, true);
                        if (pieceMoves.isInsideQueue(move)) {
                            bishop.move(board, row, col);
                            chessBoard.removeAllViews();
                            showBoard(isLoadGame);
                            setNextMoveColor();
                            if (nextMoveColor == 'b') {
                                info.setText("Next move: black");
                            } else {
                                info.setText("Next move: white");
                            }
                        }
                        if (moves.charAt(4) == '+' || moves.charAt(4) == '#') {
                            moves = moves.substring(5);
                        } else {
                            moves = moves.substring(4);
                        }
                    }
                } else if ((int) moves.charAt(1) > 96 && (int) moves.charAt(1) < 105 && (int) moves.charAt(2) > 96 && (int) moves.charAt(2) < 105) {
                    Bishop bishop = (Bishop) board.findPiece('b', nextMoveColor, -1, (int) moves.charAt(1) - 97);
                    if (moves.charAt(2) == 'x') {
                        col = moves.charAt(3) - 97;
                        row = moves.charAt(4) - 49;
                        Integer[] move = {row, col};
                        pieceMoves = bishop.getPossibleMoves(board, true);
                        if (pieceMoves.isInsideQueue(move)) {
                            bishop.move(board, row, col);
                            chessBoard.removeAllViews();
                            showBoard(isLoadGame);
                            setNextMoveColor();
                            if (nextMoveColor == 'b') {
                                info.setText("Next move: black");
                            } else {
                                info.setText("Next move: white");
                            }
                        }
                        if (moves.charAt(5) == '+' || moves.charAt(5) == '#') {
                            moves = moves.substring(6);
                        } else {
                            moves = moves.substring(5);
                        }
                    } else {
                        col = moves.charAt(2) - 97;
                        row = moves.charAt(3) - 49;
                        Location move = new Location(row, col);
                        pieceMoves = bishop.getPossibleMoves(board, true);
                        if (pieceMoves.isInsideQueue(move)) {
                            bishop.move(board, row, col);
                            chessBoard.removeAllViews();
                            showBoard(isLoadGame);
                            setNextMoveColor();
                            if (nextMoveColor == 'b') {
                                info.setText("Next move: black");
                            } else {
                                info.setText("Next move: white");
                            }
                        }
                        if (moves.charAt(4) == '+' || moves.charAt(4) == '#') {
                            moves = moves.substring(5);
                        } else {
                            moves = moves.substring(4);
                        }
                    }
                } else {
                    Bishop bishop = (Bishop) board.findPiece('b', nextMoveColor, -1, -1);
                    col = moves.charAt(1) - 97;
                    row = moves.charAt(2) - 49;
                    Integer[] move = {row, col};
                    pieceMoves = bishop.getPossibleMoves(board, true);
                    Board newBoard = board.clone();
                    while (!pieceMoves.isInsideQueue(move)) {
                        newBoard.getBoard()[bishop.row][bishop.col] = null;
                        bishop = (Bishop) newBoard.findPiece('b', nextMoveColor, -1, -1);
                        pieceMoves = bishop.getPossibleMoves(board, true);
                    }
                    bishop.move(board, row, col);
                    chessBoard.removeAllViews();
                    showBoard(isLoadGame);
                    setNextMoveColor();
                    if (nextMoveColor == 'b') {
                        info.setText("Next move: black");
                    } else {
                        info.setText("Next move: white");
                    }
                    if (moves.charAt(3) == '+' || moves.charAt(3) == '#') {
                        moves = moves.substring(4);
                    } else {
                        moves = moves.substring(3);
                    }
                }
                Log.d("moves", moves);
                return moves;
            } else if (moves.charAt(0) == 'N') {
                if (moves.charAt(1) == 'x') {
                    Knight knight = (Knight) board.findPiece('k', nextMoveColor, -1, -1);
                    col = (int) moves.charAt(2) - 97;
                    row = (int) moves.charAt(3) - 49;
                    Integer[] move = {row, col};
                    pieceMoves = knight.getPossibleMoves(board, true);
                    Board newBoard = board.clone();
                    while (!pieceMoves.isInsideQueue(move)) {
                        newBoard.getBoard()[knight.row][knight.col] = null;
                        knight = (Knight) newBoard.findPiece('k', nextMoveColor, -1, -1);
                        pieceMoves = knight.getPossibleMoves(board, true);
                    }
                    knight.move(board, row, col);
                    chessBoard.removeAllViews();
                    showBoard(isLoadGame);
                    setNextMoveColor();
                    if (nextMoveColor == 'b') {
                        info.setText("Next move: black");
                    } else {
                        info.setText("Next move: white");
                    }
                    if (moves.charAt(4) == '+' || moves.charAt(4) == '#') {
                        moves = moves.substring(5);
                    } else {
                        moves = moves.substring(4);
                    }
                } else if ((int) moves.charAt(1) > 48 && (int) moves.charAt(1) < 57) {
                    Knight knight = (Knight) board.findPiece('k', nextMoveColor, (int) moves.charAt(1) - 49, -1);
                    if (moves.charAt(2) == 'x') {
                        col = moves.charAt(3) - 97;
                        row = moves.charAt(4) - 49;
                        Integer[] move = {row, col};
                        pieceMoves = knight.getPossibleMoves(board, true);
                        if (pieceMoves.isInsideQueue(move)) {
                            knight.move(board, row, col);
                            chessBoard.removeAllViews();
                            showBoard(isLoadGame);
                            setNextMoveColor();
                            if (nextMoveColor == 'b') {
                                info.setText("Next move: black");
                            } else {
                                info.setText("Next move: white");
                            }
                        }
                        if (moves.charAt(5) == '+' || moves.charAt(5) == '#') {
                            moves = moves.substring(6);
                        } else {
                            moves = moves.substring(5);
                        }
                    } else {
                        col = moves.charAt(2) - 97;
                        row = moves.charAt(3) - 49;
                        Integer[] move = {row, col};
                        pieceMoves = knight.getPossibleMoves(board, true);
                        if (pieceMoves.isInsideQueue(move)) {
                            knight.move(board, row, col);
                            chessBoard.removeAllViews();
                            showBoard(isLoadGame);
                            setNextMoveColor();
                            if (nextMoveColor == 'b') {
                                info.setText("Next move: black");
                            } else {
                                info.setText("Next move: white");
                            }
                        }
                        if (moves.charAt(4) == '+' || moves.charAt(4) == '#') {
                            moves = moves.substring(5);
                        } else {
                            moves = moves.substring(4);
                        }
                    }
                } else if ((int) moves.charAt(1) > 96 && (int) moves.charAt(1) < 105 && (int) moves.charAt(2) > 96 && (int) moves.charAt(2) < 105) {
                    Knight knight = (Knight) board.findPiece('k', nextMoveColor, -1, (int) moves.charAt(1) - 97);
                    if (moves.charAt(2) == 'x') {
                        col = moves.charAt(3) - 97;
                        row = moves.charAt(4) - 49;
                        Integer[] move = {row, col};
                        pieceMoves = knight.getPossibleMoves(board, true);
                        if (pieceMoves.isInsideQueue(move)) {
                            knight.move(board, row, col);
                            chessBoard.removeAllViews();
                            showBoard(isLoadGame);
                            setNextMoveColor();
                            if (nextMoveColor == 'b') {
                                info.setText("Next move: black");
                            } else {
                                info.setText("Next move: white");
                            }
                        }
                        if (moves.charAt(5) == '+' || moves.charAt(5) == '#') {
                            moves = moves.substring(6);
                        } else {
                            moves = moves.substring(5);
                        }
                    } else {
                        col = moves.charAt(2) - 97;
                        row = moves.charAt(3) - 49;
                        Integer[] move = {row, col};
                        pieceMoves = knight.getPossibleMoves(board, true);
                        if (pieceMoves.isInsideQueue(move)) {
                            knight.move(board, row, col);
                            chessBoard.removeAllViews();
                            showBoard(isLoadGame);
                            setNextMoveColor();
                            if (nextMoveColor == 'b') {
                                info.setText("Next move: black");
                            } else {
                                info.setText("Next move: white");
                            }
                        }
                        if (moves.charAt(4) == '+' || moves.charAt(4) == '#') {
                            moves = moves.substring(5);
                        } else {
                            moves = moves.substring(4);
                        }
                    }
                } else {
                    Knight knight = (Knight) board.findPiece('k', nextMoveColor, -1, -1);
                    col = moves.charAt(1) - 97;
                    row = moves.charAt(2) - 49;
                    Integer[] move = {row, col};
                    pieceMoves = knight.getPossibleMoves(board, true);
                    Board newBoard = board.clone();
                    while (!pieceMoves.isInsideQueue(move)) {
                        newBoard.getBoard()[knight.row][knight.col] = null;
                        knight = (Knight) newBoard.findPiece('k', nextMoveColor, -1, -1);
                        pieceMoves = knight.getPossibleMoves(board, true);
                    }
                    knight.move(board, row, col);
                    chessBoard.removeAllViews();
                    showBoard(isLoadGame);
                    setNextMoveColor();
                    if (nextMoveColor == 'b') {
                        info.setText("Next move: black");
                    } else {
                        info.setText("Next move: white");
                    }
                    if (moves.charAt(3) == '+' || moves.charAt(3) == '#') {
                        moves = moves.substring(4);
                    } else {
                        moves = moves.substring(3);
                    }
                    Log.d("moves", moves);
                    return moves;
                }
            }
            else if (moves.charAt(0) == 'Q') {
            if (moves.charAt(1) == 'x') {
                Queen queen = (Queen) board.findPiece('q', nextMoveColor, -1, -1);
                col = (int) moves.charAt(2) - 97;
                row = (int) moves.charAt(3) - 49;
                Integer[] move = {row, col};
                pieceMoves = queen.getPossibleMoves(board, true);
                Board newBoard = board.clone();
                while (!pieceMoves.isInsideQueue(move)) {
                    newBoard.getBoard()[queen.row][queen.col] = null;
                    queen = (Queen) newBoard.findPiece('q', nextMoveColor, -1, -1);
                    pieceMoves = queen.getPossibleMoves(board, true);
                }
                queen.move(board, row, col);
                chessBoard.removeAllViews();
                showBoard(isLoadGame);
                setNextMoveColor();
                if (nextMoveColor == 'b') {
                    info.setText("Next move: black");
                } else {
                    info.setText("Next move: white");
                }
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
                    Integer[] move = {row, col};
                    pieceMoves = queen.getPossibleMoves(board, true);
                    if (pieceMoves.isInsideQueue(move)) {
                        queen.move(board, row, col);
                        chessBoard.removeAllViews();
                        showBoard(isLoadGame);
                        setNextMoveColor();
                        if (nextMoveColor == 'b') {
                            info.setText("Next move: black");
                        } else {
                            info.setText("Next move: white");
                        }
                    }
                    if (moves.charAt(5) == '+' || moves.charAt(5) == '#') {
                        moves = moves.substring(6);
                    } else {
                        moves = moves.substring(5);
                    }
                } else {
                    col = moves.charAt(2) - 97;
                    row = moves.charAt(3) - 49;
                    Integer[] move = {row, col};
                    pieceMoves = queen.getPossibleMoves(board, true);
                    if (pieceMoves.isInsideQueue(move)) {
                        queen.move(board, row, col);
                        chessBoard.removeAllViews();
                        showBoard(isLoadGame);
                        setNextMoveColor();
                        if (nextMoveColor == 'b') {
                            info.setText("Next move: black");
                        } else {
                            info.setText("Next move: white");
                        }
                    }
                    if (moves.charAt(4) == '+' || moves.charAt(4) == '#') {
                        moves = moves.substring(5);
                    } else {
                        moves = moves.substring(4);
                    }
                }
            } else if ((int) moves.charAt(1) > 96 && (int) moves.charAt(1) < 105 && (int) moves.charAt(2) > 96 && (int) moves.charAt(2) < 105) {
                Queen queen = (Queen) board.findPiece('q', nextMoveColor, -1, (int) moves.charAt(1) - 97);
                if (moves.charAt(2) == 'x') {
                    col = moves.charAt(3) - 97;
                    row = moves.charAt(4) - 49;
                    Integer[] move = {row, col};
                    pieceMoves = queen.getPossibleMoves(board, true);
                    if (pieceMoves.isInsideQueue(move)) {
                        queen.move(board, row, col);
                        chessBoard.removeAllViews();
                        showBoard(isLoadGame);
                        setNextMoveColor();
                        if (nextMoveColor == 'b') {
                            info.setText("Next move: black");
                        } else {
                            info.setText("Next move: white");
                        }
                    }
                    if (moves.charAt(5) == '+' || moves.charAt(5) == '#') {
                        moves = moves.substring(6);
                    } else {
                        moves = moves.substring(5);
                    }
                } else {
                    col = moves.charAt(2) - 97;
                    row = moves.charAt(3) - 49;
                    Integer[] move = {row, col};
                    pieceMoves = queen.getPossibleMoves(board, true);
                    if (pieceMoves.isInsideQueue(move)) {
                        queen.move(board, row, col);
                        chessBoard.removeAllViews();
                        showBoard(isLoadGame);
                        setNextMoveColor();
                        if (nextMoveColor == 'b') {
                            info.setText("Next move: black");
                        } else {
                            info.setText("Next move: white");
                        }
                    }
                    if (moves.charAt(4) == '+' || moves.charAt(4) == '#') {
                        moves = moves.substring(5);
                    } else {
                        moves = moves.substring(4);
                    }
                }
            }
            else {
                Queen queen = (Queen) board.findPiece('q', nextMoveColor, -1, -1);
                col = moves.charAt(1) - 97;
                row = moves.charAt(2) - 49;
                Integer[] move = {row, col};
                pieceMoves = queen.getPossibleMoves(board, true);
                if (pieceMoves.isInsideQueue(move)) {
                    queen.move(board, row, col);
                    chessBoard.removeAllViews();
                    showBoard(isLoadGame);
                    setNextMoveColor();
                    if (nextMoveColor == 'b') {
                        info.setText("Next move: black");
                    } else {
                        info.setText("Next move: white");
                    }
                }
                if (moves.charAt(3) == '+' || moves.charAt(3) == '#') {
                    moves = moves.substring(4);
                } else {
                    moves = moves.substring(3);
                }
            }
            Log.d("moves", moves);
            return moves;
            }
                else if (moves.charAt(0) == 'R') {
                if (moves.charAt(1) == 'x') {
                    Rook rook = (Rook) board.findPiece('r', nextMoveColor, -1, -1);
                    col = (int) moves.charAt(2) - 97;
                    row = (int) moves.charAt(3) - 49;
                    Integer[] move = {row, col};
                    pieceMoves = rook.getPossibleMoves(board, true);
                    Board newBoard = board.clone();
                    while (!pieceMoves.isInsideQueue(move)) {
                        newBoard.getBoard()[rook.row][rook.col] = null;
                        rook = (Rook) newBoard.findPiece('r', nextMoveColor, -1, -1);
                        pieceMoves = rook.getPossibleMoves(board, true);
                    }
                    rook.move(board, row, col);
                    chessBoard.removeAllViews();
                    showBoard(isLoadGame);
                    setNextMoveColor();
                    if (nextMoveColor == 'b') {
                        info.setText("Next move: black");
                    } else {
                        info.setText("Next move: white");
                    }
                    if (moves.charAt(4) == '+' || moves.charAt(4) == '#') {
                        moves = moves.substring(5);
                    } else {
                        moves = moves.substring(4);
                    }
                } else if ((int) moves.charAt(1) > 48 && (int) moves.charAt(1) < 57) {
                    Rook rook = (Rook) board.findPiece('r', nextMoveColor, (int) moves.charAt(1) - 49, -1);
                    if (moves.charAt(2) == 'x') {
                        col = moves.charAt(3) - 97;
                        row = moves.charAt(4) - 49;
                        Integer[] move = {row, col};
                        pieceMoves = rook.getPossibleMoves(board, true);
                        if (pieceMoves.isInsideQueue(move)) {
                            rook.move(board, row, col);
                            chessBoard.removeAllViews();
                            showBoard(isLoadGame);
                            setNextMoveColor();
                            if (nextMoveColor == 'b') {
                                info.setText("Next move: black");
                            } else {
                                info.setText("Next move: white");
                            }
                        }
                        if (moves.charAt(5) == '+' || moves.charAt(5) == '#') {
                            moves = moves.substring(6);
                        } else {
                            moves = moves.substring(5);
                        }
                    } else {
                        col = moves.charAt(2) - 97;
                        row = moves.charAt(3) - 49;
                        Integer[] move = {row, col};
                        pieceMoves = rook.getPossibleMoves(board, true);
                        if (pieceMoves.isInsideQueue(move)) {
                            rook.move(board, row, col);
                            chessBoard.removeAllViews();
                            showBoard(isLoadGame);
                            setNextMoveColor();
                            if (nextMoveColor == 'b') {
                                info.setText("Next move: black");
                            } else {
                                info.setText("Next move: white");
                            }
                        }
                        if (moves.charAt(4) == '+' || moves.charAt(4) == '#') {
                            moves = moves.substring(5);
                        } else {
                            moves = moves.substring(4);
                        }
                    }
                } else if ((int) moves.charAt(1) > 96 && (int) moves.charAt(1) < 105 && (int) moves.charAt(2) > 96 && (int) moves.charAt(2) < 105) {
                    Rook rook = (Rook) board.findPiece('r', nextMoveColor, -1, (int) moves.charAt(1) - 97);
                    if (moves.charAt(2) == 'x') {
                        col = moves.charAt(3) - 97;
                        row = moves.charAt(4) - 49;
                        Integer[] move = {row, col};
                        pieceMoves = rook.getPossibleMoves(board, true);
                        if (pieceMoves.isInsideQueue(move)) {
                            rook.move(board, row, col);
                            chessBoard.removeAllViews();
                            showBoard(isLoadGame);
                            setNextMoveColor();
                            if (nextMoveColor == 'b') {
                                info.setText("Next move: black");
                            } else {
                                info.setText("Next move: white");
                            }
                        }
                        if (moves.charAt(5) == '+' || moves.charAt(5) == '#') {
                            moves = moves.substring(6);
                        } else {
                            moves = moves.substring(5);
                        }
                    } else {
                        col = moves.charAt(2) - 97;
                        row = moves.charAt(3) - 49;
                        Integer[] move = {row, col};
                        pieceMoves = rook.getPossibleMoves(board, true);
                        if (pieceMoves.isInsideQueue(move)) {
                            rook.move(board, row, col);
                            chessBoard.removeAllViews();
                            showBoard(isLoadGame);
                            setNextMoveColor();
                            if (nextMoveColor == 'b') {
                                info.setText("Next move: black");
                            } else {
                                info.setText("Next move: white");
                            }
                        }
                        if (moves.charAt(4) == '+' || moves.charAt(4) == '#') {
                            moves = moves.substring(5);
                        } else {
                            moves = moves.substring(4);
                        }
                    }
                } else {
                    Rook rook = (Rook) board.findPiece('r', nextMoveColor, -1, -1);
                    col = moves.charAt(1) - 97;
                    row = moves.charAt(2) - 49;
                    Integer[] move = {row, col};
                    pieceMoves = rook.getPossibleMoves(board, true);
                    Board newBoard = board.clone();
                    while (!pieceMoves.isInsideQueue(move)) {
                        newBoard.getBoard()[rook.row][rook.col] = null;
                        rook = (Rook) newBoard.findPiece('r', nextMoveColor, -1, -1);
                        pieceMoves = rook.getPossibleMoves(board, true);
                    }
                    rook.move(board, row, col);
                    chessBoard.removeAllViews();
                    showBoard(isLoadGame);
                    setNextMoveColor();
                    if (nextMoveColor == 'b') {
                        info.setText("Next move: black");
                    } else {
                        info.setText("Next move: white");
                    }
                    if (moves.charAt(3) == '+' || moves.charAt(3) == '#') {
                        moves = moves.substring(4);
                    } else {
                        moves = moves.substring(3);
                    }
                }
                Log.d("moves", moves);
                return moves;
            } else if (moves.charAt(0) == 'K' || moves.charAt(0) == 'O') {
                King king = (King) board.findPiece('K', nextMoveColor, -1, -1);
                if (moves.charAt(0) == 'K') {
                    if (moves.charAt(1) == 'x') {
                        col = (int) moves.charAt(2) - 97;
                        row = (int) moves.charAt(3) - 49;
                        Integer[] move = {row, col};
                        pieceMoves = king.getPossibleMoves(board, true);
                        if (pieceMoves.isInsideQueue(move)) {
                            king.move(board, row, col);
                            chessBoard.removeAllViews();
                            showBoard(isLoadGame);
                            setNextMoveColor();
                            if (nextMoveColor == 'b') {
                                info.setText("Next move: black");
                            } else {
                                info.setText("Next move: white");
                            }
                        }
                        moves = moves.substring(4);
                    } else {
                        col = moves.charAt(1) - 97;
                        row = moves.charAt(2) - 49;
                        Integer[] move = {row, col};
                        pieceMoves = king.getPossibleMoves(board, true);
                        if (pieceMoves.isInsideQueue(move)) {
                            king.move(board, row, col);
                            chessBoard.removeAllViews();
                            showBoard(isLoadGame);
                            setNextMoveColor();
                            if (nextMoveColor == 'b') {
                                info.setText("Next move: black");
                            } else {
                                info.setText("Next move: white");
                            }
                        }
                        moves = moves.substring(3);
                    }
                } else {
                    if (moves.length() > 4) {
                        if (moves.charAt(2) == 'O' && moves.charAt(4) == 'O') {
                            col = king.col - 2;
                            row = king.row;
                            Integer[] move = {row, col};
                            pieceMoves = king.getPossibleMoves(board, true);
                            if (pieceMoves.isInsideQueue(move)) {
                                king.move(board, row, col);
                                chessBoard.removeAllViews();
                                showBoard(isLoadGame);
                                setNextMoveColor();
                                if (nextMoveColor == 'b') {
                                    info.setText("Next move: black");
                                } else {
                                    info.setText("Next move: white");
                                }
                                moves = moves.substring(5);
                            }
                        } else {
                            col = king.col + 2;
                            row = king.row;
                            Integer[] move = {row, col};
                            pieceMoves = king.getPossibleMoves(board, true);
                            if (pieceMoves.isInsideQueue(move)) {
                                king.move(board, row, col);
                                chessBoard.removeAllViews();
                                showBoard(isLoadGame);
                                setNextMoveColor();
                                if (nextMoveColor == 'b') {
                                    info.setText("Next move: black");
                                } else {
                                    info.setText("Next move: white");
                                }
                            }
                            moves = moves.substring(3);
                        }
                    } else {
                        col = king.col + 2;
                        row = king.row;
                        Integer[] move = {row, col};
                        pieceMoves = king.getPossibleMoves(board, true);
                        if (pieceMoves.isInsideQueue(move)) {
                            king.move(board, row, col);
                            chessBoard.removeAllViews();
                            showBoard(isLoadGame);
                            setNextMoveColor();
                            if (nextMoveColor == 'b') {
                                info.setText("Next move: black");
                            } else {
                                info.setText("Next move: white");
                            }
                            moves = moves.substring(3);
                        }
                    }
                }
                Log.d("moves", moves);
                return moves;
            } else {
                Pawn pawn = (Pawn) board.findPiece('p', nextMoveColor, -1, -1);
                if (moves.charAt(1) == 'x') {
                    col = (int) moves.charAt(2) - 97;
                    row = (int) moves.charAt(3) - 49;
                    Integer[] move = {row, col};
                    pieceMoves = pawn.getPawnPossibleMoves(board, engine.getEnPassantLocation(), true);
                    Board newBoard = board.clone();
                    while (!pieceMoves.isInsideQueue(move)) {
                        newBoard.getBoard()[pawn.row][pawn.col] = null;
                        pawn = (Pawn) newBoard.findPiece('p', nextMoveColor, -1, -1);
                        pieceMoves = pawn.getPawnPossibleMoves(board, engine.getEnPassantLocation(), true);
                    }
                    pawn.pawnMove(board, row, col, engine.getEnPassantLocation());
                    chessBoard.removeAllViews();
                    showBoard(isLoadGame);
                    setNextMoveColor();
                    if (nextMoveColor == 'b') {
                        info.setText("Next move: black");
                    } else {
                        info.setText("Next move: white");
                    }
                    if (moves.charAt(4) == '+' || moves.charAt(4) == '#') {
                        moves = moves.substring(5);
                    } else if (moves.length() > 9) {
                        if (moves.charAt(7) == 'p') {
                            if (moves.charAt(9) == '#' || moves.charAt(9) == '+') {
                                moves = moves.substring(10);
                            } else {
                                moves = moves.substring(9);
                            }
                        } else {
                            moves = moves.substring(4);
                        }
                    } else {
                        moves = moves.substring(4);
                    }
                } else {
                    col = (int) moves.charAt(0) - 97;
                    row = (int) moves.charAt(1) - 49;
                    Integer[] move = {row, col};
                    pieceMoves = pawn.getPawnPossibleMoves(board, engine.getEnPassantLocation(), true);
                    Board newBoard = board.clone();
                    while (!pieceMoves.isInsideQueue(move)) {
                        newBoard.getBoard()[pawn.row][pawn.col] = null;
                        pawn = (Pawn) newBoard.findPiece('p', nextMoveColor, -1, -1);
                        pieceMoves = pawn.getPawnPossibleMoves(board, engine.getEnPassantLocation(), true);
                    }
                    pawn.pawnMove(board, row, col, engine.getEnPassantLocation());
                    chessBoard.removeAllViews();
                    showBoard(isLoadGame);
                    setNextMoveColor();
                    if (nextMoveColor == 'b') {
                        info.setText("Next move: black");
                    } else {
                        info.setText("Next move: white");
                    }
                    if (moves.charAt(2) == '+' || moves.charAt(2) == '#') {
                        moves = moves.substring(3);
                    } else {
                        moves = moves.substring(2);
                    }
                }
                Log.d("moves", moves);
                return moves;
            }
        }
        return moves;
    }
 */
//    public BoardTree makeTree(BoardTree boardTree, int depth, BoardTree root, char color){
//        Queue<Integer> moves;
//        int[] individualMove;
//        int movesSize, allMovesSize;
//        char nextColor;
//        if (depth > 0){
//            return boardTree;
//        }
//        Board board = root.board;
//        for (int j = 0; j < 8; j++) {
//            for (int k = 0; k < 8; k++) {
//                Piece piece1 = board.getBoard()[j][k];
//                if (piece1 != null){
//                    if (piece1.color == color){
//                        if (piece1.letter == 'p'){
//                            moves = ((Pawn) piece1).getPawnPossibleMoves(root.board, engine.getEnPassantLocation(), true);
//                        }
//                        else {
//                            moves = piece1.getPossibleMoves(root.board, true);
//                        }
//                        movesSize = moves.getSize();
//                        for (int l = 0; l < movesSize; l++) {
//                            Board newBoard = root.board.clone();
//                            individualMove = moves.remove();
//                            piece1.move(newBoard, individualMove[0], individualMove[1]);
//                            BoardTree boardTree1 = new BoardTree(newBoard, new BoardTree[newBoard.getColorMoves(color, false).getSize()]);
//                            if (color == 'w'){
//                                nextColor = 'b';
//                            }
//                            else {
//                                nextColor = 'w';
//                            }
//                            return makeTree(boardTree, depth--, boardTree1, nextColor);
//                        }
//                    }
//                }
//            }
//        }
//        return boardTree;
//    }

}