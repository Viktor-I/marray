package org.viktori.matteray.function;

/**
 * Represents a function that accepts row and column indices and produces a
 * result.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #valueOf(int, int)}.
 *
 * @param <E> the type of the result of the function
 */
@FunctionalInterface
public interface MatrixIndexFunction<E> {
    /**
     * Applies this function to the given argument indices.
     *
     * @param rowIndex    the row index to apply the function to
     * @param columnIndex the column index to apply the function to
     * @return the function result
     */
    E valueOf(int rowIndex, int columnIndex);
}
