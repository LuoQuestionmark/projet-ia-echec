package Board;

import java.util.HashMap;
import java.util.TreeSet;
import java.lang.Math;

import mUtil.Coord;

public class Bishop extends Piece{

    public Bishop(Board b, boolean isBlack) {
        super(b, isBlack);
    }

    @Override
    public TreeSet<Coord> getLegalMoves(Coord currentCoord) {
        TreeSet<Coord> ret = new TreeSet<>();

        int nwLimit, swLimit, neLimit, seLimit;

        neLimit = Math.min(7 - currentCoord.x, 7 - currentCoord.y);
        seLimit = Math.min(7 - currentCoord.x, currentCoord.y);
        nwLimit = Math.min(currentCoord.x, 7 - currentCoord.y);
        swLimit = Math.min(currentCoord.x, currentCoord.y);

        HashMap<Coord, Piece> friends, enemies;
        if (this.isBlack()) {
            friends = this.board.getBlackPieces(this);
            enemies = this.board.getWhitePieces();
        }
        else {
            friends = this.board.getWhitePieces(this);
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

        return ret;
    }

    @Override
    public String getShortName() {
        return "B";
    }
    
}
