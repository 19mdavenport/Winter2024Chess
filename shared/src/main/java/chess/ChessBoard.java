package chess;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {

    private final Map<ChessPosition, ChessPiece> currentPieces;

    public ChessBoard() {
        currentPieces = new HashMap<>();
    }

    /**
     * Copy constructor. Constructs a board with pieces the same as provided board
     *
     * @param copy ChessBoard to copy the pieces of
     */
    public ChessBoard(ChessBoard copy) {
        currentPieces = new HashMap<>(copy.currentPieces);
    }


    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        if (piece == null) currentPieces.remove(position);
        else currentPieces.put(position, piece);
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        return currentPieces.get(position);
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        currentPieces.clear();

        currentPieces.put(new ChessPosition(1, 1), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK));
        currentPieces.put(new ChessPosition(1, 2), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT));
        currentPieces.put(new ChessPosition(1, 3), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP));
        currentPieces.put(new ChessPosition(1, 4), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN));
        currentPieces.put(new ChessPosition(1, 5), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING));
        currentPieces.put(new ChessPosition(1, 6), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP));
        currentPieces.put(new ChessPosition(1, 7), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT));
        currentPieces.put(new ChessPosition(1, 8), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK));
        for (int i = 1; i <= 8; i++) {
            currentPieces.put(new ChessPosition(2, i), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN));
        }

        currentPieces.put(new ChessPosition(8, 1), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK));
        currentPieces.put(new ChessPosition(8, 2), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT));
        currentPieces.put(new ChessPosition(8, 3), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP));
        currentPieces.put(new ChessPosition(8, 4), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN));
        currentPieces.put(new ChessPosition(8, 5), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING));
        currentPieces.put(new ChessPosition(8, 6), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP));
        currentPieces.put(new ChessPosition(8, 7), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT));
        currentPieces.put(new ChessPosition(8, 8), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK));
        for (int i = 1; i <= 8; i++) {
            currentPieces.put(new ChessPosition(7, i), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN));
        }
    }

    @Override
    public String toString() { // FEN
        String[][] toStringArr = new String[8][8];
        for (String[] arr : toStringArr) Arrays.fill(arr, "1");

        for (Map.Entry<ChessPosition, ChessPiece> entry : currentPieces.entrySet()) {
            ChessPosition pos = entry.getKey();
            toStringArr[8 - pos.getRow()][pos.getColumn() - 1] = entry.getValue().toString();
        }

        StringBuilder out = new StringBuilder();
        for (String[] arr : toStringArr) {
            for (String s : arr) {
                out.append(s);
            }
            out.append('/');
        }
        out.deleteCharAt(out.length() - 1); // delete trailing slash

        int i = 0;
        while (i < out.length() - 1) {
            char first = out.charAt(i);
            char second = out.charAt(i + 1);
            if (Character.isDigit(first) && Character.isDigit(second)) {
                int firstInt = Integer.parseInt(String.valueOf(first));
                int secondInt = Integer.parseInt(String.valueOf(second));
                int replace = firstInt + secondInt;
                out.replace(i, i + 2, String.valueOf(replace));
            } else i++;
        }

        return out.toString();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChessBoard board = (ChessBoard) o;

        for(int i = 1; i <= 8; i++) {
            for(int j = 1; j <= 8; j++) {
                ChessPosition pos = new ChessPosition(i, j);
                if(!Objects.equals(getPiece(pos), board.getPiece(pos))) return false;
            }
        }
        return true;
    }


    @Override
    public int hashCode() {
        int code = 0;
        for(int i = 1; i <= 8; i++) {
            for(int j = 1; j <= 8; j++) {
                ChessPosition pos = new ChessPosition(i, j);
                code += getPiece(pos).hashCode();
            }
        }
        return code;
    }
}