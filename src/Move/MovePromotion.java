package Move;

import Board.PieceType;
import mUtil.Coord;

public class MovePromotion extends Move {
    public PieceType promotion;

    public MovePromotion(Coord src, Coord dst, PieceType pt) throws IllegalArgumentException {
        super(src, dst);
        switch (pt) {
            case Bishop:
            case Knight:
            case Queen:
            case Rock:
                promotion = pt;
                break;
            case Pawn:
            case King:
            default:
                throw new IllegalArgumentException("illegal promotion type");
        }
        this.promotion = pt;
    }
    
    @Override
    public String toString() throws IllegalAccessError {
        String pt;
        switch (promotion) {
            case Bishop:
                pt = "B";
                break;
            case Knight:
                pt = "N";
                break;
            case Queen:
                pt = "Q";
                break;
            case Rock:
                pt = "R";
                break;
            case King:
            case Pawn:
            default:
                throw new IllegalAccessError("this branch shouldn't be able to accessed!");
        }
        return super.toString() + "promotion: " + pt;
    }

    @Override
    public String getNotationString() {
        String pt;
        switch (promotion) {
            case Bishop:
                pt = "b";
                break;
            case Knight:
                pt = "n";
                break;
            case Queen:
                pt = "q";
                break;
            case Rock:
                pt = "r";
                break;
            case King:
            case Pawn:
            default:
                throw new IllegalAccessError("this branch shouldn't be able to accessed! something wrong with the piece type, check if the object is not initialized correctly");
        }
        return super.getNotationString() + pt;
    }
}
