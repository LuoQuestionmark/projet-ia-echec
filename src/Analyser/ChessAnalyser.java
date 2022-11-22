package Analyser;

import java.util.Map;

import Board.Board;
import Move.Move;

public class ChessAnalyser implements Runnable {

    private Board board;
    private volatile Node root;
    private volatile Node oldRoot;

    private final Object lock = new Object();

    private volatile boolean isFinish;
    private boolean isBlackSide = false;

    public int depthLimit = 8;

    private final boolean isHeuristic = true;

    public ChessAnalyser(Board b) {
        this.board = b;
        this.isFinish = false;
        this.isBlackSide = b.isBlackMove();
        this.root = new Node(board);
    }

    public void setDepthLimit(int depthLimit) {
        this.depthLimit = depthLimit;
    }

    public Node getRoot() {
        return root;
    }

    public Move getBestMove() {
        this.isFinish = true;
        // try {
        // Thread.sleep(100);
        // } catch (InterruptedException e) {
        // e.printStackTrace();
        // return null;
        // }

        synchronized (lock) {
            if (this.oldRoot != null) {
                this.root = this.oldRoot;
            }

            Move ret = null;
            double tmp = isBlackSide ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY;

            for (Map.Entry<Move, Node> e : root.moves.entrySet()) {
                double score = e.getValue().getScore();
                if (isBlackSide && tmp > score) {
                    tmp = score;
                    ret = e.getKey();
                }
                if (!(isBlackSide) && tmp < score) {
                    tmp = score;
                    ret = e.getKey();
                    // System.out.print(e.getKey());
                    // System.out.println(e.getValue().getScore());
                }
            }
            this.root = null;
            this.oldRoot = null;
            return ret;
        }
    }

    synchronized public double explore(int depth) {
        // the entry point of function which do the search,
        // which will call a different version of overload
        // result should be stored in the best move of root,
        // c.f. the function getBestMove()
        // considering that this function can alter
        // the value within the node, to prevent future
        // error, this is a 'synchronized' here.
        root.calScore(isHeuristic);
        synchronized (lock) {
            return this.explore(depth, root);
        }
    }

    synchronized public double explore(int depth, Node current) {
        // the actual recursive body of function "explore"
        if (this.isFinish || depth == 0) {
            return current.calScore(true);
        }

        double val;
        Board newBoard;
        Node child;
        synchronized (lock) {
            if (!(current.currentBoard.isBlackMove())) {
                // white
                val = Double.NEGATIVE_INFINITY;
                for (Move m : current.currentBoard.getAvailableMoves()) {
                    newBoard = current.currentBoard.move(m);
                    child = new Node(current, newBoard);
                    current.addNode(m, child);

                    double childVal = explore(depth - 1, child);
                    val = Math.max(val, childVal);
                    if (val > current.getBeta())
                        return val;
                }
                if (current.father != null) {
                    current.father.updateBeta(val);
                }
            } else {
                // black
                val = Double.POSITIVE_INFINITY;
                for (Move m : current.currentBoard.getAvailableMoves()) {
                    newBoard = current.currentBoard.move(m);
                    child = new Node(current, newBoard);
                    current.addNode(m, child);

                    double childVal = explore(depth - 1, child);
                    val = Math.min(val, childVal);
                    if (val < current.getAlpha())
                        return val;
                }
                if (current.father != null) {
                    current.father.updateAlpha(val);
                }
            }
        }

        current.setScore(val);

        return val;
    }

    @Override
    public void run() {
        int depth = 1;
        while (!isFinish && depth <= depthLimit) {
            explore(depth);
            depth += 1;
            System.out.format("info depth %d finish\n", depth);
            this.oldRoot = this.root;
            this.root = new Node(board);
        }
    }

    public void finish() {
        this.isFinish = true;
    }
}
