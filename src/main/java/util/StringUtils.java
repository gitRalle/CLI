package util;

import exception.NoSuchDelimiterException;

import java.util.*;

public class StringUtils {

    public static String splitAfterFirst(String s, String delimiter) throws NoSuchDelimiterException {
        if (!s.contains(delimiter)) {
            throw new NoSuchDelimiterException(
                    "string does not contain delimiter."
            );
        }
        return s.split("\\s".concat(delimiter).concat("\\s"), 2)[1].split("\\s", 2)[0];
    }

    public static boolean isEmptyOrNull(String str) {
        return str == null || str.equals("");
    }

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
