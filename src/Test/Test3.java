package Test;

import java.util.ArrayList;
import java.util.Random;

import Board.*;
import Move.*;

public class Test3 {
    public static void main(String[] args) {
        Move ms;
        Board bs = new Board();
        Random r = new Random();
        ArrayList<Move> availableMoves = new ArrayList<>();

        bs.newGame();

        double evalVal;
        while(true) {
            availableMoves = bs.getAvailableMoves();
            if (availableMoves.size() == 0) {
                System.out.println(bs);
                break;
            }
            evalVal = bs.evaluate();
            if (Double.isNaN(evalVal)
            ||  Double.isInfinite(evalVal)) {
                System.out.println(bs);
                break;
            }
            ms = availableMoves.get(r.nextInt(availableMoves.size()));
            bs = bs.move(ms);
            System.out.println("move selected: " + ms);
            System.out.println("score: " + evalVal);
            // System.out.println(bs);
        }
    }
}
