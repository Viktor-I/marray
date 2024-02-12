package org.viktori.matteray;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import org.junit.jupiter.api.Test;

public class MatrixTest {

    @Test
    public void testOfZeroRowsZeroColumns() {
        Matrix<Character> matrix = Matrix.of();
        assertEquals(0, matrix.rows());
        assertEquals(0, matrix.columns());
        assertEquals(0, matrix.size());
    }

    @Test
    public void testOfZeroRowsZeroColumnsWhereEmptyArraysAreUsed() {
        Matrix<Character> matrix = Matrix.of(Array.of(), Array.of(), Array.of());
        assertEquals(0, matrix.rows());
        assertEquals(0, matrix.columns());
        assertEquals(0, matrix.size());
    }

    @Test
    public void testOfOneRowThreeColumns() {
        Matrix<Character> matrix = Matrix.of(Array.of('a', 'b', 'c'));
        assertEquals(1, matrix.rows());
        assertEquals(3, matrix.columns());
        assertEquals(3, matrix.size());
        assertEquals('a', matrix.get(0, 0));
        assertEquals('b', matrix.get(0, 1));
        assertEquals('c', matrix.get(0, 2));
    }

    @Test
    public void testOfOneRowOfOneElementWhichAreOfArrayType() {
        Character[] value = new Character[]{'a', 'b', 'c'};
        Matrix<Character[]> matrix = Matrix.of(Array.<Character[]>of(value));
        assertEquals(1, matrix.rows());
        assertEquals(1, matrix.columns());
        assertEquals(1, matrix.size());
        assertEquals(value, matrix.get(0, 0));
    }

    @Test
    public void testOfOneRowWhichIsNull() {
        assertThrowsExactly(NullPointerException.class, () -> Matrix.of((Array<Character>) null));
    }

    @Test
    public void testOfOneRowOfOneElementWhichIsNull() {
        assertThrowsExactly(NullPointerException.class, () -> Matrix.of((Array.of((Character) null))));
    }

    @Test
    public void testOfTwoRowsOfTwoElements() {
        Matrix<Character> matrix = Matrix.of(Array.of('a', 'b'), Array.of('c', 'd'));
        assertEquals(2, matrix.rows());
        assertEquals(2, matrix.columns());
        assertEquals(4, matrix.size());
        assertEquals('a', matrix.get(0, 0));
        assertEquals('b', matrix.get(0, 1));
        assertEquals('c', matrix.get(1, 0));
        assertEquals('d', matrix.get(1, 1));
    }

    @Test
    public void testOfTwoRowsWhereOneIsNull() {
        assertThrowsExactly(NullPointerException.class, () -> Matrix.of(Array.of('a', 'b'), null));
    }

    @Test
    public void testOfTwoRowsOfTwoElementsWhereOneIsNull() {
        assertThrowsExactly(NullPointerException.class, () -> Matrix.of(Array.of('a', 'b'), Array.of(null, 'd')));
    }

    @Test
    public void testOfTwoRowsWhereColumnsAreOfDifferentSize() {
        assertThrowsExactly(IllegalArgumentException.class, () -> Matrix.of(Array.of('a', 'b'), Array.of('c')));
    }

    @Test
    public void testOfThreeRowsOfTwoElements() {
        Matrix<Character> matrix = Matrix.of(Array.of('a', 'b'), Array.of('c', 'd'), Array.of('e', 'f'));
        assertEquals(3, matrix.rows());
        assertEquals(2, matrix.columns());
        assertEquals(6, matrix.size());
        assertEquals('a', matrix.get(0, 0));
        assertEquals('b', matrix.get(0, 1));
        assertEquals('c', matrix.get(1, 0));
        assertEquals('d', matrix.get(1, 1));
        assertEquals('e', matrix.get(2, 0));
        assertEquals('f', matrix.get(2, 1));
    }

    @Test
    public void testOfThreeRowsWhereTwoAreNull() {
        assertThrowsExactly(NullPointerException.class, () -> Matrix.of(null, null, Array.of('e', 'f')));
    }

    @Test
    public void testOfThreeRowsOfTwoElementsWhereTwoAreNull() {
        assertThrowsExactly(NullPointerException.class, () -> Matrix.of(Array.of(null, 'b'), Array.of('c', null), Array.of('e', 'f')));
    }

