package org.viktori.matteray;

import org.viktori.matteray.function.ArrayIndexFunction;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Predicate;

public interface Array<E> extends Collection<E> {

    // Positional Access Operation

    /**
     * Get the element at a given index in this list.
     *
     * @param index the index of the element to be returned
     * @return the element at index index in this array
     * @throws ArrayIndexOutOfBoundsException if index &lt; 0 || index &gt;= size()
     */
    E get(int index);

    // Search Operations

    /**
     * Returns the index of the first occurrence of the specified element
     * in this list, or -1 if this list does not contain the element.
     * More formally, returns the lowest index {@code i} such that
     * {@code Objects.equals(o, get(i))},
     * or -1 if there is no such index.
     *
     * @param o element to search for
     * @return the index of the first occurrence of the specified element in
     * this list, or -1 if this list does not contain the element
     * @throws ClassCastException   if the type of the specified element
     *                              is incompatible with this list
     *                              (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified element is null and this
     *                              list does not permit null elements
     *                              (<a href="Collection.html#optional-restrictions">optional</a>)
     */
    int indexOf(Object o);

    /**
     * Returns the index of the last occurrence of the specified element
     * in this list, or -1 if this list does not contain the element.
     * More formally, returns the highest index {@code i} such that
     * {@code Objects.equals(o, get(i))},
     * or -1 if there is no such index.
     *
     * @param o element to search for
     * @return the index of the last occurrence of the specified element in
     * this list, or -1 if this list does not contain the element
     * @throws ClassCastException   if the type of the specified element
     *                              is incompatible with this list
     *                              (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified element is null and this
     *                              list does not permit null elements
     *                              (<a href="Collection.html#optional-restrictions">optional</a>)
     */
    int lastIndexOf(Object o);

    // View

    /**
     * Returns an array of the portion of this array between the specified
     * {@code fromIndex}, inclusive, and {@code toIndex}, exclusive.  (If
     * {@code fromIndex} and {@code toIndex} are equal, the returned array is
     * empty.)
     *
     * @param fromIndex low endpoint (inclusive) of the subArray
     * @param toIndex   high endpoint (exclusive) of the subArray
     * @return a view of the specified range within this array
     * @throws ArrayIndexOutOfBoundsException for an illegal endpoint index value
     *                                        ({@code fromIndex < 0 || toIndex > size})
     * @throws IllegalArgumentException       if the endpoint indices are out of order
     *                                        {@code (fromIndex > toIndex)}
     */
    Array<E> subArray(int fromIndex, int toIndex);

    // Query Operations

    /**
     * Returns a list containing all of the elements in this array in proper
     * sequence (from first to last element).
     *
     * <p>The returned list will be "safe" in that no references to it are
     * maintained by this list.  (In other words, this method must
     * allocate a list if the class is mutable).
     *
     * <p>This method acts as bridge between array-based and list-based
     * APIs.
     *
     * @return a list containing all of the elements in this array in proper
     * sequence
     * @see Arrays#asList(Object[])
     */
    List<E> toList();

    // Comparison and hashing

    /**
     * Compares the specified object with this array for equality.  Returns
     * {@code true} if and only if the specified object is also a array
     * implementing this interface, both arrays have the same size, and all
     * corresponding pairs of elements in the two arrays are <i>equal</i>.
     * (Two elements {@code e1} and {@code e2} are <i>equal</i> if
     * {@code Objects.equals(e1, e2)}.). In other words, two arrays are defined to be
     * equal if they contain the same elements in the same order.  This
     * definition ensures that the equals method works properly across
     * different implementations of the {@code Array} interface.
     *
     * @param o the object to be compared for equality with this array
     * @return {@code true} if the specified object is equal to this array
     */
    boolean equals(Object o);

    /**
     * Returns the hash code value for this array.  The hash code of an array
     * is defined to be the result of the following calculation:
     * <pre>{@code
     *     int hashCode = 1;
     *     for (E e : array)
     *         hashCode = 31*hashCode + (e==null ? 0 : e.hashCode());
     * }</pre>
     * This ensures that {@code array1.equals(array2)} implies that
     * {@code array1.hashCode()==array2.hashCode()} for any two arrays,
     * {@code array1} and {@code array2}, as required by the general
     * contract of {@link Object#hashCode}.
     *
     * @return the hash code value for this array
     * @see Object#equals(Object)
     * @see #equals(Object)
     */
    int hashCode();

