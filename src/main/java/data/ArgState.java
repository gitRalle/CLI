package data;

import exception.DefaultedValueException;
import exception.ParseException;

import java.util.*;
import java.util.Map;

public class ArgState {

    private final Map<String, LinkedList<Exception>> exceptionListMap = new HashMap<>();

    public Map<String, LinkedList<Exception>> getExceptionListMap() {
        return exceptionListMap;
    }

    public boolean isValid() {
        for (Map.Entry<String, LinkedList<Exception>> kv : exceptionListMap.entrySet()) {
            for (Exception ex : kv.getValue()) {
                if (ex instanceof ParseException) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isDefaulted(String name) {
        List<Exception> exceptions = exceptionListMap.get(name);
        if (exceptions != null) {
            for (Exception ex : exceptions) {
                if (ex instanceof DefaultedValueException) {
                    return true;
                }
            }
        }
        return false;
    }

    public String getExceptions(String name) {
        List<Exception> exceptions = exceptionListMap.get(name);
        if (exceptions != null) {
            return Arrays.toString(exceptions.toArray());
        }
        return null;
    }

    public ArgState append(String name, Exception ex) {
        if (exceptionListMap.containsKey(name)) {
            exceptionListMap.get(name).add(ex);
        }
        else {
            exceptionListMap.put(name, new LinkedList<>(){{add(ex);}});
        }
        return this;
    }
}
