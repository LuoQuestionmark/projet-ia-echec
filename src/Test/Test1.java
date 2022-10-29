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
        System.out.println(b.getBlackPieces());
        System.out.println(b.getAvaiableMoves());

        System.out.println("now try the first move");
        Move m = b.getAvaiableMoves().get(0);
        System.out.println("move selected: " + m);
        Board bbis = b.move(m);
        System.out.println("result:");
        System.out.println(bbis);

        System.out.println("now try the second move");
        Move m2 = bbis.getAvaiableMoves().get(0);
        System.out.println("move selected: " + m2);
        Board bbis2 = bbis.move(m);
        System.out.println("result:");
        System.out.println(bbis2);

        // System.gc();
        // Runtime rt = Runtime.getRuntime();
        // long usedMB = (rt.totalMemory() - rt.freeMemory()) / 1024 / 1024;
        // System.out.println(usedMB);

        Board b2 = new Board();
        b2.addPiece(new Coord(0, 6), new Pawn(false));
        b2.addPiece(new Coord(0, 0), new Queen(false));
        System.out.println("print the board (test):");
        System.out.println(b2);

        System.out.println("print the available moves");
        System.out.println(b2.getAvaiableMoves());

    }
}