    /**
     * Adding an object to a fixed size array is not supported, but it is part of the
     * {@link Collection} API. This action will always throw an {@link UnsupportedOperationException}.
     *
     * @param e element whose presence in this collection is to be ensured
     * @return nothing, as it will always throw an exception
     * @throws UnsupportedOperationException always
     */
    @Override
    default boolean add(E e) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    /**
     * Adding objects to a fixed size array is not supported, but it is part of the
     * {@link Collection} API. This action will always throw an {@link UnsupportedOperationException}.
     *
     * @param c – collection containing elements to be added to this collection
     * @return nothing, as it will always throw an exception
     * @throws UnsupportedOperationException always
     */
    @Override
    default boolean addAll(Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }

    /**
     * Removing an object from a fixed size array is not supported, but it is part of the
     * {@link Collection} API. This action will always throw an {@link UnsupportedOperationException}.
     *
     * @param o element to be removed from this collection, if present
     * @return nothing, as it will always throw an exception
     * @throws UnsupportedOperationException always
     */
    @Override
    default boolean remove(Object o) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    /**
     * Removing objects from a fixed size array is not supported, but it is part of the
     * {@link Collection} API. This action will always throw an {@link UnsupportedOperationException}.
     *
     * @param filter a predicate which returns {@code true} for elements to be removed
     * @return nothing, as it will always throw an exception
     * @throws UnsupportedOperationException always
     */
    @Override
    default boolean removeIf(Predicate<? super E> filter) {
        throw new UnsupportedOperationException();
    }

    /**
     * Removing objects from a fixed size array is not supported, but it is part of the
     * {@link Collection} API. This action will always throw an {@link UnsupportedOperationException}.
     *
     * @param c collection containing elements to be removed from this collection
     * @return nothing, as it will always throw an exception
     * @throws UnsupportedOperationException always
     */
    @Override
    default boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    /**
     * Removing objects from a fixed size array is not supported, but it is part of the
     * {@link Collection} API. This action will always throw an {@link UnsupportedOperationException}.
     *
     * @param c collection containing elements to be retained in this collection
     * @return nothing, as it will always throw an exception
     * @throws UnsupportedOperationException always
     */
    @Override
    default boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    /**
     * Removing objects from a fixed size array is not supported, but it is part of the
     * {@link Collection} API. This action will always throw an {@link UnsupportedOperationException}.
     *
     * @throws UnsupportedOperationException always
     */
    @Override
    default void clear() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns an immutable array containing zero elements.
     *
     * @param <E> the {@code Array}'s element type
     * @return an empty {@code Array}
     */
    static <E> Array<E> of() {
        return new ImmutableArray<>();
    }

    /**
     * Returns an immutable array containing one non-null element.
     *
     * @param <E> the {@code Array}'s element type
     * @param e1  the single element
     * @return an {@code Array} containing the specified element
     * @throws NullPointerException if the element is {@code null}
     */
    static <E> Array<E> of(E e1) {
        return new ImmutableArray<>(requireNonNull(e1), true);
    }

    /**
     * Returns an immutable array containing two non-null elements.
     *
     * @param <E> the {@code Array}'s element type
     * @param e1  the first element
     * @param e2  the second element
     * @return an {@code Array} containing the specified elements
     * @throws NullPointerException if an element is {@code null}
     */
    static <E> Array<E> of(E e1, E e2) {
        return new ImmutableArray<>(requireNonNull(e1, e2), true);
    }

    /**
     * Returns an immutable array containing three non-null elements.
     *
     * @param <E> the {@code Array}'s element type
     * @param e1  the first element
     * @param e2  the second element
     * @param e3  the third element
     * @return an {@code Array} containing the specified elements
     * @throws NullPointerException if an element is {@code null}
     */
    static <E> Array<E> of(E e1, E e2, E e3) {
        return new ImmutableArray<>(requireNonNull(e1, e2, e3), true);
    }

