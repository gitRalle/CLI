package data;

import iface.IReflection;
import iface.IMap;

import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class Map implements IMap<String, IReflection> {

    private final LinkedHashMap<String, IReflection> map;

    public Map() {
        map = new LinkedHashMap<>();
    }

    @Override
    public IReflection match(String s) {
        for (java.util.Map.Entry<String, IReflection> kv : map.entrySet()) {
            if (Pattern.matches(kv.getKey(), s)) {
                return kv.getValue();
            }
        }
        return null;
    }

    @Override
    public IReflection put(String s, IReflection v) {
        return map.put(s, v);
    }

    public void outputKeys() {
        for (java.util.Map.Entry<String, IReflection> kv : map.entrySet()) {
            System.out.println(kv.getKey());
        }
    }

}
