// This will be the abstract class of a chess piece

package Board;

import java.util.ArrayList;

import mUtil.Coord;

public abstract class Piece {
    private boolean isBlackVal;
    private Coord coord; // the position
    abstract public boolean isBlack();
    abstract public Coord getCoord();
    abstract public ArrayList<Coord> getLegalMoves();
    abstract public boolean moveTo(Coord c);
}
