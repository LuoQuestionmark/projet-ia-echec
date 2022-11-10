package Test;


import Analyser.*;
import Board.Board;

public class Test4 {
    public static void main(String[] args) {
        Board b = new Board();
        b.newGame();
        ChessAnalyser ca = new ChessAnalyser(b);
        // long startTime = System.nanoTime();
        ca.explore(1);
        // long endTime = System.nanoTime();
        // System.out.println((endTime - startTime)/1000000000.0);
        
        ca.getRoot().dumpGraph("./output.dot");
        System.exit(0);

        Thread t = new Thread(ca);
        t.start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return;
        }

        System.out.println(ca.getBestMove());

        ca.getRoot().dumpGraph("./output.dot");
    }
}
