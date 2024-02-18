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

    /**
     * Returns a new immutable matrix containing all the elements in the specified matrix, but
     * rotated based on the specified rotation.
     *
     * @param matrix matrix to rotate
     * @param rotation {@link Matrix.Rotation Rotation} to apply
     * @return a new matrix with the same elements, but rotated according to the specified rotation
     * @see Matrix.Rotation
     */
    public static <E> Matrix<E> toRotated(Matrix<E> matrix, Matrix.Rotation rotation) {
        int rows = matrix.rows();
        int columns = matrix.columns();
        return switch (rotation) {
            case NONE -> new ImmutableMatrix<>(rows, columns, (r, c) -> matrix.get(r, c));
            case LEFT -> new ImmutableMatrix<>(columns, rows, (r, c) -> matrix.get(c, columns - 1 - r));
            case HALF -> new ImmutableMatrix<>(rows, columns, (r, c) -> matrix.get(rows - 1 - r, columns - 1 - c));
            case RIGHT -> new ImmutableMatrix<>(columns, rows, (r, c) -> matrix.get(rows - 1 - c, r));
        };
    }

    /**
     * Returns a new immutable matrix containing all the elements in the specified matrix, but
     * mirrored based on the specified mirroring axis.
     *
     * @param matrix matrix to mirror
     * @param axis {@link Matrix.Axis Axis} to mirror over
     * @return a new immutable matrix with the same elements, but mirrored on the specified axis
     */
    public static <E> Matrix<E> toMirrored(Matrix<E> matrix, Matrix.Axis axis) {
        int rows = matrix.rows();
        int columns = matrix.columns();
        return switch (axis) {
            case ROWS -> new ImmutableMatrix<>(rows, columns, (r, c) -> matrix.get(r, columns - 1 - c));
            case COLUMNS -> new ImmutableMatrix<>(rows, columns, (r, c) -> matrix.get(rows - 1 - r, c));
        };
    }

    /**
     * Return a new matrix based on the mapping function for each position.
     *
     * @param matrix to map
     * @param mappingFunction function to map each element with
     * @return a new immutable matrix based on the mapping function
     */
    public static <E1, E2> Matrix<E2> toMapped(Matrix<E1> matrix, Function<E1, E2> mappingFunction) {
        return new ImmutableMatrix<>(matrix.rows(), matrix.columns(), (r, c) -> mappingFunction.apply(matrix.get(r, c)));
    }
}
