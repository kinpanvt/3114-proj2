/**
 * This class represents a point in a 2D space with a name.
 * Points can be compared by their names and have spatial coordinates (x, y).
 */
public class Point implements Comparable<Point>{
    // Fields for the name and coordinates of the point
    private String name;
    private int x, y;

    /**
     * Constructs a Point object with a given name and coordinates.
     * 
     * @param name the name of the point
     * @param x    the x-coordinate of the point
     * @param y    the y-coordinate of the point
     */
    public Point(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the name of the point.
     * 
     * @return the name of the point
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the x-coordinate of the point.
     * 
     * @return the x-coordinate of the point
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the y-coordinate of the point.
     * 
     * @return the y-coordinate of the point
     */
    public int getY() {
        return y;
    }

    /**
     * Returns a string representation of the point, including its name and coordinates.
     * 
     * @return a string representation of the point
     */
    @Override
    public String toString() {
        return String.format("%s (%d, %d)", name, x, y);
    }

    /**
     * Compares this point to another point based on the lexicographical order of their names.
     * 
     * @param other the point to be compared
     * @return a negative integer, zero, or a positive integer as this point's name
     *         is less than, equal to, or greater than the specified point's name.
     */
    @Override
    public int compareTo(Point other) {
        return this.name.compareTo(other.name);
    }
}
