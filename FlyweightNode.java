import java.util.ArrayList;

/**
 * FlyweightNode is a class that extends QuadTreeNode.
 * It represents a node in a QuadTree that doesn't hold any data.
 */
public class FlyweightNode extends QuadTreeNode {
    /**
     * The single instance of FlyweightNode.
     */
    private static final FlyweightNode INSTANCE = new FlyweightNode();

    /**
     * Private constructor to prevent instantiation.
     */
    private FlyweightNode() {
    }

    /**
     * Returns the single instance of FlyweightNode.
     * 
     * @return The single instance of FlyweightNode.
     */
    public static FlyweightNode getInstance() {
        return INSTANCE;
    }

    /**
     * Inserts a point into the QuadTree.
     * 
     * @param point The point to insert.
     * @param x     The x-coordinate of the node.
     * @param y     The y-coordinate of the node.
     * @param size  The size of the node.
     * @return The node after insertion.
     */
    @Override
    public QuadTreeNode insert(Point point, int x, int y, int size) {
        LeafNode newNode = new LeafNode();
        return newNode.insert(point, x, y, size);
    }

    /**
     * Removes a point from the QuadTree.
     * 
     * @param point The point to remove.
     * @param x     The x-coordinate of the node.
     * @param y     The y-coordinate of the node.
     * @param size  The size of the node.
     * @return false as FlyweightNode doesn't hold any data.
     */
    @Override
    public boolean remove(Point point, int x, int y, int size) {
        return false;
    }

    /**
     * Searches for a point in the QuadTree by name.
     * 
     * @param name The name of the point to search for.
     * @return An empty list as FlyweightNode doesn't hold any data.
     */
    @Override
    public ArrayList<Point> search(String name) {
        return new ArrayList<>();
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
     * @return An empty list as FlyweightNode doesn't hold any data.
     */
    @Override
    public ArrayList<Point> regionSearch(int queryX, int queryY, int width, int height, int nodeX, int nodeY,
            int nodeSize) {
        return new ArrayList<>();
    }

    /**
     * Dumps the data of the node.
     * 
     * @param level The level of the node in the QuadTree.
     */
    @Override
    public void dump(int level) {
        // dump
    }
}