/*
 * Chessboard problem for IGN CodeFoo application.
 */


/**
 * A semi-Brute method simulator for a path a Knight can take to cover a n*m
 * chess board.
 * @author Abdi Dahir
 */
public class KnightBoardGame {

    /** Integer height of game board */
    private final int h;
    /** Integer width of game board */
    private final int w;
    /** Integer x coordinate of chess piece */
    private int xpos;
    /** Integer y coordinate of chess piece */
    private int ypos;
    /** Boolean map of game board */
    private boolean[][] board;
    /** Integer map of game boards current move sequence */
    private int[][] sequence;
    /** Integer counter for current move count */
    private int counter;
    /** Boolean flag raised when solution is found */
    public static boolean flag = false;

    /**
     * Init a brand new Knight Board Game with no prior sequence. 
     * @param height, the integer height of the board .
     * @param width, the integer width of the board.
     * @param xposition, the current integer position of the knight.
     * @param yposition, the current integer position of the knight.
     */
    public KnightBoardGame(int height, int width, int xposition, int yposition) {
        this.h = height;
        this.w = width;
        this.xpos = xposition;
        this.ypos = yposition;
        this.board = new boolean[w][h];
        this.sequence = new int[w][h];
        this.counter = 1;
        this.board[xpos][ypos] = true;
        this.sequence[xpos][ypos] = counter;

    }

    /**
     * Init a Knight Board Game starting with a previous board and sequence.
     * @param height, the integer height of the board .
     * @param width, the integer width of the board.
     * @param xposition, the current integer position of the knight.
     * @param yposition, the current integer position of the knight.
     * @param oldboard, boolean map of places reached on game board.
     * @param oldsequence, integer map of the order of places reached.
     * @param oldcounter, integer value of the current move number.
     */
    public KnightBoardGame(int height, int width, int xposition, int yposition,
            boolean[][] oldboard, int[][] oldsequence, int oldcounter) {

        this.h = height;
        this.w = width;
        this.xpos = xposition;
        this.ypos = yposition;
        this.board = oldboard;
        this.sequence = oldsequence;
        this.counter = ++oldcounter;
        this.board[xpos][ypos] = true;
        this.sequence[xpos][ypos] = counter;
    }

    /**
     * Return a copy integer map of the current sequence.
     * @return integer map of the sequence.
     */
    private int[][] getSequence() {
        int[][] copyseq = new int[w][h];
        for (int col = 0; col < w; col++) {
            System.arraycopy(sequence[col], 0, copyseq[col], 0, h);
        }
        return copyseq;
    }

    /**
     * Return a copy boolean map of the current board.
     * @return boolean map of the board.
     */
    private boolean[][] getBoard() {
        boolean[][] copyboard = new boolean[w][h];
        for (int col = 0; col < w; col++) {
            System.arraycopy(board[col], 0, copyboard[col], 0, h);
        }
        return copyboard;
    }

    /**
     * Return a whether the specified move is possible.
     * @param xpos, the current integer x coordinate.
     * @param ypos, the current integer y coordinate.
     * @param x, the integer value of the current xpos intended offset.
     * @param y, the integer value of the current ypos intended offset.
     * @return boolean value representing if move is legal.
     */
    private boolean isLegalMove(int xpos, int ypos, int x, int y) {
        int newx = xpos + x;
        int newy = ypos + y;
        if ((newx > w - 1) || (newy > h - 1)
                || (newy < 0) || (newx < 0)) {
            return false;
        }
        return !(board[newx][newy]);
    }

