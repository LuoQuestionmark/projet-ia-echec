package Board;

import java.util.TreeMap;
import java.util.TreeSet;

import mUtil.Coord;

public class Pawn extends Piece {
    public Pawn(boolean isBlack) {
        super(isBlack);
    }

    @Override
    public TreeSet<Coord> getLegalMoves(Board currentBoard, Coord currentCoord) {
        TreeSet<Coord> ret = new TreeSet<>();

        TreeMap<Coord, Piece> friends, enemies;
        if (this.isBlack()) {
            friends = currentBoard.getBlackPieces(currentCoord);
            enemies = currentBoard.getWhitePieces();
        }
        else {
            friends = currentBoard.getWhitePieces(currentCoord);
            enemies = currentBoard.getBlackPieces();
        }

        // move forward
        boolean canOneStepForward = false;
        boolean canTwoStepForward = false;
        Coord oneStepForward = null;
        Coord twoStepForward = null;

        if (this.isBlack()) {
            if (currentCoord.y > 1) {
                canTwoStepForward = true;
            }
            if (currentCoord.y == 6) {
                canOneStepForward = true;
            }
        }
        else {
            if (currentCoord.y < 6) {
                canTwoStepForward = true;
            }
            if (currentCoord.y == 1) {
                canOneStepForward = true;
            }
        }

        if (canOneStepForward) {
            oneStepForward = new Coord(currentCoord.x, currentCoord.y + (this.isBlack()?-1:1));
        }
        if (canTwoStepForward) {
            twoStepForward = new Coord(currentCoord.x, currentCoord.y + (this.isBlack()?-2:2));
        }

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
        if (canTwoStepForward) ret.add(twoStepForward);

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
