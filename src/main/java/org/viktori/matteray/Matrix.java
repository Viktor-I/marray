package org.viktori.matteray;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.IntFunction;
import java.util.function.Predicate;

public interface Matrix<E> extends Collection<E> {

    // Positional Access Operation

    /**
     * Get the element at a given index in this list.
     *
     * @param rowIndex    the row index of the element to be returned
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
     * @param fromRow    low row endpoint (inclusive) of the subMatrix
     * @param toRow      high row endpoint (exclusive) of the subMatrix
     * @param fromColumn low column endpoint (inclusive) of the subMatrix
     * @param toColumn   high row endpoint (exclusive) of the subMatrix
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

    // Query operations

    /**
     * Returns the number of elements in this matrix.  If this matrix
     * contains more than {@code Integer.MAX_VALUE} elements, returns
     * {@code Integer.MAX_VALUE}.
     *
     * @return the number of elements in this matrix
     */
    int size();

    /**
     * Returns the number of rows in this matrix.
     *
     * @return the number of rows in this matrix
     */
    int rows();

    /**
     * Returns the number of columns in this matrix.
     *
     * @return the number of columns in this matrix
     */
    int columns();

    /**
     * Returns {@code true} if this matrix is a square matrix. It is a square matrix if
     * the number of rows equals the number of columns. (If rows == columns).
     *
     * @return {@code true} if this matrix has the same amount of rows as columns
     */
    boolean isSquare();

    /**
     * Returns an array containing all of the elements in this matrix.
     * If this matrix makes any guarantees as to what order its elements
     * are returned by its iterator, this method must return the elements in
     * the same order. The returned array's {@linkplain Class#getComponentType
     * runtime component type} is {@code Object}.
     *
     * <p>Note that the returned array is not two-dimensional and will thus return all
     * elements in all rows. Use {@link #toArray2D()} for a two-dimensional array.</p>
     *
     * <p>The returned array will be "safe" in that no references to it are
     * maintained by this matrix.  (In other words, this method must
     * allocate a new array even if this matrix is backed by an array).
     * The caller is thus free to modify the returned array.
     *
     * @apiNote
     * This method acts as a bridge between array-based and collection-based APIs.
     * It returns an array whose runtime type is {@code Object[]}.
     * Use {@link #toArray(Object[]) toArray(T[])} to reuse an existing
     * array, or use {@link #toArray(IntFunction)} to control the runtime type
     * of the array.
     *
     * @return an array, whose {@linkplain Class#getComponentType runtime component
     * type} is {@code Object}, containing all of the elements in this matrix
     */
    Object[] toArray();

    /**
     * Returns an array containing all of the elements in this matrix;
     * the runtime type of the returned array is that of the specified array.
     * If the matrix fits in the specified array, it is returned therein.
     * Otherwise, a new array is allocated with the runtime type of the
     * specified array and the size of this matrix.
     *
     * <p>If this matrix fits in the specified array with room to spare
     * (i.e., the array has more elements than this matrix), the element
     * in the array immediately following the end of the matrix is set to
     * {@code null}.  (This is useful in determining the length of this
     * collection <i>only</i> if the caller knows that this matrix does
     * not contain any {@code null} elements.)
     *
     * <p>If this matrix makes any guarantees as to what order its elements
     * are returned by its iterator, this method must return the elements in
     * the same order.
     *
     * <p>Note that the returned array is not two-dimensional and will thus return all
     * elements in all rows. Use {@link #toArray2D(T[][])} for a two-dimensional array.</p>
     *
     * @apiNote
     * This method acts as a bridge between array-based and collection-based APIs.
     * It allows an existing array to be reused under certain circumstances.
     * Use {@link #toArray()} to create an array whose runtime type is {@code Object[]},
     * or use {@link #toArray(IntFunction)} to control the runtime type of
     * the array.
     *
     * <p>Suppose {@code x} is a matrix known to contain only strings.
     * The following code can be used to dump the matrix into a previously
     * allocated {@code String} array:
     *
     * <pre>
     *     String[] y = new String[SIZE];
     *     ...
     *     y = x.toArray(y);</pre>
     *
     * <p>The return value is reassigned to the variable {@code y}, because a
     * new array will be allocated and returned if the matrix {@code x} has
     * too many elements to fit into the existing array {@code y}.
     *
     * <p>Note that {@code toArray(new Object[0])} is identical in function to
     * {@code toArray()}.
     *
     * @param <T> the component type of the array to contain the matrix
     * @param a the array into which the elements of this matrix are to be
     *        stored, if it is big enough; otherwise, a new array of the same
     *        runtime type is allocated for this purpose.
     * @return an array containing all of the elements in this matrix
     * @throws ArrayStoreException if the runtime type of any element in this
     *         matrix is not assignable to the {@linkplain Class#getComponentType
     *         runtime component type} of the specified array
     * @throws NullPointerException if the specified array is null
     */
    <T> T[] toArray(T[] a);

