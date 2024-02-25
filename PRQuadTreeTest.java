import student.TestCase;
import java.util.List;

/**
 * Tests the PRQuadTree class to ensure its functionality for managing points
 * in a two-dimensional space, including insertion, removal, and searching
 * capabilities.
 * 
 * @author kinjalpandey, architg03
 * @version 02/25/24
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
        point3 = new Point("TestPoint1", 300, 300); // Same name as point1,
                                                    // different coordinates
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
        assertTrue("No points should be found after removal", foundPoints
            .isEmpty());

        // Attempt to remove a non-existent point
        removed = quadTree.remove(point3);
        assertFalse("Attempt to remove a non-existent point should fail",
            removed);
    }

}
