package org.viktori.marray;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Predicate;

public interface Matrix<E> extends Collection<E> {

    // Positional Access Operation

    /**
     * Get the element at a given index in this list.
     *
     * @param rowIndex the row index of the element to be returned
     * @param columnIndex the column index of the element to be returned
     * @return the element at (rowIndex, columnIndex) in this matrix
     * @throws ArrayIndexOutOfBoundsException if any index &lt; 0 || index &gt;= rows()/columns()
     */
    E get(int rowIndex, int columnIndex);

    // View

    /**
     * Returns a new matrix of the portion of this matrix between the specified
     * {@code fromRow}, inclusive, and {@code toRow}, exclusive, as well as the specified
     * {@code fromColumn}, inclusive, and {@code toColumn}, exclusive. (If
     * {@code fromRow} and {@code toRow} are equal, or {@code fromColumn and @toColumn} are equal
     * , the returned matrix is empty.)
     *
     * @param fromRow low row endpoint (inclusive) of the subMatrix
     * @param toRow   high row endpoint (exclusive) of the subMatrix
     * @param fromColumn low column endpoint (inclusive) of the subMatrix
     * @param toColumn high row endpoint (exclusive) of the subMatrix
     * @return a view of the specified rectangle within this matrix
     * @throws ArrayIndexOutOfBoundsException for an illegal endpoint index value
     *                                        ({@code fromIndex < 0 || toIndex > rows/columns})
     * @throws IllegalArgumentException       if the endpoint indices are out of order
     *                                        {@code (fromIndex > toIndex)}
     */
    Matrix<E> subMatrix(int fromRow, int toRow, int fromColumn, int toColumn);

    /**
     * Returns an array containing all of the elements in this matrix for
     * a specified row index, in proper column sequence (from first to last element).
     *
     * <p>The returned array will act as a view into the current matrix.
     * (In other words, if this matrix is mutable, changes must be reflected in the
     * returned array).
     *
     * @param rowIndex the row index of the row to be returned
     * @return an array containing all of the elements of this row in the matrix in proper
     * column sequence
     */
    Array<E> row(int rowIndex);

    /**
     * Returns an array containing all of the elements in this matrix for
     * a specified column index, in proper row sequence (from first to last element).
     *
     * <p>The returned array will act as a view into the current matrix.
     * (In other words, if this matrix is mutable, changes must be reflected in the
     * returned array).
     *
     * @param columnIndex the column index of the row to be returned
     * @return an array containing all of the elements of this column in the matrix in proper
     * row sequence
     */
    Array<E> column(int columnIndex);

    // Transformations
    /**
     * Returns a new matrix containing all the elements in this matrix, but rotated based
     * based on the specified rotation.
     *
     * @param  rotation {@link Rotation} to apply
     * @return a new matrix with the same elements, but rotated according to the specified rotation
     * @see Rotation
     */
    Matrix<E> toRotatedMatrix(Rotation rotation);

    /**
     * Returns a new matrix containing all the elements in this matrix, but mirrored
     * based on the specified mirroring axis.
     * @param axis {@link Axis} to mirror.
     * @return a new matrix with the same elements, but mirrored on the specified axis
     */
    Matrix<E> toMirroredMatrix(Axis axis);

    // Comparison and hashing

    /**
     * Compares the specified object with this matrix for equality.  Returns
     * {@code true} if and only if the specified object is also a matrix
     * implementing this interface, both matrices have the same size, and all
     * corresponding pairs of elements in the two matrices are <i>equal</i>.
     * (Two elements {@code e1} and {@code e2} are <i>equal</i> if
     * {@code Objects.equals(e1, e2)}.). In other words, two matrices are defined to be
     * equal if they contain the same elements in the same order.  This
     * definition ensures that the equals method works properly across
     * different implementations of the {@code Matrix} interface.
     *
     * @param o the object to be compared for equality with this matrix
     * @return {@code true} if the specified object is equal to this matrix
     */
    boolean equals(Object o);

    /**
     * Returns the hash code value for this matrix.  The hash code of an array
     * is defined to be the result of the following calculation:
     * <pre>{@code
     *     int hashCode = 1;
     *     for (Array elements : rows)
     *         for (E e : elements)
     *             hashCode = 31*hashCode + (e==null ? 0 : e.hashCode());
     * }</pre>
     * This ensures that {@code matrix1.equals(matrix2)} implies that
     * {@code matrix1.hashCode()==matrix2.hashCode()} for any two matrices,
     * {@code matrix1} and {@code matrix2}, as required by the general
     * contract of {@link Object#hashCode}.
     *
     * @return the hash code value for this matrix
     * @see Object#equals(Object)
     * @see #equals(Object)
     */
    int hashCode();

