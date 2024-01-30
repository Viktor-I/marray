package org.viktori.marray;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class ImmutableMatrixTest {

    @Test
    public void testGetIndexFrom2DArrayConstructor() {
        Matrix<String> matrix = new ImmutableMatrix<>(new String[][]{{"a", "b", "c"}, {"d", "e", "f"}, {"g", "h", "i"}});
        assertEquals("a", matrix.get(0, 0));
        assertEquals("b", matrix.get(0, 1));
        assertEquals("c", matrix.get(0, 2));
        assertEquals("d", matrix.get(1, 0));
        assertEquals("e", matrix.get(1, 1));
        assertEquals("f", matrix.get(1, 2));
        assertEquals("g", matrix.get(2, 0));
        assertEquals("h", matrix.get(2, 1));
        assertEquals("i", matrix.get(2, 2));
    }

    @Test
    public void testGetIndexFromSquareFunctionConstructor() {
        Matrix<String> matrix = new ImmutableMatrix<>(25, (r, c) -> String.format("%03d, %03d", r + 1, c + 1));
        assertEquals("001, 001", matrix.get(0, 0));
        assertEquals("013, 020", matrix.get(12, 19));
        assertEquals("025, 025", matrix.get(24, 24));
    }

    @Test
    public void testGetIndexFromFunctionConstructor() {
        Matrix<String> matrix = new ImmutableMatrix<>(5, 10, (r, c) -> String.format("%03d, %03d", r + 1, c + 1));
        assertEquals("001, 001", matrix.get(0, 0));
        assertEquals("003, 008", matrix.get(2, 7));
        assertEquals("005, 010", matrix.get(4, 9));
    }

    @Test
    public void testGetIndexFromCollectionConstructor() {
        Matrix<String> matrix = new ImmutableMatrix<>(List.of(List.of("d", "ef"), List.of("g", "hi"), List.of("ab", "c")));
        assertEquals("d", matrix.get(0, 0));
        assertEquals("ef", matrix.get(0, 1));
        assertEquals("g", matrix.get(1, 0));
        assertEquals("hi", matrix.get(1, 1));
        assertEquals("ab", matrix.get(2, 0));
        assertEquals("c", matrix.get(2, 1));
    }

    @Test
    public void testGetIndexFromCopyConstructor() {
        Matrix<String> matrix = new ImmutableMatrix<>(new ImmutableMatrix<String>(5, 10, (r, c) -> String.format("%03d, %03d", r + 1, c + 1)));
        assertEquals("001, 001", matrix.get(0, 0));
        assertEquals("003, 008", matrix.get(2, 7));
        assertEquals("005, 010", matrix.get(4, 9));
    }

    @Test
    public void testGetIndexOutOfBounds() {
        Matrix<String> matrix = new ImmutableMatrix<>(new String[][]{{"a", "b", "c"}, {"d", "e", "f"}});
        assertThrowsExactly(ArrayIndexOutOfBoundsException.class, () -> matrix.get(-1, 0));
        assertThrowsExactly(ArrayIndexOutOfBoundsException.class, () -> matrix.get(2, 0));
        assertThrowsExactly(ArrayIndexOutOfBoundsException.class, () -> matrix.get(0, -1));
        assertThrowsExactly(ArrayIndexOutOfBoundsException.class, () -> matrix.get(0, 3));
    }

    @Test
    public void testGetIllegalArgumentFromColumnRowConsistency() {
        assertThrowsExactly(IllegalArgumentException.class, () -> new ImmutableMatrix<>(new String[][]{{"a", "b", "c"}, {"d", "e"}}));
        assertThrowsExactly(IllegalArgumentException.class, () -> new ImmutableMatrix<>(List.of(List.of("a", "b"), List.of("c", "d", "e"))));
    }

    @Test
    public void testContainsWhenFound() {
        Matrix<String> matrix = new ImmutableMatrix<>(new String[][]{{"a", "b", "c"}, {"d", "e", "f"}, {"g", "h", "i"}});
        assertTrue(matrix.contains("f"));
    }

    @Test
    public void testContainsWhenNotFound() {
        Matrix<String> matrix = new ImmutableMatrix<>(new String[][]{{"a", "b", "c"}, {"d", "e", "f"}, {"g", "h", "i"}});
        assertFalse(matrix.contains("j"));
    }

    @Test
    public void testContainsAllWhenAllFound() {
        Matrix<String> matrix = new ImmutableMatrix<>(new String[][]{{"a", "b", "c"}, {"d", "e", "f"}, {"g", "h", "i"}});
        assertTrue(matrix.containsAll(Set.of("a", "g", "h")));
    }

    @Test
    public void testContainsAllWhenPartiallyFound() {
        Matrix<String> matrix = new ImmutableMatrix<>(new String[][]{{"a", "b", "c"}, {"d", "e", "f"}, {"g", "h", "i"}});
        assertFalse(matrix.containsAll(Set.of("a", "j")));
    }

    @Test
    public void testContainsAllWhenNotFound() {
        Matrix<String> matrix = new ImmutableMatrix<>(new String[][]{{"a", "b", "c"}, {"d", "e", "f"}, {"g", "h", "i"}});
        assertFalse(matrix.containsAll(Set.of("j")));
    }

    @Test
    public void testSizeAndEmptyWhenEmpty() {
        Matrix<String> matrix = new ImmutableMatrix<>(0, (r, c) -> "");
        assertTrue(matrix.isEmpty());
        assertEquals(0, matrix.size());
        assertEquals(0, matrix.rows());
        assertEquals(0, matrix.columns());
    }

    @Test
    public void testSizeAndEmptyWhenNotEmpty() {
        Matrix<String> matrix = new ImmutableMatrix<>(3, 2, (r, c) -> "");
        assertFalse(matrix.isEmpty());
        assertEquals(6, matrix.size());
        assertEquals(3, matrix.rows());
        assertEquals(2, matrix.columns());
    }

    @Test
    public void testSizeAndEmptyWhenNoRows() {
        Matrix<String> matrix = new ImmutableMatrix<>(0, 1, (r, c) -> "");
        assertTrue(matrix.isEmpty());
        assertEquals(0, matrix.size());
        assertEquals(0, matrix.rows());
        assertEquals(1, matrix.columns());
    }

    @Test
    public void testSizeAndEmptyWhenNoColumns() {
        Matrix<String> matrix = new ImmutableMatrix<>(2, 0, (r, c) -> "");
        assertTrue(matrix.isEmpty());
        assertEquals(0, matrix.size());
        assertEquals(2, matrix.rows());
        assertEquals(0, matrix.columns());
    }

    @Test
    public void testIteratableInterface() {
        Matrix<String> matrix = new ImmutableMatrix<>(4, 3, (r, c) -> (r + 1) + "," + (c + 1));

        Array<String> expected = new ImmutableArray<>("1,1", "1,2", "1,3", "2,1", "2,2", "2,3", "3,1", "3,2", "3,3", "4,1", "4,2", "4,3");

        int loops = 0;
        for (String s : matrix) {
            assertEquals(expected.get(loops), s);
            loops++;
        }
        assertEquals(12, loops);
    }

    @Test
    public void testToArray() {
        Matrix<String> matrix = new ImmutableMatrix<>(new String[][]{{"a", "b", "c", "d"}, {"e", "f", "g", "h"}});

        assertArrayEquals(new Object[]{"a", "b", "c", "d", "e", "f", "g", "h"}, matrix.toArray());
        assertArrayEquals(new String[]{"a", "b", "c", "d", "e", "f", "g", "h"}, matrix.toArray(new String[0]));
        assertArrayEquals(new String[]{"a", "b", "c", "d", "e", "f", "g", "h"}, matrix.toArray(new String[matrix.size()]));
        assertArrayEquals(new String[]{"a", "b", "c", "d", "e", "f", "g", "h"}, matrix.toArray(String[]::new));
    }

    @Test
    public void testToArrayDoesNotAllowMutation() {
        Matrix<String> matrix = new ImmutableMatrix<>(new String[][]{{"a", "b", "c", "d"}, {"e", "f", "g", "h"}});

        Object[] rawArray = matrix.toArray();
        Arrays.fill(rawArray, "Mutated");
        assertEquals("Mutated", rawArray[5]);
        assertEquals("f", matrix.get(1, 1));
    }

    @Test
    public void testToArray2D() {
        Matrix<String> matrix = new ImmutableMatrix<>(new String[][]{{"a", "b", "c", "d"}, {"e", "f", "g", "h"}});

        assertArrayEquals(new Object[][]{{"a", "b", "c", "d"}, {"e", "f", "g", "h"}}, matrix.toArray2D());
        assertArrayEquals(new String[][]{{"a", "b", "c", "d"}, {"e", "f", "g", "h"}}, matrix.toArray2D(new String[0][0]));
        assertArrayEquals(new String[][]{{"a", "b", "c", "d"}, {"e", "f", "g", "h"}}, matrix.toArray2D(new String[matrix.rows()][matrix.columns()]));
        assertArrayEquals(new String[][]{{"a", "b", "c", "d"}, {"e", "f", "g", "h"}}, matrix.toArray2D(String[][]::new));
    }

    @Test
    public void testToArray2DDoesNotAllowMutation() {
        Matrix<String> matrix = new ImmutableMatrix<>(new String[][]{{"a", "b", "c", "d"}, {"e", "f", "g", "h"}});

        Object[][] rawArray2D = matrix.toArray2D();
        for (int r = 0; r < rawArray2D.length; r++) {
            Arrays.fill(rawArray2D[r], "Mutated");
        }
        assertEquals("Mutated", rawArray2D[1][1]);
        assertEquals("f", matrix.get(1, 1));
    }

    @Test
    public void testToRotatedMatrixWhenSquare() {
        Matrix<Integer> sourceMatrix = new ImmutableMatrix<>(new Integer[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}});
        Matrix<Integer> leftRotatedMatrix = new ImmutableMatrix<>(new Integer[][]{{3, 6, 9}, {2, 5, 8}, {1, 4, 7}});
        Matrix<Integer> halfRotatedMatrix = new ImmutableMatrix<>(new Integer[][]{{9, 8, 7}, {6, 5, 4}, {3, 2, 1}});
        Matrix<Integer> rightRotatedMatrix = new ImmutableMatrix<>(new Integer[][]{{7, 4, 1}, {8, 5, 2}, {9, 6, 3}});

        assertEquals(sourceMatrix, sourceMatrix.toRotatedMatrix(Matrix.Rotation.NONE));
        assertEquals(leftRotatedMatrix, sourceMatrix.toRotatedMatrix(Matrix.Rotation.LEFT));
        assertEquals(halfRotatedMatrix, sourceMatrix.toRotatedMatrix(Matrix.Rotation.HALF));
        assertEquals(rightRotatedMatrix, sourceMatrix.toRotatedMatrix(Matrix.Rotation.RIGHT));
    }

    @Test
    public void testToRotatedMatrixWhenNonSquare() {
        Matrix<Character> sourceMatrix = new ImmutableMatrix<>(new Character[][]{{'a', 'b', 'c', 'd'}, {'e', 'f', 'g', 'h'}, {'i', 'j', 'k', 'l'}});
        Matrix<Character> leftRotatedMatrix = new ImmutableMatrix<>(new Character[][]{{'d', 'h', 'l'}, {'c', 'g', 'k'}, {'b', 'f', 'j'}, {'a', 'e', 'i'}});
        Matrix<Character> halfRotatedMatrix = new ImmutableMatrix<>(new Character[][]{{'l', 'k', 'j', 'i'}, {'h', 'g', 'f', 'e'}, {'d', 'c', 'b', 'a'}});
        Matrix<Character> rightRotatedMatrix = new ImmutableMatrix<>(new Character[][]{{'i', 'e', 'a'}, {'j', 'f', 'b'}, {'k', 'g', 'c'}, {'l', 'h', 'd'}});

        assertEquals(sourceMatrix, sourceMatrix.toRotatedMatrix(Matrix.Rotation.NONE));
        assertEquals(leftRotatedMatrix, sourceMatrix.toRotatedMatrix(Matrix.Rotation.LEFT));
        assertEquals(halfRotatedMatrix, sourceMatrix.toRotatedMatrix(Matrix.Rotation.HALF));
        assertEquals(rightRotatedMatrix, sourceMatrix.toRotatedMatrix(Matrix.Rotation.RIGHT));
    }

    @Test
    public void testToMirroredMatrixWhenSquare() {
        Matrix<Integer> sourceMatrix = new ImmutableMatrix<>(new Integer[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}});
        Matrix<Integer> rowsMirrored = new ImmutableMatrix<>(new Integer[][]{{3, 2, 1}, {6, 5, 4}, {9, 8, 7}});
        Matrix<Integer> columnsMirrored = new ImmutableMatrix<>(new Integer[][]{{7, 8, 9}, {4, 5, 6}, {1, 2, 3}});

        assertEquals(rowsMirrored, sourceMatrix.toMirroredMatrix(Matrix.Axis.ROWS));
        assertEquals(columnsMirrored, sourceMatrix.toMirroredMatrix(Matrix.Axis.COLUMNS));
    }

    @Test
    public void testToMirroredMatrixWhenNonSquare() {
        Matrix<Character> sourceMatrix = new ImmutableMatrix<>(new Character[][]{{'a', 'b', 'c', 'd'}, {'e', 'f', 'g', 'h'}, {'i', 'j', 'k', 'l'}});
        Matrix<Character> rowsMirrored = new ImmutableMatrix<>(new Character[][]{{'d', 'c', 'b', 'a'}, {'h', 'g', 'f', 'e'}, {'l', 'k', 'j', 'i'}});
        Matrix<Character> columnsMirrored = new ImmutableMatrix<>(new Character[][]{{'i', 'j', 'k', 'l'}, {'e', 'f', 'g', 'h'}, {'a', 'b', 'c', 'd'}});

        assertEquals(rowsMirrored, sourceMatrix.toMirroredMatrix(Matrix.Axis.ROWS));
        assertEquals(columnsMirrored, sourceMatrix.toMirroredMatrix(Matrix.Axis.COLUMNS));
    }

    @Test
    public void testMutate() {
        Matrix<Integer> matrix = new ImmutableMatrix<>(10, (r, c) -> r * c);

        assertThrowsExactly(UnsupportedOperationException.class, () -> matrix.add(11));
        assertThrowsExactly(UnsupportedOperationException.class, () -> matrix.addAll(List.of(11, 12, 13)));
        assertThrowsExactly(UnsupportedOperationException.class, () -> matrix.remove(9));
        assertThrowsExactly(UnsupportedOperationException.class, () -> matrix.removeIf((i) -> i % 2 == 0));
        assertThrowsExactly(UnsupportedOperationException.class, () -> matrix.retainAll(List.of(1, 2, 3)));
        assertThrowsExactly(UnsupportedOperationException.class, () -> matrix.clear());
    }
}
