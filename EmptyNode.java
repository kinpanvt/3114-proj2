public class EmptyNode extends QuadTreeNode {
    private static final EmptyNode instance = new EmptyNode();

    private EmptyNode() {}

    public static EmptyNode getInstance() {
        return instance;
    }

    @Override
    public QuadTreeNode insert(Point point, Boundary boundary) {
        // Upon inserting a point into an EmptyNode, we convert it to a LeafNode
        // containing the point, effectively updating the tree structure.
        return new LeafNode(point);
    }

    @Override
    public boolean search(Point point, Boundary boundary) {
        // Doesn't contain any points, so always return false
        return false;
    }
}