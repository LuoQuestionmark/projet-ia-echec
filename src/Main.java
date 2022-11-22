// this will be the interface of the chess engine
// working on the io

import Analyser.*;
import Board.Board;
import Move.Move;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static FileWriter logFileWriter;

    public static void main(String[] args) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        File log = new File("./log.txt");

        try {
            // init log
            logFileWriter = new FileWriter(log);

            // read input "uci"
            String input = bufferedReader.readLine();
            logFileWriter.write("-> " + input + "\n");

            if (!(input.equals("uci"))) {
                logFileWriter.write("ERROR\n");
                logFileWriter.close();
                throw new IOException("unexpected input" + input);
            }

            // send uci info
            String idName = "id name OCaptain\n";
            String idAuthor = "id author Wenhao\n";
            String uciok = "uciok\n";
            System.out.print(idName);
            System.out.print(idAuthor);
            System.out.print(uciok);
            logFileWriter.write("<- " + idName);
            logFileWriter.write("<- " + idAuthor);
            logFileWriter.write("<-" + uciok);

            // init board
            Board chessBoard = new Board();
            chessBoard.newGame();

            // read isready
            input = bufferedReader.readLine();
            logFileWriter.write("-> " + input + "\n");

            if (!(input.equals("isready"))) {
                logFileWriter.write("ERROR\n");
                logFileWriter.close();
                throw new IOException("unexpected input " + input);
            }

            // send isready
            String ready = "ready\n";
            System.out.print(ready);
            logFileWriter.write(ready);

            ChessAnalyser chessAnalyser = null;
            Thread calculThread = null;
            Move best;
            boolean waitOutput = false;
            // loop
            while (true) {
                try {
                    long t = System.currentTimeMillis();
                    while (System.currentTimeMillis() - t < 3000 && !bufferedReader.ready()) {
                    }
                    if (!bufferedReader.ready()) {
                        if (waitOutput) {
                            best = chessAnalyser.getBestMove();
                            String bestmove = "bestmove " + best.getNotationString() + "\n";
                            System.out.print(bestmove);
                            logFileWriter.write(bestmove);
                            waitOutput = false;
                        }
                        continue;
                    }

                    input = bufferedReader.readLine();
                    logFileWriter.write("-> " + input + "\n");
                    bufferedReader = new BufferedReader(new InputStreamReader(System.in));

                    String tokens[] = input.split("[ ]+");
                    switch (tokens[0]) {
                        case "go":
                            chessAnalyser = new ChessAnalyser(chessBoard);
                            calculThread = new Thread(chessAnalyser);
                            calculThread.start();
                            waitOutput = true;
                            break;
                        case "stop":
                            if (chessAnalyser == null) {
                                logFileWriter.write("ERROR: unexpected \"stop\" before go");
                            }
                            if (!(waitOutput)) continue;
                            best = chessAnalyser.getBestMove();
                            String bestmove = "bestmove " + best.getNotationString() + "\n";
                            System.out.print(bestmove);
                            logFileWriter.write(bestmove);
                            waitOutput = false;
                            break;
                        case "position":
                            if (tokens[1].equals("startpos")) {
                                chessBoard.newGame();
                            } else {
                                logFileWriter.write("unexpected input" + tokens + "\n");
                                // System.exit(1);
                            }
                            if (tokens.length > 2 && !(tokens[2].equals("moves"))) {
                                logFileWriter.write("unexpected input" + tokens + "\n");
                                // System.exit(1);
                            }
                            for (int i = 3; i < tokens.length; i++) {
                                chessBoard = chessBoard.move(tokens[i]);
                            }
                            break;
                        case "quit":
                            logFileWriter.write("here is EOF\n");
                            break;
                        default:
                            continue;
                    }
                } catch (Exception e) {
                    logFileWriter.write(e.getStackTrace() + "\n");
                    logFileWriter.write("flag");
                    logFileWriter.write(e.getMessage() + "\n");
                    e.printStackTrace();
                    throw e;
                    // continue;
                }
            }

            // logFileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        } finally {
            try {
                logFileWriter.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }
}
