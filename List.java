import java.util.Arrays;

/**
 * This class represents a generic List data structure.
 * 
 * @param <T> the type of elements in this list
 */
public class List<T> {
    /**
     * The size of the List (number of elements it contains).
     */
    private int size = 0;

    /**
     * The default capacity of the List.
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * The array used to store the elements in the List.
     */
    private Object elements[];

    /**
     * Constructs an empty List with an initial capacity of ten.
     */
    public List() {
        elements = new Object[DEFAULT_CAPACITY];
    }

    /**
     * Adds the specified element to the end of this List.
     * 
     * @param e element to be appended to this List
     */
    public void add(T e) {
        if (size == elements.length) {
            ensureCapacity();
        }
        elements[size++] = e;
    }

    /**
     * Returns the element at the specified position in this List.
     * 
     * @param i index of the element to return
     * @return the element at the specified position in this List
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    @SuppressWarnings("unchecked")
    public T get(int i) {
        if (i >= size || i < 0) {
            throw new IndexOutOfBoundsException("Index: " + i + ", Size " + i);
        }
        return (T) elements[i];
    }

    /**
     * Returns the number of elements in this List.
     * 
     * @return the number of elements in this List
     */
    public int size() {
        return size;
    }

    /**
     * Removes the element at the specified position in this List.
     * Shifts any subsequent elements to the left (subtracts one from their
     * indices).
     * 
     * @param i the index of the element to be removed
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public void remove(int i) {
        if (i >= size || i < 0) {
            throw new IndexOutOfBoundsException("Index: " + i + ", Size " + i);
        }
        Object temp = elements[i];
        int numElts = elements.length - (i + 1);
        System.arraycopy(elements, i + 1, elements, i, numElts);
        size--;
    }

    /**
     * Increases the capacity of this List instance, if necessary, to ensure
     * that it can hold at least the number of elements specified by the current
     * size.
     */
    private void ensureCapacity() {
        int newSize = elements.length * 2;
        elements = Arrays.copyOf(elements, newSize);
    }
}