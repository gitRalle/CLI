package util;

import exception.NoSuchDelimiterException;

import java.util.*;

/**
 * This class declares utility methods used by the config.Configuration class for working with strings.
 */
public class StringUtils {

    /**
     * This method returns the substring of the specified string s which is located right after the
     * first occurrence of the specified substring delimiter and the next potential whitespace character.
     * The specified substring delimiter is first padded on both ends with a whitespace character.
     * The return string before it is returned, is stripped of any potential trailing whitespace characters.
     *
     * @param s the string.
     * @param delimiter the substring.
     * @return the substring of the specified string s which is located right after the specified substring
     * delimiter and the next potential whitespace character.
     * @throws NoSuchDelimiterException in the event of the specified string delimiter not being a substring
     * of the specified string s.
     */
    public static String splitAfterFirst(String s, String delimiter) throws NoSuchDelimiterException {
        if (!s.contains(delimiter)) {
            throw new NoSuchDelimiterException(
                    "string does not contain delimiter."
            );
        }
        return s.split("\\s".concat(delimiter).concat("\\s"), 2)[1].split("\\s", 2)[0];
    }

    public static String normalize(String s) {
        return s.replaceAll("\\s{2}+", "\\s");
    }

    /**
     * Determines whether a specified string is either null or empty.
     *
     * @param s the string to be evaluated.
     * @return true if the specified string is null or empty,
     * false if the specified string is not null or empty.
     */
    public static boolean isEmptyOrNull(String s) {
        return s == null || s.equals("");
    }

    /**
     * This method permutes all of the elements in the specified list.
     *
     * @param linkedList the list whose elements are to be permuted.
     * @return a string containing all of the permutations of the specified list.
     */
    public static String permute(LinkedList<String> linkedList) {
        String str  = "(";
        int i = 0;
        for (List<String> list : permuteToSet(linkedList)) {
            str = str.concat("(" + Arrays.toString(list.toArray()) + ")");
            if (i++ != linkedList.size() - 1) {
                str = str.concat("|");
            }
        }
        return str
                .replace("[", "")
                .replace("]", "")
                .replace(", ", "")
                .concat(")");
    }

    /**
     * This method is used by this class's permute method.
     *
     * @param original the list whose elements are to be permuted.
     * @return a set of lists.
     */
    private static Set<List<String>> permuteToSet(LinkedList<String> original) {
        int length = original.size();
        Set<List<String>> set = new HashSet<>();

        for (int i = 0; i < length; i++) {
            String head = original.pop();
            original.add(head);

            List<String> list = new ArrayList<>(original);
            for (int j = 0; j < list.size(); j++) {
                if (j != 0) {
                    Collections.swap(list, j, j - 1);
                }
                set.add(new ArrayList<>(list));
            }
        }
        return set;
    }
}
