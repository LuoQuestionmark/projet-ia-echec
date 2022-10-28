package Board;

import java.util.HashMap;
import java.util.TreeSet;

import mUtil.Coord;

public class Queen extends Piece {

    public Queen(Board b, boolean isBlack) {
        super(b, isBlack);
    }

    @Override
    public TreeSet<Coord> getLegalMoves(Coord currentCoord) {
        // well this is easy, just put the code for Rock and for Bishop then we are done
        TreeSet<Coord> ret = new TreeSet<>();

        int nwLimit, swLimit, neLimit, seLimit;

        neLimit = Math.min(7 - currentCoord.x, 7 - currentCoord.y);
        seLimit = Math.min(7 - currentCoord.x, currentCoord.y);
        nwLimit = Math.min(currentCoord.x, 7 - currentCoord.y);
        swLimit = Math.min(currentCoord.x, currentCoord.y);

        HashMap<Coord, Piece> friends, enemies;
        if (this.isBlack()) {
            friends = this.board.getBlackPieces(this);
            enemies = this.board.getWhitePieces(this);
        }
        else {
            friends = this.board.getWhitePieces();
            enemies = this.board.getBlackPieces();
        }

        for (int i = 1; i <= neLimit; i++) {
            Coord tmp = new Coord(currentCoord.x + i, currentCoord.y + i);
            if (friends.keySet().contains(tmp)) break;
            ret.add(tmp);
            if (enemies.keySet().contains(tmp)) break;
        }

        for (int i = 1; i <= seLimit; i++) {
            Coord tmp = new Coord(currentCoord.x + i, currentCoord.y - i);
            if (friends.keySet().contains(tmp)) break;
            ret.add(tmp);
            if (enemies.keySet().contains(tmp)) break;
        }

        for (int i = 1; i <= nwLimit; i++) {
            Coord tmp = new Coord(currentCoord.x - i, currentCoord.y + i);
            if (friends.keySet().contains(tmp)) break;
            ret.add(tmp);
            if (enemies.keySet().contains(tmp)) break;
        }

        for (int i = 1; i <= swLimit; i++) {
            Coord tmp = new Coord(currentCoord.x - i, currentCoord.y - i);
            if (friends.keySet().contains(tmp)) break;
            ret.add(tmp);
            if (enemies.keySet().contains(tmp)) break;
        }

        int xLowerLimit, xUpperLimit, yLowerLimit, yUpperLimit;
        xLowerLimit = Math.min(0, currentCoord.x);
        xUpperLimit = Math.max(7, currentCoord.x);

        yLowerLimit = Math.min(0, currentCoord.y);
        yUpperLimit = Math.max(7, currentCoord.y);

        if (this.isBlack()) {
            friends = this.board.getBlackPieces(this);
            enemies = this.board.getWhitePieces();
        }
        else {
            friends = this.board.getWhitePieces(this);
            enemies = this.board.getBlackPieces();
        }
        

        for (Coord c: friends.keySet()) {
            if (c.y == currentCoord.y) {
                // same row
                if (c.x < currentCoord.x) {
                    xLowerLimit = Math.max(xLowerLimit, c.x + 1); // cannot eat teammate
                }
                else {
                    xUpperLimit = Math.min(xUpperLimit, c.x - 1); // cannot eat teammate
                }
            }
            else if (c.x == currentCoord.x) {
                // same col
                if (c.y < currentCoord.y) {
                    yLowerLimit = Math.max(yLowerLimit, c.y + 1); // cannot eat teammate
                }
                else {
                    yUpperLimit = Math.min(yUpperLimit, c.y - 1); // cannot eat teammate
                }
            }
        }

        for (Coord c: enemies.keySet()) {
            if (c.y == currentCoord.y) {
                // same row
                if (c.x < currentCoord.x) {
                    xLowerLimit = Math.max(xLowerLimit, c.x); // can eat enemy
                }
                else {
                    xUpperLimit = Math.min(xUpperLimit, c.x); // can eat enemy
                }
            }
            else if (c.x == currentCoord.x) {
                // same col
                if (c.y < currentCoord.y) {
                    yLowerLimit = Math.max(yLowerLimit, c.y); // can eat enemy
                }
                else {
                    yUpperLimit = Math.min(yUpperLimit, c.y); // can eat enemy
                }
            }
        }

        for (int col = xLowerLimit; col <= xUpperLimit; col++) {
            ret.add(new Coord(col, currentCoord.y));
        }
        for (int row = yLowerLimit; row <= yUpperLimit; row++) {
            ret.add(new Coord(currentCoord.x, row));
        }

        ret.remove(currentCoord);

        return ret;
    }

    @Override
    public String getShortName() {
        return "Q";
    }
    
}
