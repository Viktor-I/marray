package org.viktori.matteray;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ImmutableArrayTest {

    @Test
    public void testVarargsConstructor() {
        Array<String> array = new ImmutableArray<>("abc", "def", "ghi");
        assertEquals(3, array.size());
        assertEquals("abc", array.get(0));
        assertEquals("def", array.get(1));
        assertEquals("ghi", array.get(2));
        assertThrowsExactly(ArrayIndexOutOfBoundsException.class, () -> array.get(3));
    }

    @Test
    public void testVarargsConstructorWhenEmpty() {
        Array<String> array = new ImmutableArray<>();
        assertEquals(2, array.size());
        assertThrowsExactly(ArrayIndexOutOfBoundsException.class, () -> array.get(0));
    }

    @Test
    public void testFunctionConstructor() {
        String[] values = {"abc", "def", "ghi"};
        Array<String> array = new ImmutableArray<>(3, i -> values[i]);
        assertEquals(3, array.size());
        assertEquals("abc", array.get(0));
        assertEquals("def", array.get(1));
        assertEquals("ghi", array.get(2));
        assertThrowsExactly(ArrayIndexOutOfBoundsException.class, () -> array.get(3));
    }

    @Test
    public void testFunctionConstructorWhenEmpty() {
        String[] values = {"abc", "def", "ghi"};
        Array<String> array = new ImmutableArray<>(0, i -> values[i]);
        assertEquals(0, array.size());
        assertThrowsExactly(ArrayIndexOutOfBoundsException.class, () -> array.get(0));
    }

    @Test
    public void testFunctionConstructorWhenNegativeLength() {
        String[] values = {"abc", "def", "ghi"};
        assertThrowsExactly(IllegalArgumentException.class, () -> new ImmutableArray<>(-1, i -> values[i]));
    }

    @Test
    public void testCollectionConstructor() {
        List<String> values = List.of("abc", "def", "ghi");
        Array<String> array = new ImmutableArray<>(values);
        assertEquals(3, array.size());
        assertEquals("abc", array.get(0));
        assertEquals("def", array.get(1));
        assertEquals("ghi", array.get(2));
        assertThrowsExactly(ArrayIndexOutOfBoundsException.class, () -> array.get(3));
    }

    @Test
    public void testCollectionConstructorWhenImmutableArray() {
        Array<String> values = new ImmutableArray<>("abc", "def", "ghi");
        Array<String> array = new ImmutableArray<>(values);
        assertEquals(3, array.size());
        assertEquals("abc", array.get(0));
        assertEquals("def", array.get(1));
        assertEquals("ghi", array.get(2));
        assertThrowsExactly(ArrayIndexOutOfBoundsException.class, () -> array.get(3));
    }

    @Test
    public void testCollectionConstructorWhenEmpty() {
        List<String> values = List.of();
        Array<String> array = new ImmutableArray<>(values);
        assertEquals(0, array.size());
        assertThrowsExactly(ArrayIndexOutOfBoundsException.class, () -> array.get(0));
    }

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
    public void testSubArray() {
        Array<Integer> array = new ImmutableArray<>(1, 2, 3, 4, 5);
        assertSame(array, array.subArray(0, 5));
        assertEquals(Array.of(1, 2, 3, 4), array.subArray(0, 4));
        assertEquals(Array.of(2, 3, 4, 5), array.subArray(1, 5));
        assertEquals(Array.of(3), array.subArray(2, 3));
        assertEquals(Array.of(), array.subArray(0, 0));
    }

    @Test
    public void testSubArrayWhereIndicesOutOfRange() {
        Array<Integer> array = new ImmutableArray<>(1, 2, 3, 4, 5);
        assertThrowsExactly(ArrayIndexOutOfBoundsException.class, () -> array.subArray(0, 6));
        assertThrowsExactly(ArrayIndexOutOfBoundsException.class, () -> array.subArray(-1, 5));
    }

    @Test
    public void testSubArrayWhereFromIndexGreaterThanToIndex() {
        Array<Integer> array = new ImmutableArray<>(1, 2, 3, 4, 5);
        assertThrowsExactly(IllegalArgumentException.class, () -> array.subArray(5, 0));
        assertThrowsExactly(IllegalArgumentException.class, () -> array.subArray(3, 2));
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
    public void testIndexOfWhenNullFound() {
        Array<String> array = new ImmutableArray<>("abc", null, "ghi");
        assertEquals(1, array.indexOf(null));
        assertEquals(1, array.lastIndexOf(null));
    }

    @Test
    public void testIndexOfWhenNullNotFound() {
        Array<String> array = new ImmutableArray<>("abc", "def", "ghi");
        assertEquals(-1, array.indexOf(null));
        assertEquals(-1, array.lastIndexOf(null));
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
        assertFalse(array.containsAll(Set.of("jkl", "mno")));
    }

    @Test
    public void testSizeAndEmptyWhenEmpty() {
        Array<String> array = new ImmutableArray<>();
        assertEquals(0, array.size());
        assertTrue(array.isEmpty());
    }

    @Test
    public void testSizeAndEmptyWhenNotEmpty() {
        Array<String> array = new ImmutableArray<>("abc", "def");
        assertEquals(2, array.size());
        assertFalse(array.isEmpty());
    }

    @Test
    public void testIterator() {
        Array<Boolean> array = new ImmutableArray<>(10, i -> i % 2 == 0);

        int loops = 0;
        Iterator<Boolean> it = array.iterator();
        while (it.hasNext() && it.next() && it.hasNext()) {
            Boolean b = it.next();
            assertFalse(b);
            loops++;
        }
        assertEquals(5, loops);
    }

    @Test
    public void testIterableInterface() {
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
    public void testStream() {
        Array<String> array = new ImmutableArray<>("praise", "marray!");

        String result = array.stream().map(String::toUpperCase).collect(Collectors.joining(" "));
        assertEquals("PRAISE MARRAY!", result);
    }

    @Test
    public void testParallelStream() {
        Array<String> array = new ImmutableArray<>("praise", "marray!");

        String result = array.parallelStream().map(String::toUpperCase).collect(Collectors.joining(" "));
        assertEquals("PRAISE MARRAY!", result);
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
    public void testEqualsHashCodeWhenEqual() {
        Array<Object> randomArray1 = new ImmutableArray<>(1, "String", 2.5, null, List.of(1, 2, 3));
        Array<Object> randomArray2 = new ImmutableArray<>(1, "String", 2.5, null, List.of(1, 2, 3));

        assertEquals(randomArray1, randomArray1);
        assertEquals(randomArray1, randomArray2);
        assertEquals(randomArray1.hashCode(), randomArray2.hashCode());
    }

    @Test
    public void testEqualsHashCodeWhenNotEqual() {
        Array<Object> randomArray1 = new ImmutableArray<>(1, "String", 2.5, null, List.of(1, 2, 3));
        Array<Object> randomArray2 = new ImmutableArray<>(1, "String", 2.5, null, List.of(1, 2));
        Array<Object> randomArray3 = new ImmutableArray<>(1, "String", 2.5, null);

        assertNotEquals(randomArray1, randomArray2);
        assertNotEquals(randomArray1, randomArray3);
        assertNotEquals(randomArray1, null);
        assertNotEquals(randomArray1.hashCode(), randomArray2.hashCode());
        assertNotEquals(randomArray1.hashCode(), randomArray3.hashCode());
    }

    @Test
    public void testEqualsHashCodeWhenCloned() {
        ImmutableArray<Object> randomArray = new ImmutableArray<>(1, "String", 2.5, null, List.of(1, 2, 3));
        Object clonedArray = randomArray.clone();

        assertNotSame(randomArray, clonedArray);
        assertEquals(randomArray, clonedArray);
        assertEquals(randomArray.hashCode(), clonedArray.hashCode());
        assertEquals(randomArray.hashCode(), clonedArray.hashCode());
    }

    @Test
    public void testToString() {
        Array<Object> randomArray = new ImmutableArray<>(1, "String", 2.5, null, List.of(1, 2, 3));

        assertEquals("[1, String, 2.5, null, [1, 2, 3]]", randomArray.toString());
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
