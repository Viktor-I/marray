package org.viktori.matteray;

import org.viktori.matteray.function.ArrayIndexFunction;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.IntFunction;
import java.util.stream.Stream;

/**
 * Java array wrapper which implements the {@code Array} interface, which is immutable and easier
 * to work with than normal arrays.
 * <p>
 * The class implements all immutable List operations, and permits all elements,
 * including {@code null}. Mutable operations such as {@code set}, {@code add} or {@code remove}
 * will result in an {@link UnsupportedOperationException}.
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
public class ImmutableArray<E> implements Array<E>, Cloneable, Serializable {

    @java.io.Serial
    private static final long serialVersionUID = 2683452586122892159L;

    /**
     * Shared empty array
     */
    private static final Object[] EMPTY_ARRAY = new Object[0];

    /**
     * Raw array to hold the elements
     */
    private final Object[] elementData;

    /**
     * Length of the array.
     */
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

    /**
     * Internal constructor to create an empty array.
     */
    protected ImmutableArray() {
        this.elementData = EMPTY_ARRAY;
        this.size = 0;
    }

    /**
     * Internal constructor to create an array based on a raw array, which also gives you
     * the ability to trust it. When trusted, the array will not be cloned. This can be used when we
     * know the array cannot be modified from the outside.
     *
     * @param elementData the raw array of data to hold
     * @param trusted if the array is trusted, it will use the array as it is without cloning it.
     */
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

    /**
     * Setting an object in an immutable array is not supported, but it is part of the
     * {@link List} API. This action will always throw an {@link UnsupportedOperationException}.
     *
     * @param index index of the element to replace
     * @param element element to be stored at the specified position
     * @return nothing, as it will always throw an exception
     * @throws UnsupportedOperationException always
     */
    @Override
    public E set(int index, E element) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }


    /**
     * Returns the number of elements in this array.
     *
     * @return the number of elements in this array
     */
    @Override
    public int size() {
        return size;
    }


    /**
     * Returns {@code true} if this array contains no elements.
     *
     * @return {@code true} if this array contains no elements
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns {@code true} if this array contains the specified element.
     * More formally, returns {@code true} if and only if this array contains
     * at least one element {@code e} such that
     * {@code Objects.equals(o, e)}.
     *
     * @param o element whose presence in this array is to be tested
     * @return {@code true} if this array contains the specified element
     */
    @Override
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    /**
     * Returns {@code true} if this array contains all of the elements of the
     * specified collection.
     *
     * @param  collection collection to be checked for containment in this array
     * @return {@code true} if this array contains all of the elements of the
     *         specified collection
     * @see #contains(Object)
     */
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
    public ListIterator<E> listIterator() {
        return new ArrayListIterator<>(this);
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new ArrayListIterator<>(this, index);
    }

    @Override
    public Iterator<E> iterator() {
        return new ArrayIterator<>(this);
    }

    @Override
    public Spliterator<E> spliterator() {
        return Spliterators.spliterator(this, Spliterator.IMMUTABLE | Spliterator.ORDERED);
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
    public Array<E> subList(int fromIndex, int toIndex) {
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
