package Test;

import Board.Board;
import Board.Knight;
import mUtil.Coord;

public class Test2 {
    public static void main(String[] args) {
        Knight k = new Knight(new Board(), false, new Coord(3, 3));
        System.out.println(k.getLegalMoves());
    }
}
