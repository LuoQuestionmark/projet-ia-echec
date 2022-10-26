// This will be the chess board class.

package Board;

import java.util.ArrayList;
import java.util.HashMap;

import mUtil.Coord;

public class Board {
    private HashMap<PieceType, ArrayList<Piece>> availablePieces = new HashMap<>();
    private HashMap<Coord, Piece> board;
    // private HashMap<Coord, Piece>ArrayList<Piece> whitePieces = new ArrayList<>();
    // private ArrayList<Piece> blackPieces = new ArrayList<>();

    public Board() {
        for (PieceType pt: PieceType.values()) {
            availablePieces.put(pt, new ArrayList<Piece>());
        }

        boolean bVals[] = {true, false}; // black, white
        for (boolean isBlack: bVals) {
            // TODO: for others
            availablePieces.get(PieceType.King).add(new King(this, isBlack));
        }
    }

    public void addPiece(Coord c, Piece p) throws IllegalArgumentException {
        // throw illegalArgumentException is opertaion cannot be done
        if ((board.containsKey(c))) {
            throw new IllegalArgumentException("the Coord " + c + " is not valid");
        }
        board.put(c, p);
    }

    public HashMap<Coord, Piece> getWhitePieces() {
        HashMap<Coord, Piece> whitePieces = new HashMap<>();
        for (HashMap.Entry<Coord, Piece> e: board.entrySet()) {
            if (e.getValue().isBlack()) continue;
            whitePieces.put(e.getKey(), e.getValue());
        }
        return whitePieces;
    }

    public HashMap<Coord, Piece> getBlackPieces() {
        HashMap<Coord, Piece> blackPieces = new HashMap<>();
        for (HashMap.Entry<Coord, Piece> e: board.entrySet()) {
            if (e.getValue().isBlack()) {
                blackPieces.put(e.getKey(), e.getValue());
            }
        }
        return blackPieces;
    }

    public void promotion(Coord c, PieceType pt) throws IllegalArgumentException {
        if (pt == PieceType.Pawn || pt == PieceType.King) {
            throw new IllegalArgumentException("promotion cannot be done");
        }
        if (this.board.get(c) == null || !(this.board.get(c) instanceof Pawn)) {
            throw new IllegalArgumentException("no pawn found");
        }
        boolean isBlack = this.board.get(c).isBlack();

        switch (pt) {
            case Bishop:
            case Queen:
            case Rock:
            case Knight:
                ArrayList<Piece> pList = availablePieces.get(pt);
                Piece p = pList.get(isBlack?0:1);
                this.board.put(c, p);
                break;
            case King:
            case Pawn:
            default:
                throw new IllegalArgumentException("promotion cannot be done");           
        }
    } 

    public void newGame() {
        // start a new game
        this.board.clear();

        // TODO
    }
}
