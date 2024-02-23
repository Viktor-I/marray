package org.viktori.matteray.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import org.junit.jupiter.api.Test;
import org.viktori.matteray.Array;
import org.viktori.matteray.ImmutableArray;

import java.util.Comparator;
import java.util.Optional;
import java.util.function.BinaryOperator;

public class ArrayUtilsTest {

    @Test
    public void testApplyForEachWhenSameType() {
        Array<String> array = Array.of("Test", "of", "applyForEach");
        assertEquals(Array.of("TestTestTest", "ofofof", "applyForEachapplyForEachapplyForEach"), ArrayUtils.applyForEach(array, str -> str.repeat(3)));
    }

    @Test
    public void testApplyForEachWhenDifferentType() {
        Array<String> array = Array.of("Test", "of", "applyForEach");
        assertEquals(Array.of(4, 2, 12), ArrayUtils.applyForEach(array, String::length));
    }

    @Test
    public void testApplyForEachWhenNullArgument() {
        Array<String> array = Array.of("Test", "of", "applyForEach");
        assertThrows(NullPointerException.class, () -> ArrayUtils.applyForEach(null, str -> ""));
        assertThrows(NullPointerException.class, () -> ArrayUtils.applyForEach(array, null));
    }

    @Test
    public void testMergeForEachWhenDifferentType() {
        Array<String> array1 = Array.of("Test", "of", "applyForEach");
        Array<Double> array2 = Array.of(1.23, 4.56, 7.89);
        assertEquals(Array.of(9, 7, 17), ArrayUtils.mergeForEach(array1, array2, (str, db) -> (str + ";" + db).length()));
        assertEquals(Array.of(9, 7, 17), ArrayUtils.mergeForEach(array2, array1, (str, db) -> (db + ";" + str).length()));
    }

    @Test
    public void testMergeForEachWhenSameType() {
        Array<Integer> array1 = Array.of(1, 2, 3);
        Array<Integer> array2 = Array.of(10, 20, 30);
        assertEquals(Array.of(11, 22, 33), ArrayUtils.mergeForEach(array1, array2, Integer::sum));
        assertEquals(Array.of(11, 22, 33), ArrayUtils.mergeForEach(array2, array1, Integer::sum));
    }

    @Test
    public void testMergeForEachWhenOneArrayIsShorter() {
        Array<Integer> array1 = Array.of(1, 2, 3, 4, 5);
        Array<Integer> array2 = Array.of(10, 20, 30);
        assertEquals(Array.of(11, 22, 33), ArrayUtils.mergeForEach(array1, array2, Integer::sum));
        assertEquals(Array.of(11, 22, 33), ArrayUtils.mergeForEach(array2, array1, Integer::sum));
    }

    @Test
    public void testMergeForEachWhenNullArgument() {
        Array<String> array = Array.of("Test", "of", "applyForEach");
        assertThrows(NullPointerException.class, () -> ArrayUtils.mergeForEach(null, array, (str1, str2) -> ""));
        assertThrows(NullPointerException.class, () -> ArrayUtils.mergeForEach(array, null, (str1, str2) -> ""));
        assertThrows(NullPointerException.class, () -> ArrayUtils.mergeForEach(array, array, null));
    }

    @Test
    public void testToSorted() {
        Array<String> array = Array.of("test", "of", "applyForEach");
        assertEquals(Array.of("applyForEach", "of", "test"), ArrayUtils.toSorted(array));
        assertEquals(Array.of("of", "test", "applyForEach"), ArrayUtils.toSorted(array, Comparator.comparing(String::length)));
    }

    @Test
    public void testToSortedReturnsNewInstance() {
        Array<String> array = Array.of("test", "of", "applyForEach");
        assertNotSame(array, ArrayUtils.toSorted(array));
        assertNotSame(array, ArrayUtils.toSorted(array, Comparator.comparing(String::length)));
    }

    @Test
    public void testToReversed() {
        Array<Integer> array = Array.of(1252, 292151, -24, 2, 262);
        assertEquals(Array.of(262, 2, -24, 292151, 1252), ArrayUtils.toReversed(array));
    }

    @Test
    public void testToReversedReturnsNewInstance() {
        Array<Integer> array = Array.of(1252, 292151, -24, 2, 262);
        assertNotSame(array, ArrayUtils.toReversed(array));
    }

