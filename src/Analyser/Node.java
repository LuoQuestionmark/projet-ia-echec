package Analyser;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import Board.Board;
import Move.Move;

public class Node {
    static int index = 0;
    private int thisIndex;

    public Node father = null;
    public Board currentBoard;
    public TreeMap<Move, Node> moves = new TreeMap<>();
    private double score, beta, alpha;
    
    public double getAlpha() {
        return alpha;
    }

    public double getBeta() {
        return beta;
    }

    public double getScore() {
        return score;
    }
    
    public double calScore() {
        this.score = currentBoard.evaluate();
        return this.score;
    }

    public double calScore(boolean isHeuristic) {
        this.score = currentBoard.evaluate(isHeuristic);
        return this.score;
    }

    public Node(Board b) {
        currentBoard = b;
        beta = Double.POSITIVE_INFINITY;
        alpha = Double.NEGATIVE_INFINITY;
        thisIndex = index++;
    }

    public Node(Node father, Board b) {
        currentBoard = b;
        beta = father.getBeta();
        alpha = father.getAlpha();
        thisIndex = index++;
    }

    public void updateBeta(double val) {
        this.beta = Math.min(beta, val);
        // this.score = Math.min(this.beta, this.score);
    }
    
    public void updateAlpha(double val) {
        this.alpha = Math.max(alpha, val);
        // this.score = Math.max(this.alpha, this.score);
    }

    public void addNode(Move m, Node n) {
        this.moves.put(m, n);
    }

    public void dumpGraph(String filepath) {
        ArrayList<String> lines = new ArrayList<>();
        lines.add("digraph g {");
        lines.add("graph [rankdir = \"LR\"];");
        lines.add(dumpString("root"));
        lines.add("}");

        Path file = Paths.get(filepath);
        try {
            Files.write(file, lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String dumpString(String from) {
        String str = String.format("\"node%d\" [\nlabel = \"<f0> " + from + " | <f1> %.2f | <f3> %.2f | <f4> %.2f\" shape = \"record\"];\n", thisIndex, this.score, this.beta, this.alpha);
        for (Map.Entry<Move, Node> e: this.moves.entrySet()) {
            Node n = e.getValue();
            str += String.format("\"node%d\"-> \"node%d\" [label = \"%s\"];\n", this.thisIndex, n.thisIndex, e.getKey());
            str += n.dumpString(e.getKey().toString().replace("->", "to"));
        }
        return str;
    }

    public void setScore(double val) {
        this.score = val;
    }
}
