
public class PRQuadTree {
    private QuadTreeNode root;
    private Boundary boundary;

    public enum Quadrant {
        NW, NE, SW, SE
    }


    
    public PRQuadTree(int x, int y, int width, int height) {
        this.root = EmptyNode.getInstance();
        this.boundary = new Boundary(x, y, width, height);
    }

    public void insert(Point point) {
        if (!boundary.contains(point)) {
            System.out.println("Point " + point + " is out of the boundary and cannot be inserted.");
            return;
        }
        root = insert(root, point, boundary);
    }

    private QuadTreeNode insert(QuadTreeNode node, Point point, Boundary boundary) {
        if (node instanceof EmptyNode) {
            return new LeafNode(point);
        } else if (node instanceof LeafNode) {
            // Split the leaf node into an internal node and insert both points
            LeafNode leaf = (LeafNode) node;
            InternalNode internal = new InternalNode();
            internal.insert(leaf.point, boundary); // Insert the existing point
            internal.insert(point, boundary); // Insert the new point
            return internal;
        } else if (node instanceof InternalNode) {
            // Determine the correct quadrant and insert the point
            internalInsert((InternalNode) node, point, boundary);
            return node;
        }
        return node; // Should never reach here
    }

    private void internalInsert(InternalNode internal, Point point, Boundary boundary) {
        Quadrant quadrant = boundary.getQuadrant(point);
        Boundary subBoundary = boundary.getSubBoundary(quadrant);

        switch (quadrant) {
            case NW:
                internal.setNW(insert(internal.getNW(), point, subBoundary));
                break;
            case NE:
                internal.setNE(insert(internal.getNE(), point, subBoundary));
                break;
            case SW:
                internal.setSW(insert(internal.getSW(), point, subBoundary));
                break;
            case SE:
                internal.setSE(insert(internal.getSE(), point, subBoundary));
                break;
        }
    }

    public boolean search(Point point) {
        return search(root, point, boundary);
    }

    private boolean search(QuadTreeNode node, Point point, Boundary boundary) {
        if (node instanceof EmptyNode) {
            return false; // No point to find in an empty node.
        } else if (node instanceof LeafNode) {
            LeafNode leaf = (LeafNode) node;
            // Comparing points; consider overriding equals in Point class.
            return leaf.getPoint().equals(point);
        } else if (node instanceof InternalNode) {
            InternalNode internal = (InternalNode) node;
            Quadrant quadrant = boundary.getQuadrant(point);
            Boundary subBoundary = boundary.getSubBoundary(quadrant);
            // Utilizing getters to access child nodes.
            switch (quadrant) {
                case NW:
                    return search(internal.getNW(), point, subBoundary);
                case NE:
                    return search(internal.getNE(), point, subBoundary);
                case SW:
                    return search(internal.getSW(), point, subBoundary);
                case SE:
                    return search(internal.getSE(), point, subBoundary);
            }
        }
        return false; 
    }
}