package Board;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.TreeSet;

import mUtil.Coord;

public class King extends Piece {

    public King(boolean isBlack) {
        super(isBlack);
    }

    @Override
    public TreeSet<Coord> getLegalMoves(Board currentBoard, Coord currentCoord) {
        // this one is tricky, because there is a rule binding with
        // the move of the opponent; king cannot suicide
        TreeSet<Coord> ret = new TreeSet<Coord>();
        ArrayList<Integer> xList = new ArrayList<>();
        ArrayList<Integer> yList = new ArrayList<>();

        // step 1: add

        xList.add(currentCoord.x);
        yList.add(currentCoord.y);

        if (currentCoord.x > 0) xList.add(currentCoord.x - 1);
        if (currentCoord.y > 0) yList.add(currentCoord.y - 1);
        if (currentCoord.x < 7) xList.add(currentCoord.x + 1);
        if (currentCoord.y < 7) yList.add(currentCoord.y + 1);

        for (int x: xList) {
            for (int y: yList) {
                ret.add(new Coord(x, y));
            }
        }
        ret.remove(currentCoord);

        // step 2: remove
        // TreeMap<Coord,Piece> enemies;
        TreeSet<Coord> illegaCoords = new TreeSet<Coord>();
        // if (this.isBlack()) {
        //     enemies = currentBoard.getWhitePieces();
        // }
        // else {
        //     enemies = currentBoard.getBlackPieces();
        // }

        // for (Map.Entry<Coord,Piece> e: enemies.entrySet()) {
        //     if (e.getValue().getShortName().equals("K")) {
        //         // if it's opponent king, then the calculus should be different
        //         // to prevent infinite loop
        //         // so we calculate manually the coords around the enemy king
        //         Coord oppoKingCoord = e.getKey();
        //         for (int x = oppoKingCoord.x - 1; x < oppoKingCoord.x + 1; x++) {
        //             for (int y = oppoKingCoord.y - 1; y < oppoKingCoord.y + 1; y++) {
        //                 if (x < 0 || x > 7 || y < 0 || y > 7) continue;
        //                 if (x == oppoKingCoord.x && y == oppoKingCoord.y) continue;
        //                 illegaCoords.add(new Coord(x, y));
        //             }
        //         }
        //         continue;
        //     }
        //     // getLegalMoves() result
        //     for (Coord m: e.getValue().getLegalMoves(currentBoard, e.getKey())) {
        //         if (ret.contains(m)) {
        //             illegaCoords.add(m);
        //         }
        //     }
        // }

        for (Coord c: ret) {
            if (Board.isUnderAttack(currentBoard, c, this.isBlack())) {
                illegaCoords.add(c);
            }
        }

        TreeMap<Coord,Piece> friends;
        if (this.isBlack()) {
            friends = currentBoard.getBlackPieces(currentCoord);
        }
        else {
            friends = currentBoard.getWhitePieces(currentCoord);
        }
        for (Coord c: friends.keySet()) {
            if (ret.contains(c)) {
                illegaCoords.add(c);
            }

        }

        ret.removeAll(illegaCoords);
        return ret;
    }

    @Override
    public String getShortName() {
        return "K";
    }
}
