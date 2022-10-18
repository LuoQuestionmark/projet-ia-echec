package Test;
import Board.Board;
import Board.King;
import mUtil.Coord;

public class Test1 {
    public static void main(String[] args) {
        King k = new King(new Board(), true, new Coord(0, 0));
        System.out.println(k.getLegalMoves());
    }
}
