package iface;

public interface IMap<K, ILambda> {

    ILambda match(K k);

    ILambda put(K k, ILambda v);
}
