import java.util.ArrayList;

public class PRQuadTree {
    private QuadTreeNode root;
    private final int size;

    public PRQuadTree(int size) {
        this.root = FlyweightNode.getInstance();
        this.size = size;
    }

    public void insert(Point point) {
        root = root.insert(point, size, size, size);
    }

    public boolean remove(Point point) {
        return root.remove(point, 0, 0, size);
    }

    public ArrayList<Point> regionSearch(int x, int y, int width, int height) {
        return root.regionSearch(x, y, width, height, 0, 0, size);
    }

    public ArrayList<Point> searchByName(String name) {
        return root.search(name);
    }

    public Point searchByCoordinates(int x, int y) {
        return searchByCoordinates(root, x, y, 0, 0, size);
    }

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

    public ArrayList<Point> findDuplicates() {
        ArrayList<Point> allPoints = new ArrayList<>();
        collectPoints(root, allPoints);
        return identifyDuplicates(allPoints);
    }

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

    public void dump() {
        System.out.println("QuadTree Dump:");
        dump(root, 0);
    }

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

    private void printIndent(int depth) {
        for (int i = 0; i < depth; i++) {
            System.out.print("  ");
        }
    }
}
