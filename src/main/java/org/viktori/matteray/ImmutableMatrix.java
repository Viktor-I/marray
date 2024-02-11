package org.viktori.matteray;

import org.viktori.matteray.function.MatrixIndexFunction;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.IntFunction;
import java.util.stream.Stream;

/**
 * Java double array wrapper which implements the {@code Matrix} interface, which is immutable and easier
 * to work with than normal arrays as it is more similar to a {@link List} of lists with
 * fixed size in both directions.
 * The class implements all immutable Collection operations, and permits all elements,
 * including {@code null}. Mutable operations such as {@code add} or {@code remove} will result
 * in an {@link UnsupportedOperationException}.
 *
 * <p>The {@code size}, {@code isEmpty}, {@code get}, {@code set},
 * {@code iterator}.
 * <p>
 * Due to its immutable nature, the implementation is thread safe.
 *
 * @param <E> the type of elements in this matrix
 * @author Viktor Ingemansson
 * @see Collection
 * @see Array
 */
public class ImmutableMatrix<E> implements Matrix<E>, Cloneable {

    private static final Object[][] EMPTY_MATRIX = new Object[0][0];

    private final Object[][] elementData;
    private final int rows;
    private final int columns;
    private final long totalSize;

    /**
     * Constructs an immutable square matrix with the specified length (width and height), and function
     * to populate values with.
     *
     * @param length       the row and column count the matrix (i.e its height and width)
     * @param initFunction the function to initialize values in the matrix
     * @throws IllegalArgumentException if the specified initial capacity
     *                                  is negative
     */
    public ImmutableMatrix(int length, MatrixIndexFunction<E> initFunction) {
        this(length, length, initFunction);
    }

