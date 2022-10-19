package Board;

import java.util.ArrayList;
import java.util.TreeSet;
import java.lang.Math;

import mUtil.Coord;

public class Bishop extends Piece{

    public Bishop(Board b, boolean isBlack, Coord initCoord) {
        super(b, isBlack, initCoord);
    }

    @Override
    public TreeSet<Coord> getLegalMoves() {
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

        return ret;
    }
    
}
