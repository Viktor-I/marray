package org.viktori.matteray.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import org.junit.jupiter.api.Test;
import org.viktori.matteray.Array;
import org.viktori.matteray.Matrix;

public class MatrixUtilsTest {

    @Test
    public void testApplyForEachWhenSameType() {
        Matrix<String> matrix = Matrix.of(Array.of("xx", "xy", "xz"), Array.of("yx", "yy", "yz"));
        assertEquals(Matrix.of(Array.of("xxxx", "xyxy", "xzxz"), Array.of("yxyx", "yyyy", "yzyz")), MatrixUtils.applyForEach(matrix, str -> str.repeat(2)));
    }

    @Test
    public void testApplyForEachWhenDifferentType() {
        Matrix<String> matrix = Matrix.of(Array.of("x", "xy", "xyz"), Array.of("zyx", "zy", "z"));
        assertEquals(Matrix.of(Array.of(1, 2, 3), Array.of(3, 2, 1)), MatrixUtils.applyForEach(matrix, String::length));
    }

    @Test
    public void testApplyForEachWhenNullArgument() {
        Matrix<String> matrix = Matrix.of(Array.of("x", "xy", "xyz"), Array.of("zyx", "zy", "z"));
        assertThrows(NullPointerException.class, () -> MatrixUtils.applyForEach(null, str -> ""));
        assertThrows(NullPointerException.class, () -> MatrixUtils.applyForEach(matrix, null));
    }

    @Test
    public void testMergeForEachWhenDifferentType() {
        Matrix<String> matrix1 = Matrix.of(Array.of("x", "xy", "xyz"), Array.of("zyx", "zy", "z"));
        Matrix<Double> matrix2 = Matrix.of(Array.of(1.0, 1.2, 1.23), Array.of(4.0, 5.6, 7.89));
        assertEquals(Matrix.of(Array.of(5, 6, 8), Array.of(7, 6, 6)), MatrixUtils.mergeForEach(matrix1, matrix2, (str, db) -> (str + ";" + db).length()));
        assertEquals(Matrix.of(Array.of(5, 6, 8), Array.of(7, 6, 6)), MatrixUtils.mergeForEach(matrix2, matrix1, (db, str) -> (db + ";" + str).length()));
    }

    @Test
    public void testMergeForEachWhenSameType() {
        Matrix<Integer> matrix1 = Matrix.of(Array.of(1, 2, 3), Array.of(2, 4, 6));
        Matrix<Integer> matrix2 = Matrix.of(Array.of(10, 20, 30), Array.of(20, 40, 60));
        assertEquals(Matrix.of(Array.of(9, 18, 27), Array.of(18, 36, 54)), MatrixUtils.mergeForEach(matrix1, matrix2, (i, j) -> j - i));
        assertEquals(Matrix.of(Array.of(-9, -18, -27), Array.of(-18, -36, -54)), MatrixUtils.mergeForEach(matrix2, matrix1, (i, j) -> j - i));
    }

    @Test
    public void testMergeForEachWhenOneMatrixHasLessRows() {
        Matrix<Integer> matrix1 = Matrix.of(Array.of(1, 2, 3), Array.of(2, 4, 6), Array.of(3, 6, 9));
        Matrix<Integer> matrix2 = Matrix.of(Array.of(10, 20, 30), Array.of(20, 40, 60));
        assertEquals(Matrix.of(Array.of(9, 18, 27), Array.of(18, 36, 54)), MatrixUtils.mergeForEach(matrix1, matrix2, (i, j) -> j - i));
        assertEquals(Matrix.of(Array.of(-9, -18, -27), Array.of(-18, -36, -54)), MatrixUtils.mergeForEach(matrix2, matrix1, (i, j) -> j - i));
    }

    @Test
    public void testMergeForEachWhenOneMatrixHasLessColumns() {
        Matrix<Integer> matrix1 = Matrix.of(Array.of(1, 2, 3, 4), Array.of(2, 4, 6, 8));
        Matrix<Integer> matrix2 = Matrix.of(Array.of(10, 20, 30), Array.of(20, 40, 60));
        assertEquals(Matrix.of(Array.of(9, 18, 27), Array.of(18, 36, 54)), MatrixUtils.mergeForEach(matrix1, matrix2, (i, j) -> j - i));
        assertEquals(Matrix.of(Array.of(-9, -18, -27), Array.of(-18, -36, -54)), MatrixUtils.mergeForEach(matrix2, matrix1, (i, j) -> j - i));
    }

    @Test
    public void testMergeForEachWhenNullArgument() {
        Matrix<String> matrix = Matrix.of(Array.of("x", "xy", "xyz"), Array.of("zyx", "zy", "z"));
        assertThrows(NullPointerException.class, () -> MatrixUtils.mergeForEach(null, matrix, (str1, str2) -> ""));
        assertThrows(NullPointerException.class, () -> MatrixUtils.mergeForEach(matrix, null, (str1, str2) -> ""));
        assertThrows(NullPointerException.class, () -> MatrixUtils.mergeForEach(matrix, matrix, null));
    }

