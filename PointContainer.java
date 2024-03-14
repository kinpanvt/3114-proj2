
class PointContainer {
    private Point[] points;
    private int size = 0;

    public PointContainer(int initialCapacity) {
        points = new Point[initialCapacity];
    }

    public void addPoint(Point point) {
        if (size == points.length) {
            resize();
        }
        points[size++] = point;
    }

    private void resize() {
        Point[] newPoints = new Point[points.length * 2];
        System.arraycopy(points, 0, newPoints, 0, points.length);
        points = newPoints;
    }

    // Getters
    public Point[] getPoints() {
        return points;
    }

    public int getSize() {
        return size;
    }
}
