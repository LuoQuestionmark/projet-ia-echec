package Test;

import Board.*;
import Move.*;
import mUtil.*;

public class Test2 {
    public static void main(String[] args) {
        Board b2 = new Board();
        b2.addPiece(new Coord(0, 1), new Pawn(false));
        b2.addPiece(new Coord(1, 3), new Pawn(true));
        System.out.println("print the board (test enpassant):");
        System.out.println(b2);
        Move m = b2.getAvaiableMoves().get(1);
        System.out.println("move white pawn: " + m);
        Board b3 = b2.move(m);
        System.out.println(b3);
        System.out.println("now see if enpassant is available");
        System.out.println(b3.getAvaiableMoves());
    }
}
