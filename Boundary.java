public class Boundary {
    private int x, y, width, height;

    public Boundary(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean contains(Point point) {
        return point.getX() >= x && point.getX() < x + width &&
               point.getY() >= y && point.getY() < y + height;
    }

    public Boundary getSubBoundary(Quadrant quadrant) {
        int subWidth = width / 2;
        int subHeight = height / 2;
        switch (quadrant) {
            case NW:
                return new Boundary(x, y, subWidth, subHeight);
            case NE:
                return new Boundary(x + subWidth, y, subWidth, subHeight);
            case SW:
                return new Boundary(x, y + subHeight, subWidth, subHeight);
            case SE:
                return new Boundary(x + subWidth, y + subHeight, subWidth, subHeight);
            default:
                throw new IllegalArgumentException("Unknown Quadrant");
        }
    }

    public Quadrant getQuadrant(Point point) {
        boolean isNorth = point.getY() < y + height / 2;
        boolean isWest = point.getX() < x + width / 2;

        if (isNorth && isWest) return Quadrant.NW;
        if (isNorth && !isWest) return Quadrant.NE;
        if (!isNorth && isWest) return Quadrant.SW;
        if (!isNorth && !isWest) return Quadrant.SE;

        // Default case should not occur
        throw new IllegalStateException("Point does not fit within any quadrant");
    }

    public boolean intersects(Boundary other) {
        // Check if one rectangle is to the left of the other
        if (this.x + this.width <= other.x || other.x + other.width <= this.x) {
            return false;
        }
        // Check if one rectangle is above the other
        if (this.y + this.height <= other.y || other.y + other.height <= this.y) {
            return false;
        }
        return true; // Overlapping occurs
    }
}
