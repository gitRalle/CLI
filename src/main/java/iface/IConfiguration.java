package iface;

import config.ReflectionMap;

import java.util.Collection;

public interface IConfiguration {

    IConsole console();

    ReflectionMap<String, IReflection> map();

    void addControllers(Collection<Object> controllers);
}