    @Test
    public void testOfFourRowsOfTwoElements() {
        Matrix<Character> matrix = Matrix.of(Array.of('a', 'b'), Array.of('c', 'd'), Array.of('e', 'f'), Array.of('g', 'h'));
        assertEquals(4, matrix.rows());
        assertEquals(2, matrix.columns());
        assertEquals(8, matrix.size());
        assertEquals('a', matrix.get(0, 0));
        assertEquals('b', matrix.get(0, 1));
        assertEquals('c', matrix.get(1, 0));
        assertEquals('d', matrix.get(1, 1));
        assertEquals('e', matrix.get(2, 0));
        assertEquals('f', matrix.get(2, 1));
        assertEquals('g', matrix.get(3, 0));
        assertEquals('h', matrix.get(3, 1));
    }

    @Test
    public void testOfFourRowsWhereOneIsNull() {
        assertThrowsExactly(NullPointerException.class, () -> Matrix.of(null, Array.of('c', 'd'), Array.of('e', 'f'), Array.of('g', 'h')));
    }

    @Test
    public void testOfFourRowsOfTwoElementsWhereOneIsNull() {
        assertThrowsExactly(NullPointerException.class, () -> Matrix.of(Array.of('a', null), Array.of('c', 'd'), Array.of('e', 'f'), Array.of('g', 'h')));
    }

    @Test
    public void testOfFiveRowsOfOneElement() {
        Matrix<Character> matrix = Matrix.of(Array.of('a'), Array.of('b'), Array.of('c'), Array.of('d'), Array.of('e'));
        assertEquals(5, matrix.rows());
        assertEquals(1, matrix.columns());
        assertEquals(5, matrix.size());
        assertEquals('a', matrix.get(0, 0));
        assertEquals('b', matrix.get(1, 0));
        assertEquals('c', matrix.get(2, 0));
        assertEquals('d', matrix.get(3, 0));
        assertEquals('e', matrix.get(4, 0));
    }

    @Test
    public void testOfFiveRowsWhereAllAreNull() {
        assertThrowsExactly(NullPointerException.class, () -> Matrix.of(null, null, null, null, null));
    }

    @Test
    public void testOfFiveRowsOfOneElementWhereAllAreNull() {
        assertThrowsExactly(NullPointerException.class, () -> Matrix.of(Array.of((Character) null), Array.of((Character) null), Array.of((Character) null), Array.of((Character) null), Array.of((Character) null)));
    }

    @Test
    public void testOfArbitraryNumberOfRowsAndElements() {
        Matrix<Character> matrix = Matrix.of(Array.of('a', 'b'),
                Array.of('c', 'd'), Array.of('e', 'f'),
                Array.of('g', 'h'), Array.of('i', 'j'),
                Array.of('k', 'l'), Array.of('m', 'n'),
                Array.of('o', 'p'), Array.of('q', 'r'),
                Array.of('s', 't'), Array.of('u', 'v'),
                Array.of('w', 'x'), Array.of('y', 'z'));
        assertEquals(13, matrix.rows());
        assertEquals(2, matrix.columns());
        assertEquals(26, matrix.size());
        assertEquals('a', matrix.get(0, 0));
        assertEquals('b', matrix.get(0, 1));
        assertEquals('c', matrix.get(1, 0));
        assertEquals('z', matrix.get(12, 1));
    }

    @Test
    public void testOfArbitraryNumberOfRowsWhereOneIsNull() {
        assertThrowsExactly(NullPointerException.class, () -> Matrix.of(Array.of('a', 'b'),
                Array.of('c', 'd'), Array.of('e', 'f'),
                Array.of('g', 'h'), Array.of('i', 'j'),
                Array.of('k', 'l'), Array.of('m', 'n'),
                Array.of('o', 'p'), Array.of('q', 'r'),
                Array.of('s', 't'), Array.of('u', 'v'),
                Array.of('w', 'x'), null));
    }

