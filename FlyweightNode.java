import java.util.ArrayList;

public class FlyweightNode extends QuadTreeNode {
    private static final FlyweightNode INSTANCE = new FlyweightNode();

    private FlyweightNode() {
    }

    public static FlyweightNode getInstance() {
        return INSTANCE;
    }

    @Override
    public QuadTreeNode insert(Point point, int x, int y, int size) {
        LeafNode newNode = new LeafNode();
        return newNode.insert(point, x, y, size);
    }

    @Override
    public boolean remove(Point point, int x, int y, int size) {
        return false;
    }

    @Override
    public ArrayList<Point> search(String name) {
        return new ArrayList<>();
    }

    @Override
    public ArrayList<Point> regionSearch(int queryX, int queryY, int width, int height, int nodeX, int nodeY,
            int nodeSize) {
        return new ArrayList<>();
    }

    @Override
    public void dump(int level) {
        // dump
    }
}
