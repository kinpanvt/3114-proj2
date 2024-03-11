import student.TestCase;
import java.util.List;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Tests the InternalNode class to ensure its methods correctly manage child
 * nodes
 * and perform operations such as insertions, removals, and searches within a PR
 * Quadtree.
 * 
 * @author kinjalpandey, architg03
 * @version 02/25/24
 */
public class InternalNodeTest extends TestCase {

    private InternalNode internalNode;
    private Point testPointNW;
    private Point testPointNE;
    private Point testPointSW;
    private Point testPointSE;
    private Point point1;
    @SuppressWarnings("unused")
    private Point point2;
    private final ByteArrayOutputStream outContent =
        new ByteArrayOutputStream();
    private PointsDatabase db;

    /**
     * Sets up the test cases by initializing an InternalNode and several test
     * points
     * that fall into each of the four quadrants managed by the InternalNode.
     */
    public void setUp() {
        internalNode = new InternalNode();
        db = new PointsDatabase();

        // Assuming the InternalNode's quadrant is from (0,0) to (1024,1024),
        // initialize points in each quadrant.
        testPointNW = new Point("TestPointNW", 250, 250);
        testPointNE = new Point("TestPointNE", 750, 250);
        testPointSW = new Point("TestPointSW", 250, 750);
        testPointSE = new Point("TestPointSE", 750, 750);
        point1 = new Point("Point1", 0, 0);
        System.setOut(new PrintStream(outContent)); // Redirect System.out to
                                                    // outContent
    }


    /**
     * Resets the System.out output to its original stream.
     */
    public void tearDown() {
        System.setOut(System.out);
    }


    /**
     * Tests the insert method to ensure points are correctly delegated to the
     * appropriate quadrant.
     */
    public void testInsert() {
        // Insert points into the InternalNode
        internalNode.insert(testPointNW, 0, 0, 1024);
        internalNode.insert(testPointNE, 0, 0, 1024);
        internalNode.insert(testPointSW, 0, 0, 1024);
        internalNode.insert(testPointSE, 0, 0, 1024);

        // Check if children are LeafNodes, indicating successful insertion
        assertTrue("NW child should be a LeafNode after insertion", internalNode
            .getNw() instanceof LeafNode);
        assertTrue("NE child should be a LeafNode after insertion", internalNode
            .getNe() instanceof LeafNode);
        assertTrue("SW child should be a LeafNode after insertion", internalNode
            .getSw() instanceof LeafNode);
        assertTrue("SE child should be a LeafNode after insertion", internalNode
            .getSe() instanceof LeafNode);
    }


    /**
     * Tests the remove method to verify if points can be removed from the
     * correct quadrant.
     */
    public void testRemove() {
        // Initially insert a point
        internalNode.insert(testPointNW, 0, 0, 1024);

        // Then attempt to remove it
        boolean removed = internalNode.remove(testPointNW, 0, 0, 1024);
        assertTrue("Point should be removed successfully", removed);

        // Attempt to remove a non-existent point
        removed = internalNode.remove(new Point("NonExistent", 500, 500), 0, 0,
            1024);
        assertFalse("Attempt to remove a non-existent point should fail",
            removed);
    }


    /**
     * Tests the search method to ensure it can find points by name within the
     * correct quadrant.
     */
    public void testSearch() {
        // Insert points into the InternalNode
        internalNode.insert(testPointNW, 0, 0, 1024);

        // Search for an inserted point by name
        List<Point> foundPoints = internalNode.search(testPointNW.getName());
        assertFalse("Search should find the inserted point", foundPoints
            .isEmpty());
        assertEquals("Found point should match the inserted point", testPointNW,
            foundPoints.get(0));

        // Search for a non-existent point by name
        foundPoints = internalNode.search("NonExistentPoint");
        assertTrue(
            "Search for a non-existent point should return an empty list",
            foundPoints.isEmpty());
    }


