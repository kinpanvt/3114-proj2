import java.util.ArrayList;

public class PRQuadTree {
    private QuadTreeNode root;
    private final int size;

    /**
     * Constructs a PRQuadTree with the specified size.
     * 
     * @param size the size of the QuadTree
     */
    public PRQuadTree(int size) {
        this.root = FlyweightNode.getInstance();
        this.size = size;
    }

    /**
     * Inserts a point into the QuadTree.
     * 
     * @param point the point to be inserted
     */
    public void insert(Point point) {
        root = root.insert(point, 0, 0, size);
    }

    /**
     * Removes a point from the QuadTree.
     * 
     * @param point the point to be removed
     * @return true if the point was removed, false otherwise
     */
    public boolean remove(Point point) {
        return root.remove(point, 0, 0, size);
    }

    /**
     * Searches for points within a specified region in the QuadTree.
     * 
     * @param x      the x-coordinate of the top-left corner of the region
     * @param y      the y-coordinate of the top-left corner of the region
     * @param width  the width of the region
     * @param height the height of the region
     * @return a list of points within the specified region
     */
    public ArrayList<Point> regionSearch(int x, int y, int width, int height) {
        return root.regionSearch(x, y, width, height, 0, 0, size);
    }

    /**
     * Searches for points by name in the QuadTree.
     * 
     * @param name the name of the points to search for
     * @return a list of points with the specified name
     */
    public ArrayList<Point> searchByName(String name) {
        return root.search(name);
    }

    /**
     * Searches for a point by coordinates in the QuadTree.
     * 
     * @param x the x-coordinate of the point
     * @param y the y-coordinate of the point
     * @return the point with the specified coordinates, or null if no such point
     *         exists
     */
    public Point searchByCoordinates(int x, int y) {
        return searchByCoordinates(root, x, y, 0, 0, size);
    }

    /**
     * Searches for a point by coordinates in the QuadTree.
     * 
     * @param node       the node to start the search from
     * @param x          the x-coordinate of the point
     * @param y          the y-coordinate of the point
     * @param startX     the x-coordinate of the starting point of the search region
     * @param startY     the y-coordinate of the starting point of the search region
     * @param regionSize the size of the search region
     * @return the point with the specified coordinates, or null if no such point
     *         exists
     */
    private Point searchByCoordinates(QuadTreeNode node, int x, int y, int startX, int startY, int regionSize) {
        if (node == null || node instanceof FlyweightNode) {
            return null;
        } else if (node instanceof LeafNode) {
            Point point = ((LeafNode) node).getPoint(x, y);
            if (point != null && point.getX() == x && point.getY() == y) {
                return point;
            }
            return null;
        } else if (node instanceof InternalNode) {
            int halfSize = regionSize / 2;
            int midX = startX + halfSize;
            int midY = startY + halfSize;

            if (x < midX) {
                return y < midY
                        ? searchByCoordinates(((InternalNode) node).getNw(), x, y, startX, startY, halfSize)
                        : searchByCoordinates(((InternalNode) node).getSw(), x, y, startX, midY, halfSize);
            } else {
                return y < midY
                        ? searchByCoordinates(((InternalNode) node).getNe(), x, y, midX, startY, halfSize)
                        : searchByCoordinates(((InternalNode) node).getSe(), x, y, midX, midY, halfSize);
            }
        }
        return null;
    }

    /**
     * Finds duplicate points in the QuadTree.
     * 
     * @return a list of duplicate points
     */
    public ArrayList<Point> findDuplicates() {
        ArrayList<Point> allPoints = new ArrayList<>();
        collectPoints(root, allPoints);
        return identifyDuplicates(allPoints);
    }

    /**
     * Collects all points in the QuadTree.
     * 
     * @param node      the node to start the collection from
     * @param allPoints the list to store the collected points
     */
    private void collectPoints(QuadTreeNode node, ArrayList<Point> allPoints) {
        if (node == null || node instanceof FlyweightNode) {
            return;
        } else if (node instanceof LeafNode) {
            allPoints.addAll(((LeafNode) node).getPoints());
        } else if (node instanceof InternalNode) {
            collectPoints(((InternalNode) node).getNw(), allPoints);
            collectPoints(((InternalNode) node).getNe(), allPoints);
            collectPoints(((InternalNode) node).getSw(), allPoints);
            collectPoints(((InternalNode) node).getSe(), allPoints);
        }
    }

    /**
     * Identifies duplicate points in a list of points.
     * 
     * @param allPoints the list of points to check for duplicates
     * @return a list of duplicate points
     */
    private ArrayList<Point> identifyDuplicates(ArrayList<Point> allPoints) {
        ArrayList<Point> duplicates = new ArrayList<>();
        for (int i = 0; i < allPoints.size(); i++) {
            Point currentPoint = allPoints.get(i);
            for (int j = i + 1; j < allPoints.size(); j++) {
                Point comparePoint = allPoints.get(j);
                if (currentPoint.getX() == comparePoint.getX() && currentPoint.getY() == comparePoint.getY()
                        && !duplicates.contains(currentPoint)) {
                    duplicates.add(currentPoint);
                    duplicates.add(comparePoint);
                }
            }
        }
        return duplicates;
    }

    /**
     * Prints the structure of the QuadTree.
     */
    public void dump() {
        System.out.println("QuadTree Dump:");
        dump(root, 0);
    }

    /**
     * Prints the structure of a node in the QuadTree.
     * 
     * @param node  the node to print
     * @param depth the depth of the node in the QuadTree
     */
    private void dump(QuadTreeNode node, int depth) {
        int nodesPrinted = 0;
        if (node == null) {
            printIndent(depth);
            System.out.println("Node at 0, 0" + size + ": Empty");
            nodesPrinted++;
        }
        if (node instanceof InternalNode) {
            printIndent(depth);
            System.out.println("Internal");
            nodesPrinted++;
        } else if (node instanceof LeafNode) {
            LeafNode leafNode = (LeafNode) node;
            printIndent(depth);
            for (Point point : leafNode.getPoints()) {
                System.out.println("Leaf: " + point);
                nodesPrinted++;
            }
        } else if (node instanceof FlyweightNode) {
            printIndent(depth);
            System.out.println("Node at 0, 0, " + size + ": Empty");
            nodesPrinted++;
        }
        System.out.println(nodesPrinted + " quadtree nodes printed");
    }

    /**
     * Prints a number of indents.
     * 
     * @param depth the number of indents to print
     */
    private void printIndent(int depth) {
        for (int i = 0; i < depth; i++) {
            System.out.print("  ");
        }
    }
}
