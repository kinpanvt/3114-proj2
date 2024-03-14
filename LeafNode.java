import java.util.ArrayList;

public class LeafNode extends QuadTreeNode {
    private static final int MAX_POINTS = 1;
    private ArrayList<Point> points = new ArrayList<>();

    /**
     * Gets all points stored in this leaf node.
     * 
     * @return A list of all points.
     */
    public ArrayList<Point> getPoints() {
        return new ArrayList<>(points);
    }

    @Override
    public QuadTreeNode insert(Point point, int x, int y, int size) {
        for (Point existingPoint : points) {
            if (existingPoint.equals(point)) {
                return this; // Point already exists, do nothing.
            }
        }
        points.add(point);

        if (points.size() > MAX_POINTS) {
            InternalNode internalNode = new InternalNode();
            for (Point existingPoint : points) {
                // Reinsert existing points into the new internal node.
                internalNode = (InternalNode) internalNode.insert(existingPoint, x, y, size);
            }
            return internalNode; // Return the new internal node after splitting.
        }
        return this; // Return this leaf node if no splitting is needed.
    }

    @Override
    public boolean remove(Point point, int x, int y, int size) {
        if (!points.contains(point)) {
            return false;
        }
        return points.remove(point);
    }

    @Override
    public ArrayList<Point> search(String name) {
        ArrayList<Point> matchingPoints = new ArrayList<>();
        for (Point p : points) {
            if (p.getName().equals(name)) {
                matchingPoints.add(p);
            }
        }
        return matchingPoints;
    }

    @Override
    public ArrayList<Point> regionSearch(int queryX, int queryY, int width, int height, int nodeX, int nodeY,
            int nodeSize) {
        ArrayList<Point> foundPoints = new ArrayList<>();
        for (Point p : points) {
            if (p.getX() >= queryX && p.getX() <= queryX + width && p.getY() >= queryY && p.getY() <= queryY + height) {
                foundPoints.add(p);
            }
        }
        return foundPoints;
    }

    @Override
    public void dump(int level) {
        // Implementation needed for dumping the tree structure
    }

    public Point getPoint(int x, int y) {
        for (Point p : points) {
            if (p.getX() == x && p.getY() == y) {
                return p;
            }
        }
        return null;
    }
}