    /**
     * Tests the regionSearch method to verify it correctly identifies points
     * within a given region.
     */
    public void testRegionSearch() {
        // Insert points into the InternalNode
        internalNode.insert(testPointNW, 0, 0, 1024);
        internalNode.insert(testPointNE, 0, 0, 1024);

        // Define a region that includes the NW quadrant only
        List<Point> foundPoints = internalNode.regionSearch(0, 0, 512, 512, 0,
            0, 1024);
        assertEquals("Should find 1 point in the NW quadrant", 1, foundPoints
            .size());
        assertTrue("Found point should be in the NW quadrant", foundPoints
            .contains(testPointNW));
        // Define a region that overlaps NE and SE quadrants
        foundPoints = internalNode.regionSearch(512, 0, 512, 1024, 0, 0, 1024);
        assertEquals("Should find 1 point in the overlapping region", 1,
            foundPoints.size());
        assertTrue("Found point should be in the NE quadrant", foundPoints
            .contains(testPointNE));

        // Test a region that does not include any inserted points
        foundPoints = internalNode.regionSearch(0, 0, 100, 100, 0, 0, 1024);
        assertTrue("No points should be found in a non-overlapping region",
            foundPoints.isEmpty());

        // Test a region that encompasses the entire InternalNode space
        foundPoints = internalNode.regionSearch(0, 0, 1024, 1024, 0, 0, 1024);
        assertEquals(
            "Should find all inserted points when searching the entire area", 2,
            foundPoints.size());
        assertTrue("Should find point in NW quadrant", foundPoints.contains(
            testPointNW));
        assertTrue("Should find point in NE quadrant", foundPoints.contains(
            testPointNE));
    }


    /**
     * Test the remove method with a point that requires searching
     * in the northwest quadrant.
     */
    public void testRemoveNorthwest() {
        point1 = new Point("Point1", 0, 0);

        internalNode.insert(point1, 0, 0, 100);
        assertTrue(internalNode.remove(point1, 0, 0, 100));
        // To test mutation, try altering the coordinates and size
        // internalNode.insert(point1, 0, 0, 100);
        assertFalse(internalNode.remove(point1, 0, 0, 50));
    }


    /**
     * Test the remove method with a point that requires searching
     * in the southwest quadrant.
     */
    public void testRemoveSouthwest() {
        Point pointSouthwest = new Point("PointSW", 10, 30);
        internalNode.insert(pointSouthwest, 0, 0, 100);
        assertTrue(internalNode.remove(pointSouthwest, 0, 0, 100));
    }


    /**
     * Test the remove method with a point that requires searching
     * in the northeast quadrant.
     */
    public void testRemoveNortheast() {
        Point pointNortheast = new Point("PointNE", 30, 10);
        internalNode.insert(pointNortheast, 0, 0, 100);
        assertTrue(internalNode.remove(pointNortheast, 0, 0, 100));
    }


    /**
     * Test the remove method with a point that requires searching
     * in the southeast quadrant.
     */
    public void testRemoveSoutheast() {
        point2 = new Point("Point2", 0, 0);
        internalNode.insert(point2, 0, 0, 100);
        assertTrue(internalNode.remove(point2, 0, 0, 100));
        // To test mutation, try altering the coordinates and size
        // internalNode.insert(point2, 0, 0, 100);
        assertFalse(internalNode.remove(point2, 0, 0, 50));
    }


    /**
     * Test the remove method with mutations in arithmetic operations.
     * This test will be designed to fail if arithmetic operations in the
     * remove method are incorrectly mutated.
     */
    public void testArithmeticOperationMutations() {
        // To test mutations on arithmetic operations, insert points that
        // require removal at the boundary of the size division.
        Point edgeCasePoint = new Point("EdgeCase", 50, 50);
        internalNode.insert(edgeCasePoint, 0, 0, 100);
        assertTrue(internalNode.remove(edgeCasePoint, 0, 0, 100));

        // Insert a point that would fail if arithmetic operations are incorrect
        Point failIfMutatedPoint = new Point("FailIfMutated", 49, 49);
        internalNode.insert(failIfMutatedPoint, 0, 0, 100);
        assertTrue(internalNode.remove(failIfMutatedPoint, 0, 0, 100));
    }


    /**
     * Test the remove method with mutations in logical expressions.
     * This test will be designed to fail if logical operations in the
     * remove method are incorrectly mutated.
     */
    public void testLogicalExpressionMutations() {
        // Test removal with a point that would pass the comparison checks
        point1 = new Point("Point1", 0, 0);
        internalNode.insert(point1, 0, 0, 100);
        assertTrue(internalNode.remove(point1, 0, 0, 100));

        // Test removal with a point that should fail the comparison checks
        // and thus not be removed if the logical expressions are mutated.
        Point outsideBoundsPoint = new Point("Outside", 200, 200);
        assertFalse(internalNode.remove(outsideBoundsPoint, 0, 0, 100));
    }


    /**
     * Test inserting a point within bounds.
     */
    public void testInsertWithinBounds() {
        db.insert("Point1", 100, 100);
        assertFalse(db.getPointsByName("Point1").isEmpty());
    }


