package Test;

import Board.Board;

public class Test1 {
    public static void main(String[] args) {
        Board b = new Board();
        b.newGame();

        System.out.println("print the board (init):");
        System.out.println(b);

        System.out.println("print the available moves");
        System.out.println(b.getAvaiableMoves());
    }
}
