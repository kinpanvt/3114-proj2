public class LeafNode extends QuadTreeNode {
    private Point[] points;
    private int pointCount;
    static final int CAPACITY = 4; 

    public LeafNode(Point initialPoint) {
        points = new Point[CAPACITY];

        points[0] = initialPoint;
        pointCount = 1;
    }
    
    public LeafNode(Point[] points, int numberOfPoints) {
        this.points = new Point[numberOfPoints];
        this.pointCount = numberOfPoints;
        System.arraycopy(points, 0, this.points, 0, numberOfPoints);
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

    public boolean allPointsSame() {
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
        if (pointCount < CAPACITY) { 
            points[pointCount++] = point;
            return this; // No need to split yet.
        } else {
            // Need to split as adding this point exceeds CAPACITY.
            if (shouldSplit()) {
                return splitNodeAndInsert(point, boundary);
            } else {
                // Even though we're at capacity, all points including the new one have the same coordinates.
                points[pointCount++] = point; // This requires expanding the array or handling this exception.
                return this;
            }
        }
    }
    
    private QuadTreeNode splitNodeAndInsert(Point point, Boundary boundary) {
        InternalNode parent = new InternalNode();
        // Reinsert existing points into the new structure, which will create new leaf nodes as necessary.
        for (Point existingPoint : this.points) {
            parent.insert(existingPoint, boundary);
        }
        parent.insert(point, boundary); // Insert the new point as well.
        return parent;
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