    /**
     * Returns an array containing all of the elements in this collection,
     * using the provided {@code generator} function to allocate the returned array.
     *
     * <p>If this collection makes any guarantees as to what order its elements
     * are returned by its iterator, this method must return the elements in
     * the same order.
     *
     * <p>Note that the returned array is not two-dimensional and will thus return all
     * elements in all rows. Use {@link #toArray2D(IntFunction)} for a two-dimensional array.</p>
     *
     * @apiNote
     * This method acts as a bridge between array-based and collection-based APIs.
     * It allows creation of an array of a particular runtime type. Use
     * {@link #toArray()} to create an array whose runtime type is {@code Object[]},
     * or use {@link #toArray(Object[]) toArray(T[])} to reuse an existing array.
     *
     * <p>Suppose {@code x} is a matrix known to contain only strings.
     * The following code can be used to dump the matrix into a newly
     * allocated array of {@code String}:
     *
     * <pre>
     *     String[] y = x.toArray(String[]::new);</pre>
     *
     * @implSpec
     * The default implementation calls the generator function with zero
     * and then passes the resulting array to {@link #toArray(Object[]) toArray(T[])}.
     *
     * @param <T> the component type of the array to contain the collection
     * @param generator a function which produces a new array of the desired
     *                  type and the provided length
     * @return an array containing all of the elements in this collection
     * @throws ArrayStoreException if the runtime type of any element in this
     *         matrix is not assignable to the {@linkplain Class#getComponentType
     *         runtime component type} of the generated array
     * @throws NullPointerException if the generator function is null
     */
    default <T> T[] toArray(IntFunction<T[]> generator) {
        return toArray(generator.apply(0));
    }

    /**
     * Returns a two-dimensional array containing all of the elements in this matrix.
     * The outer array contains the rows and the inner array each column for every row.
     *
     * <p>The returned array will be "safe" in that no references to it are
     * maintained by this matrix.  (In other words, this method must
     * allocate a new array even if this matrix is backed by an array).
     * The caller is thus free to modify the returned two-dimensional array.
     *
     * @return a row-based array with column-arrays inside, whose {@linkplain Class#getComponentType runtime component
     * type} is {@code Object}, containing all of the elements in this matrix
     */
    Object[][] toArray2D();

    /**
     * Returns a two-dimensional array containing all of the elements in this matrix.
     * The outer array contains the rows and the inner array each column for every row.
     *
     * <p>If this matrix fits in the specified array with room to spare
     * (i.e., the array has more elements than this matrix), the element
     * in the array immediately following the end of the matrix is set to
     * {@code null}.  (This is useful in determining the length of this
     * collection <i>only</i> if the caller knows that this matrix does
     * not contain any {@code null} elements.)
     *
     * <p>If this matrix makes any guarantees as to what order its elements
     * are returned by its iterator, this method must return the elements in
     * the same order.
     *
     * <p>Suppose {@code x} is a matrix known to contain only strings.
     * The following code can be used to dump the matrix into a previously
     * allocated {@code String} two-dimensional-array:
     *
     * <pre>
     *     String[][] y = new String[ROWS][COLUMNS];
     *     ...
     *     y = x.toArray2D(y);</pre>
     *
     * <p>The return value is reassigned to the variable {@code y}, because a
     * new two-dimensional array will be allocated and returned if the matrix {@code x} has
     * too many elements to fit into the existing two-dimensional array {@code y}.
     *
     * <p>Note that {@code toArray2D(new Object[0][0])} is identical in function to
     * {@code toArray2D()}.
     *
     * @param <T> the component type of the two-dimensional array to contain the matrix
     * @param a the two-dimensional array into which the elements of this matrix are to be
     *        stored, if it is big enough; otherwise, a new two-dimensional array of the same
     *        runtime type is allocated for this purpose.
     * @return a row-based array with column-arrays inside containing all of the elements in this matrix
     * @throws ArrayStoreException if the runtime type of any element in this
     *         matrix is not assignable to the {@linkplain Class#getComponentType
     *         runtime component type} of the specified array
     * @throws NullPointerException if the specified array is null
     */
    <T> T[][] toArray2D(T[][] a);

    /**
     * Returns a two-dimensional array containing all of the elements in this collection,
     * using the provided {@code generator} function to allocate the returned two-dimensional array.
     * The outer array contains the rows and the inner array each column for every row.
     *
     * <p>If this collection makes any guarantees as to what order its elements
     * are returned by its iterator, this method must return the elements in
     * the same order.
     *
     * <p>Suppose {@code x} is a matrix known to contain only strings.
     * The following code can be used to dump the matrix into a newly
     * allocated two-dimensional array of {@code String}:
     *
     * <pre>
     *     String[][] y = x.toArray2D(String[][]::new);</pre>
     *
     * @implSpec
     * The default implementation calls the generator function with zero
     * and then passes the resulting array to {@link #toArray2D(Object[][]) toArray2D(T[][])}.
     *
     * @param <T> the component type of the array to contain the matrix
     * @param generator a function which produces a new array of the desired
     *                  type and the provided length
     * @return an array containing all of the elements in this matrix
     * @throws ArrayStoreException if the runtime type of any element in this
     *         matrix is not assignable to the {@linkplain Class#getComponentType
     *         runtime component type} of the generated array
     * @throws NullPointerException if the generator function is null
     */
    default <T> T[][] toArray2D(IntFunction<T[][]> generator) {
        return toArray2D(generator.apply(0));
    }

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
        RIGHT
    }

    /**
     * Matrix axis, which can be used for mirroring/reflection.
     * <p>
     * Following options exist:
     * <ul>
     *     <li>{@link #ROWS} - mirror rows.</li>
     *     <li>{@link #COLUMNS} - mirror columns.</li>
     * </ul>
     * </p>
     */
    enum Axis {
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
    }

    final class MatrixIterator<E> implements Iterator<E> {
        private final Matrix<E> matrix;
        int row = 0;
        int column = 0;
        MatrixIterator(final Matrix<E> matrix) {
            this.matrix = matrix;
        }

        @Override
        public boolean hasNext() {
            return row < matrix.rows();
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            E next = matrix.get(row, column);
            if (column < matrix.columns()) {
                column++;
                if (column == matrix.columns()) {
                    column = 0;
                    row++;
                }
            }
            return next;
        }
    }
}