    /**
     * Constructs an immutable square matrix with the specified length (width and height), and function
     * to populate values with. When given explicit rows and columns like this, it is allowed to
     * create a matrix with multiple rows and zero columns, or vice versa.
     *
     * @param rows         row count in the matrix (i.e. its height)
     * @param columns      column count in the matrix (i.e. its width)
     * @param initFunction the function to initialize values in the matrix
     * @throws IllegalArgumentException if the specified initial capacity
     *                                  is negative
     */
    public ImmutableMatrix(int rows, int columns, MatrixIndexFunction<E> initFunction) {
        this.rows = rows;
        this.columns = columns;
        this.totalSize = (long) rows * (long) columns;
        if (rows > 0 || columns > 0) {
            this.elementData = new Object[rows][columns];
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < columns; c++) {
                    this.elementData[r][c] = initFunction.valueOf(r, c);
                }
            }
        } else if (rows == 0 || columns == 0) {
            this.elementData = EMPTY_MATRIX; // Saves memory
        } else {
            throw new IllegalArgumentException("Illegal size: " + rows + ", " + columns);
        }
    }

    /**
     * Constructs an immutable matrix based on the specified arguments.
     * Note that the array is shallow copied internally to guarantee immutability.
     *
     * @param elementData the raw matrix of data to hold
     * @throws IllegalArgumentException if column count is not consistent across all rows
     */
    @SafeVarargs
    public ImmutableMatrix(E[]... elementData) {
        this(elementData, false, getRows(elementData), validateAndGetColumns(elementData));
    }

    /**
     * Constructs an immutable matrix containing the elements of the specified
     * rows, in the order they are returned by the collection's iterators.
     *
     * @param elementRows the rows of element data to hold
     * @throws IllegalArgumentException if column count is not consistent across all rows
     */
    @SafeVarargs
    public ImmutableMatrix(Collection<E>... elementRows) {
        this(Array.of(elementRows));
    }

    /**
     * Constructs an empty immutable matrix.
     */
    public ImmutableMatrix() {
        this.elementData = EMPTY_MATRIX;
        this.columns = 0;
        this.rows = 0;
        this.totalSize = 0;
    }

    /**
     * Constructs an immutable matrix containing the elements of the specified
     * collections, in the order they are returned by the collections'
     * iterators. The outer collection will be used for rows, and the inner collection will contain the columns.
     *
     * @param collections the collection of collections whose elements are to be placed into this matrix
     * @throws NullPointerException     if the specified collection is null
     * @throws IllegalArgumentException if size is not consistent across all inner collections
     */
    public <C extends Collection<E>> ImmutableMatrix(Collection<C> collections) {
        this.columns = validateAndGetColumns(collections);
        this.rows = columns > 0 ? collections.size() : 0;
        if (rows > 0) {
            this.elementData = new Object[rows][columns];
            int r = 0;
            for (Collection<? extends E> row : collections) {
                this.elementData[r] = row.toArray(this.elementData[r]);
                r++;
            }
        } else {
            this.elementData = EMPTY_MATRIX;
        }
        this.totalSize = (long) rows * (long) columns;
    }

    /**
     * Constructs an immutable matrix containing the elements of the specified
     * matrix, in the same positions.
     *
     * @param matrix the matrix whose elements are to be placed into this matrix
     * @throws NullPointerException if the specified matrix is null
     */
    public ImmutableMatrix(Matrix<? extends E> matrix) {
        this(matrix instanceof ImmutableMatrix<?> im ? im.elementData : matrix.toArray2D(), true, matrix.rows(), matrix.columns());
    }

    private ImmutableMatrix(Object[][] elementData, boolean trusted, int rows, int columns) {
        this.columns = columns;
        this.rows = rows;
        if (rows == 0 || columns == 0) {
            this.elementData = EMPTY_MATRIX; // Saves memory
        } else if (trusted) {
            this.elementData = elementData;
        } else {
            this.elementData = new Object[rows][columns];
            for (int r = 0; r < rows; r++) {
                System.arraycopy(elementData[r], 0, this.elementData[r], 0, columns);
            }
        }
        this.totalSize = (long) rows * (long) columns;
    }

    private static int getRows(Object[][] elementData) {
        if (elementData.length > 0 && elementData[0].length > 0) {
            return elementData.length;
        }
        return 0;
    }

    private static int validateAndGetColumns(Object[][] elementData) {
        int columns = 0;
        for (int r = 0; r < elementData.length; r++) {
            if (r == 0) {
                columns = elementData[r].length;
            } else if (elementData[r].length != columns) {
                throw new IllegalArgumentException("Number of columns in matrix must be consistent across all rows");
            }
        }
        return columns;
    }

    private static <C extends Collection<E>, E> int validateAndGetColumns(Collection<C> collections) {
        int columns = 0;
        int r = 0;
        for (Collection<? extends E> row : collections) {
            if (r == 0) {
                columns = row.size();
            } else if (row.size() != columns) {
                throw new IllegalArgumentException("Number of columns in matrix must be consistent across all rows");
            }
            r++;
        }
        return columns;
    }

    /**
     * Returns the element at the specified position in this matrix.
     *
     * @param rowIndex    row index of the element to return
     * @param columnIndex column index of the element to return
     * @return the element at the specified position in this matrix
     * @throws ArrayIndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public E get(int rowIndex, int columnIndex) {
        return (E) elementData[rowIndex][columnIndex];
    }

    @Override
    public int size() {
        return totalSize > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) totalSize;
    }

    @Override
    public int rows() {
        return rows;
    }

    @Override
    public int columns() {
        return columns;
    }

    @Override
    public boolean isEmpty() {
        return totalSize == 0;
    }

    @Override
    public boolean isSquare() {
        return columns == rows;
    }

    @Override
    public boolean contains(Object o) {
        for (Object[] row : elementData) {
            for (Object value : row) {
                if (Objects.equals(value, o)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        for (Object e : collection) {
            if (!contains(e)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Iterator<E> iterator() {
        return new MatrixIterator<>(this);
    }

    @Override
    public Spliterator<E> spliterator() {
        return Spliterators.spliterator(this, 0);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Stream<E> stream() {
        return Stream.of((E[]) toArray());
    }

    @Override
    public Stream<E> parallelStream() {
        return stream().parallel();
    }

    @Override
    public Object[] toArray() {
        if (totalSize > Integer.MAX_VALUE) {
            throw new ArrayIndexOutOfBoundsException("Array index overflow: " + totalSize);
        }

        Object[] array = new Object[(int) totalSize];
        return toFlattenedArray(array);
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (totalSize > Integer.MAX_VALUE) {
            throw new ArrayIndexOutOfBoundsException("Array index overflow: " + totalSize);
        }

        int size = (int) totalSize;
        if (a.length < size) {
            // Make a new array of a's runtime type, but my contents:
            final Class<?> newType = a.getClass();
            T[] copy = createArrayOfType(size, newType);
            return toFlattenedArray(copy);
        }
        T[] flattened = toFlattenedArray(a);
        if (flattened.length > size) {
            a[size] = null;
        }
        return a;
    }

    @Override
    public <T> T[] toArray(IntFunction<T[]> generator) {
        if (totalSize > Integer.MAX_VALUE) {
            throw new ArrayIndexOutOfBoundsException("Array index overflow: " + totalSize);
        }

        return toArray(generator.apply((int) totalSize));
    }

    @SuppressWarnings("unchecked")
    private static <T> T[] createArrayOfType(int size, Class<?> newType) {
        return newType == Object[].class
                ? (T[]) new Object[size]
                : (T[]) java.lang.reflect.Array.newInstance(newType.getComponentType(), size);
    }

    @SuppressWarnings("unchecked")
    private <T> T[] toFlattenedArray(T[] array) {
        int i = 0;
        for (int r = 0; r < rows; r++) {
            Object[] row = elementData[r];
            for (int c = 0; c < columns; c++) {
                array[i++] = (T) row[c];
            }
        }
        return array;
    }

    @Override
    public Object[][] toArray2D() {
        Object[][] array2D = new Object[rows][columns];
        for (int r = 0; r < rows; r++) {
            System.arraycopy(elementData[r], 0, array2D[r], 0, columns);
        }
        return array2D;
    }

    @Override
    public <T> T[][] toArray2D(T[][] a) {
        if (a.length < rows) {
            // Make a new array of a's runtime type, but my contents:
            T[][] copy = createArrayOfType(rows, a.getClass());
            copy2DContentsInto(copy);
            return copy;
        }
        copy2DContentsInto(a);
        if (a.length > rows) {
            a[rows] = null;
        }
        return a;
    }

    @Override
    public <T> T[][] toArray2D(IntFunction<T[][]> generator) {
        return toArray2D(generator.apply(rows));
    }

    @SuppressWarnings("unchecked")
    private <T> void copy2DContentsInto(T[][] array) {
        for (int r = 0; r < rows; r++) {
            if (array[r] == null) {
                array[r] = createArrayOfType(columns, array.getClass().getComponentType());
            }

            if (array[r].length < columns) {
                array[r] = (T[]) Arrays.copyOf(elementData[r], columns, array[r].getClass());
            } else {
                for (int c = 0; c < columns; c++) {
                    array[r][c] = (T) elementData[r][c];
                }
                if (array[r].length > columns) {
                    array[r][columns] = null;
                }
            }
        }
    }


    @Override
    public Matrix<E> subMatrix(int fromRowIndex, int toRowIndex, int fromColumnIndex, int toColumnIndex) {
        if (fromRowIndex == 0 && toRowIndex == rows &&
                fromColumnIndex == 0 && toColumnIndex == columns) {
            return this;
        }

        subMatrixRangeCheck(fromRowIndex, toRowIndex, rows);
        subMatrixRangeCheck(fromColumnIndex, toColumnIndex, columns);

        int rows = toRowIndex - fromRowIndex;
        int columns = toColumnIndex - fromColumnIndex;
        Object[][] elementData = new Object[rows][columns];
        for (int i = 0, r = fromRowIndex; r < toRowIndex; i++, r++) {
            elementData[i] = Arrays.copyOfRange(this.elementData[r], fromColumnIndex, toColumnIndex);
        }

        return new ImmutableMatrix<>(elementData, true, rows, columns);
    }

    private static void subMatrixRangeCheck(int fromIndex, int toIndex, int size) {
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
    @SuppressWarnings("unchecked")
    public Array<E> row(int rowIndex) {
        return new ImmutableArray<>((E[]) elementData[rowIndex]);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Array<E> column(int columnIndex) {
        final Object[] column = new Object[rows];
        for (int r = 0; r < rows; r++) {
            column[r] = elementData[r][columnIndex];
        }
        return new ImmutableArray<>((E[]) column);
    }

    @Override
    public int hashCode() {
        int result = 1;

        for (Object[] row : elementData) {
            result = 31 * result + Arrays.hashCode(row);
        }

        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (o == null) {
            return false;
        } else if (o instanceof ImmutableMatrix<?> im && im.rows() == rows() && im.columns() == columns()) {
            return allDataEqual(im.elementData);
        } else if (o instanceof Matrix<?> m && m.rows() == rows() && m.columns() == columns()) {
            return allDataEqual(m.toArray2D());
        }
        return false;
    }

    private boolean allDataEqual(Object[][] otherData) {
        for (int r = 0; r < rows; r++) {
            if (!Arrays.equals(elementData[r], otherData[r])) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        for (int r = 0; r < elementData.length; r++) {
            if (r > 0) {
                stringBuilder.append(", ");
            }
            Object[] rowArray = elementData[r];
            stringBuilder.append(Arrays.toString(rowArray));
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    /**
     * Returns a shallow copy of this {@code ImmutableMatrix} instance.  (The
     * elements themselves are not copied.)
     *
     * @return a clone of this {@code ImmutableMatrix} instance
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