    /**
     * Adding an object to a fixed size matrix is not supported, but it is part of the
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
     * Adding objects to a fixed size matrix is not supported, but it is part of the
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
     * Removing an object from a fixed size matrix is not supported, but it is part of the
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
     * Removing objects from a fixed size matrix is not supported, but it is part of the
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
     * Removing objects from a fixed size matrix is not supported, but it is part of the
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
     * Removing objects from a fixed size matrix is not supported, but it is part of the
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
     * Removing objects from a fixed size matrix is not supported, but it is part of the
     * {@link Collection} API. This action will always throw an {@link UnsupportedOperationException}.
     *
     * @throws UnsupportedOperationException always
     */
    @Override
    default void clear() {
        throw new UnsupportedOperationException();
    }

    /**
     * Matrix rotation.
     * <p>
     * Following options exist:
     * <ul>
     *     <li>{@link #NONE} - no rotation.</li>
     *     <li>{@link #LEFT} - rotate left.</li>
     *     <li>{@link #HALF} - rotate halfway.</li>
     *     <li>{@link #RIGHT} - rotate right.</li>
     * </ul>
     * </p>
     */
    enum Rotation {
        /**
         * <p>No rotation, 0&deg;. (rowIndex = rowIndex, columnIndex = columnIndex)</p>
         * <p>Example:<br>
         * <pre>
         * [1, 2, 3]    [4, 5, 6]<br>
         * [4, 5, 6] -> [4, 5, 6]<br>
         * [7, 8, 9]    [7, 8. 9]<br>
         * </pre>
         * </p>
         */
        NONE,

        /**
         * <p>Left rotation, -90&deg;. (rowIndex = columns - 1 - columnIndex, columnIndex = rowIndex)</p>
         * <p>Example:<br>
         * <pre>
         * [1, 2, 3]    [3, 6, 9]<br>
         * [4, 5, 6] -> [2, 5, 8]<br>
         * [7, 8, 9]    [1, 4. 7]<br>
         * </pre>
         * </p>
         */
        LEFT,

        /**
         * <p>Halfway rotation, &plusmn;180&deg;. (rowIndex = rows - 1 - rowIndex, columnIndex = columns - 1 - columnIndex)</p>
         * <p>Example:<br>
         * <pre>
         * [1, 2, 3]    [9, 8, 7]<br>
         * [4, 5, 6] -> [6, 5, 4]<br>
         * [7, 8, 9]    [3, 2. 1]<br>
         * </pre>
         * </p>
         */
        HALF,

        /**
         * <p>Right rotation, +90&deg;. (rowIndex = columnIndex, columnIndex = rows - 1 - rowIndex)</p>
         * <p>Example:<br>
         * <pre>
         * [1, 2, 3]    [7, 4, 1]<br>
         * [4, 5, 6] -> [8, 5, 2]<br>
         * [7, 8, 9]    [9, 6. 3]<br>
         * </pre>
         * </p>
         */
        RIGHT,
    }

    /**
     * Matrix axis, which can be used for mirroring/reflection.
     * <p>
     * Following options exist:
     * <ul>
     *     <li>{@link #NONE} - no mirroring.</li>
     *     <li>{@link #ROWS} - mirror rows.</li>
     *     <li>{@link #COLUMNS} - mirror columns.</li>
     *     <li>{@link #BOTH} - mirror both rows and columns.</li>
     * </ul>
     * </p>
     */
    enum Axis {
        /**
         * <p>No mirroring. (rowIndex = rowIndex, columnIndex = columnIndex)</p>
         * <p>Example:<br>
         * <pre>
         * [1, 2, 3]    [4, 5, 6]<br>
         * [4, 5, 6] -> [4, 5, 6]<br>
         * [7, 8, 9]    [7, 8. 9]<br>
         * </pre>
         * </p>
         */
        NONE,

        /**
         * <p>Mirror rows, i.e. inverse column index. (rowIndex = rowIndex, columnIndex = columns - 1 - columnIndex)</p>
         * <p>Example:<br>
         * <pre>
         * [1, 2, 3]    [3, 2, 1]<br>
         * [4, 5, 6] -> [6, 5, 3]<br>
         * [7, 8, 9]    [9, 8. 7]<br>
         * </pre>
         * </p>
         */
        ROWS,

        /**
         * <p>Mirror columns, i.e. inverse row index. (rowIndex = rows - 1 - rowIndex, columnIndex = columnIndex)</p>
         * <p>Example:<br>
         * <pre>
         * [1, 2, 3]    [7, 8, 9]<br>
         * [4, 5, 6] -> [4, 5, 6]<br>
         * [7, 8, 9]    [1, 2. 3]<br>
         * </pre>
         * </p>
         */
        COLUMNS,

        /**
         * <p>Mirror both rows and columns, i.e. inverse row and column index. (rowIndex = rows - 1 - rowIndex, columnIndex = columns - 1 - columnIndex)</p>
         * <p>Example:<br>
         * <pre>
         * [1, 2, 3]    [9, 8, 7]<br>
         * [4, 5, 6] -> [6, 5, 4]<br>
         * [7, 8, 9]    [3, 2. 1]<br>
         * </pre>
         * </p>
         */
        BOTH,
    }
}
