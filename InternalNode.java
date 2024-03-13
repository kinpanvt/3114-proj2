public class InternalNode extends QuadTreeNode {

    private QuadTreeNode nw = EmptyNode.getInstance();
    private QuadTreeNode ne = EmptyNode.getInstance();
    private QuadTreeNode sw = EmptyNode.getInstance();
    private QuadTreeNode se = EmptyNode.getInstance();
    
    public InternalNode() {
        this.nw = EmptyNode.getInstance();
        this.ne = EmptyNode.getInstance();
        this.sw = EmptyNode.getInstance();
        this.se = EmptyNode.getInstance();
    }
    
    // Gets the NW Quadrant
    public QuadTreeNode getNW() {
        return nw;
    }

    // Gets the NE Quadrant
    public QuadTreeNode getNE() {
        return ne;
    }
    
    // Gets the SW Quadrant
    public QuadTreeNode getSW() {
        return sw;
    }
    
    // Gets the SEs Quadrant
    public QuadTreeNode getSE() {
        return se;
    }


    // Sets up the NW Quadrant
    public void setNW(QuadTreeNode node) {
        this.nw = node;
    }
    
    // Sets up the NE Quadrant
    public void setNE(QuadTreeNode node) {
        this.ne = node;
    }
    
    // Sets up the SW Quadrant
    public void setSW(QuadTreeNode node) {
        this.sw = node;
    }
    
    // Sets up the SE Quadrant
    public void setSE(QuadTreeNode node) {
        this.se = node;
    }

    @Override
    public QuadTreeNode insert(Point point, Boundary boundary) {
        // Determine the quadrant for the point
        Quadrant quadrant = boundary.getQuadrant(point);
        switch (quadrant) {
            case NW:
                if (nw instanceof EmptyNode) {
                    nw = new LeafNode(point);
                } else {
                    Boundary subBoundary = boundary.getSubBoundary(Quadrant.NW);
                    nw = nw.insert(point, subBoundary);
                }
                break;
            case NE:
                if (ne instanceof EmptyNode) {
                    ne = new LeafNode(point);
                } else {
                    Boundary subBoundary = boundary.getSubBoundary(Quadrant.NE);
                    ne = ne.insert(point, subBoundary);
                }
                break;
            case SW:
                if (sw instanceof EmptyNode) {
                    sw = new LeafNode(point);
                } else {
                    Boundary subBoundary = boundary.getSubBoundary(Quadrant.SW);
                    sw = sw.insert(point, subBoundary);
                }
                break;
            case SE:
                if (se instanceof EmptyNode) {
                    se = new LeafNode(point);
                } else {
                    Boundary subBoundary = boundary.getSubBoundary(Quadrant.SE);
                    se = se.insert(point, subBoundary);
                }
                break;
        }
        return this; // Return the current node after insertion
    }


    @Override
    public boolean search(Point point, Boundary boundary) {
        // Check if the point exists in the correct quadrant
        return false;
    }
}

