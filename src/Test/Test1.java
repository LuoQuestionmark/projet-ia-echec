package Test;
import Board.*;
import mUtil.Coord;

public class Test1 {
    public static void main(String[] args) {
        King k = new King(new Board(), true, new Coord(0, 0));
        System.out.println(k.getLegalMoves());

        Rock r = new Rock(new Board(), false, new Coord(3, 4));
        System.out.println(r.getLegalMoves());

        Bishop b = new Bishop(new Board(), false, new Coord(3, 2));
        System.out.println(b.getLegalMoves());

        Queen q = new Queen(new Board(), false, new Coord(3, 3));
        System.out.println(q.getLegalMoves());

        Pawn p = new Pawn(new Board(), false, new Coord(3, 1));
        System.out.println(p.getLegalMoves());
        p.moveTo(new Coord(3, 2));
        System.out.println(p.getLegalMoves());
    }
}
