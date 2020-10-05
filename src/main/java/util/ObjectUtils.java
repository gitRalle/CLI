package util;

import exception.ParseException;

import java.lang.reflect.Type;

/**
 * <summary>Class which declares static utility methods for working with Objects,
 * is only used in this project by the config.Configuration class.</summary>
 */
public class ObjectUtils {

    public static Object toObject(Type type, String value)
            throws IllegalArgumentException, ParseException
    {
        if (value == null)
            throw new IllegalArgumentException(
                    "value must not be null."
            );

        switch (type.getTypeName()) {
            case "java.lang.String":
                return value;
            case "char":
            case "java.lang.Character":
                if (value.length() == 1)
                {
                    return value.charAt(0);
                }
                else
                {
                    throw new ParseException(
                            "could not parse '" + value + "' into a char."
                    );
                }
            case "int":
            case "java.lang.Integer":
                try
                {
                    return Integer.parseInt(value);
                }
                catch (NumberFormatException ex)
                {
                    throw new ParseException(
                            "could not parse '" + value + "' into an int."
                    );
                }
            case "short":
            case "java.lang.Short":
                try
                {
                    return Short.parseShort(value);
                }
                catch (NumberFormatException ex)
                {
                    throw new ParseException(
                            "could not parse '" + value + "' into a short."
                    );
                }
            case "boolean":
            case "java.lang.Boolean":
                if (value.toLowerCase().equals("true") ||
                        value.toLowerCase().equals("false") ||
                        value.toLowerCase().equals("y") ||
                        value.toLowerCase().equals("n") ||
                        value.toLowerCase().equals("yes") ||
                        value.toLowerCase().equals("no"))
                {
                    return Boolean.parseBoolean(value);
                }
                else
                {
                    throw new ParseException(
                            "could not parse '" + value + "' into a boolean."
                    );
                }
            case "double":
            case "java.lang.Double":
                try
                {
                    return Double.parseDouble(value);
                }
                catch (NumberFormatException ex)
                {
                    throw new ParseException(
                            "could not parse '" + value + "' into a double."
                    );
                }
            case "float":
            case "java.lang.Float":
                try
                {
                    return Float.parseFloat(value);
                }
                catch (NumberFormatException ex)
                {
                    throw new ParseException(
                            "could not parse '" + value + "' into a float."
                    );
                }
            case "long":
            case "java.lang.Long":
                try
                {
                    return Long.parseLong(value);
                }
                catch (NumberFormatException ex)
                {
                    throw new ParseException(
                            "could not parse '" + value + "' into a long."
                    );
                }
            case "byte":
            case "java.lang.Byte":
                try
                {
                    return Byte.parseByte(value);
                }
                catch (NumberFormatException ex)
                {
                    throw new ParseException(
                            "could not parse '" + value + "' into a byte."
                    );
                }
        }

        throw new IllegalArgumentException(
                "'" + type.getTypeName() + "' cannot be parsed from a string."
        );
    }

    public static Object toDefaultValue(Type type)
            throws IllegalArgumentException {

        switch (type.getTypeName()) {
            case "java.lang.String":
            case "char":
            case "java.lang.Character":
                return null;
            case "int":
            case "java.lang.Integer":
                return 0;
            case "short":
            case "java.lang.Short":
                return (short) 0;
            case "boolean":
            case "java.lang.Boolean":
                return false;
            case "double":
            case "java.lang.Double":
                return 0.0;
            case "float":
            case "java.lang.Float":
                return 0.0f;
            case "long":
            case "java.lang.Long":
                return (long) 0;
            case "byte":
            case "java.lang.Byte":
                return (byte) 0;
        }
        throw new IllegalArgumentException(
                "type must be a primitive or wrapper class."
        );
    }
}
