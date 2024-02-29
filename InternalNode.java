import java.util.ArrayList;
import java.util.List;

/**
 * Represents an internal node in a PR Quadtree. Internal nodes do not hold data
 * themselves but have references
 * to four child nodes that represent the NW, NE, SW, and SE quadrants of the
 * space the internal node occupies.
 * 
 * @author kinjalpandey, architg03
 * @version 02/25/24
 */
public class InternalNode extends QuadTreeNode {

    private QuadTreeNode nw;
    private QuadTreeNode ne;
    private QuadTreeNode sw;
    private QuadTreeNode se;

    /**
     * Constructs an InternalNode with all child nodes initialized to the
     * FlyweightNode instance,
     * representing empty quadrants.
     */
    public InternalNode() {
        setNw(setNe(setSw(setSe(FlyweightNode.getInstance()))));
    }


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
    @Override
    public QuadTreeNode insert(Point point, int x, int y, int size) {
        int halfSize = size / 2;
        int midX = x + halfSize;
        int midY = y + halfSize;

        // Determine the quadrant for the point and insert it into that
        // quadrant.
        if (point.getX() < midX) {
            if (point.getY() < midY) {
                setNw(getNw() == FlyweightNode.getInstance()
                    ? new LeafNode()
                    : getNw());
                getNw().insert(point, x, y, halfSize);
            }
            else {
                setSw(getSw() == FlyweightNode.getInstance()
                    ? new LeafNode()
                    : getSw());
                getSw().insert(point, x, midY, halfSize);
            }
        }
        else {
            if (point.getY() < midY) {
                setNe(getNe() == FlyweightNode.getInstance()
                    ? new LeafNode()
                    : getNe());
                getNe().insert(point, midX, y, halfSize);
            }
            else {
                setSe(getSe() == FlyweightNode.getInstance()
                    ? new LeafNode()
                    : getSe());
                getSe().insert(point, midX, midY, halfSize);
            }
        }

        return this;
    }


    /**
     * Removes a point from the appropriate quadrant. If the removal leads to an
     * empty quadrant,
     * it considers converting the child node back to a FlyweightNode.
     *
     * @param point
     *            The point to remove.
     * @param x
     *            The x-coordinate of the upper left corner of the current
     *            region.
     * @param y
     *            The y-coordinate of the upper left corner of the current
     *            region.
     * @param size
     *            The size of the current region.
     * @return true if the point was removed; false otherwise.
     */
    @Override
    public boolean remove(Point point, int x, int y, int size) {
        int midX = x + size / 2;
        int midY = y + size / 2;
        boolean removed = false;

        if (point.getX() < midX) {
            if (point.getY() < midY) {
                removed = getNw().remove(point, x, y, size / 2);
            }
            else {
                removed = getSw().remove(point, x, midY, size / 2);
            }
        }
        else {
            if (point.getY() < midY) {
                removed = getNe().remove(point, midX, y, size / 2);
            }
            else {
                removed = getSe().remove(point, midX, midY, size / 2);
            }
        }

        // Check if children are all FlyweightNodes, convert this to a
        // FlyweightNode if true.
        // Note: This requires access to the parent node or a more complex tree
        // restructuring mechanism.
        return removed;
    }


    /**
     * Searches for all points matching a given name within the quadrants of
     * this internal node.
     *
     * @param name
     *            The name of the points to search for.
     * @return A list of points matching the given name.
     */
    @Override
    public List<Point> search(String name) {
        List<Point> foundPoints = new ArrayList<>();
        foundPoints.addAll(getNw().search(name));
        foundPoints.addAll(getNe().search(name));
        foundPoints.addAll(getSw().search(name));
        foundPoints.addAll(getSe().search(name));
        return foundPoints;
    }


