package org.finalframework.core;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Stream;

/**
 * 常见主要基础类型及其包装类型集
 *
 * @author likly
 * @version 1.0
 * @date 2019-01-22 13:27:32
 * @since 1.0
 */
@SuppressWarnings("all")
public final class PrimaryTypes implements Streamable<Class>, Iterable<Class> {

    public static final PrimaryTypes BYTE = new PrimaryTypes(byte.class, Byte.class);
    public static final PrimaryTypes CHAR = new PrimaryTypes(char.class, Character.class);
    public static final PrimaryTypes BOOLEAN = new PrimaryTypes(boolean.class, Boolean.class);
    public static final PrimaryTypes SHORT = new PrimaryTypes(short.class, Short.class);
    public static final PrimaryTypes INTEGER = new PrimaryTypes(int.class, Integer.class);
    public static final PrimaryTypes LONG = new PrimaryTypes(long.class, Long.class);
    public static final PrimaryTypes FLOAT = new PrimaryTypes(float.class, Float.class);
    public static final PrimaryTypes DOUBLE = new PrimaryTypes(double.class, Double.class);
    public static final PrimaryTypes STRING = new PrimaryTypes(String.class);
    public static final PrimaryTypes ALL = new PrimaryTypes(
            byte.class, Byte.class, short.class, Short.class,
            char.class, Character.class, boolean.class, Boolean.class,
            int.class, Integer.class, long.class, Long.class,
            float.class, Float.class, double.class, Double.class,
            String.class);
    private final Set<Class> types = new HashSet<>();

    private PrimaryTypes(Class<?>... classes) {
        this.types.addAll(Arrays.asList(classes));
    }

    @Override
    public Stream<Class> stream() {
        return types.stream();
    }

    @Override
    public Iterator<Class> iterator() {
        return types.iterator();
    }

    public static boolean isByte(Class<?> clazz) {
        return BYTE.types.contains(clazz);
    }

    public static boolean isChar(Class<?> clazz) {
        return CHAR.types.contains(clazz);
    }

    public static boolean isBoolean(Class<?> clazz) {
        return BOOLEAN.types.contains(clazz);
    }

    public static boolean isShort(Class<?> clazz) {
        return SHORT.types.contains(clazz);
    }

    public static boolean isInteger(Class<?> clazz) {
        return INTEGER.types.contains(clazz);
    }

    public static boolean isLong(Class<?> clazz) {
        return LONG.types.contains(clazz);
    }

    public static boolean isFloat(Class<?> clazz) {
        return FLOAT.types.contains(clazz);
    }

    public static boolean isDouble(Class<?> clazz) {
        return DOUBLE.types.contains(clazz);
    }

    public static boolean isString(Class clazz) {
        return STRING.types.contains(clazz);
    }
}
