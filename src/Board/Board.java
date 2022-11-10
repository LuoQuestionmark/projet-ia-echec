// This will be the chess board class.

package Board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import Move.*;
import mUtil.*;

public class Board implements Cloneable {

    private final double heuristicConst = 0.1;

    private HashMap<PieceType, ArrayList<Piece>> availablePieces = new HashMap<>();
    private TreeMap<Coord, Piece> board = new TreeMap<>();

    private boolean isBlackMove = false;
    
    public Coord enpassant = null; // null means no "enpassant"
    // otherwise is the last coord of the "enpassant"
    
    // these variables are used to track the "castling"
    // zero for black, one for white
    private boolean[] isKingMoved = {false, false};
    private boolean[] isLeftRockMoved = {false, false};
    private boolean[] isRightRockMoved = {false, false};
    
    public Board() {
        for (PieceType pt: PieceType.values()) {
            availablePieces.put(pt, new ArrayList<Piece>());
        }
        
        boolean bVals[] = {true, false}; // black, white
        for (boolean isBlack: bVals) {
            availablePieces.get(PieceType.Bishop).add(new Bishop(isBlack));
            availablePieces.get(PieceType.Knight).add(new Knight(isBlack));
            availablePieces.get(PieceType.Pawn).add(new Pawn(isBlack));
            availablePieces.get(PieceType.Rock).add(new Rock(isBlack));
            availablePieces.get(PieceType.King).add(new King(isBlack));
            availablePieces.get(PieceType.Queen).add(new Queen(isBlack));
        }
    }

    public boolean isBlackMove() {
        return isBlackMove;
    }
    
    public void addPiece(Coord c, Piece p) throws IllegalArgumentException {
        // throw illegalArgumentException is operation cannot be done
        if ((board.containsKey(c))) {
            throw new IllegalArgumentException("the Coord " + c + " is not valid");
        }
        board.put(c, p);
    }

    public TreeMap<Coord, Piece> getWhitePieces() {
        TreeMap<Coord, Piece> whitePieces = new TreeMap<>();
        for (HashMap.Entry<Coord, Piece> e: board.entrySet()) {
            if (e.getValue().isBlack()) continue;
            whitePieces.put(e.getKey(), e.getValue());
        }
        return whitePieces;
    }

    public TreeMap<Coord, Piece> getBlackPieces() {
        TreeMap<Coord, Piece> blackPieces = new TreeMap<>();
        for (HashMap.Entry<Coord, Piece> e: board.entrySet()) {
            if (e.getValue().isBlack()) {
                blackPieces.put(e.getKey(), e.getValue());
            }
        }
        return blackPieces;
    }

    public TreeMap<Coord, Piece> getWhitePieces(Coord excludeCoord) {
        TreeMap<Coord, Piece> whitePieces = new TreeMap<>();
        for (HashMap.Entry<Coord, Piece> e: board.entrySet()) {
            if (e.getValue().isBlack()) continue;
            if (e.getKey().equals(excludeCoord)) continue;
            whitePieces.put(e.getKey(), e.getValue());
        }
        return whitePieces;
    }

    public TreeMap<Coord, Piece> getBlackPieces(Coord excludeCoord) {
        TreeMap<Coord, Piece> blackPieces = new TreeMap<>();
        for (HashMap.Entry<Coord, Piece> e: board.entrySet()) {
            if (e.getKey().equals(excludeCoord)) continue;
            if (e.getValue().isBlack()) {
                blackPieces.put(e.getKey(), e.getValue());
            }
        }
        return blackPieces;
    }

    public void promotion(Coord c, PieceType pt) throws IllegalArgumentException {
        if (pt == PieceType.Pawn || pt == PieceType.King) {
            throw new IllegalArgumentException("promotion cannot be done");
        }
        if (this.board.get(c) == null || !(this.board.get(c) instanceof Pawn)) {
            throw new IllegalArgumentException("no pawn found");
        }
        boolean isBlack = this.board.get(c).isBlack();

        switch (pt) {
            case Bishop:
            case Queen:
            case Rock:
            case Knight:
                ArrayList<Piece> pList = availablePieces.get(pt);
                Piece p = pList.get(isBlack?0:1);
                this.board.put(c, p);
                break;
            case King:
            case Pawn:
            default:
                throw new IllegalArgumentException("promotion cannot be done");           
        }
    } 

