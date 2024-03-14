
public abstract class QuadTreeNode {
    
    /**
     * Inserts a point into the appropriate position within the tree. The specific
     * behavior of this method will vary depending on the type of node (leaf,
     * internal, or empty).
     *
     * @param point The point to insert.
     * @param boundary The spatial boundary representing this node's area.
     * @return The node after insertion (which may be a new node if the current node is modified).
     */
    public abstract QuadTreeNode insert(Point point, Boundary boundary);

    /**
     * Searches for a specific point within the tree. This method checks if the
     * point exists in the subtree rooted at the current node.
     *
     * @param point The point to search for.
     * @param boundary The spatial boundary representing this node's area.
     * @return true if the point is found, false otherwise.
     */
    public abstract boolean search(Point point, Boundary boundary);

    
}