import student.TestCase;

/**
 * Tests the Point class to ensure its immutability and correct behavior of
 * its constructor, accessor methods, and toString method.
 */
public class PointTest extends TestCase {

    private Point point;

    /**
     * Sets up the test cases by initializing a Point instance.
     */
    public void setUp() {
        // Initialize a Point with a specific name and coordinates
        point = new Point("TestPoint", 50, 75);
    }
    
    /**
     * Tests the constructor and accessor methods.
     */
    public void testAccessors() {
        // Test getName
        assertEquals("Name should be 'TestPoint'", "TestPoint", point.getName());

        // Test getX
        assertEquals("X coordinate should be 50", 50, point.getX());

        // Test getY
        assertEquals("Y coordinate should be 75", 75, point.getY());
    }
    
    /**
     * Tests the toString method.
     */
    public void testToString() {
        // Test toString
        String expectedOutput = "TestPoint (50, 75)";
        assertEquals("toString should return the correct format", expectedOutput, point.toString());
    }
}
