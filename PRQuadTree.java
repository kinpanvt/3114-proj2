import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a Point Region Quadtree for managing a collection of points
 * in a two-dimensional space. The PR Quadtree divides the space into four
 * quadrants recursively until each leaf node contains at most one point.
 * 
 * @author kinjalpandey, architg03
 * @version 02/25/24
 */
public class PRQuadTree {
    /**
     * The root node of the PR Quadtree, which could be an internal node or a
     * leaf node.
     */
    private QuadTreeNode root;

    /**
     * The size of the space managed by the PR Quadtree.
     * This is used to define the initial boundary of the quadtree.
     */
    private final int size;

    /**
     * Constructs a PRQuadTree with a specified size for the 2D space boundary.
     *
     * @param size
     *            The length and width of the square boundary of the quadtree.
     *            Assumes a square space.
     */
    public PRQuadTree(int size) {
        this.root = FlyweightNode.getInstance(); // Initializes with a flyweight
                                                 // node to represent empty
                                                 // space.
        this.size = size;
    }


    /**
     * Inserts a point into the PR Quadtree. If the point's location is already
     * occupied, the tree will adjust accordingly to accommodate the new point.
     *
     * @param point
     *            The point to insert into the quadtree.
     */
    public void insert(Point point) {
        root = root.insert(point, size, size, size);
    }


    /**
     * Removes a point from the PR Quadtree. If the point does not exist, no
     * action is taken.
     *
     * @param point
     *            The point to remove from the quadtree.
     * @return True if the point was successfully removed, false otherwise.
     */
    public boolean remove(Point point) {
        return root.remove(point, 0, 0, size);
    }


    /**
     * Searches for all points within a specified rectangular region.
     *
     * @param x
     *            The x-coordinate of the upper-left corner of the search
     *            region.
     * @param y
     *            The y-coordinate of the upper-left corner of the search
     *            region.
     * @param width
     *            The width of the search region.
     * @param height
     *            The height of the search region.
     * @return A list of points found within the specified region.
     */
    public List<Point> regionSearch(int x, int y, int width, int height) {
        return root.regionSearch(x, y, width, height, 0, 0, size);
    }


    /**
     * Searches for a point by its name within the PR Quadtree.
     *
     * @param name
     *            The name of the point to search for.
     * @return A list of points matching the given name. Note that names are not
     *         guaranteed to be unique, hence the return type is a list.
     */
    public List<Point> searchByName(String name) {
        return root.search(name);
    }


    /**
     * searchByCoordinates
     * 
     * @param x
     *            coord
     * @param y
     *            coord
     * @return Point
     */
    public Point searchByCoordinates(int x, int y) {
        return searchByCoordinates(root, x, y, 0, 0, size);
    }


    private Point searchByCoordinates(
        QuadTreeNode node,
        int x,
        int y,
        int startX,
        int startY,
        int regionSize) {
        if (node == null || node instanceof FlyweightNode) {
            return null; // Node is empty or doesn't contain any points.
        }
        else if (node instanceof LeafNode) {
            // Assuming LeafNode has a method to return its point or null if the
            // point doesn't match the coordinates
            Point point = ((LeafNode)node).getPoint(x, y);
            if (point != null && point.getX() == x && point.getY() == y) {
                return point;
            }
            return null;
        }
        else if (node instanceof InternalNode) {
            int halfSize = regionSize / 2;
            int midX = startX + halfSize;
            int midY = startY + halfSize;

            // Determine the correct quadrant
            if (x < midX) {
                return y < midY
                    ? searchByCoordinates(((InternalNode)node).getNw(), x, y,
                        startX, startY, halfSize)
                    : searchByCoordinates(((InternalNode)node).getSw(), x, y,
                        startX, midY, halfSize);
            }
            else {
                return y < midY
                    ? searchByCoordinates(((InternalNode)node).getNe(), x, y,
                        midX, startY, halfSize)
                    : searchByCoordinates(((InternalNode)node).getSe(), x, y,
                        midX, midY, halfSize);
            }
        }
        return null; // Fallback case, should not be reached.
    }


    /**
     * findDuplicates()
     * 
     * @return List<Point> list
     */
    public List<Point> findDuplicates() {
        List<Point> allPoints = new ArrayList<>();
        collectPoints(root, allPoints); // Collect all points from the quadtree
        return identifyDuplicates(allPoints); // Identify duplicates among
                                              // collected points
    }


    private void collectPoints(QuadTreeNode node, List<Point> allPoints) {
        if (node == null || node instanceof FlyweightNode) {
            // No points to collect in this path
            return;
        }
        else if (node instanceof LeafNode) {
            // Add all points from the leaf node
            allPoints.addAll(((LeafNode) node).getPoints());
        }
        else if (node instanceof InternalNode) {
            // Recursively collect points from all children
            collectPoints(((InternalNode) node).getNw(), allPoints);
            collectPoints(((InternalNode) node).getNe(), allPoints);
            collectPoints(((InternalNode) node).getSw(), allPoints);
            collectPoints(((InternalNode) node).getSe(), allPoints);
        }
    }


    private List<Point> identifyDuplicates(List<Point> allPoints) {
        Map<String, Point> pointMap = new HashMap<>();
        List<Point> duplicates = new ArrayList<>();

        for (Point point : allPoints) {
            String coordKey = point.getX() + "," + point.getY();
            if (pointMap.containsKey(coordKey)) {
                // If it's the first duplicate, add the original too
                if (!duplicates.contains(pointMap.get(coordKey))) {
                    duplicates.add(pointMap.get(coordKey));
                }
                duplicates.add(point); // Add the current duplicate
            }
            else {
                pointMap.put(coordKey, point);
            }
        }
        return duplicates;
    }


    /**
     * dump
     */
    public void dump() {
        System.out.println("QuadTree Dump:");
        dump(root, 0); // Start the recursive dump from the root
    }


    private void dump(QuadTreeNode node, int depth) {
        if (node == null) {
            printIndent(depth);
            System.out.println("Empty");
            return;
        }

        // Handle different types of nodes
        if (node instanceof InternalNode) {
            printIndent(depth);
            System.out.println("Internal");
            // Recursively dump children nodes
            // ... rest of the internal node handling ...
        }
        else if (node instanceof LeafNode) {
            LeafNode leafNode = (LeafNode) node;
            printIndent(depth);
            // Dump all points in the leaf node
            for (Point point : leafNode.getPoints()) {
                System.out.println("Leaf: " + point);
            }
        }
        else if (node instanceof FlyweightNode) {
            printIndent(depth);
            System.out.println("Flyweight (empty)");
        }
    }


    private void printIndent(int depth) {
        for (int i = 0; i < depth; i++) {
            System.out.print("  "); // Two spaces per depth level for
                                    // indentation
        }
    }

}
