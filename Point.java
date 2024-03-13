/**
 * Represents a point in a two-dimensional space with a name identifier.
 * This class is immutable, meaning once an instance is created, its state
 * cannot be changed.
 * 
 * @author kinjalpandey, architg03
 * @version 02/25/24
 */
public class Point {
    /**
     * The name identifier for the point.
     */
    private final String name;

    /**
     * The x-coordinate of the point.
     */
    private final int x;

    /**
     * The y-coordinate of the point.
     */
    private final int y;

    /**
     * Constructs a new Point with the specified name, x-coordinate, and
     * y-coordinate.
     *
     * @param name
     *            The name identifier for the point.
     * @param x
     *            The x-coordinate of the point.
     * @param y
     *            The y-coordinate of the point.
     */
    public Point(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }


    /**
     * Returns the name of the point.
     *
     * @return The name identifier of the point.
     */
    public String getName() {
        return name;
    }


    /**
     * Returns the x-coordinate of the point.
     *
     * @return The x-coordinate.
     */
    public int getX() {
        return x;
    }


    /**
     * Returns the y-coordinate of the point.
     *
     * @return The y-coordinate.
     */
    public int getY() {
        return y;
    }


    /**
     * Returns a string representation of the point in the format "name (x, y)".
     *
     * @return The string representation of the point.
     */
    @Override
    public String toString() {
        return name + " " + x + " " + y;
    }
}
