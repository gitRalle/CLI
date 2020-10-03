package config;

import iface.IReflection;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.regex.Pattern;

public class ReflectionMap<V, K> {

    /**
     *
     */
    private final HashMap<String, IReflection> primaryMap;

    /**
     *
     */
    private final HashMap<String, IReflection> secondaryMap;

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
    @Nullable
    public IReflection match(String k) {
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
    @Nullable
    IReflection put(String k, IReflection v) {
        return primaryMap.put(k, v);
    }

    /**
     * <summary>Appends an IReflection object to a stored IReflection object
     * from the underlying secondary map.</summary>
     *
     * @param k the key.
     * @param v the value.
     * @return the previously stored value, is there was one.
     */
    @Nullable
    IReflection append(@Nullable String k, IReflection newValue) {
        IReflection previousValue = secondaryMap.get(k);

        if (previousValue != null) {
            return secondaryMap.put(k, input -> {
                previousValue.invoke(input);
                newValue.invoke(input);
            });
        }
        return secondaryMap.put(k, newValue);
    }

    boolean duplicate(String s1) {
        return primaryMap.keySet().stream().anyMatch(s2 -> Pattern.compile(s2).matcher(s1).matches() ||
                Pattern.compile(s1).matcher(s2).matches()) || primaryMap.containsKey(s1);
    }

    public String getKeys() {
        return Arrays.toString(primaryMap.keySet().toArray());
    }

    public String getArgsDoNotMatchKeys() {
        return Arrays.toString(secondaryMap.keySet().toArray());
    }

}