    public void newGame() {
        // start a new game
        this.board.clear();
        this.isBlackMove = false;

        this.addPiece(new Coord(0, 0), availablePieces.get(PieceType.Rock).get(1));
        this.addPiece(new Coord(7, 0), availablePieces.get(PieceType.Rock).get(1));
        this.addPiece(new Coord(0, 7), availablePieces.get(PieceType.Rock).get(0));
        this.addPiece(new Coord(7, 7), availablePieces.get(PieceType.Rock).get(0));

        this.addPiece(new Coord(1, 0), availablePieces.get(PieceType.Knight).get(1));
        this.addPiece(new Coord(6, 0), availablePieces.get(PieceType.Knight).get(1));
        this.addPiece(new Coord(1, 7), availablePieces.get(PieceType.Knight).get(0));
        this.addPiece(new Coord(6, 7), availablePieces.get(PieceType.Knight).get(0));

        this.addPiece(new Coord(2, 0), availablePieces.get(PieceType.Bishop).get(1));
        this.addPiece(new Coord(5, 0), availablePieces.get(PieceType.Bishop).get(1));
        this.addPiece(new Coord(2, 7), availablePieces.get(PieceType.Bishop).get(0));
        this.addPiece(new Coord(5, 7), availablePieces.get(PieceType.Bishop).get(0));

        this.addPiece(new Coord(3, 0), availablePieces.get(PieceType.Queen).get(1));
        this.addPiece(new Coord(3, 7), availablePieces.get(PieceType.Queen).get(0));

        this.addPiece(new Coord(4, 0), availablePieces.get(PieceType.King).get(1));
        this.addPiece(new Coord(4, 7), availablePieces.get(PieceType.King).get(0));

        for (int i = 0; i < 8; i++) {
            this.addPiece(new Coord(i, 1), availablePieces.get(PieceType.Pawn).get(1));
            this.addPiece(new Coord(i, 6), availablePieces.get(PieceType.Pawn).get(0));
        }

    }

    public ArrayList<Move> getAvailableMoves() {
        ArrayList<Move> ret = new ArrayList<>();
        TreeMap<Coord, Piece> pieces;

        if (isBlackMove) {
            pieces = getBlackPieces();
        }
        else {
            pieces = getWhitePieces();
        }

        for (Map.Entry<Coord, Piece> e: pieces.entrySet()) {
            Coord src = e.getKey();
            for (Coord dst: e.getValue().getLegalMoves(this, src)) {
                // special case: promotion detection
                if (e.getValue().getShortName().equals("P")) {
                    if ((  e.getValue().isBlack()  && dst.y == 0)
                    ||  (!(e.getValue().isBlack()) && dst.y == 7)) {
                        for (PieceType pt: PieceType.values()) {
                            if (pt == PieceType.King || pt == PieceType.Pawn) continue;
                            ret.add(new MovePromotion(src, dst, pt));
                        }
                    }
                    else {
                        ret.add(new Move(src, dst));
                    }
                }
                else {
                    ret.add(new Move(src, dst));
                }
            }
        }

        // special case: en passant
        if (enpassant != null) {
            for (Map.Entry<Coord, Piece> e: pieces.entrySet()) {
                if (e.getValue().getShortName().equals("P") == false) continue;
                if (e.getKey().y != enpassant.y) continue;
                if (Math.abs(e.getKey().x - enpassant.x) != 1) continue;
                ret.add(new MoveEnpassnt(e.getKey(), new Coord(enpassant.x, enpassant.y + (e.getValue().isBlack()?-1:1))));
            }
        }

        // special case: castling
        ret.addAll(getMoveCastlings());

        return ret;
    }

