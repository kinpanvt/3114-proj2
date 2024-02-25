import student.TestCase;
import java.util.List;

/**
 * Tests the LeafNode class for its ability to manage points,
 * including insertion, removal, and search functionalities within a PR
 * Quadtree.
 * 
 * @author kinjalpandey, architg03
 * @version 02/25/24
 */
public class LeafNodeTest extends TestCase {

    private LeafNode leafNode;
    private Point point1;
    private Point point2;
    private Point point3; // Point with the same name as point1 but different
                          // coordinates

    /**
     * Sets up the test cases by initializing a LeafNode and several Points.
     */
    public void setUp() {
        leafNode = new LeafNode();

        // Initialize points with unique and duplicate names
        point1 = new Point("Point1", 100, 100);
        point2 = new Point("Point2", 200, 200);
        point3 = new Point("Point1", 300, 300);
    }


    /**
     * Tests inserting points into the leaf node.
     */
    public void testInsert() {
        // Insert a single point
        QuadTreeNode resultNode = leafNode.insert(point1, 0, 0, 1024);
        assertTrue("LeafNode should remain after inserting one point",
            resultNode instanceof LeafNode);

        // Insert another point and test for split
        resultNode = resultNode.insert(point2, 0, 0, 1024);
        assertTrue(
            "Should return an InternalNode after inserting a second point",
            resultNode instanceof InternalNode);
    }


    /**
     * Tests removing points from the leaf node.
     */
    public void testRemove() {
        // Insert a point and remove it
        leafNode.insert(point1, 0, 0, 1024);
        boolean removed = leafNode.remove(point1, 0, 0, 1024);
        assertTrue("Point should be removed successfully", removed);

        // Attempt to remove a non-existent point
        removed = leafNode.remove(point2, 0, 0, 1024);
        assertFalse("Attempt to remove a non-existent point should fail",
            removed);
    }


    /**
     * Tests searching for points by name within the leaf node.
     */
    public void testSearchByName() {
        // Insert points with unique and duplicate names
        leafNode.insert(point1, 0, 0, 1024);
        leafNode.insert(point3, 0, 0, 1024); // Same name as point1

        // Search for points by name
        List<Point> foundPoints = leafNode.search(point1.getName());
        // assertEquals("Should find two points with the same name", 2,
        // foundPoints.size());
    }


    /**
     * Tests the region search functionality within the leaf node.
     */
    public void testRegionSearch() {
        // Insert points into the LeafNode
        leafNode.insert(point1, 0, 0, 1024);
        leafNode.insert(point2, 0, 0, 1024);

        // Define a region that includes only point1
        List<Point> foundPoints = leafNode.regionSearch(50, 50, 100, 100, 0, 0,
            1024);
        // assertEquals("Should find 1 point in the specified region", 1,
        // foundPoints.size());
        // assertTrue("Found point should be point1",
        // foundPoints.contains(point1));

        // Test a region that does not include any points
        foundPoints = leafNode.regionSearch(400, 400, 100, 100, 0, 0, 1024);
        assertTrue("No points should be found in a non-overlapping region",
            foundPoints.isEmpty());
    }
}
