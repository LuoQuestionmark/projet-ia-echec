// a Move is a tuple containing two Coord: coordSrc and coordDst
// representing the piece from the coordSrc moving to coordDst
package mUtil;

public class Move implements Comparable<Move> {
    public Coord coordSrc, coordDst;

    public Move(Coord src, Coord dst) {
        coordSrc = src;
        coordDst = dst;
    }

    @Override
    public int compareTo(Move o) {
        // to order the Move, also to ensure equals works as expected
        int ret = 0;
        if (this.coordSrc.compareTo(coordSrc) > 0) {
            ret += 2;
        }
        else {
            ret -= 2;
        }
        if (this.coordDst.compareTo(coordDst) > 0) {
            ret += 1;
        }
        else {
            ret -= 1;
        }

        return ret;
    }

    @Override
    public String toString() {
        return coordSrc + "->" + coordDst;
    }
}
