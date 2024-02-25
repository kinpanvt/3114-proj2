import student.TestCase;
import java.util.List;

/**
 * Tests the InternalNode class to ensure its methods correctly manage child nodes
 * and perform operations such as insertions, removals, and searches within a PR Quadtree.
 */
public class InternalNodeTest extends TestCase {
    
    private InternalNode internalNode;
    private Point testPointNW;
    private Point testPointNE;
    private Point testPointSW;
    private Point testPointSE;
    
    /**
     * Sets up the test cases by initializing an InternalNode and several test points
     * that fall into each of the four quadrants managed by the InternalNode.
     */
    public void setUp() {
        internalNode = new InternalNode();
        
        // Assuming the InternalNode's quadrant is from (0,0) to (1024,1024),
        // initialize points in each quadrant.
        testPointNW = new Point("TestPointNW", 250, 250);
        testPointNE = new Point("TestPointNE", 750, 250);
        testPointSW = new Point("TestPointSW", 250, 750);
        testPointSE = new Point("TestPointSE", 750, 750);
    }
    
    /**
     * Tests the insert method to ensure points are correctly delegated to the appropriate quadrant.
     */
    public void testInsert() {
        // Insert points into the InternalNode
        internalNode.insert(testPointNW, 0, 0, 1024);
        internalNode.insert(testPointNE, 0, 0, 1024);
        internalNode.insert(testPointSW, 0, 0, 1024);
        internalNode.insert(testPointSE, 0, 0, 1024);
        
        // Check if children are LeafNodes, indicating successful insertion
        assertTrue("NW child should be a LeafNode after insertion", internalNode.nw instanceof LeafNode);
        assertTrue("NE child should be a LeafNode after insertion", internalNode.ne instanceof LeafNode);
        assertTrue("SW child should be a LeafNode after insertion", internalNode.sw instanceof LeafNode);
        assertTrue("SE child should be a LeafNode after insertion", internalNode.se instanceof LeafNode);
    }
    
    /**
     * Tests the remove method to verify if points can be removed from the correct quadrant.
     */
    public void testRemove() {
        // Initially insert a point
        internalNode.insert(testPointNW, 0, 0, 1024);
        
        // Then attempt to remove it
        boolean removed = internalNode.remove(testPointNW, 0, 0, 1024);
        assertTrue("Point should be removed successfully", removed);
        
        // Attempt to remove a non-existent point
        removed = internalNode.remove(new Point("NonExistent", 500, 500), 0, 0, 1024);
        assertFalse("Attempt to remove a non-existent point should fail", removed);
    }
    
    /**
     * Tests the search method to ensure it can find points by name within the correct quadrant.
     */
    public void testSearch() {
        // Insert points into the InternalNode
        internalNode.insert(testPointNW, 0, 0, 1024);
        
        // Search for an inserted point by name
        List<Point> foundPoints = internalNode.search(testPointNW.getName());
        assertFalse("Search should find the inserted point", foundPoints.isEmpty());
        assertEquals("Found point should match the inserted point", testPointNW, foundPoints.get(0));
        
        // Search for a non-existent point by name
        foundPoints = internalNode.search("NonExistentPoint");
        assertTrue("Search for a non-existent point should return an empty list", foundPoints.isEmpty());
    }
    
    /**
     * Tests the regionSearch method to verify it correctly identifies points within a given region.
     */
    public void testRegionSearch() {
        // Insert points into the InternalNode
        internalNode.insert(testPointNW, 0, 0, 1024);
        internalNode.insert(testPointNE, 0, 0, 1024);
        
        // Define a region that includes the NW quadrant only
        List<Point> foundPoints = internalNode.regionSearch(0, 0, 512, 512, 0, 0, 1024);
        assertEquals("Should find 1 point in the NW quadrant", 1, foundPoints.size());
        assertTrue("Found point should be in the NW quadrant", foundPoints.contains(testPointNW));
        // Define a region that overlaps NE and SE quadrants
        foundPoints = internalNode.regionSearch(512, 0, 512, 1024, 0, 0, 1024);
        assertEquals("Should find 1 point in the overlapping region", 1, foundPoints.size());
        assertTrue("Found point should be in the NE quadrant", foundPoints.contains(testPointNE));

        // Test a region that does not include any inserted points
        foundPoints = internalNode.regionSearch(0, 0, 100, 100, 0, 0, 1024);
        assertTrue("No points should be found in a non-overlapping region", foundPoints.isEmpty());

        // Test a region that encompasses the entire InternalNode space
        foundPoints = internalNode.regionSearch(0, 0, 1024, 1024, 0, 0, 1024);
        assertEquals("Should find all inserted points when searching the entire area", 2, foundPoints.size());
        assertTrue("Should find point in NW quadrant", foundPoints.contains(testPointNW));
        assertTrue("Should find point in NE quadrant", foundPoints.contains(testPointNE));
    }
    
}