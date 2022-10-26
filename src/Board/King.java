package Board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

import mUtil.Coord;

public class King extends Piece {

    public King(Board b, boolean isBlack) {
        super(b, isBlack);
    }

    @Override
    public TreeSet<Coord> getLegalMoves(Coord currentCoord) {
        // this one is tricky, because there is a rule binding with
        // the move of the opponent; king cannot suicide
        TreeSet<Coord> ret = new TreeSet<Coord>();
        ArrayList<Integer> xList = new ArrayList<>();
        ArrayList<Integer> yList = new ArrayList<>();

        // step 1: add

        xList.add(currentCoord.x);
        yList.add(currentCoord.y);

        if (currentCoord.x > 0) xList.add(currentCoord.x - 1);
        if (currentCoord.y > 0) yList.add(currentCoord.y - 1);
        if (currentCoord.x < 7) xList.add(currentCoord.x + 1);
        if (currentCoord.y < 7) yList.add(currentCoord.y + 1);

        for (int x: xList) {
            for (int y: yList) {
                ret.add(new Coord(x, y));
            }
        }
        ret.remove(currentCoord);

        // step 2: remove
        HashMap<Coord,Piece> enemies;
        TreeSet<Coord> illegaCoords = new TreeSet<Coord>();
        if (this.isBlack()) {
            enemies = board.getWhitePieces();
        }
        else {
            enemies = board.getBlackPieces();
        }

        for (HashMap.Entry<Coord,Piece> e: enemies.entrySet()) {
            for (Coord m: e.getValue().getLegalMoves(e.getKey())) {
                if (ret.contains(m)) {
                    illegaCoords.add(m);
                }
            }
        }

        HashMap<Coord,Piece> friends;
        if (this.isBlack()) {
            friends = board.getBlackPieces();
        }
        else {
            friends = board.getWhitePieces();
        }
        for (Coord c: friends.keySet()) {
            if (ret.contains(c)) {
                illegaCoords.add(c);
            }

        }

        ret.removeAll(illegaCoords);
        return ret;
    }

    @Override
    public String getShortName() {
        return "K";
    }
}
