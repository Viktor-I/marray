package org.viktori.marray;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Spliterator;
import java.util.function.IntFunction;
import java.util.stream.Stream;

/**
 * Java array wrapper which implements the {@code Array} interface, which is immutable and easier
 * to work with than normal arrays as it is more similar to an ArrayList with
 * fixed size.
 * The class implements all immutable Collection operations, and permits all elements,
 * including {@code null}. Mutable operations such as {@code add} or {@code remove} will result
 * in an {@link UnsupportedOperationException}.
 *
 * <p>The {@code size}, {@code isEmpty}, {@code get}, {@code set},
 * {@code iterator}, and {@code listIterator} operations run in constant
 * time.
 * <p>
 * Due to its immutable nature, the implementation is thread safe.
 *
 * @param <E> the type of elements in this array
 * @author Viktor Ingemansson
 * @see Collection
 * @see Array
 */
public class ImmutableArray<E> implements Array<E> {

    private static final Object[] EMPTY_ARRAY = new Object[0];

    private final Object[] elementData;

    /**
     * Constructs an immutable array with the specified length, and function
     * to populate values with.
     *
     * @param length the length of the array
     * @throws IllegalArgumentException if the specified initial capacity
     *                                  is negative
     */
    public ImmutableArray(int length, IntFunction<E> initFunction) {
        if (length > 0) {
            this.elementData = new Object[length];
            for (int i = 0; i < length; i++) {
                this.elementData[i] = initFunction.apply(i);
            }
        } else if (length == 0) {
            this.elementData = EMPTY_ARRAY; // Saves memory
        } else {
            throw new IllegalArgumentException("Illegal Length: " + length);
        }
    }

    /**
     * Constructs an immutable array based on the specified arguments.
     * Note that the array is shallow copied internally to guarantee immutability.
     *
     * @param elementData the raw array of data to hold
     */
    @SafeVarargs
    public ImmutableArray(E... elementData) {
        if (elementData.length > 0) {
            this.elementData = new Object[elementData.length];
            System.arraycopy(elementData, 0, this.elementData, 0, elementData.length);
        } else {
            this.elementData = EMPTY_ARRAY; // Saves memory
        }
    }

    /**
     * Constructs an immutable array containing the elements of the specified
     * collection, in the order they are returned by the collection's
     * iterator.
     *
     * @param collection the collection whose elements are to be placed into this list
     * @throws NullPointerException if the specified collection is null
     */
    public ImmutableArray(Collection<? extends E> collection) {
        if (collection instanceof ImmutableArray<?> ia) {
            this.elementData = ia.elementData;
        } else if (collection.size() > 0) {
            this.elementData = collection.toArray();
        } else {
            this.elementData = EMPTY_ARRAY;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public E get(int index) {
        return (E) elementData[index];
    }

    @Override
    public int size() {
        return elementData.length;
    }

    @Override
    public boolean isEmpty() {
        return elementData.length == 0;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        for (Object e : collection) {
            if (!contains(e)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns the index of the first occurrence of the specified element
     * in this array, or -1 if this array does not contain the element.
     * More formally, returns the lowest index {@code i} such that
     * {@code Objects.equals(o, get(i))},
     * or -1 if there is no such index.
     */
    public int indexOf(Object o) {
        Object[] es = elementData;
        if (o == null) {
            for (int i = 0; i < elementData.length; i++) {
                if (es[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < elementData.length; i++) {
                if (o.equals(es[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * Returns the index of the last occurrence of the specified element
     * in this array, or -1 if this array does not contain the element.
     * More formally, returns the highest index {@code i} such that
     * {@code Objects.equals(o, get(i))},
     * or -1 if there is no such index.
     */
    public int lastIndexOf(Object o) {
        Object[] es = elementData;
        if (o == null) {
            for (int i = elementData.length - 1; i >= 0; i--) {
                if (es[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = elementData.length - 1; i >= 0; i--) {
                if (o.equals(es[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public Iterator<E> iterator() {
        return toList().iterator();
    }

    @Override
    public Spliterator<E> spliterator() {
        return toList().spliterator();
    }

    @Override
    public ListIterator<E> listIterator() {
        return toList().listIterator();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return toList().listIterator(index);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Stream<E> stream() {
        return Stream.of((E[]) elementData);
    }

    @Override
    public Stream<E> parallelStream() {
        return stream().parallel();
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(elementData, elementData.length);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < elementData.length)
            // Make a new array of a's runtime type, but my contents:
            return (T[]) Arrays.copyOf(elementData, elementData.length, a.getClass());
        System.arraycopy(elementData, 0, a, 0, elementData.length);
        if (a.length > elementData.length)
            a[elementData.length] = null;
        return a;
    }

    @Override
    public <T> T[] toArray(IntFunction<T[]> generator) {
        return toArray(generator.apply(elementData.length));
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<E> toList() {
        return List.of((E[]) elementData);
    }

    @Override
    public boolean add(E t) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(elementData);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (o == null) {
            return false;
        } else if (o instanceof ImmutableArray<?> ia && ia.size() == size()) {
            return Arrays.equals(elementData, ia.elementData);
        } else if (o instanceof Array<?> a && a.size() == size()) {
            return Arrays.equals(elementData, a.toArray());
        }
        return false;
    }

    @Override
    public String toString() {
        return Arrays.toString(elementData);
    }

    /**
     * Returns a shallow copy of this {@code ImmutableArray} instance.  (The
     * elements themselves are not copied.)
     *
     * @return a clone of this {@code ImmutableArray} instance
     */
    @Override
    public Object clone() {
        return new ImmutableArray<>(elementData);
    }
}
