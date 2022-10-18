package mUtil;

public class Coord {
    int x, y;
    public Coord(int x, int y) throws IllegalArgumentException {
        if (x < 0 || x > 7) {
            throw new IllegalArgumentException("illegal value of x");
        }
        if (y < 0 || y > 7) {
            throw new IllegalArgumentException("illegal value of y");
        }
        this.x = x;
        this.y = y;
    }
}
