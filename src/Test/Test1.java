package Test;

import java.util.Random;

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

        System.out.println("now try the first move");
        Move m = b.getAvaiableMoves().get(0);
        System.out.println("move selected: " + m);
        Board bbis = b.move(m);
        System.out.println("result:");
        System.out.println(bbis);

        System.out.println("now try the second move");
        Move m2 = bbis.getAvaiableMoves().get(0);
        System.out.println("move selected: " + m2);
        Board bbis2 = bbis.move(m2);
        System.out.println("result:");
        System.out.println(bbis2);

        System.out.println("and some more");
        Move ms;
        Board bs = bbis2;
        Random r = new Random();

        for (int i = 0; i < 10; i++) {
            ms = bs.getAvaiableMoves().get(r.nextInt(bs.getAvaiableMoves().size()));
            bs = bs.move(ms);
            System.out.println("move selected: " + ms);
            System.out.println(bs);
        }
    }
}
