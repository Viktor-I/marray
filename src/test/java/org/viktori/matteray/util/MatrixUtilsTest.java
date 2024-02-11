package org.viktori.matteray.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.viktori.matteray.ImmutableMatrix;
import org.viktori.matteray.Matrix;

public class MatrixUtilsTest {

    @Test
    public void testApplyForEachWhenSameType() {
        Matrix<String> matrix = new ImmutableMatrix<>(new String[][]{{"xx", "xy", "xz"}, {"yx", "yy", "yz"}});
        assertEquals(new ImmutableMatrix<>(new String[][]{{"xxxx", "xyxy", "xzxz"}, {"yxyx", "yyyy", "yzyz"}}), MatrixUtils.applyForEach(matrix, str -> str.repeat(2)));
    }

    @Test
    public void testApplyForEachWhenDifferentType() {
        Matrix<String> matrix = new ImmutableMatrix<>(new String[][]{{"x", "xy", "xyz"}, {"zyx", "zy", "z"}});
        assertEquals(new ImmutableMatrix<>(new Integer[][]{{1, 2, 3}, {3, 2, 1}}), MatrixUtils.applyForEach(matrix, String::length));
    }

    @Test
    public void testApplyForEachWhenNullArgument() {
        Matrix<String> matrix = new ImmutableMatrix<>(new String[][]{{"x", "xy", "xyz"}, {"zyx", "zy", "z"}});
        assertThrows(NullPointerException.class, () -> MatrixUtils.applyForEach(null, str -> ""));
        assertThrows(NullPointerException.class, () -> MatrixUtils.applyForEach(matrix, null));
    }

    @Test
    public void testMergeForEachWhenDifferentType() {
        Matrix<String> matrix1 = new ImmutableMatrix<>(new String[][]{{"x", "xy", "xyz"}, {"zyx", "zy", "z"}});
        Matrix<Double> matrix2 = new ImmutableMatrix<>(new Double[][]{{1.0, 1.2, 1.23}, {4.0, 5.6, 7.89}});
        assertEquals(new ImmutableMatrix<>(new Integer[][]{{5, 6, 8},{7, 6, 6}}), MatrixUtils.mergeForEach(matrix1, matrix2, (str, db) -> (str + ";" + db).length()));
        assertEquals(new ImmutableMatrix<>(new Integer[][]{{5, 6, 8},{7, 6, 6}}), MatrixUtils.mergeForEach(matrix2, matrix1, (db, str) -> (db + ";" + str).length()));
    }

    @Test
    public void testMergeForEachWhenSameType() {
        Matrix<Integer> matrix1 = new ImmutableMatrix<>(new Integer[][]{{1, 2, 3}, {2, 4, 6}});
        Matrix<Integer> matrix2 = new ImmutableMatrix<>(new Integer[][]{{10, 20, 30}, {20, 40, 60}});
        assertEquals(new ImmutableMatrix<>(new Integer[][]{{9, 18, 27}, {18, 36, 54}}), MatrixUtils.mergeForEach(matrix1, matrix2, (i, j) -> j - i));
        assertEquals(new ImmutableMatrix<>(new Integer[][]{{-9, -18, -27}, {-18, -36, -54}}), MatrixUtils.mergeForEach(matrix2, matrix1, (i, j) -> j - i));
    }

    @Test
    public void testMergeForEachWhenOneMatrixHasLessRows() {
        Matrix<Integer> matrix1 = new ImmutableMatrix<>(new Integer[][]{{1, 2, 3}, {2, 4, 6}, {3, 6, 9}});
        Matrix<Integer> matrix2 = new ImmutableMatrix<>(new Integer[][]{{10, 20, 30}, {20, 40, 60}});
        assertEquals(new ImmutableMatrix<>(new Integer[][]{{9, 18, 27}, {18, 36, 54}}), MatrixUtils.mergeForEach(matrix1, matrix2, (i, j) -> j - i));
        assertEquals(new ImmutableMatrix<>(new Integer[][]{{-9, -18, -27}, {-18, -36, -54}}), MatrixUtils.mergeForEach(matrix2, matrix1, (i, j) -> j - i));
    }

    @Test
    public void testMergeForEachWhenOneMatrixHasLessColumns() {
        Matrix<Integer> matrix1 = new ImmutableMatrix<>(new Integer[][]{{1, 2, 3, 4}, {2, 4, 6, 8}});
        Matrix<Integer> matrix2 = new ImmutableMatrix<>(new Integer[][]{{10, 20, 30}, {20, 40, 60}});
        assertEquals(new ImmutableMatrix<>(new Integer[][]{{9, 18, 27}, {18, 36, 54}}), MatrixUtils.mergeForEach(matrix1, matrix2, (i, j) -> j - i));
        assertEquals(new ImmutableMatrix<>(new Integer[][]{{-9, -18, -27}, {-18, -36, -54}}), MatrixUtils.mergeForEach(matrix2, matrix1, (i, j) -> j - i));
    }

