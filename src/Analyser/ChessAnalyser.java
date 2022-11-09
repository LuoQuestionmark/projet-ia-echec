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
    
    private final int depthLimit = 3; 
    private final boolean isHeuristic = true;
    
    public ChessAnalyser(Board b) {
        this.board = b;
        this.isFinish = false;
        this.isBlackSide = b.isBlackMove();
        this.root = new Node(board);
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

    synchronized public void explore(int depth) {
        // the entry point of function which do the search,
        // which will call a different version of overload
        // result should be stored in the best move of root,
        // c.f. the function getBestMove()
        // considering that this function can alter
        // the value within the node, to prevent future
        // error, this is a 'synchronized' here.
        root.calScore(isHeuristic);
        this.explore(depth, root);
    }

    synchronized public void explore(int depth, Node father) {
        // the actual recursive body of function "explore"
        if (this.isFinish) return;

        Board b = father.currentBoard;

        for (Move m: b.getAvailableMoves()) {
            // simulate each possibility
            Board nb = b.move(m);
            Node nn = new Node(father, nb);

            double score = nn.calScore(isHeuristic);
            father.addNode(m, nn);

            // alpha-beta cut
            if (isBlackSide && father.getMin() < score) {
                return;
            }
            if (!(isBlackSide) && father.getMax() > score) {
                return;
            }

            // if is not cut then do the recursion
            if (depth > 0) {
                explore(depth - 1, nn);
            }

            // update the value of alpha and beta, this can only be done when the children are visited
            if (b.isBlackMove()) {
                father.updateMax(nn.getScore());
            }
            else {
                father.updateMin(nn.getScore());
            }
        }
    }    

    @Override
    public void run() {
        int depth = 2;
        while (!isFinish && depth <= depthLimit) {
            explore(depth);
            depth += 1;
            this.oldRoot = this.root;
        }
    }
}
