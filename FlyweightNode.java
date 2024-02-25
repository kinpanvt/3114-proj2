import java.util.Collections;
import java.util.List;

/**
 * A FlyweightNode class for use in a PR Quadtree implementation.
 * This class follows the Flyweight design pattern to minimize memory usage
 * by sharing a single instance to represent all empty leaf nodes in the PR
 * Quadtree.
 * 
 * @author kinjalpandey, architg03
 * @version 02/25/24
 */
public class FlyweightNode extends QuadTreeNode {
    // Single instance of the FlyweightNode
    private static final FlyweightNode INSTANCE = new FlyweightNode();

    /**
     * Private constructor to prevent instantiation outside this class.
     */
    private FlyweightNode() {
    }


    /**
     * Provides access to the singleton instance of the FlyweightNode.
     *
     * @return The singleton instance of FlyweightNode.
     */
    public static FlyweightNode getInstance() {
        return INSTANCE;
    }


    /**
     * Inserts a point into the PR Quadtree. This method is overridden in the
     * FlyweightNode
     * to perform no action directly, as the FlyweightNode represents an empty
     * quadrant.
     * Instead, it triggers the creation of a LeafNode containing the point.
     *
     * @param point
     *            The point to insert.
     * @param x
     *            The x-coordinate of the top-left corner of the current
     *            quadrant.
     * @param y
     *            The y-coordinate of the top-left corner of the current
     *            quadrant.
     * @param size
     *            The size of the current quadrant.
     * @return A new LeafNode containing the inserted point.
     */
    @Override
    public QuadTreeNode insert(Point point, int x, int y, int size) {
        LeafNode newNode = new LeafNode();
        return newNode.insert(point, x, y, size); // Delegate to the new leaf
                                                  // node's insert method.
    }


    /**
     * Removes a point from the PR Quadtree. This method is overridden in the
     * FlyweightNode
     * to always return false, as the FlyweightNode represents an empty quadrant
     * where
     * there are no points to remove.
     *
     * @param point
     *            The point to remove.
     * @param x
     *            The x-coordinate of the top-left corner of the current
     *            quadrant.
     * @param y
     *            The y-coordinate of the top-left corner of the current
     *            quadrant.
     * @param size
     *            The size of the current quadrant.
     * @return Always false, as no points exist in the FlyweightNode.
     */
    @Override
    public boolean remove(Point point, int x, int y, int size) {
        return false; // No action needed as FlyweightNode is empty.
    }


    /**
     * Searches for points with the specified name in the PR Quadtree.
     * This method is overridden in the FlyweightNode to always return an empty
     * list,
     * as the FlyweightNode represents an empty quadrant where no points are
     * stored.
     *
     * @param name
     *            The name of the point to search for.
     * @return An empty list, as no points exist in the FlyweightNode.
     */
    @Override
    public List<Point> search(String name) {
        return Collections.emptyList(); // No points in FlyweightNode.
    }


    /**
     * Performs a region search in the PR Quadtree. This method is overridden in
     * the FlyweightNode
     * to always return an empty list, as the FlyweightNode represents an empty
     * quadrant where
     * no points are stored.
     *
     * @param queryX
     *            The x-coordinate of the top-left corner of the query region.
     * @param queryY
     *            The y-coordinate of the top-left corner of the query region.
     * @param width
     *            The width of the query region.
     * @param height
     *            The height of the query region.
     * @param nodeX
     *            The x-coordinate of the top-left corner of the current
     *            quadrant.
     * @param nodeY
     *            The y-coordinate of the top-left corner of the current
     *            quadrant.
     * @param nodeSize
     *            The size of the current quadrant.
     * @return An empty list, as no points exist in the FlyweightNode.
     */
    @Override
    public List<Point> regionSearch(
        int queryX,
        int queryY,
        int width,
        int height,
        int nodeX,
        int nodeY,
        int nodeSize) {
        return Collections.emptyList(); // No points in FlyweightNode for any
                                        // region.
    }
}