    @Test
    public void testToRotatedWhenSquare() {
        Matrix<Integer> sourceMatrix = Matrix.of(Array.of(1, 2, 3), Array.of(4, 5, 6), Array.of(7, 8, 9));
        Matrix<Integer> leftRotatedMatrix = Matrix.of(Array.of(3, 6, 9), Array.of(2, 5, 8), Array.of(1, 4, 7));
        Matrix<Integer> halfRotatedMatrix = Matrix.of(Array.of(9, 8, 7), Array.of(6, 5, 4), Array.of(3, 2, 1));
        Matrix<Integer> rightRotatedMatrix = Matrix.of(Array.of(7, 4, 1), Array.of(8, 5, 2), Array.of(9, 6, 3));

        assertEquals(sourceMatrix, MatrixUtils.toRotated(sourceMatrix, Matrix.Rotation.NONE));
        assertEquals(leftRotatedMatrix, MatrixUtils.toRotated(sourceMatrix, Matrix.Rotation.LEFT));
        assertEquals(halfRotatedMatrix, MatrixUtils.toRotated(sourceMatrix, Matrix.Rotation.HALF));
        assertEquals(rightRotatedMatrix, MatrixUtils.toRotated(sourceMatrix, Matrix.Rotation.RIGHT));
    }

    @Test
    public void testToRotatedWhenNonSquare() {
        Matrix<Character> sourceMatrix = Matrix.of(Array.of('a', 'b', 'c', 'd'), Array.of('e', 'f', 'g', 'h'), Array.of('i', 'j', 'k', 'l'));
        Matrix<Character> leftRotatedMatrix = Matrix.of(Array.of('d', 'h', 'l'), Array.of('c', 'g', 'k'), Array.of('b', 'f', 'j'), Array.of('a', 'e', 'i'));
        Matrix<Character> halfRotatedMatrix = Matrix.of(Array.of('l', 'k', 'j', 'i'), Array.of('h', 'g', 'f', 'e'), Array.of('d', 'c', 'b', 'a'));
        Matrix<Character> rightRotatedMatrix = Matrix.of(Array.of('i', 'e', 'a'), Array.of('j', 'f', 'b'), Array.of('k', 'g', 'c'), Array.of('l', 'h', 'd'));

        assertEquals(sourceMatrix, MatrixUtils.toRotated(sourceMatrix, Matrix.Rotation.NONE));
        assertEquals(leftRotatedMatrix, MatrixUtils.toRotated(sourceMatrix, Matrix.Rotation.LEFT));
        assertEquals(halfRotatedMatrix, MatrixUtils.toRotated(sourceMatrix, Matrix.Rotation.HALF));
        assertEquals(rightRotatedMatrix, MatrixUtils.toRotated(sourceMatrix, Matrix.Rotation.RIGHT));
    }

    @Test
    public void testToMirroredWhenSquare() {
        Matrix<Integer> sourceMatrix = Matrix.of(Array.of(1, 2, 3), Array.of(4, 5, 6), Array.of(7, 8, 9));
        Matrix<Integer> rowsMirrored = Matrix.of(Array.of(3, 2, 1), Array.of(6, 5, 4), Array.of(9, 8, 7));
        Matrix<Integer> columnsMirrored = Matrix.of(Array.of(7, 8, 9), Array.of(4, 5, 6), Array.of(1, 2, 3));

        assertEquals(rowsMirrored, MatrixUtils.toMirrored(sourceMatrix, Matrix.Axis.ROWS));
        assertEquals(columnsMirrored, MatrixUtils.toMirrored(sourceMatrix, Matrix.Axis.COLUMNS));
    }

    @Test
    public void testToMirroredWhenNonSquare() {
        Matrix<Character> sourceMatrix = Matrix.of(Array.of('a', 'b', 'c', 'd'), Array.of('e', 'f', 'g', 'h'), Array.of('i', 'j', 'k', 'l'));
        Matrix<Character> rowsMirrored = Matrix.of(Array.of('d', 'c', 'b', 'a'), Array.of('h', 'g', 'f', 'e'), Array.of('l', 'k', 'j', 'i'));
        Matrix<Character> columnsMirrored = Matrix.of(Array.of('i', 'j', 'k', 'l'), Array.of('e', 'f', 'g', 'h'), Array.of('a', 'b', 'c', 'd'));

        assertEquals(rowsMirrored, MatrixUtils.toMirrored(sourceMatrix, Matrix.Axis.ROWS));
        assertEquals(columnsMirrored, MatrixUtils.toMirrored(sourceMatrix, Matrix.Axis.COLUMNS));
    }

    @Test
    public void testToMapped() {
        Matrix<String> matrix = Matrix.of(Array.of("This", "is"), Array.of("a", "test"), Array.of("for", "matrix"));
        assertEquals(Matrix.of(Array.of(4, 2), Array.of(1, 4), Array.of(3, 6)), MatrixUtils.toMapped(matrix, String::length));
    }

