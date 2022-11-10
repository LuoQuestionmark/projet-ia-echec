package Analyser;

import java.util.Map;

import Board.Board;
import Move.Move;

public class ChessAnalyser implements Runnable {

    private Board board;
    private volatile Node root;
    private volatile Node oldRoot;
    
    private volatile boolean isFinish;
    private boolean isBlackSide;
    
    public int depthLimit = 2; 
    
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
        //     Thread.sleep(100);
        // } catch (InterruptedException e) {
        //     e.printStackTrace();
        //     return null;
        // }

        if (this.oldRoot != null) {
            this.root = this.oldRoot;
        }

        Move ret = null;
        double tmp = isBlackSide?Double.POSITIVE_INFINITY:Double.NEGATIVE_INFINITY;

        for (Map.Entry<Move, Node> e: root.moves.entrySet()) {
            double score = e.getValue().getScore();
            if (isBlackSide && tmp > score) {
                tmp = score;
                ret = e.getKey();
            }
            if (!(isBlackSide) && tmp < score) {
                tmp = score;
                ret = e.getKey();
            }
        }
        
        return ret;
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
        return this.explore(depth, root);
    }

    synchronized public double explore(int depth, Node father) {
        // the actual recursive body of function "explore"
        if (this.isFinish || depth == 0) {
            return father.calScore(true);
        }

        double val;
        Board nb;
        Node nn;
        if (!(father.currentBoard.isBlackMove())) {
            val = Double.NEGATIVE_INFINITY;
            for (Move m: father.currentBoard.getAvailableMoves()) {
                nb = father.currentBoard.move(m);
                nn = new Node(father, nb);
                father.addNode(m, nn);

                double childVal = explore(depth - 1, nn);
                val = Math.max(val, childVal);
                if (val > father.getMin()) break;
                father.updateMax(val);
            }
        }
        else {
            val = Double.POSITIVE_INFINITY;
            for (Move m: father.currentBoard.getAvailableMoves()) {
                nb = father.currentBoard.move(m);
                nn = new Node(father, nb);
                father.addNode(m, nn);

                double childVal = explore(depth - 1, nn);
                val = Math.min(val, childVal);
                if (val < father.getMax()) break;
                father.updateMin(val);
            }
        }
        
        father.setScore(val);

        return val;
    }    

    @Override
    public void run() {
        int depth = 1;
        while (!isFinish && depth <= depthLimit) {
            explore(depth);
            depth += 1;
            this.oldRoot = this.root;
        }
    }
}
