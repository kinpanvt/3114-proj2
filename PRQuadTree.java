
public class PRQuadTree {
    private QuadTreeNode root;
    private Boundary boundary;


    
    public PRQuadTree(int x, int y, int width, int height) {
        this.root = EmptyNode.getInstance();
        this.boundary = new Boundary(x, y, width, height);
    }

    public void insert(Point point) {
        root = insert(root, point, boundary);
    }

    private QuadTreeNode insert(QuadTreeNode node, Point point, Boundary boundary) {
        if (node instanceof EmptyNode) {
            return new LeafNode(point); // Convert empty node to leaf node with the new point
        } else if (node instanceof LeafNode) {
            LeafNode leaf = (LeafNode) node;
            if (leaf.getPointCount() < 3 || leaf.allPointsSame()) {
                leaf.addPoint(point);
                return leaf;
            } else {
                // Need to split the leaf node
                InternalNode internal = new InternalNode();
                for (Point p : leaf.getPoints()) {
                    internal = (InternalNode) insert(internal, p, boundary); // Reinsert existing points
                }
                internal = (InternalNode) insert(internal, point, boundary); // Insert new point
                return internal;
            }
        } else if (node instanceof InternalNode) {
            InternalNode internal = (InternalNode) node;
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
            return internal;
        } else {
            // Should not reach here
            throw new IllegalStateException("Unknown QuadTreeNode type");
        }
    }
    public boolean search(Point point) {
        return search(root, point, boundary);
    }