    /**
     * Returns an immutable array containing four non-null elements.
     *
     * @param <E> the {@code Array}'s element type
     * @param e1  the first element
     * @param e2  the second element
     * @param e3  the third element
     * @param e4  the fourth element
     * @return an {@code Array} containing the specified elements
     * @throws NullPointerException if an element is {@code null}
     */
    static <E> Array<E> of(E e1, E e2, E e3, E e4) {
        return new ImmutableArray<>(requireNonNull(e1, e2, e3, e4), true);
    }

    /**
     * Returns an immutable array containing five non-null elements.
     *
     * @param <E> the {@code Array}'s element type
     * @param e1  the first element
     * @param e2  the second element
     * @param e3  the third element
     * @param e4  the fourth element
     * @param e5  the fifth element
     * @return an {@code Array} containing the specified elements
     * @throws NullPointerException if an element is {@code null}
     */
    static <E> Array<E> of(E e1, E e2, E e3, E e4, E e5) {
        return new ImmutableArray<>(requireNonNull(e1, e2, e3, e4, e5), true);
    }

    /**
     * Returns an immutable array containing an arbitrary number of non-null elements.
     *
     * @param <E>      the {@code Array}'s element type
     * @param elements the elements to be contained in the array
     * @return an {@code Array} containing the specified elements
     * @throws NullPointerException if an element is {@code null} or if the array is {@code null}
     * @apiNote This method also accepts a single raw array as an argument. The element type of
     * the resulting array will be the component type of the raw array, and the size of
     * the array will be equal to the length of the array. To create an array with
     * a single element that is a raw array, do the following:
     *
     * <pre>{@code
     *     String[] strings = ... ;
     *     Array<String[]> array = Array.<String[]>of(strings);
     * }</pre>
     * <p>
     * This will cause the {@link Array#of(Object) Array.of(E)} method
     * to be invoked instead.
     */
    @SafeVarargs
    static <E> Array<E> of(E... elements) {
        return new ImmutableArray<>(requireNonNull((Object[]) elements), false);
    }

    /**
     * Returns an immutable array with the specified length, and function
     * to populate values with.
     *
     * @param length       the length of the array
     * @param initFunction the function to initialize values in the array
     * @throws NullPointerException     if an element is {@code null} or if the initFunction is {@code null}
     * @throws IllegalArgumentException if the specified initial capacity
     *                                  is negative
     */
    static <E> Array<E> of(int length, ArrayIndexFunction<E> initFunction) {
        return requireNonNull(new ImmutableArray<>(length, initFunction));
    }

    /**
     * Returns an immutable array containing the elements of
     * the given Collection, in its iteration order. The given Collection must not be null,
     * and it must not contain any null elements. If the given Collection is subsequently
     * modified, the returned Array will not reflect such modifications.
     *
     * @param <E>  the {@code Array}'s element type
     * @param coll a {@code Collection} from which elements are drawn, must be non-null
     * @return an {@code Array} containing the elements of the given {@code Collection}
     * @throws NullPointerException if coll is null, or if it contains any nulls
     * @implNote If the given Collection is an immutable array,
     * calling copyOf may return the same instance, as it is safe for reuse.
     */
    static <E> Array<E> copyOf(Collection<E> coll) {
        if (coll instanceof ImmutableArray<E> ia) {
            return requireNonNull(ia);
        }
        return new ImmutableArray<>(requireNonNull(coll));
    }

    private static Object[] requireNonNull(Object... elements) {
        for (Object e : elements) {
            Objects.requireNonNull(e);
        }
        return elements;
    }

    private static <C extends Collection<?>> C requireNonNull(C coll) {
        Objects.requireNonNull(coll);
        for (Object e : coll) {
            Objects.requireNonNull(e);
        }
        return coll;
    }

    final class ArrayIterator<E> implements Iterator<E> {
        private final Array<E> array;
        int index = 0;

        ArrayIterator(final Array<E> array) {
            this.array = array;
        }

        @Override
        public boolean hasNext() {
            return index < array.size();
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return array.get(index++);
        }
    }
}
