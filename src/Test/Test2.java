package Test;

import Board.Board;
import Board.Rock;
import mUtil.Coord;

public class Test2 {
    public static void main(String[] args) {
        Rock r = new Rock(new Board(), false, new Coord(3, 4));
        System.out.println(r.getLegalMoves());
    }
}