    public Board move(Move m) throws IllegalArgumentException {
        // this time try to *move* a piece and get a new Board.
        Piece p;
        Coord enpassantCoord = null;

        // check exceptions
        if ((p = this.board.get(m.coordSrc)) == null) {
            throw new IllegalArgumentException("illegal move: no piece can be found at the position \"" + m.coordSrc +"\"");
        }
        if (p.isBlack() ^ this.isBlackMove) {
            throw new IllegalArgumentException("illegal move: not the right color/turn");
        }
        if (this.board.get(m.coordDst) != null) {
            if (p.isBlack() == this.board.get(m.coordDst).isBlack()) {
                throw new IllegalArgumentException("illegal move: cannot move to a place which is occupied by a piece of same color");
            }
        }

        // check enpassant
        if (p.getShortName().equals("P")) {
            if (m.coordDst.y == 3 && !(p.isBlack())
            || (m.coordDst.y == 4 &&   p.isBlack())) {
                enpassantCoord = m.coordDst;
            }
        }        

        // now the action
        try {
            Board ret = this.clone();  // copy the object
            ret.isBlackMove = !(ret.isBlackMove); // inverse the turn
            ret.enpassant = enpassantCoord;

            if (m.coordSrc.equals(new Coord(0, 0))) {
                ret.isLeftRockMoved[1] = true;
            }
            else if (m.coordSrc.equals(new Coord(0, 7))) {
                ret.isRightRockMoved[1] = true;
            }
            else if (m.coordSrc.equals(new Coord(0, 7))) {
                ret.isLeftRockMoved[0] = true;
            }
            else if (m.coordSrc.equals(new Coord(7, 7))) {
                ret.isRightRockMoved[0] = true;
            }
            else if (m.coordSrc.equals(new Coord(0, 5))) {
                ret.isKingMoved[1] = true;
            }
            else if (m.coordSrc.equals(new Coord(7, 5))) {
                ret.isKingMoved[0] = true;
            }

            if (m instanceof MoveEnpassnt) {
                ret.board.remove(m.coordSrc);
                ret.board.remove(this.enpassant);
                ret.board.put(m.coordDst, p);
            }
            else if (m instanceof MovePromotion) {
                PieceType newType = ((MovePromotion)m).promotion;
                Piece newPiece = this.availablePieces.get(newType).get(p.isBlack()?0:1);
                ret.board.remove(m.coordSrc);
                ret.board.remove(m.coordDst);
                ret.board.put(m.coordDst, newPiece);
            }
            else if (m instanceof MoveCastling) {
                MoveCastling mc = ((MoveCastling)m);
                Piece rockPiece = this.availablePieces.get(PieceType.Rock).get(p.isBlack()?0:1);
                ret.board.remove(m.coordSrc);
                ret.board.put(m.coordDst, p);
                ret.board.remove(mc.coordSrc);
                ret.board.put(mc.coordDst2, rockPiece);
            }
            else {
                // move the piece
                ret.board.remove(m.coordSrc);
                ret.board.remove(m.coordDst);
                ret.board.put(m.coordDst, p);
            }

            return ret;
        }
        catch (CloneNotSupportedException e) {
            throw new IllegalArgumentException("this board is not initialized properly with a board, thus cannot be cloned");
        }
    }

    public static boolean isUnderAttack(Board b, Coord c, boolean coordOnBlackSide) {
        TreeMap<Coord, Piece> enemies;
        if (coordOnBlackSide) {
            enemies = b.getWhitePieces();
        }
        else {
            enemies = b.getBlackPieces();
        }

        for (Map.Entry<Coord,Piece> e: enemies.entrySet()) {
            if (e.getValue().getShortName().equals("K")) {
                // if it's opponent king, then the calculus should be different
                // to prevent infinite loop
                // so we calculate manually the coords around the enemy king
                Coord oppoKingCoord = e.getKey();
                for (int x = oppoKingCoord.x - 1; x < oppoKingCoord.x + 1; x++) {
                    for (int y = oppoKingCoord.y - 1; y < oppoKingCoord.y + 1; y++) {
                        if (x < 0 || x > 7 || y < 0 || y > 7) continue;
                        if (x == oppoKingCoord.x && y == oppoKingCoord.y) continue;
                        if (c.x == x && c.y == y) return true;
                    }
                }
                continue;
            }
            for (Coord m: e.getValue().getLegalMoves(b, e.getKey())) {
                if (c.equals(m)) {
                    return true;
                }
            }
        }

        return false;
    }

