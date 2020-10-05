package config;

import iface.IReflection;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.regex.Pattern;

/**
 * <summary>This class is responsible for storing all of the regex patterns -- derived
 * from the annotated methods, and their respective IReflection objects.</summary>
 */
public class ReflectionMap {

    /**
     * <summary>This Map is where all of the regex patterns - derived from the
     * class, method and parameter keywords are stored, and their respective
     * IReflection objects.</summary>
     */
    private final HashMap<String, IReflection> primaryMap;

    /**
     * <summary>This Map is where all of the regex patterns - derived from the
     * class and method keywords are stored, and their respective IReflection objects.
     * No parameter keywords are used in the creation of this map's keys.
     * It is used to hold the implementation(s) outputting the partialMatchMessage(s).</summary>
     */
    private final HashMap<String, IReflection> secondaryMap;

    /**
     * Default constructor, which initializes both Maps with default settings.
     */
    ReflectionMap() {
        primaryMap = new HashMap<>(16, 0.75f);
        secondaryMap = new HashMap<>(16, 0.75f);
    }

    /**
     * <summary>Matches k with an IReflection object from the underlying primary or secondary map,
     * where the primary map is searched first.</summary>
     *
     * @param k the key.
     * @return the value.
     */
    public @Nullable IReflection match(String k) {
        final java.util.Map.Entry<String, IReflection> entry =
                primaryMap.entrySet().stream().
                filter(pkv -> Pattern.compile(pkv.getKey()).matcher(k).matches())
                .findFirst().orElseGet(() ->
                        secondaryMap.entrySet().stream()
                                .filter(skv -> Pattern.compile(skv.getKey()).matcher(k).matches())
                                .findFirst().orElse(null)
                );
        return entry != null ? entry.getValue() : null;
    }

    /**
     * <summary>Puts a new <K, IReflection> entry into the underlying primary Map.</summary>
     *
     * @param k the key.
     * @param v the value.
     * @return the previously stored value, if there was one.
     */
    @Nullable IReflection put(String k, IReflection v) {
        return primaryMap.put(k, v);
    }

    /**
     * <summary>Appends an IReflection object to a stored IReflection object (if it exists)
     * from the underlying secondary map, or creates a new one.</summary>
     *
     * @param k the key.
     * @param v the value.
     * @return the previously stored value, if there was one.
     */
    @Nullable IReflection append(@Nullable String k, IReflection newValue) {
        IReflection previousValue = secondaryMap.get(k);

        if (previousValue != null) {
            return secondaryMap.put(k, input -> {
                previousValue.invoke(input);
                newValue.invoke(input);
            });
        }
        return secondaryMap.put(k, newValue);
    }

    /**
     * <summary>Returns all of the keys from the primary map in the form of a String.</summary>
     *
     * @return the String.
     */
    public String getKeys() {
        return Arrays.toString(primaryMap.keySet().toArray());
    }

    /**
     * <summary>Returns all of the keys from the secondary map in the form of a String.</summary>
     *
     * @return the String.
     */
    public String getArgsDoNotMatchKeys() {
        return Arrays.toString(secondaryMap.keySet().toArray());
    }
}
