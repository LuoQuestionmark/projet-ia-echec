package Board;

import java.util.ArrayList;
import java.util.TreeSet;

import mUtil.Coord;

public class Queen extends Piece {

    public Queen(Board b, boolean isBlack, Coord initCoord) {
        super(b, isBlack, initCoord);
    }

    @Override
    public TreeSet<Coord> getLegalMoves() {
        // well this is easy, just put the code for Rock and for Bishop then we are done
        TreeSet<Coord> ret = new TreeSet<>();

        int nwLimit, swLimit, neLimit, seLimit;

        neLimit = Math.min(7 - this.coord.x, 7 - this.coord.y);
        seLimit = Math.min(7 - this.coord.x, this.coord.y);
        nwLimit = Math.min(this.coord.x, 7 - this.coord.y);
        swLimit = Math.min(this.coord.x, this.coord.y);

        ArrayList<Piece> friends, enemies;
        if (this.isBlack()) {
            friends = this.board.getBlackPieces();
            enemies = this.board.getWhitePieces();
        }
        else {
            friends = this.board.getWhitePieces();
            enemies = this.board.getBlackPieces();
        }

        TreeSet<Coord> friendsCoords = new TreeSet<>();
        TreeSet<Coord> enemiesCooods = new TreeSet<>();

        for (Piece p: friends) {
            friendsCoords.add(p.coord);
        }
        for (Piece p: enemies) {
            enemiesCooods.add(p.coord);
        }

        for (int i = 1; i <= neLimit; i++) {
            Coord tmp = new Coord(this.coord.x + i, this.coord.y + i);
            if (friendsCoords.contains(tmp)) break;
            ret.add(tmp);
            if (enemiesCooods.contains(tmp)) break;
        }

        for (int i = 1; i <= seLimit; i++) {
            Coord tmp = new Coord(this.coord.x + i, this.coord.y - i);
            if (friendsCoords.contains(tmp)) break;
            ret.add(tmp);
            if (enemiesCooods.contains(tmp)) break;
        }

        for (int i = 1; i <= nwLimit; i++) {
            Coord tmp = new Coord(this.coord.x - i, this.coord.y + i);
            if (friendsCoords.contains(tmp)) break;
            ret.add(tmp);
            if (enemiesCooods.contains(tmp)) break;
        }

        for (int i = 1; i <= swLimit; i++) {
            Coord tmp = new Coord(this.coord.x - i, this.coord.y - i);
            if (friendsCoords.contains(tmp)) break;
            ret.add(tmp);
            if (enemiesCooods.contains(tmp)) break;
        }

        int xLowerLimit, xUpperLimit, yLowerLimit, yUpperLimit;
        xLowerLimit = Math.min(0, this.coord.x);
        xUpperLimit = Math.max(7, this.coord.x);

        yLowerLimit = Math.min(0, this.coord.y);
        yUpperLimit = Math.max(7, this.coord.y);

        if (this.isBlack()) {
            friends = this.board.getBlackPieces();
            enemies = this.board.getWhitePieces();
        }
        else {
            friends = this.board.getWhitePieces();
            enemies = this.board.getBlackPieces();
        }

        for (Piece p: friends) {
            if (p.coord.y == this.coord.y) {
                // same row
                if (p.coord.x < this.coord.x) {
                    xLowerLimit = Math.max(xLowerLimit, p.coord.x + 1); // cannot eat teammate
                }
                else {
                    xUpperLimit = Math.min(xUpperLimit, p.coord.x - 1); // cannot eat teammate
                }
            }
            else if (p.coord.x == this.coord.x) {
                // same col
                if (p.coord.y < this.coord.y) {
                    yLowerLimit = Math.max(yLowerLimit, p.coord.y + 1); // cannot eat teammate
                }
                else {
                    yUpperLimit = Math.min(yUpperLimit, p.coord.y - 1); // cannot eat teammate
                }
            }
        }

        for (Piece p: enemies) {
            if (p.coord.y == this.coord.y) {
                // same row
                if (p.coord.x < this.coord.x) {
                    xLowerLimit = Math.max(xLowerLimit, p.coord.x); // can eat enemy
                }
                else {
                    xUpperLimit = Math.min(xUpperLimit, p.coord.x); // can eat enemy
                }
            }
            else if (p.coord.x == this.coord.x) {
                // same col
                if (p.coord.y < this.coord.y) {
                    yLowerLimit = Math.max(yLowerLimit, p.coord.y); // can eat enemy
                }
                else {
                    yUpperLimit = Math.min(yUpperLimit, p.coord.y); // can eat enemy
                }
            }
        }

        for (int col = xLowerLimit; col <= xUpperLimit; col++) {
            ret.add(new Coord(col, this.coord.y));
        }
        for (int row = yLowerLimit; row <= yUpperLimit; row++) {
            ret.add(new Coord(this.coord.x, row));
        }

        ret.remove(this.coord);

        return ret;
    }
    
}
