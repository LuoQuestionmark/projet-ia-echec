package Board;

import java.util.ArrayList;
import java.util.TreeSet;

import mUtil.Coord;

public class King extends Piece {

    public King(Board b, boolean isBlack, Coord initCoord) {
        super(b, isBlack, initCoord);
    }

    @Override
    public TreeSet<Coord> getLegalMoves() {
        // this one is tricky, because there is a rule binding with
        // the move of the opponent; king cannot suicide
        TreeSet<Coord> ret = new TreeSet<Coord>();
        ArrayList<Integer> xList = new ArrayList<>();
        ArrayList<Integer> yList = new ArrayList<>();

        // step 1: add

        xList.add(this.coord.x);
        yList.add(this.coord.y);

        if (this.coord.x > 0) xList.add(this.coord.x - 1);
        if (this.coord.y > 0) yList.add(this.coord.y - 1);
        if (this.coord.x < 7) xList.add(this.coord.x + 1);
        if (this.coord.y < 7) yList.add(this.coord.y + 1);

        for (int x: xList) {
            for (int y: yList) {
                ret.add(new Coord(x, y));
            }
        }
        ret.remove(this.coord);

        // step 2: remove
        ArrayList<Piece> enemies;
        TreeSet<Coord> illegaCoords = new TreeSet<Coord>();
        if (this.isBlack()) {
            enemies = board.getWhitePieces();
        }
        else {
            enemies = board.getBlackPieces();
        }
        for (Piece p: enemies) {
            for (Coord m: p.getLegalMoves()) {
                if (ret.contains(m)) {
                    illegaCoords.add(m);
                }
            }
        }

        ret.removeAll(illegaCoords);

        return ret;
    }
}
