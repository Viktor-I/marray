package org.viktori.matteray.util;

import org.viktori.matteray.Array;
import org.viktori.matteray.ImmutableArray;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

public final class ArrayUtils {

    private ArrayUtils() {
        // static class
    }

    /**
     * Apply a function on each element of an array and return a new immutable array with the result.
     * There is no strict type requirement, so it is possible to return a different type. This works
     * similar to the {@link Stream#map} function.
     *
     * @param array    array to apply the function on
     * @param function function to apply on each element
     * @return a new immutable array based on the result of the function
     * @throws NullPointerException if the array or the function is null
     */
    public static <E, R> Array<R> applyForEach(Array<E> array, Function<E, R> function) {
        Objects.requireNonNull(array);
        Objects.requireNonNull(function);
        return new ImmutableArray<>(array.size(), i -> function.apply(array.get(i)));
    }

    /**
     * Merge two arrays of equal size into a new immutable array with the result of the merge.
     * There is no strict requirements regarding type, so it is possible to have two arrays of
     * different type returning a third type. If the arrays are of different size, the resulting
     * array will be based on the size of the shortest array,
     * i.e. {@code min(array1.size(), array2.size())}.
     *
     * @param array1 first array to merge
     * @param array2 second array to merge
     * @param mergeFunction function to apply on each pair of elements
     * @return a new immutable array based on the result of the merge function
     * @throws NullPointerException if any of the arrays, or the function is null
     */
    public static <E1, E2, R> Array<R> mergeForEach(Array<E1> array1, Array<E2> array2, BiFunction<E1, E2, R> mergeFunction) {
        Objects.requireNonNull(array1);
        Objects.requireNonNull(array2);
        Objects.requireNonNull(mergeFunction);
        return new ImmutableArray<>(Math.min(array1.size(), array2.size()), i -> mergeFunction.apply(array1.get(i), array2.get(i)));
    }

    /**
     * Sort a comparable array according to its natural order.
     * @param array array to sort
     * @return a new immutable array, sorted by its natural order
     */
    public static <E extends Comparable<E>> Array<E> toSorted(Array<E> array) {
        Object[] rawArray = array.toArray();
        Arrays.sort(rawArray);
        return new SortedImmutableArray<>(rawArray);
    }

    /**
     * Sort a comparable array based on a comparator.
     * @param array array to sort
     * @return a new immutable array, sorted according to comparator
     */
    @SuppressWarnings("unchecked")
    public static <E> Array<E> toSorted(Array<E> array, Comparator<? super E> comparator) {
        Object[] rawArray = array.toArray();
        Arrays.sort(rawArray, (Comparator<Object>) Objects.requireNonNull(comparator));
        return new SortedImmutableArray<>(rawArray);
    }

    private static class SortedImmutableArray<E> extends ImmutableArray<E> {
        private SortedImmutableArray(Object[] elements) {
            super(elements, true);
        }
    }

    /**
     * Return a new array where the indices are reversed.
     * <p>Example: [15, 20, 10] -> [10, 20, 15]</p>
     *
     * @param array array to reverse
     * @return a new immutable array where indices are reversed
     */
    public static <E extends Comparable<E>> Array<E> toReversed(Array<E> array) {
        return new ImmutableArray<>(array.size(), i -> array.get(array.size() - 1 - i));
    }
}