    private ArrayList<MoveCastling> getMoveCastlings() {
        // function return all possible castlings or empty list
        ArrayList<MoveCastling> ret = new ArrayList<>();
        int index = isBlackMove?0:1;
        int row = isBlackMove?7:0;
        Piece p;
        if (isKingMoved[index]) return ret;
        if ((p = board.get(new Coord(4, row))) == null) return ret;
        if (p.getShortName().equals("K") == false) return ret;

        boolean canLeftCastling = true;
        boolean canRightCastling = true;
        if (!(isLeftRockMoved[index])) {
            for (int i = 1; i < 4; i++) {
                if (board.containsKey(new Coord(i, row))) {
                    canLeftCastling = false;
                    break;
                }
                if (Board.isUnderAttack(this, new Coord(i, row), isBlackMove)) {
                    canLeftCastling = false;
                    break;
                }
            }

            if (canLeftCastling) {
                ret.add(new MoveCastling(new Coord(4, row),
                                         new Coord(2, row),
                                         new Coord(0, row),
                                         new Coord(3, row)));
            }
        }

        if (!(isRightRockMoved[index])) {
            for (int i = 4; i < 7; i++) {
                if (board.containsKey(new Coord(i, row))) {
                    canRightCastling = false;
                    break;
                }
                if (Board.isUnderAttack(this, new Coord(i, row), isBlackMove)) {
                    canRightCastling = false;
                    break;
                }
            }

            if (canRightCastling) {
                ret.add(new MoveCastling(new Coord(4, row),
                                         new Coord(6, row),
                                         new Coord(7, row),
                                         new Coord(5, row)));
            }
        }

        return ret;
    }

    public double evaluate() throws IllegalArgumentException {
        // this function should give a score to the actual board, see the README for more details.

        double score = 0;
        boolean isBlackKingAlive = false;
        boolean isWhiteKingAlive = false;

        for (Piece p: this.board.values()) {
            // this should include every and all pieces
            int positivity;
            double pieceScore;

            if (p.isBlack()) {
                positivity = -1;
            }
            else {
                positivity = 1;
            }

            switch (p.getShortName()) {
                case "P":
                    pieceScore = 1;
                    break;

                case "N":
                case "B":
                    pieceScore = 3;
                    break;
                
                case "R":
                    pieceScore = 5;
                    break;

                case "Q":
                    pieceScore = 9;
                    break;

                case "K":
                    // the test is done elsewhere
                    pieceScore = 0;
                    if (positivity == 1) {
                        isWhiteKingAlive = true;
                    }
                    else {
                        isBlackKingAlive = true;
                    }
                    break;
            
                default:
                    throw new IllegalArgumentException("Error when analyzing the board: un recognized piece shortname: " + p.getShortName());
            }

            score += positivity * pieceScore;
        }

        if (!(isBlackKingAlive || isWhiteKingAlive)) {
            // illegal situation
            // still wondering if it could happen
            return Double.NaN;
        }
        else if (!(isBlackKingAlive)) {
            return Double.POSITIVE_INFINITY;
        }
        else if (!(isWhiteKingAlive)) {
            return Double.NEGATIVE_INFINITY;
        }

        return score;
    }

    public double evaluate(boolean addHeuristic) throws IllegalArgumentException {
        double ret = evaluate();
        if (!(addHeuristic)) return ret;

        // now do the job
        for (Coord c: this.getBlackPieces().keySet()) {
            ret += Math.abs(c.y - 2) * this.heuristicConst;
        }
        for (Coord c: this.getWhitePieces().keySet()) {
            ret -= Math.abs(c.y - 5) * this.heuristicConst;
        }

        return ret;
    }

    @Override
    public String toString() {
        String ret = "";

        for (int y = 7; y >= 0; y--) {
            ret += "|";
            for (int x = 0; x < 8; x++) {
                String pieceNote = "  ";
                for (Coord c: board.keySet()) {
                    if (c.equals(new Coord(x, y))) {
                        pieceNote = board.get(c).getShortName() + (board.get(c).isBlack()?"B":"W");
                        break;
                    }
                }
                ret += pieceNote + "|";
            }
            ret += "\n";
        }

        ret += String.format("evaluation: %.2f", this.evaluate(true));

        return ret;
    }

    @Override
    public Board clone() throws CloneNotSupportedException {
        if (this.board == null) {
            throw new CloneNotSupportedException("board not totally initialized: no board initialized");
        }

        Board ret = new Board();
        ret.availablePieces = this.availablePieces;
        ret.board = new TreeMap<Coord, Piece>(this.board);
        ret.isBlackMove = this.isBlackMove;

        ret.isKingMoved = this.isKingMoved;
        ret.isLeftRockMoved = this.isLeftRockMoved;
        ret.isRightRockMoved = this.isRightRockMoved;

        return ret;
    }
}
