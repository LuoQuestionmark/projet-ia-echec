import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import Analyser.ChessAnalyser;
import Board.Board;
import Interface.InputNotifier;
import Move.Move;


public class OCAP {
    final static int timeout = 2000;
    static Board board;
    static ChessAnalyser analyser;
    static InputNotifier notifier;
    static BufferedReader bufferedReader;

    public static void main(String[] args) throws IOException {
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        startReadInput();
        while (true) {
            try {
                Thread.sleep(timeout);
                if (analyser != null) {
                    Move m = analyser.getBestMove();
                    printBestMove(m);
                }
            } catch (InterruptedException e) {
                while (bufferedReader.ready()) {
                    String line = bufferedReader.readLine();
                    inputParser(line);
                }
            }
        }

    }

    private static void startReadInput() {
        notifier = new InputNotifier(Thread.currentThread());
        Thread threadNotifyThread = new Thread(notifier);
        threadNotifyThread.start();
    }

    private static void inputParser(String inputString) {
        String[] tokens = inputString.split("[ ]+");
        switch (tokens[0]) {
            case "uci":
                printUCI();
                break;
            case "isready":
                printIsReady();
                break;
            case "go":
                startAnalyser();
                break;
            case "stop":
                Move m = stopAnalyser();
                if (m == null)
                    break;
                printBestMove(m);
                break;
            case "quit":
                if (notifier != null) {
                    notifier.finish();
                }
                if (analyser != null) {
                    analyser.finish();
                }
                System.exit(0);
                break;
            case "position":
                dealPosition(tokens);
                break;
            default:
                break;
        }
    }

    private static void printUCI() {
        String idName = "id name OCaptain\n";
        String idAuthor = "id author Wenhao\n";
        String uciok = "uciok\n";
        System.out.print(idName);
        System.out.print(idAuthor);
        System.out.print(uciok);
    }

    private static void printIsReady() {
        String ready = "ready\n";
        System.out.print(ready);
    }

    private static void startAnalyser() {
        if (board == null) return;
        analyser = new ChessAnalyser(board);
        Thread analyseThread = new Thread(analyser);
        analyseThread.start();
    }

    private static Move stopAnalyser() {
        if (analyser == null)
            return null;
        return analyser.getBestMove();
    }

    private static void printBestMove(Move best) {
        String bestmove = "bestmove " + best.getNotationString() + "\n";
        System.out.print(bestmove);
        analyser.finish();
        analyser = null;
    }

    private static void dealPosition(String[] tokens) {
        if (!(tokens[0].equals("position"))
                || !(tokens[1].equals("startpos"))) {
            return;
        }
        board = new Board();
        board.newGame();
        if (tokens.length < 3) {
            return;
        }
        if (!(tokens[2].equals("moves")))
            return;

        for (int i = 3; i < tokens.length; i++) {
            board = board.move(tokens[i]);
        }
    }
}
