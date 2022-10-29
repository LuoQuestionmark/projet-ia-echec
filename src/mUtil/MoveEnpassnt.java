// just need a class to mark the situation
// nothing special to do here

package mUtil;

public class MoveEnpassnt extends Move {
    public MoveEnpassnt(Coord src, Coord dst) {
        super(src, dst);
    }

    @Override
    public String toString() {
        return super.toString() + "(en passant)";
    }
}
