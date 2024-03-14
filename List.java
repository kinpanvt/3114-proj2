import java.util.Arrays;

public class List<T> {
    private int size = 0;
    private static final int DEFAULT_CAPACITY = 10;
    private Object elements[];

    public List() {
        elements = new Object[DEFAULT_CAPACITY];
    }

    public void add(T e) {
        if (size == elements.length) {
            ensureCapacity();
        }
        elements[size++] = e;
    }

    @SuppressWarnings("unchecked")
    public T get(int i) {
        if (i >= size || i < 0) {
            throw new IndexOutOfBoundsException("Index: " + i + ", Size " + i);
        }
        return (T) elements[i];
    }

    public int size() {
        return size;
    }

    public void remove(int i) {
        if (i >= size || i < 0) {
            throw new IndexOutOfBoundsException("Index: " + i + ", Size " + i);
        }
        Object temp = elements[i];
        int numElts = elements.length - (i + 1);
        System.arraycopy(elements, i + 1, elements, i, numElts);
        size--;
    }

    private void ensureCapacity() {
        int newSize = elements.length * 2;
        elements = Arrays.copyOf(elements, newSize);
    }
}