package Test;

import Board.PieceType;
import Move.MovePromotion;
import mUtil.Coord;

public class Test7 {
    public static void main(String[] args) {
        Coord c = new Coord(0, 0);
        System.out.println("expect: a1");
        System.out.println(c.getNotationString());

        MovePromotion m = new MovePromotion(new Coord(0, 6), new Coord(0, 7), PieceType.Queen);
        System.out.println("expect: a7a8q");
        System.out.println(m.getNotationString());
    }
}