    /**
     * Performs a region search to find all points within a specified
     * rectangular area.
     *
     * @param queryX
     *            The x-coordinate of the upper left corner of the query region.
     * @param queryY
     *            The y-coordinate of the upper left corner of the query region.
     * @param width
     *            The width of the query region.
     * @param height
     *            The height of the query region.
     * @param nodeX
     *            The x-coordinate of the upper left corner of the current
     *            node's region.
     * @param nodeY
     *            The y-coordinate of the upper left corner of the current
     *            node's region.
     * @param nodeSize
     *            The size of the current node's region.
     * @return A list of points found within the query region.
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
        List<Point> foundPoints = new ArrayList<>();
        int midX = nodeX + nodeSize / 2;
        int midY = nodeY + nodeSize / 2;

        // For each quadrant, check if it intersects with the search area.
        if (intersects(queryX, queryY, width, height, nodeX, nodeY, midX
            - nodeX, midY - nodeY)) {
            foundPoints.addAll(getNw().regionSearch(queryX, queryY, width,
                height, nodeX, nodeY, nodeSize / 2));
        }
        if (intersects(queryX, queryY, width, height, midX, nodeY, nodeX
            + nodeSize - midX, midY - nodeY)) {
            foundPoints.addAll(getNe().regionSearch(queryX, queryY, width,
                height, midX, nodeY, nodeSize / 2));
        }
        if (intersects(queryX, queryY, width, height, nodeX, midY, midX - nodeX,
            nodeY + nodeSize - midY)) {
            foundPoints.addAll(getSw().regionSearch(queryX, queryY, width,
                height, nodeX, midY, nodeSize / 2));
        }
        if (intersects(queryX, queryY, width, height, midX, midY, nodeX
            + nodeSize - midX, nodeY + nodeSize - midY)) {
            foundPoints.addAll(getSe().regionSearch(queryX, queryY, width,
                height, midX, midY, nodeSize / 2));
        }

        return foundPoints;
    }


    /**
     * Helper method to determine if two regions intersect.
     *
     * @param queryX,
     *            queryY, width, height The coordinates and dimensions of the
     *            search area.
     * @param nodeX,
     *            nodeY, nodeWidth, nodeHeight The coordinates and dimensions of
     *            the node's quadrant.
     * @return true if the regions intersect; false otherwise.
     */
    private boolean intersects(
        int queryX,
        int queryY,
        int width,
        int height,
        int nodeX,
        int nodeY,
        int nodeWidth,
        int nodeHeight) {
        return !(nodeX >= queryX + width || nodeX + nodeWidth <= queryX
            || nodeY >= queryY + height || nodeY + nodeHeight <= queryY);
    }


    /**
     * Getter for nw
     * 
     * @return nw
     */
    public QuadTreeNode getNw() {
        return nw;
    }


    /**
     * Setter for nw
     * 
     * @param parNW
     *            QuadTreeNode
     * @return nw
     */
    public QuadTreeNode setNw(QuadTreeNode parNW) {
        this.nw = parNW;
        return parNW;
    }


    /**
     * Getter for ne
     * 
     * @return ne
     */
    public QuadTreeNode getNe() {
        return ne;
    }


    /**
     * Setter for ne
     * 
     * @param parNE
     *            QuadTreeNode
     * @return ne
     */
    public QuadTreeNode setNe(QuadTreeNode parNE) {
        this.ne = parNE;
        return parNE;
    }


    /**
     * Getter for sw
     * 
     * @return sw
     */
    public QuadTreeNode getSw() {
        return sw;
    }


    /**
     * Setter for sw
     * 
     * @param parSW
     *            QuadTreeNode
     * @return sw
     */
    public QuadTreeNode setSw(QuadTreeNode parSW) {
        this.sw = parSW;
        return parSW;
    }


    /**
     * Getter for se
     * 
     * @return se
     */
    public QuadTreeNode getSe() {
        return se;
    }


    /**
     * Setter for se
     * 
     * @param parSE
     *            QuadTreeNode
     * @return se
     */
    public QuadTreeNode setSe(QuadTreeNode parSE) {
        this.se = parSE;
        return parSE;

    }


    /**
     * Dump Method
     * 
     * @param level
     *            lvl
     */
    public void dump(int level) {
        System.out.println(indent(level) + "InternalNode");
        nw.dump(level + 1);
        ne.dump(level + 1);
        sw.dump(level + 1);
        se.dump(level + 1);
    }


    // Helper method for indentation
    private String indent(int level) {
        return " ".repeat(level * 2);
    }
}
