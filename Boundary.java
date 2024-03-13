public class Boundary {
    private int x, y, width, height;

    public enum Quadrant {
        NW, NE, SW, SE
    }
    
    public Boundary(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    // Check if a point is within this boundary
    public boolean contains(Point point) {
        return point.getX() >= x && point.getX() < x + width &&
               point.getY() >= y && point.getY() < y + height;
    }

    // Subdivide the boundary into quadrants
    public Boundary getSubBoundary(PRQuadTree.Quadrant quadrant) {
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
                return null;
        }
    }

    public PRQuadTree.Quadrant getQuadrant(Point point) {
        boolean isNorth = point.getY() < y + height / 2;
        boolean isWest = point.getX() < x + width / 2;

        if (isNorth && isWest) return PRQuadTree.Quadrant.NW;
        if (isNorth) return PRQuadTree.Quadrant.NE;
        if (isWest) return PRQuadTree.Quadrant.SW;
        return PRQuadTree.Quadrant.SE;
    }
}