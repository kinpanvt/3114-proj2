import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Leaf node class
 * 
 * @author kinjalpandey, architg03
 * @version 02/25/24
 */
public class LeafNode extends QuadTreeNode {
    private static final int MAX_POINTS = 1; // Assuming a leaf node can contain
                                             // up to 3 points before splitting
    private List<Point> points = new ArrayList<>();

    /**
     * insert
     * 
     * @param point
     *            point
     * @param x
     *            X coord
     * @param y
     *            Y coord
     * @param size
     *            size
     * @return QuadTreeNode
     */
    @Override
    public QuadTreeNode insert(Point point, int x, int y, int size) {
        // Split if this node exceeds MAX_POINTS and the new point is in a
        // different location.
        if (points.size() >= MAX_POINTS && points.stream().anyMatch(p -> p
            .getX() != point.getX() || p.getY() != point.getY())) {
            InternalNode internalNode = new InternalNode();
            // Re-insert the existing points into the internal node
            for (Point existingPoint : points) {
                internalNode.insert(existingPoint, x, y, size);
            }
            // Clear the points since they are now in the internal node
            points.clear();
            // Insert the new point into the internal node
            return internalNode.insert(point, x, y, size);
        }
        else {
            // Otherwise, add the point to this leaf node.
            points.add(point);
            return this;
        }
    }


    /**
     * Gets all points stored in this leaf node.
     * 
     * @return A list of all points.
     */
    public List<Point> getPoints() {
        return new ArrayList<>(points);
    }


    @Override
    public boolean remove(Point point, int x, int y, int size) {
        return points.remove(point);
    }


    @Override
    public List<Point> search(String name) {
        return points.stream().filter(p -> p.getName().equals(name)).collect(
            Collectors.toList());
    }


    @Override
    public List<Point> regionSearch(
        int queryX,
        int queryY,
        int width,
        int height,
        int nodeX,
        int nodeY,
        int nodeSize) {
        return points.stream().filter(p -> p.getX() >= queryX && p
            .getX() <= queryX + width && p.getY() >= queryY && p
                .getY() <= queryY + height).collect(Collectors.toList());
    }


    @Override
    public void dump(int level) {
        // TODO Auto-generated method stub

    }


    /**
     * Gets the point with the specified coordinates.
     * 
     * @param x
     *            x-coord
     * @param y
     *            y-coord
     * @return Point with the specified coordinates, or null if no such point
     *         exists.
     */
    public Point getPoint(int x, int y) {
        for (Point p : points) {
            if (p.getX() == x && p.getY() == y) {
                return p;
            }
        }
        return null; // No point found with the specified coordinates.
    }

}
