/* A simple test that try to play an interactive game
 * do this prototype before make a real interface
 */
package Test;

import Analyser.*;
import Board.Board;
import Move.Move;
import mUtil.Coord;

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class Test5 {
    public static void main(String[] args) {
        Board b = new Board();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        b.newGame();
        try {
            do {
                ChessAnalyser ca = new ChessAnalyser(b);
                ca.setDepthLimit(3);
                Thread t = new Thread(ca);
                t.run();
                Thread.sleep(8000);
                Move m = ca.getBestMove();
                System.out.println(m);
                b = b.move(m);
                System.out.println(b);
                Move oppoMove;
                while (true) {
                    String s = br.readLine();
                    if (s.equals("p")) {
                        ca.getRoot().dumpGraph("./output.dot");
                        continue;
                    }
                    String[] tokens = s.split("[ ]+" );
                    if (tokens.length != 4) continue;
                    int[] vals = new int[4];
                    for (int i = 0; i < 4; i++) {
                        vals[i] = Integer.parseInt(tokens[i]);
                    }
                    oppoMove = new Move(new Coord(vals[0], vals[1]), new Coord(vals[2], vals[3]));
                    break;
                }
                System.out.println("detect input: " + oppoMove);
                b = b.move(oppoMove);
            } while (true);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
