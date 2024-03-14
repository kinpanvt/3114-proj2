public class LeafNode extends QuadTreeNode {
    private Point[] points;
    private int pointCount;
    private static final int CAPACITY = 4; 

    public LeafNode(Point initialPoint) {
        points = new Point[CAPACITY];

        points[0] = initialPoint;
        pointCount = 1;
    }

    public boolean addPoint(Point point) {
        // Reserve last spot to determine split
        if (pointCount < CAPACITY - 1) { 
            points[pointCount++] = point;
            return true;
        }
        return false;
    }

    public boolean shouldSplit() {
        // Split condition 
        return pointCount == CAPACITY - 1 && !allPointsSame();
    }

    private boolean allPointsSame() {
        for (int i = 1; i < pointCount; i++) {
            if (!points[i].equals(points[0])) {
                return false;
            }
        }
        return true;
    }

    public Point[] getPoints() {
        return points;
    }

    public int getPointCount() {
        return pointCount;
    }

    @Override
    public QuadTreeNode insert(Point point, Boundary boundary) {
        throw new UnsupportedOperationException("LeafNode insert operation should be handled by PRQuadTree.");
    }

    @Override
    public boolean search(Point point, Boundary boundary) {
        for (int i = 0; i < pointCount; i++) {
            if (points[i].equals(point)) {
                return true;
            }
        }
        return false;
    }

    public boolean removePoint(Point point) {
        for (int i = 0; i < pointCount; i++) {
            // Check if the current point matches the one to be removed
            if (points[i].equals(point)) {
                // Shift the rest of the points down one position to fill the gap
                for (int j = i; j < pointCount - 1; j++) {
                    points[j] = points[j + 1];
                }
                // Nullify the last element now duplicated
                points[pointCount - 1] = null;
                // Decrement pointCount because we removed a point
                pointCount--;
                return true; // Return true to indicate the point was found and removed
            }
        }
        return false; // Return false if the point was not found
    }
}
