package Board;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.TreeSet;

import mUtil.Coord;

public class King extends Piece {

    public King(boolean isBlack) {
        super(isBlack);
    }

    @Override
    public TreeSet<Coord> getLegalMoves(Board currentBoard, Coord currentCoord) {
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
        // TreeMap<Coord,Piece> enemies;
        TreeSet<Coord> illegalCoords = new TreeSet<Coord>();
        //     }
        // }

        TreeMap<Coord,Piece> friends;
        if (this.isBlack()) {
            friends = currentBoard.getBlackPieces(currentCoord);
        }
        else {
            friends = currentBoard.getWhitePieces(currentCoord);
        }
        for (Coord c: friends.keySet()) {
            if (ret.contains(c)) {
                illegalCoords.add(c);
            }

        }

        ret.removeAll(illegalCoords);
        return ret;
    }

    @Override
    public String getShortName() {
        return "K";
    }
}
