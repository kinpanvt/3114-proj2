
public class LeafNode extends QuadTreeNode {
    Point point;

    public LeafNode(Point point) {
        this.point = point;
    }
    
    public Point getPoint() {
        return this.point;
    }

    @Override
    public void insert(Point point, Boundary boundary) {
        // PRQuadTree handles splitting
        throw new UnsupportedOperationException("LeafNode insert operation should be handled by PRQuadTree.");
    }

    @Override
    public boolean search(Point point, Boundary boundary) {
        return this.point.equals(point);
    }
}
