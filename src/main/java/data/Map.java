package data;

import iface.IReflection;
import iface.IMap;

import java.util.*;
import java.util.regex.Pattern;

public class Map implements IMap<String, IReflection> {

    private final HashMap<String, IReflection> map;
    private final HashMap<String, IReflection> notFoundMap;

    public Map() {
        map = new HashMap<>(16, 0.75f);
        notFoundMap = new HashMap<>(16, 0.75f);
    }

    @Override
    public IReflection put(String k, IReflection v) {
        return map.put(k, v);
    }

    @Override
    public IReflection match(String k) {
        for (java.util.Map.Entry<String, IReflection> kv : map.entrySet()) {
            if (Pattern.compile(kv.getKey()).matcher(k).matches()) {
                return kv.getValue();
            }
        }
        for (java.util.Map.Entry<String, IReflection> kv : notFoundMap.entrySet()) {
            if (Pattern.compile(kv.getKey()).matcher(k).matches()) {
                return kv.getValue();
            }
        }
        return null;
    }

    public IReflection append(String k, IReflection newValue) {
        IReflection previousValue = notFoundMap.get(k);

        if (previousValue != null) {
            return notFoundMap.put(k, input -> {
                previousValue.invoke(input);
                newValue.invoke(input);
            });
        }
        return notFoundMap.put(k, newValue);
    }

    // predicate
    public boolean duplicate(String s1) {
        return map.keySet().stream().anyMatch(s2 -> Pattern.compile(s2).matcher(s1).matches() ||
                Pattern.compile(s1).matcher(s2).matches()) || map.containsKey(s1);
    }

    public void outputKeys() {
        System.out.println(Arrays.toString(map.keySet().toArray()));
    }

    public void outputNotFoundKeys() {
        System.out.println(Arrays.toString(notFoundMap.keySet().toArray()));
    }

}
