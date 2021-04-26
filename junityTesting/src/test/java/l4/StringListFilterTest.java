package l4;

import l4.StringListFilter;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StringListFilterTest {

    @Test
    void filterNone() {
        List<String> l = List.of("test1", "test2", "test3");
        String s = "banana";
        assertLinesMatch(l, StringListFilter.filterNotEqual(l, s));
    }

    @Test
    void filterAll() {
        List<String> l = List.of("banana", "banana", "banana");
        String s = "banana";
        assertLinesMatch(Collections.emptyList(), StringListFilter.filterNotEqual(l, s));
    }

    @Test
    void filterSome() {
        List<String> l = List.of("apple", "banana", "orange");
        List<String> expected = List.of("apple", "orange");
        String s = "banana";
        assertLinesMatch(expected, StringListFilter.filterNotEqual(l, s));
    }

    @Test
    void filterContainingNull() {
        var l = Arrays.asList(null, "banana", null, "orange", "banana", null, null, "apple", null);
        var expected = Arrays.asList(null, null, "orange", null, null, "apple", null);
        String s = "banana";
        assertArrayEquals(expected.toArray(), StringListFilter.filterNotEqual(l, s).toArray());
    }

    @Test
    void filterThrowsOnNullList() {
        var thrown = assertThrows(IllegalArgumentException.class,
                                  () -> StringListFilter.filterNotEqual(null, "banana"));
        assertEquals(thrown.getMessage(), "List is null");
    }

    @Test
    void filterThrowsOnNullString() {
        var thrown = assertThrows(IllegalArgumentException.class,
                                  () -> StringListFilter.filterNotEqual(Collections.emptyList(), null));
        assertEquals(thrown.getMessage(), "String is null");
    }
}