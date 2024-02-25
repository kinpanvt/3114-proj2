import student.TestCase;
import java.util.List;

/**
 * Tests the PRQuadTree class to ensure its functionality for managing points
 * in a two-dimensional space, including insertion, removal, and searching capabilities.
 */
public class PRQuadTreeTest extends TestCase {
    
    private PRQuadTree quadTree;
    private Point point1;
    private Point point2;
    private Point point3;

    /**
     * Sets up the test cases by initializing a PRQuadTree and several Points.
     */
    public void setUp() {
        // Initialize the PRQuadTree with a specified boundary size
        quadTree = new PRQuadTree(1024);
        
        // Initialize points within the boundary
        point1 = new Point("TestPoint1", 100, 100);
        point2 = new Point("TestPoint2", 200, 200);
        point3 = new Point("TestPoint1", 300, 300); // Same name as point1, different coordinates
    }
    
    /**
     * Tests inserting points into the PRQuadTree.
     */
    public void testInsert() {
        // Insert a point and verify
        quadTree.insert(point1);
        List<Point> foundPoints = quadTree.searchByName(point1.getName());
        assertEquals("One point should be found after insertion", 1, foundPoints.size());
        assertTrue("Inserted point should be found by name", foundPoints.contains(point1));
        
        // Insert another point and verify
        quadTree.insert(point2);
        foundPoints = quadTree.regionSearch(0, 0, 1024, 1024);
        assertEquals("Two points should be found after another insertion", 2, foundPoints.size());
    }
    
    /**
     * Tests removing points from the PRQuadTree.
     */
    public void testRemove() {
        // Insert a point and then remove it
        quadTree.insert(point1);
        boolean removed = quadTree.remove(point1);
        assertTrue("Point should be removed successfully", removed);
        
        // Verify removal by attempting to find the point
        List<Point> foundPoints = quadTree.searchByName(point1.getName());
        assertTrue("No points should be found after removal", foundPoints.isEmpty());
        
        // Attempt to remove a non-existent point
        removed = quadTree.remove(point3);
        assertFalse("Attempt to remove a non-existent point should fail", removed);
    }
    
    /**
     * Tests searching for points within a specified rectangular region.
     */
    public void testRegionSearch() {
        // Insert points
        quadTree.insert(point1);
        quadTree.insert(point2);
        
        // Search within a region that includes both points
        List<Point> foundPoints = quadTree.regionSearch(0, 0, 300, 300);
        assertEquals("Two points should be found within the region", 2, foundPoints.size());
        
        // Search within a region that includes neither point
        foundPoints = quadTree.regionSearch(400, 400, 100, 100);
        assertTrue("No points should be found in an empty region", foundPoints.isEmpty());
    }
    
    /**
     * Tests searching for points by name.
     */
    public void testSearchByName() {
        // Insert points with the same name
        quadTree.insert(point1);
        quadTree.insert(point3); // Same name as point1, different coordinates
     // Search by name
        List<Point> foundPoints = quadTree.searchByName(point1.getName());
        assertEquals("Two points with the same name should be found", 2, foundPoints.size());
        assertTrue("Search by name should find the first point", foundPoints.contains(point1));
        assertTrue("Search by name should find the second point", foundPoints.contains(point3));

        // Search for a non-existent name
        foundPoints = quadTree.searchByName("NonExistentPoint");
        assertTrue("Search for a non-existent name should return an empty list", foundPoints.isEmpty());
    }
}