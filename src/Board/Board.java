// This will be the chess board class.

package Board;

import java.util.ArrayList;
import java.util.HashMap;

import mUtil.*;

public class Board {
    private HashMap<PieceType, ArrayList<Piece>> availablePieces = new HashMap<>();
    private HashMap<Coord, Piece> board = new HashMap<>();

    private boolean isBlackMove = false;

    public Board() {
        for (PieceType pt: PieceType.values()) {
            availablePieces.put(pt, new ArrayList<Piece>());
        }

        boolean bVals[] = {true, false}; // black, white
        for (boolean isBlack: bVals) {
            availablePieces.get(PieceType.Bishop).add(new Bishop(this, isBlack));
            availablePieces.get(PieceType.Knight).add(new Knight(this, isBlack));
            availablePieces.get(PieceType.Pawn).add(new Pawn(this, isBlack));
            availablePieces.get(PieceType.Rock).add(new Rock(this, isBlack));
            availablePieces.get(PieceType.King).add(new King(this, isBlack));
            availablePieces.get(PieceType.Queen).add(new Queen(this, isBlack));
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
        this.isBlackMove = false;

        this.addPiece(new Coord(0, 0), availablePieces.get(PieceType.Rock).get(1));
        this.addPiece(new Coord(7, 0), availablePieces.get(PieceType.Rock).get(1));
        this.addPiece(new Coord(0, 7), availablePieces.get(PieceType.Rock).get(0));
        this.addPiece(new Coord(7, 7), availablePieces.get(PieceType.Rock).get(0));

        this.addPiece(new Coord(1, 0), availablePieces.get(PieceType.Knight).get(1));
        this.addPiece(new Coord(6, 0), availablePieces.get(PieceType.Knight).get(1));
        this.addPiece(new Coord(1, 7), availablePieces.get(PieceType.Knight).get(0));
        this.addPiece(new Coord(6, 7), availablePieces.get(PieceType.Knight).get(0));

        this.addPiece(new Coord(2, 0), availablePieces.get(PieceType.Bishop).get(1));
        this.addPiece(new Coord(5, 0), availablePieces.get(PieceType.Bishop).get(1));
        this.addPiece(new Coord(2, 7), availablePieces.get(PieceType.Bishop).get(0));
        this.addPiece(new Coord(5, 7), availablePieces.get(PieceType.Bishop).get(0));

        this.addPiece(new Coord(3, 0), availablePieces.get(PieceType.Queen).get(1));
        this.addPiece(new Coord(3, 7), availablePieces.get(PieceType.Queen).get(0));

        this.addPiece(new Coord(4, 0), availablePieces.get(PieceType.King).get(1));
        this.addPiece(new Coord(4, 7), availablePieces.get(PieceType.King).get(0));

        for (int i = 0; i < 8; i++) {
            this.addPiece(new Coord(i, 1), availablePieces.get(PieceType.Pawn).get(1));
            this.addPiece(new Coord(i, 6), availablePieces.get(PieceType.Pawn).get(0));
        }

    }

    public ArrayList<Move> getAvaiableMoves() {
        ArrayList<Move> ret = new ArrayList<>();
        HashMap<Coord, Piece> pieces;

        if (isBlackMove) {
            pieces = getBlackPieces();
        }
        else {
            pieces = getWhitePieces();
        }

        for (HashMap.Entry<Coord, Piece> e: pieces.entrySet()) {
            Coord src = e.getKey();
            for (Coord dst: e.getValue().getLegalMoves(src)) {
                // special case: promotion detection
                if (e.getValue().getShortName().equals("P")) {
                    if ((  e.getValue().isBlack()  && dst.y == 0)
                    ||  (!(e.getValue().isBlack()) && dst.y == 7)) {
                        for (PieceType pt: PieceType.values()) {
                            if (pt == PieceType.King || pt == PieceType.Pawn) continue;
                            ret.add(new MovePromotion(src, dst, pt));
                        }
                    }
                }
                else {
                    ret.add(new Move(src, dst));
                }
            }
        }

        return ret;
    }



    @Override
    public String toString() {
        String ret = "";

        for (int y = 7; y >= 0; y--) {
            ret += "|";
            for (int x = 0; x < 8; x++) {
                String pieceNote = "  ";
                for (Coord c: board.keySet()) {
                    if (c.equals(new Coord(x, y))) {
                        pieceNote = board.get(c).getShortName() + (board.get(c).isBlack()?"B":"W");
                        break;
                    }
                }
                ret += pieceNote + "|";
            }
            ret += "\n";
        }

        return ret;
    }
}