    /**
     * Return whether all board locations have been reached by knight.
     * @return boolean value representing if game is complete.
     */
    private boolean isGameOver() {
        for (int c = 0; c < w; c++) {
            for (int r = 0; r < h; r++) {
                if (board[c][r] == false) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Return if the presented x,y move is the best move as according to the
     * Warnsdorff's algorithm.
     * @param x, integer representing proposed best x direction movement.
     * @param y, integer representing proposed best y direction movement.
     * @param moves, list of all movements available to this piece.
     * @return boolean representing if the presented move is the best move.
     */
    private boolean isBestMove(int x, int y, ChessMove[] moves) {
        // The Warnsdorff algo saved my program from taking days to finish
        // boards of size greater then 6*6. Without this "isBest" check
        // Program computes ALL paths and must check every combination.
        ChessMove bestmove = new ChessMove(x, y);
        int leastcount = 10;
        int currentcount;
        for (ChessMove move : moves) {
            currentcount = avalibleMoves(move, moves);
            if (currentcount < leastcount) {
                leastcount = currentcount;
                bestmove = move;
            }
        }
        return ((bestmove.getX() == x) && (bestmove.getY() == y));
    }

    /**
     * Return the integer count for how many moves can be made from a specified
     * spot on the grid in respect to the current game board.
     * @param move the intended first Chess Move from the current position.
     * @param moves the list of all ChessMoves for this piece.
     * @return the integer amount of available moves.
     */
    private int avalibleMoves(ChessMove move, ChessMove[] moves) {
        if (!isLegalMove(xpos, ypos, move.getX(), move.getY())) {
            return 10;
        }
        int totalmoves = 0;
        for (ChessMove secondmove : moves) {
            if (isLegalMove(xpos + move.getX(), ypos + move.getY(),
                    secondmove.getX(), secondmove.getY())) {
                totalmoves++;
            }
        }
        return totalmoves;
    }

    /**
     * Inner Class representing a vector move on a chess board.
     */
    private class ChessMove {

        /** integer x component of the chess move vector */
        private final int xmove;
        /** integer y component of the chess move vector */
        private final int ymove;

        /**
         * Init the chess move vector.
         * @param x, integer x component of the chess move vector.
         * @param y, integer y component of the chess move vector.
         */
        private ChessMove(int x, int y) {
            this.xmove = x;
            this.ymove = y;
        }

        /**
         * Return the x component.
         * @return integer value of xmove.
         */
        public int getX() {
            return xmove;
        }

        /**
         * Return the y component.
         * @return integer value of ymove.
         */
        public int getY() {
            return ymove;
        }
    }

    /**
     * Return the array of all ChessMoves for a Knight Piece.
     * @return ChessMove array.
     */
    private ChessMove[] getKnightMoves() {
        ChessMove[] moves = new ChessMove[8];
        moves[0] = new ChessMove(1, 2);
        moves[1] = new ChessMove(1, -2);
        moves[2] = new ChessMove(-1, 2);
        moves[3] = new ChessMove(-1, -2);
        moves[4] = new ChessMove(2, 1);
        moves[5] = new ChessMove(2, -1);
        moves[6] = new ChessMove(-2, 1);
        moves[7] = new ChessMove(-2, -1);
        return moves;
    }

    /**
     * Simulate all possible Knight Moves starting from point (xpos,ypos)
     * Create a new board for the best legal move and print the sequence
     * when all places on board have been reached. 
     */
    public void play() {
        ChessMove[] moves = getKnightMoves();
        for (ChessMove e : moves) {
            if (isLegalMove(xpos, ypos, e.getX(), e.getY())) {
                int newxpos = e.getX() + xpos;
                int newypos = e.getY() + ypos;
                if (isBestMove(e.getX(), e.getY(), moves)) {
                    board[newxpos][newypos] = true;
                    if (isGameOver()) {
                        counter++;
                        sequence[newxpos][newypos] = counter;
                        printMoves();
                        flag = true;
                        return;
                    } else {
                        board[newxpos][newypos] = false;
                        KnightBoardGame game = new KnightBoardGame(h, w, newxpos,
                                newypos, getBoard(), getSequence(), counter);
                        game.play();
                    }
                }
            }
        }
    }

    /**
     * Print out the sequence map as a grid.
     */
    private void printMoves() {
        String seq = "";
        String number = "";
        for (int col = 0; col < w; col++) {
            for (int row = 0; row < h; row++) {
                number = String.valueOf(sequence[col][row]);
                // This conditioning makes the grid appear boxlike for
                // board sizes of 10*9 or less.
                if (number.length() == 1) {
                    number = "0" + number;
                }
                seq = seq + " | " + number;
            }
            seq = seq + " |\n";
        }
        System.out.println(seq);
    }

    /**
     * Parse the command line and run the knight game.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int width = 8;
        int height = 8;
        int xpos = 0;
        int ypos = 0;
        if ((args.length == 1) || (args.length == 3) || (args.length > 4)) {
            usage();
            return;
        } else if (args.length == 2) {
            try {
                width = Integer.valueOf(args[0]);
                height = Integer.valueOf(args[1]);
            } catch (NumberFormatException e) {
                usage();
                return;
            }
        } else if (args.length == 4) {
            try {
                width = Integer.valueOf(args[0]);
                height = Integer.valueOf(args[1]);
                xpos = Integer.valueOf(args[2]);
                ypos = Integer.valueOf(args[3]);
            } catch (NumberFormatException e) {
                usage();
                return;
            }
        }
		if(!(xpos < width) || !(ypos < width)) {
            System.err.println("Bounds Error: x,y coordinates invalid");
            return;
        }
        KnightBoardGame game = new KnightBoardGame(width, height, xpos, ypos);
        game.play();
        if(!flag){
            System.out.println("No min solution found.");
        }
    }

    /**
     * Print Usage message to stderr.
     */
    public static void usage() {
        System.err.println("Usage: KnightBoardGame [width height] [xpos ypos]");
    }
}
