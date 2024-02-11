package org.viktori.matteray;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class ArrayTest {

    @Test
    public void testOfZeroElements() {
        Array<Character> array = Array.of();
        assertEquals(0, array.size());
    }

    @Test
    public void testOfOneElement() {
        Array<Character> array = Array.of('a');
        assertEquals(1, array.size());
        assertEquals('a', array.get(0));
    }

    @Test
    public void testOfOneElementWhichIsOfArrayType() {
        Character[] value = new Character[]{'a', 'b', 'c'};
        Array<Character[]> array = Array.<Character[]>of(value);
        assertEquals(1, array.size());
        assertEquals(value, array.get(0));
    }

    @Test
    public void testOfOneElementWhichIsNull() {
        assertThrowsExactly(NullPointerException.class, () -> Array.of((Character) null));
    }

    @Test
    public void testOfTwoElements() {
        Array<Character> array = Array.of('a', 'b');
        assertEquals(2, array.size());
        assertEquals('a', array.get(0));
        assertEquals('b', array.get(1));
    }

    @Test
    public void testOfTwoElementsWhereOneIsNull() {
        assertThrowsExactly(NullPointerException.class, () -> Array.of('a', null));
    }

    @Test
    public void testOfThreeElements() {
        Array<Character> array = Array.of('a', 'b', 'c');
        assertEquals(3, array.size());
        assertEquals('a', array.get(0));
        assertEquals('b', array.get(1));
        assertEquals('c', array.get(2));
    }

    @Test
    public void testOfThreeElementsWhereTwoAreNull() {
        assertThrowsExactly(NullPointerException.class, () -> Array.of(null, null, 'c'));
    }

    @Test
    public void testOfFourElements() {
        Array<Character> array = Array.of('a', 'b', 'c', 'd');
        assertEquals(4, array.size());
        assertEquals('a', array.get(0));
        assertEquals('b', array.get(1));
        assertEquals('c', array.get(2));
        assertEquals('d', array.get(3));
    }

    @Test
    public void testOfFourElementsWhereOneIsNull() {
        assertThrowsExactly(NullPointerException.class, () -> Array.of(null, 'b', 'c', 'd'));
    }

    @Test
    public void testOfFiveElements() {
        Array<Character> array = Array.of('a', 'b', 'c', 'd', 'e');
        assertEquals(5, array.size());
        assertEquals('a', array.get(0));
        assertEquals('b', array.get(1));
        assertEquals('c', array.get(2));
        assertEquals('d', array.get(3));
        assertEquals('e', array.get(4));
    }

    @Test
    public void testOfFiveElementsWhereAllAreNull() {
        assertThrowsExactly(NullPointerException.class, () -> Array.of(null, null, null, null, null));
    }

    @Test
    public void testOfArbitraryNumberOfElements() {
        Array<Character> array = Array.of('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
                'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z');
        assertEquals(26, array.size());
        assertEquals('a', array.get(0));
        assertEquals('b', array.get(1));
        assertEquals('c', array.get(2));
        assertEquals('z', array.get(25));
    }

    @Test
    public void testOfArbitraryNumberOfElementsWhereOneIsNull() {
        assertThrowsExactly(NullPointerException.class, () -> Array.of('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
                'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', null));
    }

    @Test
    public void testCopyOfThreeElementsInArray() {
        Array<Character> copy = Array.copyOf(Array.of('a', 'b', 'c'));
        assertEquals(3, copy.size());
        assertEquals('a', copy.get(0));
        assertEquals('b', copy.get(1));
        assertEquals('c', copy.get(2));
    }

    @Test
    public void testCopyOfThreeElementsInArrayContainingNulls() {
        assertThrowsExactly(NullPointerException.class, () -> Array.copyOf(new ImmutableArray<>(null, 'b', null)));
    }

    @Test
    public void testCopyOfThreeElementsInList() {
        Array<Character> copy = Array.copyOf(List.of('a', 'b', 'c'));
        assertEquals(3, copy.size());
        assertEquals('a', copy.get(0));
        assertEquals('b', copy.get(1));
        assertEquals('c', copy.get(2));
    }

    @Test
    public void testCopyOfThreeElementsInListContainingNulls() {
        assertThrowsExactly(NullPointerException.class, () -> Array.copyOf(Arrays.asList(null, 'b', null)));
    }
}
