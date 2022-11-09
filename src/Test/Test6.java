package Test;

import java.util.Random;

import javax.naming.NameAlreadyBoundException;

import Board.*;
import Move.*;
import mUtil.Coord;

public class Test6 {
    public static void main(String[] args) {
        Board b = new Board();
        b.newGame();

        System.out.println("print the board (init):");
        System.out.println(b);

        Move m = new Move(new Coord(7, 1), new Coord(7, 3));
        Move m2 = new Move(new Coord(7, 6), new Coord(7, 5));
        Move m3 = new Move(new Coord(7, 3), new Coord(7, 5));

        b = b.move(m);
        System.out.println(b);
        b = b.move(m2);
        System.out.println(b);
        // b = b.move(m3);
        System.out.println(b.getAvailableMoves());
    }
}
