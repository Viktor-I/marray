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
}
