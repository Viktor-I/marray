package org.viktori.matteray.function;

/**
 * Represents a function that accepts an index and produces a
 * result.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #valueOf(int)}.
 *
 * @param <E> the type of the result of the function
 */
@FunctionalInterface
public interface ArrayIndexFunction<E> {
    /**
     * Applies this function to the given argument index.
     *
     * @param index    the index to apply the function to
     * @return the function result
     */
    E valueOf(int index);
}
