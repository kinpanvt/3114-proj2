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

    /**
     * Inserts a point into the QuadTree.
     * If the node is full, it creates a new InternalNode and reinserts all points.
     * 
     * @param point The point to insert.
     * @param x     The x-coordinate of the node.
     * @param y     The y-coordinate of the node.
     * @param size  The size of the node.
     * @return The node after insertion.
     */
    @Override
    public QuadTreeNode insert(Point point, int x, int y, int size) {
        for (Point existingPoint : points) {
            if (existingPoint.equals(point)) {
                // If the point already exists, just return this node.
                return this;
            }
        }

        if (points.size() < MAX_POINTS || (points.size() == MAX_POINTS && points.contains(point))) {
            points.add(point);
            return this; // Return this leaf node if no splitting is needed.
        }

        // If the node is full, create a new internal node and reinsert all points.
        InternalNode internalNode = new InternalNode();
        for (Point existingPoint : points) {
            // Reinsert existing points into the new internal node.
            internalNode = (InternalNode) internalNode.insert(existingPoint, x, y, size);
        }
        // Add the new point to the list after reinserting.
        internalNode = (InternalNode) internalNode.insert(point, x, y, size);
        return internalNode; // Return the new internal node after splitting.
    }

    /**
     * Removes a point from the QuadTree.
     * 
     * @param point The point to remove.
     * @param x     The x-coordinate of the node.
     * @param y     The y-coordinate of the node.
     * @param size  The size of the node.
     * @return true if the point was removed, false otherwise.
     */
    @Override
    public boolean remove(Point point, int x, int y, int size) {
        if (!points.contains(point)) {
            return false;
        }
        return points.remove(point);
    }

    /**
     * Searches for a point in the QuadTree by name.
     * 
     * @param name The name of the point to search for.
     * @return A list of points that match the given name.
     */
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

    /**
     * Searches for points in a specific region of the QuadTree.
     * 
     * @param queryX   The x-coordinate of the query region.
     * @param queryY   The y-coordinate of the query region.
     * @param width    The width of the query region.
     * @param height   The height of the query region.
     * @param nodeX    The x-coordinate of the node.
     * @param nodeY    The y-coordinate of the node.
     * @param nodeSize The size of the node.
     * @return A list of points that are within the specified region.
     */
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

    /**
     * Dumps the data of the node.
     * 
     * @param level The level of the node in the QuadTree.
     */
    @Override
    public void dump(int level) {
        // Implementation needed for dumping the tree structure
    }

    /**
     * Returns the point at the specified coordinates.
     * 
     * @param x The x-coordinate of the point.
     * @param y The y-coordinate of the point.
     * @return The point at the specified coordinates, or null if no such point
     *         exists.
     */
    public Point getPoint(int x, int y) {
        for (Point p : points) {
            if (p.getX() == x && p.getY() == y) {
                return p;
            }
        }
        return null;
    }
}
