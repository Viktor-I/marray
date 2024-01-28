package org.viktori.marray;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

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
