package Test;

import Board.*;
import mUtil.*;

public class Test1 {
    public static void main(String[] args) {
        Board b = new Board();
        b.newGame();

        System.out.println("print the board (init):");
        System.out.println(b);

        System.out.println("print the available moves");
        System.out.println(b.getAvaiableMoves());

        Board b2 = new Board();
        b2.addPiece(new Coord(0, 6), new Pawn(b2, false));
        b2.addPiece(new Coord(0, 0), new Queen(b2, false));
        System.out.println("print the board (test):");
        System.out.println(b2);

        System.out.println("print the available moves");
        System.out.println(b2.getAvaiableMoves());

    }
}