    @Test
    public void testMergeForEachWhenNullArgument() {
        Matrix<String> matrix = new ImmutableMatrix<>(new String[][]{{"x", "xy", "xyz"}, {"zyx", "zy", "z"}});
        assertThrows(NullPointerException.class, () -> MatrixUtils.mergeForEach(null, matrix, (str1, str2) -> ""));
        assertThrows(NullPointerException.class, () -> MatrixUtils.mergeForEach(matrix, null, (str1, str2) -> ""));
        assertThrows(NullPointerException.class, () -> MatrixUtils.mergeForEach(matrix, matrix, null));
    }

    @Test
    public void testToRotatedWhenSquare() {
        Matrix<Integer> sourceMatrix = new ImmutableMatrix<>(new Integer[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}});
        Matrix<Integer> leftRotatedMatrix = new ImmutableMatrix<>(new Integer[][]{{3, 6, 9}, {2, 5, 8}, {1, 4, 7}});
        Matrix<Integer> halfRotatedMatrix = new ImmutableMatrix<>(new Integer[][]{{9, 8, 7}, {6, 5, 4}, {3, 2, 1}});
        Matrix<Integer> rightRotatedMatrix = new ImmutableMatrix<>(new Integer[][]{{7, 4, 1}, {8, 5, 2}, {9, 6, 3}});

        assertEquals(sourceMatrix, MatrixUtils.toRotated(sourceMatrix, Matrix.Rotation.NONE));
        assertEquals(leftRotatedMatrix, MatrixUtils.toRotated(sourceMatrix, Matrix.Rotation.LEFT));
        assertEquals(halfRotatedMatrix, MatrixUtils.toRotated(sourceMatrix, Matrix.Rotation.HALF));
        assertEquals(rightRotatedMatrix, MatrixUtils.toRotated(sourceMatrix, Matrix.Rotation.RIGHT));
    }

    @Test
    public void testToRotatedWhenNonSquare() {
        Matrix<Character> sourceMatrix = new ImmutableMatrix<>(new Character[][]{{'a', 'b', 'c', 'd'}, {'e', 'f', 'g', 'h'}, {'i', 'j', 'k', 'l'}});
        Matrix<Character> leftRotatedMatrix = new ImmutableMatrix<>(new Character[][]{{'d', 'h', 'l'}, {'c', 'g', 'k'}, {'b', 'f', 'j'}, {'a', 'e', 'i'}});
        Matrix<Character> halfRotatedMatrix = new ImmutableMatrix<>(new Character[][]{{'l', 'k', 'j', 'i'}, {'h', 'g', 'f', 'e'}, {'d', 'c', 'b', 'a'}});
        Matrix<Character> rightRotatedMatrix = new ImmutableMatrix<>(new Character[][]{{'i', 'e', 'a'}, {'j', 'f', 'b'}, {'k', 'g', 'c'}, {'l', 'h', 'd'}});

        assertEquals(sourceMatrix, MatrixUtils.toRotated(sourceMatrix, Matrix.Rotation.NONE));
        assertEquals(leftRotatedMatrix, MatrixUtils.toRotated(sourceMatrix, Matrix.Rotation.LEFT));
        assertEquals(halfRotatedMatrix, MatrixUtils.toRotated(sourceMatrix, Matrix.Rotation.HALF));
        assertEquals(rightRotatedMatrix, MatrixUtils.toRotated(sourceMatrix, Matrix.Rotation.RIGHT));
    }

    @Test
    public void testToMirroredWhenSquare() {
        Matrix<Integer> sourceMatrix = new ImmutableMatrix<>(new Integer[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}});
        Matrix<Integer> rowsMirrored = new ImmutableMatrix<>(new Integer[][]{{3, 2, 1}, {6, 5, 4}, {9, 8, 7}});
        Matrix<Integer> columnsMirrored = new ImmutableMatrix<>(new Integer[][]{{7, 8, 9}, {4, 5, 6}, {1, 2, 3}});

        assertEquals(rowsMirrored, MatrixUtils.toMirrored(sourceMatrix, Matrix.Axis.ROWS));
        assertEquals(columnsMirrored, MatrixUtils.toMirrored(sourceMatrix, Matrix.Axis.COLUMNS));
    }

    @Test
    public void testToMirroredWhenNonSquare() {
        Matrix<Character> sourceMatrix = new ImmutableMatrix<>(new Character[][]{{'a', 'b', 'c', 'd'}, {'e', 'f', 'g', 'h'}, {'i', 'j', 'k', 'l'}});
        Matrix<Character> rowsMirrored = new ImmutableMatrix<>(new Character[][]{{'d', 'c', 'b', 'a'}, {'h', 'g', 'f', 'e'}, {'l', 'k', 'j', 'i'}});
        Matrix<Character> columnsMirrored = new ImmutableMatrix<>(new Character[][]{{'i', 'j', 'k', 'l'}, {'e', 'f', 'g', 'h'}, {'a', 'b', 'c', 'd'}});

        assertEquals(rowsMirrored, MatrixUtils.toMirrored(sourceMatrix, Matrix.Axis.ROWS));
        assertEquals(columnsMirrored, MatrixUtils.toMirrored(sourceMatrix, Matrix.Axis.COLUMNS));
    }
}
