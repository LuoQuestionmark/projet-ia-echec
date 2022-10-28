package Board;

import java.util.HashMap;
import java.util.TreeSet;

import mUtil.Coord;

public class Pawn extends Piece {
    private boolean isMoved;

    public Pawn(Board b, boolean isBlack) {
        super(b, isBlack);
        this.isMoved = false;
    }

    @Override
    public TreeSet<Coord> getLegalMoves(Coord currentCoord) {
        TreeSet<Coord> ret = new TreeSet<>();

        HashMap<Coord, Piece> friends, enemies;
        if (this.isBlack()) {
            friends = this.board.getBlackPieces();
            enemies = this.board.getWhitePieces();
        }
        else {
            friends = this.board.getWhitePieces();
            enemies = this.board.getBlackPieces();
        }

        // move forward
        Coord oneStepForward = new Coord(currentCoord.x, currentCoord.y + (this.isBlack()?-1:1));
        Coord twoStepForward = new Coord(currentCoord.x, currentCoord.y + (this.isBlack()?-2:2));
        boolean canOneStepForward = true;
        boolean canTwoStepForward = true;

        for (Coord c: friends.keySet()) {
            if (c.equals(oneStepForward)) {
                canOneStepForward = false;
                canTwoStepForward = false;
            }
        }
        for (Coord c: enemies.keySet()) {
            if (c.equals(oneStepForward)) {
                canOneStepForward = false;
                canTwoStepForward = false;
            }
        }
        for (Coord c: friends.keySet()) {
            if (c.equals(twoStepForward)) {
                canTwoStepForward = false;
            }
        }
        for (Coord c: enemies.keySet()) {
            if (c.equals(twoStepForward)) {
                canTwoStepForward = false;
            }
        }

        if (canOneStepForward) ret.add(oneStepForward);
        if (!this.isMoved && canTwoStepForward) ret.add(twoStepForward);

        // eat
        if (currentCoord.x > 0) {
            Coord eatWest = new Coord(currentCoord.x - 1, currentCoord.y + (this.isBlack()?-1:1));
            for (Coord c: enemies.keySet()) {
                if (c.equals(eatWest)) ret.add(eatWest);
            }
        }
        if (currentCoord.x < 7) {
            Coord eatEast = new Coord(currentCoord.x + 1, currentCoord.y + (this.isBlack()?-1:1));
            for (Coord c: enemies.keySet()) {
                if (c.equals(eatEast)) ret.add(eatEast);
            }
        }

        return ret;
    }

    public boolean canBePromoted(Coord currentCoord) {
        if (currentCoord.y == 7 && !(this.isBlack())) return true;
        if (currentCoord.y == 0 &&   this.isBlack() ) return true;
        return false;
    }

    @Override
    public String getShortName() {
        return "P";
    }
}
