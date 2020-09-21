package iface;

public interface IMap<K, IReflection> {

    IReflection match(K k);

    IReflection put(K k, IReflection v);

    IReflection append(K k, IReflection v);

    boolean duplicate(String s);

}