    @Test
    public void testToMapped() {
        Array<String> array = Array.of("This", "is", "a", "test");
        assertEquals(Array.of(4, 2, 1, 4), ArrayUtils.toMapped(array, String::length));
    }

    @Test
    void testAggregateWithSumOfIntegers() {
        assertEquals(0, ArrayUtils.aggregate(Array.of(), Integer::sum, 0));
        assertEquals(5, ArrayUtils.aggregate(Array.of(5), Integer::sum, 0));
        assertEquals(15, ArrayUtils.aggregate(Array.of(5, 10), Integer::sum, 0));
        assertEquals(-5, ArrayUtils.aggregate(Array.of(5, -10), Integer::sum, 0));
        assertEquals(120, ArrayUtils.aggregate(Array.of(5, 12, 103), Integer::sum, 0));
    }

    @Test
    void testAggregateWithFactorOfIntegers() {
        assertEquals(0, ArrayUtils.aggregate(Array.of(), (curr, next) -> curr * next, 0));
        assertEquals(5, ArrayUtils.aggregate(Array.of(5), (curr, next) -> curr * next, 0));
        assertEquals(50, ArrayUtils.aggregate(Array.of(5, 10), (curr, next) -> curr * next, 0));
        assertEquals(-50, ArrayUtils.aggregate(Array.of(5, -10), (curr, next) -> curr * next, 0));
        assertEquals(6180, ArrayUtils.aggregate(Array.of(5, 12, 103), (curr, next) -> curr * next, 0));
    }

    @Test
    void testAggregateWithMinMaxOfIntegers() {
        assertEquals(0, ArrayUtils.aggregate(Array.of(), Integer::min, 0));
        assertEquals(0, ArrayUtils.aggregate(Array.of(), Integer::max, 0));
        assertEquals(5, ArrayUtils.aggregate(Array.of(5), Integer::min, 0));
        assertEquals(5, ArrayUtils.aggregate(Array.of(5), Integer::max, 0));
        assertEquals(5, ArrayUtils.aggregate(Array.of(5, 10), Integer::min, 0));
        assertEquals(10, ArrayUtils.aggregate(Array.of(5, 10), Integer::max, 0));
        assertEquals(-10, ArrayUtils.aggregate(Array.of(5, -10), Integer::min, 0));
        assertEquals(5, ArrayUtils.aggregate(Array.of(5, -10), Integer::max, 0));
        assertEquals(5, ArrayUtils.aggregate(Array.of(5, 12, 103), Integer::min, 0));
        assertEquals(103, ArrayUtils.aggregate(Array.of(5, 12, 103), Integer::max, 0));
    }

    @Test
    void testAggregateWithConcatenationOfStrings() {
        assertEquals("", ArrayUtils.aggregate(Array.of(), String::concat, ""));
        assertEquals("One", ArrayUtils.aggregate(Array.of("One"), String::concat, ""));
        assertEquals("OneTwo", ArrayUtils.aggregate(Array.of("One", "Two"), String::concat, ""));
        assertEquals("OneTwoThree", ArrayUtils.aggregate(Array.of("One", "", "Two", "", "Three"), String::concat, ""));
    }

    @Test
    void testAggregateWithConcatenationOfStringsWithNulls() {
        assertEquals("", ArrayUtils.aggregate(new ImmutableArray<>((String) null), String::concat, ""));
        assertEquals("One", ArrayUtils.aggregate(new ImmutableArray<>("One", null), String::concat, ""));
        assertEquals("OneTwo", ArrayUtils.aggregate(new ImmutableArray<>(null, "One", null, "Two"), String::concat, ""));
    }

