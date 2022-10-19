package Board;

import java.util.ArrayList;
import java.util.TreeSet;

import mUtil.Coord;

public class Knight extends Piece {

    public Knight(Board b, boolean isBlack, Coord initCoord) {
        super(b, isBlack, initCoord);
    }

    @Override
    public TreeSet<Coord> getLegalMoves() {
        ArrayList<Integer> p1 = new ArrayList<>();
        p1.add(1); p1.add(-1);
        ArrayList<Integer> p2 = new ArrayList<>(p1);
        ArrayList<Integer> p3 = new ArrayList<>(p1);

        TreeSet<Coord> ret = new TreeSet<>();

        for (int i: p1) {
            for (int j: p2) {
                for (int k: p3) {
                    int x = i * j * (k>0?2:1);
                    int y = j * (k<0?2:1);
                    // System.out.printf("%d, %d\n", x, y);
                    if ((x + coord.x >= 0 && x + coord.x < 8)
                      &&(y + coord.y >= 0 && y + coord.y < 8))
                    {
                        ret.add(new Coord(x + coord.x,  + y + coord.y));
                    }
                }
            }
        }

        ArrayList<Piece> friends;
        if (this.isBlack()) {
            friends = this.board.getBlackPieces();
        }
        else {
            friends = this.board.getWhitePieces();
        }

        TreeSet<Coord> illegaCoords = new TreeSet<Coord>();

        for (Piece p: friends) {
            for (Coord c: ret) {
                if (c.equals(p.coord)) {
                    illegaCoords.add(c);
                }
            }
        }

        ret.removeAll(illegaCoords);

        return ret;
    }
    
}
