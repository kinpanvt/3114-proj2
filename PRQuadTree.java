import java.util.List;

/**
 * Represents a Point Region Quadtree for managing a collection of points
 * in a two-dimensional space. The PR Quadtree divides the space into four
 * quadrants recursively until each leaf node contains at most one point.
 */
public class PRQuadTree {
    /**
     * The root node of the PR Quadtree, which could be an internal node or a leaf node.
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
     * @param size The length and width of the square boundary of the quadtree.
     *             Assumes a square space.
     */
    public PRQuadTree(int size) {
        this.root = FlyweightNode.getInstance(); // Initializes with a flyweight node to represent empty space.
        this.size = size;
    }

    /**
     * Inserts a point into the PR Quadtree. If the point's location is already
     * occupied, the tree will adjust accordingly to accommodate the new point.
     *
     * @param point The point to insert into the quadtree.
     */
    public void insert(Point point) {
        root = root.insert(point, size, size, size);
    }

    /**
     * Removes a point from the PR Quadtree. If the point does not exist, no action is taken.
     *
     * @param point The point to remove from the quadtree.
     * @return True if the point was successfully removed, false otherwise.
     */
    public boolean remove(Point point) {
        return root.remove(point, 0, 0, size);
    }

    /**
     * Searches for all points within a specified rectangular region.
     *
     * @param x      The x-coordinate of the upper-left corner of the search region.
     * @param y      The y-coordinate of the upper-left corner of the search region.
     * @param width  The width of the search region.
     * @param height The height of the search region.
     * @return A list of points found within the specified region.
     */
    public List<Point> regionSearch(int x, int y, int width, int height) {
        return root.regionSearch(x, y, width, height, 0, 0, size);
    }

    /**
     * Searches for a point by its name within the PR Quadtree.
     *
     * @param name The name of the point to search for.
     * @return A list of points matching the given name. Note that names are not
     *         guaranteed to be unique, hence the return type is a list.
     */
    public List<Point> searchByName(String name) {
        return root.search(name);
    }

}
