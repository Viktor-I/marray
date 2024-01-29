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
import java.util.TreeSet;

public class ImmutableArrayTest {

    @Test
    public void testGetIndexFromVarargsConstructor() {
        Array<String> array = new ImmutableArray<>("abc", "def", "ghi");
        assertEquals("abc", array.get(0));
        assertEquals("def", array.get(1));
        assertEquals("ghi", array.get(2));
    }

    @Test
    public void testGetIndexFromFunctionConstructor() {
        Array<String> array = new ImmutableArray<>(25, i -> String.format("%03d", i + 1));
        assertEquals("001", array.get(0));
        assertEquals("013", array.get(12));
        assertEquals("025", array.get(24));
    }

    @Test
    public void testGetIndexFromCollectionConstructor() {
        Array<String> array = new ImmutableArray<>(new TreeSet<>(List.of("def", "ghi", "abc")));
        assertEquals("abc", array.get(0));
        assertEquals("def", array.get(1));
        assertEquals("ghi", array.get(2));
    }

    @Test
    public void testGetIndexFromCopyConstructor() {
        Array<String> array = new ImmutableArray<>(new ImmutableArray<>(25, i -> String.format("%03d", i + 1)));
        assertEquals("001", array.get(0));
        assertEquals("013", array.get(12));
        assertEquals("025", array.get(24));
    }

    @Test
    public void testGetIndexOutOfBounds() {
        Array<String> array = new ImmutableArray<>("abc, def, ghi");
        assertThrowsExactly(ArrayIndexOutOfBoundsException.class, () -> array.get(-1));
        assertThrowsExactly(ArrayIndexOutOfBoundsException.class, () -> array.get(3));
    }

    @Test
    public void testIndexOfWhenFound() {
        Array<String> array = new ImmutableArray<>("abc", "def", "ghi");
        assertEquals(1, array.indexOf("def"));
        assertEquals(1, array.lastIndexOf("def"));
    }

    @Test
    public void testIndexOfWhenNotFound() {
        Array<String> array = new ImmutableArray<>("abc", "def", "ghi");
        assertEquals(-1, array.indexOf("jkl"));
        assertEquals(-1, array.lastIndexOf("jkl"));
    }

    @Test
    public void testIndexOfWhenFoundSeveralTimes() {
        Array<String> array = new ImmutableArray<>("abc", "ghi", "def", "def", "ghi", "def");
        assertEquals(2, array.indexOf("def"));
        assertEquals(5, array.lastIndexOf("def"));
    }

    @Test
    public void testContainsWhenFound() {
        Array<String> array = new ImmutableArray<>("abc", "def", "ghi");
        assertTrue(array.contains("def"));
    }

    @Test
    public void testContainsWhenNotFound() {
        Array<String> array = new ImmutableArray<>("abc", "def", "ghi");
        assertFalse(array.contains("jkl"));
    }

    @Test
    public void testContainsAllWhenAllFound() {
        Array<String> array = new ImmutableArray<>("abc", "def", "ghi");
        assertTrue(array.containsAll(Set.of("abc", "def")));
    }

    @Test
    public void testContainsAllWhenPartiallyFound() {
        Array<String> array = new ImmutableArray<>("abc", "def", "ghi");
        assertFalse(array.containsAll(Set.of("abc", "jkl")));
    }

    @Test
    public void testContainsAllWhenNotFound() {
        Array<String> array = new ImmutableArray<>("abc", "def", "ghi");
        assertFalse(array.containsAll(Set.of("jkl")));
    }

    @Test
    public void testSizeAndEmptyWhenEmpty() {
        Array<String> array = new ImmutableArray<>();
        assertTrue(array.isEmpty());
        assertEquals(0, array.size());
    }

    @Test
    public void testSizeAndEmptyWhenNotEmpty() {
        Array<String> array = new ImmutableArray<>("abc", "def");
        assertFalse(array.isEmpty());
        assertEquals(2, array.size());
    }

    @Test
    public void testIteratableInterface() {
        Array<Boolean> array = new ImmutableArray<>(10, i -> i % 2 == 0);

        int loops = 0;
        boolean expected = false;
        for (Boolean b : array) {
            expected = !expected; // Even or odd
            loops++;
            assertEquals(expected, b);
        }
        assertEquals(10, loops);
    }

    @Test
    public void testToList() {
        Array<String> array = new ImmutableArray<>("abc", "def", "ghi");

        assertEquals(List.of("abc", "def", "ghi"), array.toList());
    }

    @Test
    public void testToArray() {
        Array<String> array = new ImmutableArray<>("abc", "def", "ghi");

        assertArrayEquals(new Object[]{"abc", "def", "ghi"}, array.toArray());
        assertArrayEquals(new String[]{"abc", "def", "ghi"}, array.toArray(new String[0]));
        assertArrayEquals(new String[]{"abc", "def", "ghi"}, array.toArray(new String[array.size()]));
        assertArrayEquals(new String[]{"abc", "def", "ghi"}, array.toArray(String[]::new));
    }

    @Test
    public void testToArrayDoesNotAllowMutation() {
        Array<String> array = new ImmutableArray<>("abc", "def", "ghi");

        Object[] rawArray = array.toArray();
        Arrays.fill(rawArray, "Mutated");
        assertEquals("Mutated", rawArray[1]);
        assertEquals("def", array.get(1));
    }

    @Test
    public void testMutate() {
        Array<Integer> array = new ImmutableArray<>(10, i -> i);

        assertThrowsExactly(UnsupportedOperationException.class, () -> array.add(11));
        assertThrowsExactly(UnsupportedOperationException.class, () -> array.addAll(List.of(11, 12, 13)));
        assertThrowsExactly(UnsupportedOperationException.class, () -> array.remove(9));
        assertThrowsExactly(UnsupportedOperationException.class, () -> array.removeIf((i) -> i % 2 == 0));
        assertThrowsExactly(UnsupportedOperationException.class, () -> array.retainAll(List.of(1, 2, 3)));
        assertThrowsExactly(UnsupportedOperationException.class, () -> array.clear());
    }
}