    @Test
    public void testOfArbitraryNumberOfRowsOfWhereOneElementIsNull() {
        assertThrowsExactly(NullPointerException.class, () -> Matrix.of(Array.of('a', 'b'),
                Array.of('c', 'd'), Array.of('e', 'f'),
                Array.of('g', 'h'), Array.of('i', 'j'),
                Array.of('k', 'l'), Array.of('m', 'n'),
                Array.of(null, 'p'), Array.of('q', 'r'),
                Array.of('s', 't'), Array.of('u', 'v'),
                Array.of('w', 'x'), Array.of('y', 'z')));
    }

    @Test
    public void testOfArbitraryNumberOfRowsWhereColumnsAreOfDifferentSize() {
        assertThrowsExactly(IllegalArgumentException.class, () -> Matrix.of(Array.of('a', 'b'),
                Array.of('c', 'd', 'e'), Array.of('f'),
                Array.of('g', 'h', 'i', 'j'), Array.of('k', 'l'),
                Array.of('m', 'n', 'o'), Array.of('p', 'q'),
                Array.of('s', 't'), Array.of('v', 'w', 'x', 'y', 'z')));
    }

    @Test
    public void testOfWithSquareInitFunction() {
        Matrix<Integer> matrix = Matrix.of(3,
                (r, c) -> (r + 1) * (int) Math.pow(10, c));
        assertEquals(3, matrix.rows());
        assertEquals(3, matrix.columns());
        assertEquals(9, matrix.size());
        assertEquals(1, matrix.get(0, 0));
        assertEquals(10, matrix.get(0, 1));
        assertEquals(100, matrix.get(0, 2));
        assertEquals(2, matrix.get(1, 0));
        assertEquals(20, matrix.get(1, 1));
        assertEquals(200, matrix.get(1, 2));
        assertEquals(3, matrix.get(2, 0));
        assertEquals(30, matrix.get(2, 1));
        assertEquals(300, matrix.get(2, 2));
    }

    @Test
    public void testOfWithSquareInitFunctionWhereSomeAreNull() {
        assertThrowsExactly(NullPointerException.class, () -> Matrix.of(3, 3,
                (r, c) -> c % 2 == 0 && r % 2 == 0 ? null : (r + 1) * (int) Math.pow(10, c)));
    }

    @Test
    public void testOfWithInitFunction() {
        Matrix<Integer> matrix = Matrix.of(4, 3,
                (r, c) -> (r + 1) * (int) Math.pow(10, c));
        assertEquals(4, matrix.rows());
        assertEquals(3, matrix.columns());
        assertEquals(12, matrix.size());
        assertEquals(1, matrix.get(0, 0));
        assertEquals(10, matrix.get(0, 1));
        assertEquals(100, matrix.get(0, 2));
        assertEquals(2, matrix.get(1, 0));
        assertEquals(20, matrix.get(1, 1));
        assertEquals(200, matrix.get(1, 2));
        assertEquals(3, matrix.get(2, 0));
        assertEquals(30, matrix.get(2, 1));
        assertEquals(300, matrix.get(2, 2));
        assertEquals(4, matrix.get(3, 0));
        assertEquals(40, matrix.get(3, 1));
        assertEquals(400, matrix.get(3, 2));
    }

    @Test
    public void testOfWithInitFunctionWhereSomeAreNull() {
        assertThrowsExactly(NullPointerException.class, () -> Matrix.of(4, 3,
                (r, c) -> c % 2 == 0 && r % 2 == 0 ? null : (r + 1) * (int) Math.pow(10, c)));
    }

    @Test
    public void testCopyOfTwoRowsOfThreeElementsInMatrix() {
        Matrix<Character> copy = Matrix.copyOf(Matrix.of(Array.of('a', 'b', 'c'), Array.of('d', 'e', 'f')));
        assertEquals(2, copy.rows());
        assertEquals(3, copy.columns());
        assertEquals(6, copy.size());
        assertEquals('a', copy.get(0, 0));
        assertEquals('b', copy.get(0, 1));
        assertEquals('c', copy.get(0, 2));
        assertEquals('d', copy.get(1, 0));
        assertEquals('e', copy.get(1, 1));
        assertEquals('f', copy.get(1, 2));
    }

    @Test
    public void testCopyOfTwoRowsOfThreeElementsInMatrixContainingNulls() {
        assertThrowsExactly(NullPointerException.class, () -> Matrix.copyOf(new ImmutableMatrix<>(Array.of(null, 'b'), Array.of('c', null))));
    }
}
