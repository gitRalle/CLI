package config;

import iface.IReflection;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.regex.Pattern;

/**
 * This class is responsible for the storing and querying of the [Regular expression, IReflection] entries
 * created by the config.Configuration class.
 */
public class ReflectionMap {

    /**
     * This hashmap stores all of the [Regular expressions, IReflection] entries,
     * whom's values are to be invoked when a complete match occurs.
     */
    private final HashMap<String, IReflection> primaryMap;

    /**
     * This hashmap stores all of the [Regular Expressions, IReflection] entries,
     * whom's values are to be invoked when a partial match occurs.
     */
    private final HashMap<String, IReflection> secondaryMap;

    /**
     * This constructor sets and initializes both maps with default map settings.
     */
    protected ReflectionMap() {
        primaryMap = new HashMap<>(16, 0.75f);
        secondaryMap = new HashMap<>(16, 0.75f);
    }

    /**
     * This method returns the IReflection value to which the specified key is mapped, or null if
     * neither of this class's maps contain any mapping for the key.
     * More formally, if Pattern.compile(entry.getKey()).matcher(key).matches() then the IReflection value
     * associated with entry.getKey() is returned.
     *
     * @param key the key who's associated value is to be returned.
     * @return the value to which the specified key is mapped, or null if neither of this class's maps contain
     * any mapping for the key.
     */
    public @Nullable IReflection match(String key) {
        final java.util.Map.Entry<String, IReflection> entry =
                primaryMap.entrySet().stream().
                filter(pkv -> Pattern.compile(pkv.getKey()).matcher(key).matches())
                .findFirst().orElseGet(() ->
                        secondaryMap.entrySet().stream()
                                .filter(skv -> Pattern.compile(skv.getKey()).matcher(key).matches())
                                .findFirst().orElse(null)
                );
        return entry != null ? entry.getValue() : null;
    }

    /**
     * Associates the specified value with the specified key in this class's primary map.
     * If the map previously contained a mapping for the key, the old value is replaced.
     *
     * @param key key with which the specified value is to be associated.
     * @param value value to be associated with the specified key.
     * @return the previous value associated with the key, or null if there was no mapping for the key.
     */
    @Nullable IReflection put(String key, IReflection value) {
        return primaryMap.put(key, value);
    }

    /**
     * Appends the value associated with the specified key to the specified new value and puts it into
     * this class's secondary map, if the value associated with the specified key is not null.
     * Otherwise performs a put operation using the specified key and specified new value.
     *
     * @param key key with which the specified value is to be associated.
     * @param newValue the new value to be appended to the value associated with the specified key.
     * @return the previous value associated with the key, or null if there was no mapping for this key.
     */
    @Nullable IReflection append(@Nullable String key, IReflection newValue) {
        IReflection previousValue = secondaryMap.get(key);

        if (previousValue != null) {
            return secondaryMap.put(key, input -> {
                previousValue.invoke(input);
                newValue.invoke(input);
            });
        }
        return secondaryMap.put(key, newValue);
    }

    /**
     * Performs an Arrays.toString() operation on this class's primary map's key set and returns the result.
     * @return the keys of this class's primary map in the form of a string.
     */
    public String keys() {
        return Arrays.toString(primaryMap.keySet().toArray());
    }

    /**
     * Performs an Arrays.toString() operation on this class's secondary map's key set and returns the result.
     * @return the keys of this class's secondary map in the form of a string.
     */
    public String noMatchKeys() {
        return Arrays.toString(secondaryMap.keySet().toArray());
    }
}