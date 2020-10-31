package config;

import iface.IReflection;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.regex.Pattern;

/**
 * Class stores and allows for querying of [RegExp's, {@linkplain IReflection}] entries.
 */
public final class ReflectionMap {

    /**
     * This hashmap stores all of the [Regular Expression, IReflection] entries,
     * whose's values are to be returned whenever a complete pattern match occurs.
     */
    private final HashMap<String, IReflection> primaryMap;

    /**
     * This hashmap stores all of the [Regular Expression, IReflection] entries,
     * whose's values are to be returned whenever a partial pattern match occurs.
     */
    private final HashMap<String, IReflection> secondaryMap;

    /**
     * Constructor a new object and initializes both maps with default map settings.
     */
    protected ReflectionMap() {
        primaryMap = new HashMap<>(16, 0.75f);
        secondaryMap = new HashMap<>(16, 0.75f);
    }

    /**
     * This method returns the {@link IReflection} value to which the specified key is mapped, or <code>null</code> if
     * neither of this class's maps contain any mapping for the key.<br>
     * More formally, if <code>Pattern.compile(entry.getKey()).matcher(key).matches()</code><br>then the IReflection
     * value associated with<br><code>entry.getKey()</code> is returned.
     *
     * @param key the key who's associated value is to be returned.
     * @return the value to which the specified key is mapped, or <code>null</code> if neither of this class's
     * maps contain any mapping for the key.
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
     * Associates the specified value with the specified key in this class's {@link #primaryMap}.<br>
     * If the map previously contained a mapping for the key, the old value is replaced.
     *
     * @param key key with which the specified value is to be associated.
     * @param value value to be associated with the specified key.
     * @return the previous value associated with the key, or <code>null</code> if there was no mapping for the key.
     */
    @Nullable IReflection put(String key, IReflection value) {
        return primaryMap.put(key, value);
    }

    /**
     * Appends the value associated with the specified key to the specified new value and puts it into
     * this class's {@link #secondaryMap}, if the value associated with the specified key is not <code>null</code>.
     * Otherwise performs a put operation using the specified key and specified new value.
     *
     * @param key key with which the specified value is to be associated.
     * @param newValue the new value to be appended to the value associated with the specified key.
     * @return the previous value associated with the key, or <code>null</code> if there was no mapping for this key.
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
     * Performs an {@link Arrays#toString(Object[])} operation on this class's {@link #primaryMap}'s
     * <code>KeySet</code> and returns the result.
     * @return the keys of this class's primary map in the form of a string.
     */
    public String keys() {
        return Arrays.toString(primaryMap.keySet().toArray());
    }

    /**
     * Performs an {@link Arrays#toString(Object[])} operation on this class's {@link #secondaryMap}'s
     * <code>KeySet</code> and returns the result.
     * @return the keys of this class's secondary map in the form of a string.
     */
    public String noMatchKeys() {
        return Arrays.toString(secondaryMap.keySet().toArray());
    }
}