    private boolean search(QuadTreeNode node, Point point, Boundary boundary) {
        if (node instanceof EmptyNode) {
            return false; // No point to find in an empty node.
        } else if (node instanceof LeafNode) {
            // Directly utilize the containsPoint method to check if the node contains the target point.
            return ((LeafNode)node).search(point, boundary);
        } else if (node instanceof InternalNode) {
            InternalNode internal = (InternalNode) node;
            Quadrant quadrant = boundary.getQuadrant(point);
            Boundary subBoundary = boundary.getSubBoundary(quadrant);
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

    public void dump() {
        System.out.println("QuadTree Dump:");
        dump(root, "", true);
    }

    private void dump(QuadTreeNode node, String indent, boolean isLast) {
        // Base case: if the node is empty, print "Empty" and return
        if (node instanceof EmptyNode) {
            System.out.println(indent + (isLast ? "└── " : "├── ") + "Empty");
            return;
        }

        // If the node is a leaf, print its points
        if (node instanceof LeafNode) {
            LeafNode leaf = (LeafNode) node;
            System.out.println(indent + (isLast ? "└── " : "├── ") + "Leaf");
            Point[] points = leaf.getPoints(); // Assuming LeafNode has getPoints method
            for (int i = 0; i < leaf.getPointCount(); i++) { // Assuming LeafNode has getPointCount method
                System.out.println(indent + (i == leaf.getPointCount() - 1 ? "    └── " : "    ├── ") + points[i]);
            }
        }

        // If the node is an internal node, recursively dump its children
        if (node instanceof InternalNode) {
            InternalNode internal = (InternalNode) node;
            System.out.println(indent + (isLast ? "└── " : "├── ") + "Internal");
            // Assuming InternalNode class has methods to get children: getNW(), getNE(), getSW(), getSE()
            dump(internal.getNW(), indent + (isLast ? "    " : "│   "), false);
            dump(internal.getNE(), indent + (isLast ? "    " : "│   "), false);
            dump(internal.getSW(), indent + (isLast ? "    " : "│   "), false);
            dump(internal.getSE(), indent + (isLast ? "    " : "│   "), true);
        }
    }
    
    public void findDuplicates() {
        PointContainer allPoints = new PointContainer(10); // Initial capacity
        collectPoints(root, allPoints); // Start collecting points from the root
        
        // Now manually identify duplicates
        for (int i = 0; i < allPoints.getSize(); i++) {
            Point current = allPoints.getPoints()[i];
            for (int j = i + 1; j < allPoints.getSize(); j++) {
                Point compare = allPoints.getPoints()[j];
                if (current.getX() == compare.getX() && current.getY() == compare.getY()) {
                    System.out.println("Duplicate point found: " + current);
                    break; // Assuming you only want to report a duplicate once
                }
            }
        }
    }

    private void collectPoints(QuadTreeNode node, PointContainer allPoints) {
        if (node instanceof EmptyNode) {
            return;
        }
        if (node instanceof LeafNode) {
            LeafNode leaf = (LeafNode) node;
            for (int i = 0; i < leaf.getPointCount(); i++) {
                allPoints.addPoint(leaf.getPoints()[i]);
            }
        } else if (node instanceof InternalNode) {
            InternalNode internal = (InternalNode) node;
            collectPoints(internal.getNW(), allPoints);
            collectPoints(internal.getNE(), allPoints);
            collectPoints(internal.getSW(), allPoints);
            collectPoints(internal.getSE(), allPoints);
        }
    }

    public PointContainer regionSearch(int x, int y, int width, int height) {
        PointContainer foundPoints = new PointContainer(10); // Initial capacity
        Boundary searchArea = new Boundary(x, y, width, height);
        regionSearchHelper(this.root, this.boundary, searchArea, foundPoints);
        return foundPoints;
    }

    private void regionSearchHelper(QuadTreeNode node, Boundary nodeBoundary, Boundary searchArea, PointContainer foundPoints) {
        if (node instanceof EmptyNode) {
            // No points to consider in an empty node.
            return;
        }

        // Check if the search area intersects with the current node's boundary
        if (!nodeBoundary.intersects(searchArea)) {
            // If there's no intersection, no need to search this subtree.
            return;
        }

        if (node instanceof LeafNode) {
            // For leaf nodes, check each point to see if it falls within the search area.
            LeafNode leaf = (LeafNode) node;
            for (int i = 0; i < leaf.getPointCount(); i++) {
                Point point = leaf.getPoints()[i];
                if (searchArea.contains(point)) {
                    foundPoints.addPoint(point);
                }
            }
        } else if (node instanceof InternalNode) {
            // For internal nodes, recurse into each child with the appropriate sub-boundary.
            InternalNode internal = (InternalNode) node;
            Boundary nwBoundary = nodeBoundary.getSubBoundary(Quadrant.NW);
            Boundary neBoundary = nodeBoundary.getSubBoundary(Quadrant.NE);
            Boundary swBoundary = nodeBoundary.getSubBoundary(Quadrant.SW);
            Boundary seBoundary = nodeBoundary.getSubBoundary(Quadrant.SE);
            
            regionSearchHelper(internal.getNW(), nwBoundary, searchArea, foundPoints);
            regionSearchHelper(internal.getNE(), neBoundary, searchArea, foundPoints);
            regionSearchHelper(internal.getSW(), swBoundary, searchArea, foundPoints);
            regionSearchHelper(internal.getSE(), seBoundary, searchArea, foundPoints);
        }
    }

    public boolean remove(Point point) {
        return remove(this.root, point, this.boundary) != null;
    }

    private QuadTreeNode remove(QuadTreeNode node, Point point, Boundary boundary) {
        if (node instanceof EmptyNode) {
            return null; // Point not found
        }

        if (node instanceof LeafNode) {
            LeafNode leaf = (LeafNode) node;
            if (leaf.removePoint(point)) { // Assumes LeafNode has a method to remove a point
                // Check if the leaf is now empty and should be turned into an EmptyNode
                if (leaf.getPointCount() == 0) {
                    return EmptyNode.getInstance();
                }
                return leaf; // Leaf still has points, no further action needed
            } else {
                return null; // Point not found in this leaf
            }
        } else if (node instanceof InternalNode) {
            InternalNode internal = (InternalNode) node;
            Quadrant quadrant = boundary.getQuadrant(point);
            Boundary subBoundary = boundary.getSubBoundary(quadrant);
            switch (quadrant) {
                case NW:
                    internal.setNW(remove(internal.getNW(), point, subBoundary));
                    break;
                case NE:
                    internal.setNE(remove(internal.getNE(), point, subBoundary));
                    break;
                case SW:
                    internal.setSW(remove(internal.getSW(), point, subBoundary));
                    break;
                case SE:
                    internal.setSE(remove(internal.getSE(), point, subBoundary));
                    break;
            }
            // After removal, check if the internal node should merge its children
            node = checkAndMerge(internal, boundary);
        }
        return node; // Return the node after potentially removing a point
    }

    private QuadTreeNode checkAndMerge(InternalNode node, Boundary boundary) {
        if (allChildrenAreLeaves(node)) {
            // Initialize with the maximum possible size, but you might adjust the logic to handle dynamic sizes.
            Point[] allPoints = new Point[LeafNode.CAPACITY * 4]; // Assuming maximum capacity from all leaves.
            int index = 0;

            // Collect all points from leaf nodes.
            index = collectPoints(node.getNW(), allPoints, index);
            index = collectPoints(node.getNE(), allPoints, index);
            index = collectPoints(node.getSW(), allPoints, index);
            index = collectPoints(node.getSE(), allPoints, index);

            // Determine if these points can fit into a single LeafNode.
            if (canMerge(allPoints, index)) {
                // Create a new LeafNode with all these points. You'll need to adjust LeafNode's constructor to handle an array of Points.
                return new LeafNode(allPoints, index); // Adjusted constructor needed here.
            }
        }

        // If not all children are leaves or can't be merged, return the node as is.
        return node;
    }

    // Helper method to check if all children of an internal node are LeafNodes.
    private boolean allChildrenAreLeaves(InternalNode node) {
        return (node.getNW() instanceof LeafNode) &&
               (node.getNE() instanceof LeafNode) &&
               (node.getSW() instanceof LeafNode) &&
               (node.getSE() instanceof LeafNode);
    }

    // Method to collect points from a node and add them to the allPoints array.
    private int collectPoints(QuadTreeNode node, Point[] allPoints, int startIndex) {
        if (node instanceof LeafNode) {
            LeafNode leaf = (LeafNode) node;
            for (int i = 0; i < leaf.getPointCount(); i++) {
                allPoints[startIndex++] = leaf.getPoints()[i];
            }
        }
        return startIndex; // Return the new start index for the next addition.
    }

    // Check if the collected points can be merged into a single LeafNode.
    private boolean canMerge(Point[] allPoints, int count) {
        if (count > LeafNode.CAPACITY) {
            return false; // Too many points to merge into a single LeafNode.
        }

        // You may need to add additional logic here based on your project's merge rules.
        return true;
    }
    
}