    @Test
    public void testMultiplyWithDimensions3x2And2x3() {
        Matrix<Integer> matrix1 = Matrix.of(
                Array.of(1, 2, 3),
                Array.of(4, 5, 6));
        Matrix<Integer> matrix2 = Matrix.of(
                Array.of(1, 2),
                Array.of(3, 4),
                Array.of(5, 6));

        assertEquals(Matrix.of(
                Array.of(22, 28),
                Array.of(49, 64)), MatrixUtils.multiply(matrix1, matrix2, (x, y) -> x * y, (x, y) -> x + y));
        assertEquals(Matrix.of(
                Array.of(22, 28),
                Array.of(49, 64)), MatrixUtils.multiply(matrix1, matrix2, (x, y) -> x * y, (x, y) -> x + y, 0));
        assertEquals(Matrix.of(
                Array.of(9, 12, 15),
                Array.of(19, 26, 33),
                Array.of(29, 40, 51)), MatrixUtils.multiply(matrix2, matrix1, (x, y) -> x * y, (x, y) -> x + y));
        assertEquals(Matrix.of(
                Array.of(9, 12, 15),
                Array.of(19, 26, 33),
                Array.of(29, 40, 51)), MatrixUtils.multiply(matrix2, matrix1, (x, y) -> x * y, (x, y) -> x + y, 0));
    }

    @Test
    public void testMultiplyWithDimensions1x5And5x1() {
        Matrix<Integer> matrix1 = Matrix.of(Array.of(1, 2, 3, 4, 5));
        Matrix<Integer> matrix2 = Matrix.of(Array.of(1), Array.of(2), Array.of(3), Array.of(4), Array.of(5));

        assertEquals(Matrix.of(
                Array.of(55)), MatrixUtils.multiply(matrix1, matrix2, (x, y) -> x * y, (x, y) -> x + y));
        assertEquals(Matrix.of(
                Array.of(55)), MatrixUtils.multiply(matrix1, matrix2, (x, y) -> x * y, (x, y) -> x + y, 0));
        assertEquals(Matrix.of(
                Array.of(1, 2, 3, 4, 5),
                Array.of(2, 4, 6, 8, 10),
                Array.of(3, 6, 9, 12, 15),
                Array.of(4, 8, 12, 16, 20),
                Array.of(5, 10, 15, 20, 25)), MatrixUtils.multiply(matrix2, matrix1, (x, y) -> x * y, (x, y) -> x + y));
        assertEquals(Matrix.of(
                Array.of(1, 2, 3, 4, 5),
                Array.of(2, 4, 6, 8, 10),
                Array.of(3, 6, 9, 12, 15),
                Array.of(4, 8, 12, 16, 20),
                Array.of(5, 10, 15, 20, 25)), MatrixUtils.multiply(matrix2, matrix1, (x, y) -> x * y, (x, y) -> x + y, 0));
    }

    @Test
    public void testMultiplyWithDimensions3x1And2x4() {
        Matrix<Integer> matrix1 = Matrix.of(
                Array.of(1, 2, 3));
        Matrix<Integer> matrix2 = Matrix.of(
                Array.of(1, 2),
                Array.of(3, 4),
                Array.of(5, 6),
                Array.of(7, 8));

        assertThrowsExactly(IllegalArgumentException.class, () -> MatrixUtils.multiply(matrix1, matrix2, (x, y) -> x * y, (x, y) -> x + y));
        assertThrowsExactly(IllegalArgumentException.class, () -> MatrixUtils.multiply(matrix1, matrix2, (x, y) -> x * y, (x, y) -> x + y, 0));
        assertThrowsExactly(IllegalArgumentException.class, () -> MatrixUtils.multiply(matrix2, matrix1, (x, y) -> x * y, (x, y) -> x + y));
        assertThrowsExactly(IllegalArgumentException.class, () -> MatrixUtils.multiply(matrix2, matrix1, (x, y) -> x * y, (x, y) -> x + y, 0));
    }

    @Test
    public void testMultiplyWithDimensions0x0And0x0() {
        Matrix<Integer> matrix = Matrix.of();

        assertEquals(Matrix.of(), MatrixUtils.multiply(matrix, matrix, (x, y) -> x * y, (x, y) -> x + y));
        assertEquals(Matrix.of(), MatrixUtils.multiply(matrix, matrix, (x, y) -> x * y, (x, y) -> x + y, 0));
    }

    @Test
    public void testMultiplyWithDimensions2x0And0x2() {
        Matrix<Integer> matrix1 = Matrix.of(2, 0, (r, c) -> 0);
        Matrix<Integer> matrix2 = Matrix.of(0, 2, (r, c) -> 0);

        assertThrowsExactly(IllegalArgumentException.class, () -> MatrixUtils.multiply(matrix1, matrix2, (x, y) -> x * y, (x, y) -> x + y));
        assertEquals(Matrix.of(
                Array.of(0, 0),
                Array.of(0, 0)), MatrixUtils.multiply(matrix1, matrix2, (x, y) -> x * y, (x, y) -> x + y, 0));
        assertEquals(Matrix.of(), MatrixUtils.multiply(matrix2, matrix1, (x, y) -> x * y, (x, y) -> x + y));
        assertEquals(Matrix.of(), MatrixUtils.multiply(matrix2, matrix1, (x, y) -> x * y, (x, y) -> x + y, 0));
    }
}
