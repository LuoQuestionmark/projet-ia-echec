package Move;

import mUtil.Coord;

public class MoveCastling extends Move {
    public Coord coordSrc2, coordDst2;

    public MoveCastling(Coord src, Coord dst, Coord src2, Coord dst2) {
        // src2, dst2: the movement of Rock
        super(src, dst);
        this.coordSrc2 = src2;
        this.coordDst2 = dst2;
    }

    @Override
    public String toString() {
        return super.toString() + coordSrc2 + "->" + coordDst2 + " (castling)";
    }
    
}
