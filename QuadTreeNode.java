
public abstract class QuadTreeNode {
    // Abstract methods that children will implement
    public abstract void insert(Point point, Boundary boundary);
    public abstract boolean search(Point point, Boundary boundary);
    /**
     * Inserts a point into the appropriate quadrant of this internal node.
     *
     * @param point
     *            The point to insert.
     * @param x
     *            The x-coordinate of the upper left corner of the current
     *            region.
     * @param y
     *            The y-coordinate of the upper left corner of the current
     *            region.
     * @param size
     *            The size of the current region.
     * @return
     */
    public QuadTreeNode insert(Point point, int x, int y, int size) {
        // TODO Auto-generated method stub
        return null;
    }
}