    @Test
    void testAggregateWithMinMaxOfStringsInNaturalOrder() {
        assertEquals("", ArrayUtils.aggregate(Array.of(), BinaryOperator.minBy(Comparator.naturalOrder()), ""));
        assertEquals("", ArrayUtils.aggregate(Array.of(), BinaryOperator.maxBy(Comparator.naturalOrder()), ""));
        assertEquals("One", ArrayUtils.aggregate(Array.of("One"), BinaryOperator.minBy(Comparator.naturalOrder()), ""));
        assertEquals("One", ArrayUtils.aggregate(Array.of("One"), BinaryOperator.maxBy(Comparator.naturalOrder()), ""));
        assertEquals("One", ArrayUtils.aggregate(Array.of("One", "Two"), BinaryOperator.minBy(Comparator.naturalOrder()), ""));
        assertEquals("Two", ArrayUtils.aggregate(Array.of("One", "Two"), BinaryOperator.maxBy(Comparator.naturalOrder()), ""));
        assertEquals("", ArrayUtils.aggregate(Array.of("One", "", "Two", "", "Three"), BinaryOperator.minBy(Comparator.naturalOrder()), ""));
        assertEquals("Two", ArrayUtils.aggregate(Array.of("One", "", "Two", "", "Three"), BinaryOperator.maxBy(Comparator.naturalOrder()), ""));
    }

    @Test
    void testAggregateWithAverageOfDoubles() {
        assertEquals(Optional.empty(), ArrayUtils.aggregate(Array.of(), Double::sum).map(sum -> divideBy(sum, 0)));
        assertEquals(Optional.of(5.0), ArrayUtils.aggregate(Array.of(5.0), Double::sum).map(sum -> divideBy(sum, 1)));
        assertEquals(Optional.of(7.5), ArrayUtils.aggregate(Array.of(5.0, 10.0), Double::sum).map(sum -> divideBy(sum, 2)));
        assertEquals(Optional.of(12.5), ArrayUtils.aggregate(Array.of(5.0, 12.5, 20.0), Double::sum).map(sum -> divideBy(sum, 3)));
    }

    private Double divideBy(Double value, int arraySize) {
        return value / arraySize;
    }

    @Test
    public void testDotProductWithSize5() {
        Array<Integer> vector1 = Array.of(1, 2, 3, 4, 5);
        Array<Integer> vector2 = Array.of(2, 4, 6, 8, 10);

        assertEquals(110, ArrayUtils.dotProduct(vector1, vector2, (x, y) -> x * y, (x, y) -> x + y));
        assertEquals(110, ArrayUtils.dotProduct(vector1, vector2, (x, y) -> x * y, (x, y) -> x + y, 0));
        assertEquals(110, ArrayUtils.dotProduct(vector2, vector1, (x, y) -> x * y, (x, y) -> x + y));
        assertEquals(110, ArrayUtils.dotProduct(vector2, vector1, (x, y) -> x * y, (x, y) -> x + y, 0));
    }

    @Test
    public void testDotProductWithSize5And4() {
        Array<Integer> vector1 = Array.of(1, 2, 3, 4, 5);
        Array<Integer> vector2 = Array.of(2, 4, 6, 8);

        assertThrowsExactly(IllegalArgumentException.class, () -> ArrayUtils.dotProduct(vector1, vector2, (x, y) -> x * y, (x, y) -> x + y));
        assertThrowsExactly(IllegalArgumentException.class, () -> ArrayUtils.dotProduct(vector1, vector2, (x, y) -> x * y, (x, y) -> x + y, 0));
        assertThrowsExactly(IllegalArgumentException.class, () -> ArrayUtils.dotProduct(vector2, vector1, (x, y) -> x * y, (x, y) -> x + y));
        assertThrowsExactly(IllegalArgumentException.class, () -> ArrayUtils.dotProduct(vector2, vector1, (x, y) -> x * y, (x, y) -> x + y, 0));
    }

    @Test
    public void testDotProductWithSize0() {
        Array<Integer> vector1 = Array.of();
        Array<Integer> vector2 = Array.of();

        assertThrowsExactly(IllegalArgumentException.class, () -> ArrayUtils.dotProduct(vector1, vector2, (x, y) -> x * y, (x, y) -> x + y));
        assertEquals(0, ArrayUtils.dotProduct(vector1, vector2, (x, y) -> x * y, (x, y) -> x + y, 0));
        assertThrowsExactly(IllegalArgumentException.class, () -> ArrayUtils.dotProduct(vector2, vector1, (x, y) -> x * y, (x, y) -> x + y));
        assertEquals(0, ArrayUtils.dotProduct(vector2, vector1, (x, y) -> x * y, (x, y) -> x + y, 0));
    }
}
