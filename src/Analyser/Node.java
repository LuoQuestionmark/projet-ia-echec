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

    public Board currentBoard;
    public TreeMap<Move, Node> moves = new TreeMap<>();
    private double score, min, max;
    
    public double getMax() {
        return max;
    }

    public double getMin() {
        return min;
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
        min = Double.POSITIVE_INFINITY;
        max = Double.NEGATIVE_INFINITY;
        thisIndex = index++;
    }

    public Node(Node father, Board b) {
        currentBoard = b;
        min = father.getMin();
        max = father.getMax();
        thisIndex = index++;
    }

    public void updateMin(double val) {
        this.min = Math.min(min, val);
        this.score = Math.min(this.min, this.score);
    }

    public void updateMax(double val) {
        this.max = Math.max(max, val);
        this.score = Math.max(this.max, this.score);
    }

    public void addNode(Move m, Node n) {
        this.moves.put(m, n);
    }

    public void dumpGraph(String filepath) {
        ArrayList<String> lines = new ArrayList<>();
        lines.add("digraph g {");
        lines.add("graph [rankdir = \"LR\"];");
        lines.add(dumpString());
        lines.add("}");

        Path file = Paths.get(filepath);
        try {
            Files.write(file, lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String dumpString() {
        String str = String.format("\"node%d\" [\nlabel = \"<f0> %d | <f1> %.2f | <f3> %.2f %.2f\" shape = \"record\"];\n", thisIndex, thisIndex, this.score, this.min, this.max);
        for (Map.Entry<Move, Node> e: this.moves.entrySet()) {
            Node n = e.getValue();
            str += String.format("\"node%d\"-> \"node%d\" [label = \"%s\"];\n", this.thisIndex, n.thisIndex, e.getKey());
            str += n.dumpString();
        }
        return str;
    }
}
