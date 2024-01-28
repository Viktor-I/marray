package org.viktori.marray;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
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

    // List Iterators

    /**
     * Returns a list iterator over the elements in this array (in proper
     * sequence).
     *
     * @return a list iterator over the elements in this array (in proper
     * sequence)
     */
    ListIterator<E> listIterator();

    /**
     * Returns a list iterator over the elements in this array (in proper
     * sequence), starting at the specified position in the array.
     * The specified index indicates the first element that would be
     * returned by an initial call to {@link ListIterator#next next}.
     * An initial call to {@link ListIterator#previous previous} would
     * return the element with the specified index minus one.
     *
     * @param index index of the first element to be returned from the
     *              list iterator (by a call to {@link ListIterator#next next})
     * @return a list iterator over the elements in this list (in proper
     * sequence), starting at the specified position in the list
     * @throws IndexOutOfBoundsException if the index is out of range
     *                                   ({@code index < 0 || index > size()})
     */
    ListIterator<E> listIterator(int index);

    // View

    /**
     * Returns a new array of the portion of this array between the specified
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

}
