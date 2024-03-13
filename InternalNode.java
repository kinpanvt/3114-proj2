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
    public void insert(Point point, Boundary boundary) {
        // Determine the quadrant for the point and insert it into the
        // appropriate child
    }


    @Override
    public boolean search(Point point, Boundary boundary) {
        // Check if the point exists in the correct quadrant
        return false;
    }
}

