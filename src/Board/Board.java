// This will be the chess board class.

package Board;

import java.util.ArrayList;

import mUtil.Coord;

public class Board {
    private ArrayList<Piece> whitePieces = new ArrayList<>();
    private ArrayList<Piece> blackPieces = new ArrayList<>();

    public boolean addPiece(Piece p) throws IllegalArgumentException {
        // throw illegalArgumentException is opertaion cannot be done
        for (Piece wp: whitePieces) {
            if (wp.coord.equals(p.coord)) {
                throw new IllegalArgumentException();
            }
        }
        for (Piece bp: blackPieces) {
            if (bp.coord.equals(p.coord)) {
                throw new IllegalArgumentException();
            }
        }
        if (p.isBlack()) {
            blackPieces.add(p);
        }
        else {
            whitePieces.add(p);
        }
        return true;
    }

    public ArrayList<Piece> getWhitePieces() {
        return whitePieces;
    }

    public ArrayList<Piece> getBlackPieces() {
        return blackPieces;
    }

    public void promotion(Pawn p) {
        // this one without argument, so it will demande directely in the stdout ?
        // TODO
    }

    public void promotion(Pawn p, PieceType pt) throws IllegalArgumentException {
        if (pt == PieceType.Pawn || pt == PieceType.King) {
            throw new IllegalArgumentException("promotion cannot be done");
        }
        Coord c = p.coord;
        Boolean pIsBlack = p.isBlack();
        if (p.isBlack()) {
            this.blackPieces.remove(p);
        }
        else {
            this.whitePieces.remove(p);
        }
        switch (pt) {
            case Bishop:
                Bishop b = new Bishop(this, pIsBlack, c);
                this.addPiece(b);
                break;
            case Queen:
                Queen q = new Queen(this, pIsBlack, c);
                this.addPiece(q);
                break;
            case Rock:
                Rock r = new Rock(this, pIsBlack, c);
                this.addPiece(r);
                break;
            case King:
            case Pawn:
            default:
                throw new IllegalArgumentException("promotion cannot be done");           
        }
    } 

    public void newGame() {
        // start a new game
        this.whitePieces.clear();
        this.blackPieces.clear();

        this.addPiece(new Rock(this, false, new Coord(0, 0)));
        this.addPiece(new Rock(this, false, new Coord(7, 0)));
        this.addPiece(new Rock(this, true, new Coord(0, 7)));
        this.addPiece(new Rock(this, true, new Coord(7, 7)));
        
        this.addPiece(new Knight(this, false, new Coord(1, 0)));
        this.addPiece(new Knight(this, false, new Coord(6, 0)));
        this.addPiece(new Knight(this, true, new Coord(1, 7)));
        this.addPiece(new Knight(this, true, new Coord(6, 7)));

        this.addPiece(new Bishop(this, false, new Coord(2, 0)));
        this.addPiece(new Bishop(this, false, new Coord(5, 0)));
        this.addPiece(new Bishop(this, true, new Coord(2, 7)));
        this.addPiece(new Bishop(this, true, new Coord(5, 7)));

        this.addPiece(new Queen(this, false, new Coord(3, 0)));
        this.addPiece(new Queen(this, true, new Coord(3, 0)));
        this.addPiece(new Rock(this, false, new Coord(4, 7)));
        this.addPiece(new Rock(this, true, new Coord(4, 7)));

        for (int i = 0; i < 8; i++) {
            this.addPiece(new Pawn(this, false, new Coord(i, 1)));
            this.addPiece(new Pawn(this, true, new Coord(6, i)));
        }
    }
}