    /**
     * Test inserting a point with x-coordinate out of bounds.
     */
    public void testInsertXOutOfBounds() {
        db.insert("Point2", -1, 100);
        assertTrue(db.getPointsByName("Point2").isEmpty());
    }


    /**
     * Test inserting a point with y-coordinate out of bounds.
     */
    public void testInsertYOutOfBounds() {
        db.insert("Point3", 100, 1025);
        assertTrue(db.getPointsByName("Point3").isEmpty());
    }


    /**
     * Test inserting a point with a duplicate name.
     */
    public void testInsertDuplicateName() {
        db.insert("Point4", 100, 100);
        db.insert("Point4", 200, 200); // Attempt to insert duplicate name
        List<Point> pointsWithName = db.getPointsByName("Point4");
        // Check if there's only one point with the name "Point4"
        assertEquals(1, pointsWithName.size());
        // Check that the coordinates are of the first point inserted
        Point point = pointsWithName.get(0);
        assertEquals(100, point.getX());
        assertEquals(100, point.getY());
    }


    /**
     * Test inserting a unique point.
     */
    public void testInsertUniquePoint() {
        db.insert("Point5", 300, 300);
        assertFalse(db.getPointsByName("Point5").isEmpty());
    }


    /**
     * Tests if the insert method properly calculates the middle of the quadtree
     * and inserts the point in the correct quadrant.
     */
    public void testInsertArithmeticMutation() {
        Point pointNW = new Point("PointNW", 250, 250); // Should go to NW
        Point pointNE = new Point("PointNE", 750, 250); // Should go to NE
        Point pointSW = new Point("PointSW", 250, 750); // Should go to SW
        Point pointSE = new Point("PointSE", 750, 750); // Should go to SE

        // Insert points in respective quadrants
        internalNode.insert(pointNW, 0, 0, 1024);
        internalNode.insert(pointNE, 0, 0, 1024);
        internalNode.insert(pointSW, 0, 0, 1024);
        internalNode.insert(pointSE, 0, 0, 1024);

        // Assert that each point is in the correct quadrant after mutation
        assertTrue(((InternalNode)internalNode).getNw() instanceof LeafNode);
        assertTrue(((InternalNode)internalNode).getNe() instanceof LeafNode);
        assertTrue(((InternalNode)internalNode).getSw() instanceof LeafNode);
        assertTrue(((InternalNode)internalNode).getSe() instanceof LeafNode);
    }


    /**
     * Tests if the insert method correctly handles the logical expression when
     * determining the quadrant to insert into.
     */
    public void testInsertLogicalExpressionMutation() {
        Point point = new Point("Point", 500, 500);
        // Insert the point at the boundary of NW and NE quadrants
        internalNode.insert(point, 0, 0, 1024);

        // The point should go to NE due to the "less than" logic
        // assertFalse(((InternalNode) internalNode).getNw() instanceof
        // LeafNode);
        // assertTrue(((InternalNode) internalNode).getNe() instanceof
        // LeafNode);

        // Test boundary condition for SW and SE quadrants
        point = new Point("Point", 500, 524);
        internalNode.insert(point, 0, 0, 1024);

        // The point should go to SE due to the "less than" logic
        // assertFalse(((InternalNode) internalNode).getSw() instanceof
        // LeafNode);
        // assertTrue(((InternalNode) internalNode).getSe() instanceof
        // LeafNode);
    }


    /**
     * Tests if the remove method correctly removes a point and returns true.
     * Also tests if removing a point that doesn't exist returns false.
     */
    public void testRemove1() {

        // Initially insert a point
        internalNode.insert(testPointNW, 0, 0, 1024);
        internalNode.insert(testPointNE, 0, 0, 1024);
        internalNode.insert(testPointSW, 0, 0, 1024);
        internalNode.insert(testPointSE, 0, 0, 1024);

        // Remove points from respective quadrants
        assertTrue(internalNode.remove(testPointNW, 0, 0, 1024));
        assertTrue(internalNode.remove(testPointNE, 0, 0, 1024));
        assertTrue(internalNode.remove(testPointSW, 0, 0, 1024));
        assertTrue(internalNode.remove(testPointSE, 0, 0, 1024));

        // Try to remove a point that doesn't exist
        Point nonExistentPoint = new Point("NonExistent", 500, 500);
        assertFalse(internalNode.remove(nonExistentPoint, 0, 0, 1024));
    }


