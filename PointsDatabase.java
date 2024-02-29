import java.util.ArrayList;
import java.util.List;

/**
 * This be the javadoc to be done
 * 
 * @author kinjalpandey, architg03
 * @version 02/28/2024
 * 
 */
public class PointsDatabase {
    private SkipList<String, Point> skipList;
    private PRQuadTree quadTree;
    private static final int WORLD_SIZE = 1024;

    /**
     * init
     */
    public PointsDatabase() {
        this.skipList = new SkipList<>();
        this.quadTree = new PRQuadTree(WORLD_SIZE);
    }

    /**
     * to do
     * @param args stuff
     */
    public static void main(String[] args) {

    }

    /**
     * gets Points From SkipList
     * @param name name of list
     * @return List<Point> list
     */
    public List<Point> getPointsFromSkipList(String name) {
        List<KVPair<String, Point>> pairs = skipList.search(name);
        List<Point> points = new ArrayList<>();
        for (KVPair<String, Point> pair : pairs) {
            points.add(pair.getValue());
        }
        return points;
    }

    /**
     * gets Points From SkipList
     * @param name name of list
     * @return List<Point> list
     */
    public List<Point> getPointsByName(String name) {
        List<KVPair<String, Point>> searchResults = skipList.search(name);
        List<Point> points = new ArrayList<>();
        for (KVPair<String, Point> pair : searchResults) {
            points.add(pair.getValue());
        }
        return points;
    }

    /**
     * insert 
     * @param name of rectangle
     * @param x coord
     * @param y coord
     */
    public void insert(String name, int x, int y) {
        if (x < 0 || y < 0 || x >= WORLD_SIZE || y >= WORLD_SIZE) {
            System.out.println("Point " + name
                + " out of bounds and cannot be inserted.");
            return;
        }

        Point point = new Point(name, x, y);
        // Check for existing point with the same name
        if (!skipList.search(name).isEmpty()) {
            System.out.println("A point with name " + name
                + " already exists.");
            return;
        }

        // Create a KVPair object for the insertion
        KVPair<String, Point> pair = new KVPair<>(name, point);
        skipList.insert(pair); // Now we're passing a KVPair as expected by the
                               // SkipList.insert method
        quadTree.insert(point);
        System.out.println("Point inserted: " + point);
    }

    /**
     * remove
     * @param name name
     */
    public void remove(String name) {
        List<Point> points = getPointsFromSkipList(name); // Use the utility
                                                          // method
        if (points.isEmpty()) {
            System.out.println("Point with name " + name + " does not exist.");
            return;
        }

        Point pointToRemove = points.get(0);
        skipList.remove(name); // Assuming removal by key
        quadTree.remove(pointToRemove); // Assuming direct removal by Point
                                        // object is supported
        System.out.println("Point removed: " + pointToRemove);
    }

    /**
     * remove
     * @param x coord
     * @param y coord
     */
    public void remove(int x, int y) {
        // Assuming PRQuadTree provides a method to directly find points by
        // coordinates
        @SuppressWarnings("unchecked")
        List<Point> points = (List<Point>)quadTree.searchByCoordinates(x, y);
        if (points.isEmpty()) {
            System.out.println("No point found at (" + x + ", " + y + ")");
            return;
        }

        // Assuming we're dealing with unique points or just removing the first
        // found
        Point pointToRemove = points.get(0);
        quadTree.remove(pointToRemove); // Adjusted for hypothetical direct
                                        // removal
        skipList.remove(pointToRemove.getName());
        System.out.println("Point removed: " + pointToRemove);
    }

    /**
     * Region Search
     * @param x coord
     * @param y coord
     * @param width coord
     * @param height coord
     */
    public void regionSearch(int x, int y, int width, int height) {
        if (width <= 0 || height <= 0) {
            System.out.println("Invalid region dimensions.");
            return;
        }

        List<Point> points = quadTree.regionSearch(x, y, width, height);
        System.out.println("Region search found " + points.size() + " points:");
        for (Point point : points) {
            System.out.println(point);
        }
    }

    /**
     * dupe
     */
    public void duplicates() {
        // Assuming you've implemented or will implement findDuplicates in
        // PRQuadTree
        List<Point> duplicates = quadTree.findDuplicates();
        if (duplicates.isEmpty()) {
            System.out.println("No duplicates found.");
            return;
        }

        for (Point duplicate : duplicates) {
            System.out.println("Duplicate point: " + duplicate);
        }
    }

    /**
     * Search
     * @param name rec
     */
    public void search(String name) {
        // Directly call the skipList.search(name) which returns
        // ArrayList<KVPair<String, Point>>
        List<KVPair<String, Point>> pairs = skipList.search(name);

        // Check if the search result is empty
        if (pairs.isEmpty()) {
            System.out.println("No point found with name " + name);
            return;
        }

        System.out.println("Search results for '" + name + "':");
        // Iterate through the list of KVPair<String, Point>, extracting and
        // printing each Point
        for (KVPair<String, Point> pair : pairs) {
            // Extract the Point from the KVPair and print it
            Point point = pair.getValue(); // Get the Point object from the
                                           // KVPair
            System.out.println(point); // Assuming Point class has a meaningful
                                       // toString() method
        }
    }

    /**
     * Dumps
     */
    public void dump() {
        System.out.println("SkipList contents:");
        skipList.dump(); // Assuming dump method in SkipList prints its contents

        System.out.println("PRQuadTree contents:");
        quadTree.dump(); // Implement this method in PRQuadTree to print its
                         // contents
    }
}
