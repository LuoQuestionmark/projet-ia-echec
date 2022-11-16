// just need a class to mark the situation
// nothing special to do here

package Move;

import mUtil.Coord;

public class MoveEnpassant extends Move {
    public MoveEnpassant(Coord src, Coord dst) {
        super(src, dst);
    }

    @Override
    public String toString() {
        return super.toString() + "(en passant)";
    }
}