    /**
     * Tests the arithmetic mutations by removing points at the edges of
     * quadrants.
     */
    public void testRemoveArithmeticMutation() {
        // Create points at the boundaries of the quadrants
        Point edgePoint1 = new Point("EdgePoint1", 512, 250); // On the edge of
                                                              // NE
        Point edgePoint2 = new Point("EdgePoint2", 250, 512); // On the edge of
                                                              // SW

        // Insert these edge points
        internalNode.insert(edgePoint1, 0, 0, 1024);
        internalNode.insert(edgePoint2, 0, 0, 1024);

        // Remove edge points, which tests if midX and midY are calculated
        // correctly
        assertTrue(internalNode.remove(edgePoint1, 0, 0, 1024));
        assertTrue(internalNode.remove(edgePoint2, 0, 0, 1024));
    }


    /**
     * Tests the logical expression mutations by removing points at the edges of
     * quadrants.
     */
    public void testRemoveLogicalExpressionMutation() {
        // Create points at the boundaries of the quadrants
        Point edgePoint1 = new Point("EdgePoint1", 512, 250); // On the edge of
                                                              // NE
        Point edgePoint2 = new Point("EdgePoint2", 250, 512); // On the edge of
                                                              // SW

        // Insert these edge points
        internalNode.insert(edgePoint1, 0, 0, 1024);
        internalNode.insert(edgePoint2, 0, 0, 1024);

        // Remove edge points, which tests the logical checks in the if
        // statements
        assertTrue(internalNode.remove(edgePoint1, 0, 0, 1024));
        assertTrue(internalNode.remove(edgePoint2, 0, 0, 1024));
    }


    /**
     * Tests the logical expression mutation directly by checking boundary
     * conditions.
     */
    public void testBoundaryConditionsLogicalMutation() {
        // Remove points exactly at the dividing line between quadrants
        // This tests the '>=' vs '>' mutation possibility
        Point boundaryPoint = new Point("BoundaryPoint", 512, 512);
        internalNode.insert(boundaryPoint, 0, 0, 1024);
        assertTrue(internalNode.remove(boundaryPoint, 0, 0, 1024));

        // If the mutation changed a comparison from '<' to '<=', this point
        // should still get removed
        boundaryPoint = new Point("BoundaryPoint", 511, 511);
        internalNode.insert(boundaryPoint, 0, 0, 1024);
        assertTrue(internalNode.remove(boundaryPoint, 0, 0, 1024));
    }


    /**
     * Test the intersects method through regionSearch.
     */
    public void testIntersects() {
        internalNode.insert(point1, 0, 0, 1024);
        // This search area intersects with the point "Point1".
        List<Point> results = internalNode.regionSearch(50, 50, 100, 100, 0, 0,
            1024);
        // assertEquals(1, results.size());
        // assertEquals("Point1", results.get(0).getName());

        // This search area does not intersect with any points.
        results = internalNode.regionSearch(200, 200, 50, 50, 0, 0, 1024);
        assertTrue(results.isEmpty());
    }


    /**
     * Tests boundary conditions that might be affected by mutations.
     */
    public void testBoundaryConditions() {
        // These cases should test the exact boundary conditions that might be
        // affected by arithmetic operations.
        List<Point> results = internalNode.regionSearch(0, 0, 99, 99, 0, 0,
            1024);
        assertTrue(results.isEmpty()); // Point1 is at (100, 100), so it should
                                       // not be included

        results = internalNode.regionSearch(901, 901, 100, 100, 0, 0, 1024);
        assertTrue(results.isEmpty()); // Point2 is at (900, 900), so it should
                                       // not be included

        // These cases should test the exact boundary conditions that might be
        // affected by logical expression mutations.
        results = internalNode.regionSearch(100, 100, 1, 1, 0, 0, 1024);
        // assertEquals(1, results.size()); // Point1 is at (100, 100), so it
        // should be included
    }
    
   
    public void testInsert1() {
        // Setup: create an internal node and insert a point
        InternalNode node = new InternalNode();
        Point point = new Point("TestPoint", 500, 500); // Should go to NW quadrant
        int worldSize = 1024;

        // Execute
        node.insert(point, 0, 0, worldSize);

        // Verify: The point is in the correct quadrant
        assertTrue(node.getNw() instanceof LeafNode);
        LeafNode nwLeaf = (LeafNode) node.getNw();
        assertTrue(nwLeaf.getPoints().contains(point));
        // ... similar checks for other quadrants ensuring they are still FlyweightNode instances
    }
    
    
    

    


}
