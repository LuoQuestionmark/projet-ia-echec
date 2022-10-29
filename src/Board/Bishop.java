package Board;

import java.util.TreeMap;
import java.util.TreeSet;
import java.lang.Math;

import mUtil.Coord;

public class Bishop extends Piece{

    public Bishop(boolean isBlack) {
        super(isBlack);
    }

    @Override
    public TreeSet<Coord> getLegalMoves(Board currentBoard, Coord currentCoord) {
        TreeSet<Coord> ret = new TreeSet<>();

        int nwLimit, swLimit, neLimit, seLimit;

        neLimit = Math.min(7 - currentCoord.x, 7 - currentCoord.y);
        seLimit = Math.min(7 - currentCoord.x, currentCoord.y);
        nwLimit = Math.min(currentCoord.x, 7 - currentCoord.y);
        swLimit = Math.min(currentCoord.x, currentCoord.y);

        TreeMap<Coord, Piece> friends, enemies;
        if (this.isBlack()) {
            friends = currentBoard.getBlackPieces(currentCoord);
            enemies = currentBoard.getWhitePieces();
        }
        else {
            friends = currentBoard.getWhitePieces(currentCoord);
            enemies = currentBoard.getBlackPieces();
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
