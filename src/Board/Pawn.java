package Board;

import java.util.ArrayList;
import java.util.TreeSet;

import mUtil.Coord;

public class Pawn extends Piece {
    private boolean isMoved;

    public Pawn(Board b, boolean isBlack, Coord initCoord) {
        super(b, isBlack, initCoord);
        this.isMoved = false;
    }

    @Override
    public TreeSet<Coord> getLegalMoves() {
        TreeSet<Coord> ret = new TreeSet<>();

        ArrayList<Piece> friends, enemies;
        if (this.isBlack()) {
            friends = this.board.getBlackPieces();
            enemies = this.board.getWhitePieces();
        }
        else {
            friends = this.board.getWhitePieces();
            enemies = this.board.getBlackPieces();
        }

        // move forward
        Coord oneStepForward = new Coord(this.coord.x, this.coord.y + (this.isBlack()?-1:1));
        Coord twoStepForward = new Coord(this.coord.x, this.coord.y + (this.isBlack()?-2:2));
        boolean canOneStepForward = true;
        boolean canTwoStepForward = true;

        for (Piece p: friends) {
            if (p.coord.equals(oneStepForward)) {
                canOneStepForward = false;
                canTwoStepForward = false;
            }
        }
        for (Piece p: enemies) {
            if (p.coord.equals(oneStepForward)) {
                canOneStepForward = false;
                canTwoStepForward = false;
            }
        }
        for (Piece p: friends) {
            if (p.coord.equals(twoStepForward)) {
                canTwoStepForward = false;
            }
        }
        for (Piece p: enemies) {
            if (p.coord.equals(twoStepForward)) {
                canTwoStepForward = false;
            }
        }

        if (canOneStepForward) ret.add(oneStepForward);
        if (!this.isMoved && canTwoStepForward) ret.add(twoStepForward);

        // eat
        Coord eatMinor = new Coord(this.coord.x + (this.isBlack()?-1:1), this.coord.y - 1);
        Coord eatMajor = new Coord(this.coord.x + (this.isBlack()?-1:1), this.coord.y + 1);

        for (Piece p: enemies) {
            if (p.coord.equals(eatMinor)) ret.add(eatMinor);
            if (p.coord.equals(eatMajor)) ret.add(eatMajor);
        }

        return ret;
    }

    @Override
    public boolean moveTo(Coord c) {
        this.coord = c;
        this.isMoved = true;
        // if (this.coord.y == 7 && !(this.isBlack())) this.board.promotion(this);
        // if (this.coord.y == 0 &&   this.isBlack() ) this.board.promotion(this);
        return true;
    }

    public boolean canBePromoted() {
        if (this.coord.y == 7 && !(this.isBlack())) return true;
        if (this.coord.y == 0 &&   this.isBlack() ) return true;
        return false;
    }
}
