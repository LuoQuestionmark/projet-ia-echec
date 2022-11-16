package mUtil;

public class Coord implements Comparable<Coord> {
    public int x, y;
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

    public String toString() {
        return String.format("(%d, %d)", x, y);
    }

    public String getNotationString() {
        // this return the standard notation, for example e2
        String str = "";
        str += (char)(x + 97);
        str += String.valueOf(y + 1);
        return str;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Coord)) return false;
        Coord c = (Coord)o;
        if (c.x == this.x && c.y == this.y) return true;
        return false;
    }

    @Override
    public int compareTo(Coord o) {
        return (this.x - o.x) * 8 + this.y - o.y;
    }
}
