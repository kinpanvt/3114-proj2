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
    private List<Point> points = new ArrayList<>();

    @Override
    public QuadTreeNode insert(Point point, int x, int y, int size) {
        // If the leaf node is empty, simply add the point and return this node.
        if (this.points.isEmpty()) {
            this.points.add(point);
            return this;
        }
        else {
            // If the node already has a point, it needs to split.
            QuadTreeNode internalNode = new InternalNode();

            // Re-insert existing point(s) into the new internal node.
            for (Point existingPoint : this.points) {
                internalNode = internalNode.insert(existingPoint, x, y, size);
            }

            // Clear the points in the current leaf (now unnecessary since
            // they're moved to the internal node).
            this.points.clear();

            // Insert the new point into the internal node.
            return internalNode.insert(point, x, y, size);
        }
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
     * gets point
     * @return Point point
     */
    public Point getPoint() {
        // TODO Auto-generated method stub
        return null;
    }

}
