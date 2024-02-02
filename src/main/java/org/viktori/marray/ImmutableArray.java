package org.viktori.marray;

import org.viktori.marray.function.ArrayIndexFunction;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.IntFunction;
import java.util.stream.Stream;

/**
 * Java array wrapper which implements the {@code Array} interface, which is immutable and easier
 * to work with than normal arrays as it is more similar to a {@link List} with
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
public class ImmutableArray<E> implements Array<E>, Cloneable {

    private static final Object[] EMPTY_ARRAY = new Object[0];

    private final Object[] elementData;
    private final int size;

    /**
     * Constructs an immutable array with the specified length, and function
     * to populate values with.
     *
     * @param length       the length of the array
     * @param initFunction the function to initialize values in the array
     * @throws IllegalArgumentException if the specified initial capacity
     *                                  is negative
     */
    public ImmutableArray(int length, ArrayIndexFunction<E> initFunction) {
        this(initiateArrayFromFunction(length, initFunction), true);
    }

    private static <E> Object[] initiateArrayFromFunction(int length, ArrayIndexFunction<E> initFunction) {
        if (length > 0) {
            Object[] elementData = new Object[length];
            for (int i = 0; i < length; i++) {
                elementData[i] = initFunction.valueOf(i);
            }
            return elementData;
        } else if (length == 0) {
            return EMPTY_ARRAY;
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
        this(elementData, false);
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
        this(collection instanceof ImmutableArray<?> ia ? ia.elementData : collection.toArray(), true);
    }

    protected ImmutableArray() {
        this.elementData = EMPTY_ARRAY;
        this.size = 0;
    }

    protected ImmutableArray(Object[] elementData, boolean trusted) {
        if (elementData.length == 0) {
            this.elementData = EMPTY_ARRAY;
        } else if (trusted) {
            this.elementData = elementData;
        } else {
            this.elementData = new Object[elementData.length];
            System.arraycopy(elementData, 0, this.elementData, 0, elementData.length);
        }
        this.size = elementData.length;
    }

    /**
     * Returns the element at the specified position in this array.
     *
     * @param index index of the element to return
     * @return the element at the specified position in this array
     * @throws ArrayIndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public E get(int index) {
        return (E) elementData[index];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
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
            for (int i = 0; i < size; i++) {
                if (es[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
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
            for (int i = size - 1; i >= 0; i--) {
                if (es[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = size - 1; i >= 0; i--) {
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
        return Arrays.copyOf(elementData, size);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            // Make a new array of a's runtime type, but my contents:
            return (T[]) Arrays.copyOf(elementData, size, a.getClass());
        }
        for (int i = 0; i < a.length; i++) {
            a[i] = (T) elementData[i];
        }
        if (a.length > size) {
            a[size] = null;
        }
        return a;
    }

    @Override
    public <T> T[] toArray(IntFunction<T[]> generator) {
        return toArray(generator.apply(size));
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<E> toList() {
        return List.of((E[]) elementData);
    }

    @Override
    public Array<E> subArray(int fromIndex, int toIndex) {
        if (fromIndex == 0 && toIndex == size) {
            return this;
        }

        subArrayRangeCheck(fromIndex, toIndex, size);
        return new ImmutableArray<>(Arrays.copyOfRange(elementData, fromIndex, toIndex), true);
    }

    private static void subArrayRangeCheck(int fromIndex, int toIndex, int size) {
        if (fromIndex < 0) {
            throw new ArrayIndexOutOfBoundsException("fromIndex = " + fromIndex);
        }
        if (toIndex > size) {
            throw new ArrayIndexOutOfBoundsException("toIndex = " + toIndex);
        }
        if (fromIndex > toIndex) {
            throw new IllegalArgumentException("fromIndex(" + fromIndex +
                    ") > toIndex(" + toIndex + ")");
        }
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
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            // This shouldn't happen
            throw new InternalError(e);
        }
    }
}
