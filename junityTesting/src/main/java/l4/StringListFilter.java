package l4;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Solution to Task 1.
 */
public class StringListFilter {

    /**
     * Filters the list using specified string.
     * @param l list of strings to filter
     * @param s string to filter out from string
     * @return returns new list containing elements of list {@code l} except element {@code s}
     * @throws IllegalArgumentException if either one of arguments is null
     */
    public static List<String> filterNotEqual(List<String> l, String s){
        if(l == null) throw new IllegalArgumentException("List is null");
        if(s == null) throw new IllegalArgumentException("String is null");

        return l.stream().filter(elem -> !s.equals(elem)).collect(Collectors.toList());
    }
}
