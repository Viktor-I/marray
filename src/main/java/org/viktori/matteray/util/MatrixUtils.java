package org.viktori.matteray.util;

import org.viktori.matteray.ImmutableMatrix;
import org.viktori.matteray.Matrix;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

public final class MatrixUtils {

    private MatrixUtils() {
        // static class
    }

    /**
     * Apply a function on each element of a matrix and return a new immutable matrix with the result.
     * There is no strict type requirement, so it is possible to return a different type. This works
     * similar to the {@link Stream#map} function.
     *
     * @param matrix   matrix to apply the function on
     * @param function function to apply on each element
     * @return a new immutable matrix based on the result of the function
     * @throws NullPointerException if the matrix or the function is null
     */
    public static <E, R> Matrix<R> applyForEach(Matrix<E> matrix, Function<E, R> function) {
        Objects.requireNonNull(matrix);
        Objects.requireNonNull(function);
        return new ImmutableMatrix<>(matrix.rows(), matrix.columns(), (r, c) -> function.apply(matrix.get(r, c)));
    }

    /**
     * Merge two matrices of equal size into a new immutable matrix with the result of the merge.
     * There is no strict requirements regarding type, so it is possible to have two matrices of
     * different type returning a third type. If the matrices are of different size, the resulting
     * matrix will be based on the size of the smallest width and height,
     * i.e. {@code min(matrix1.rows(), matrix2.rows())} and {@code min(matrix1.columns(), matrix2.columns())}.
     *
     * @param matrix1       first matrix to merge
     * @param matrix2       second matrix to merge
     * @param mergeFunction function to apply on each pair of elements
     * @return a new immutable array based on the result of the merge function
     * @throws NullPointerException if any of the arrays, or the function is null
     */
    public static <E1, E2, R> Matrix<R> mergeForEach(Matrix<E1> matrix1, Matrix<E2> matrix2, BiFunction<E1, E2, R> mergeFunction) {
        Objects.requireNonNull(matrix1);
        Objects.requireNonNull(matrix2);
        Objects.requireNonNull(mergeFunction);
        return new ImmutableMatrix<>(Math.min(matrix1.rows(), matrix2.rows()), Math.min(matrix1.columns(), matrix2.columns()), (r, c) -> mergeFunction.apply(matrix1.get(r, c), matrix2.get(r, c)));
    }
}
