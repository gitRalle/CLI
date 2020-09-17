package iface;

import java.util.Collection;

public interface IConfiguration {

    IConsole getConsole();

    IMap<String, IReflection> getMap();

    void addControllers(Collection<Object> controllers);
}
