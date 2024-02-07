package org.viktori.matteray.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.viktori.matteray.Array;

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
}
