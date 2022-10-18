package Board;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
import java.lang.Math;

import mUtil.Coord;

public class Rock extends Piece{

    public Rock(Board b, boolean isBlack, Coord initCoord) {
        super(b, isBlack, initCoord);
    }

    @Override
    public TreeSet<Coord> getLegalMoves() {
        int xLowerLimit, xUpperLimit, yLowerLimit, yUpperLimit;
        xLowerLimit = Math.min(0, this.coord.x);
        xUpperLimit = Math.max(7, this.coord.x);

        yLowerLimit = Math.min(0, this.coord.y);
        yUpperLimit = Math.max(7, this.coord.y);
        
        ArrayList<Piece> friends, enemies;
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

        TreeSet<Coord> ret = new TreeSet<>();

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
