package cn.com.likly.finalframework.data.mybatis.handler;

import lombok.NonNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-27 09:46
 * @since 1.0
 */
public abstract class JavaType {
    private static final Set<Class<?>> byteSets = new HashSet<>(Arrays.asList(byte.class, Byte.class));
    private static final Set<Class<?>> charSets = new HashSet<>(Arrays.asList(char.class, Character.class));
    private static final Set<Class<?>> shortSets = new HashSet<>(Arrays.asList(short.class, Short.class));
    private static final Set<Class<?>> intSets = new HashSet<>(Arrays.asList(int.class, Integer.class));
    private static final Set<Class<?>> longSets = new HashSet<>(Arrays.asList(long.class, Long.class));
    private static final Set<Class<?>> booleanSets = new HashSet<>(Arrays.asList(boolean.class, Boolean.class));
    private static final Set<Class<?>> stringSets = new HashSet<>(Arrays.asList(String.class));
    private static final Set<Class<?>> primarySets = new HashSet<>();

    static {

        primarySets.addAll(byteSets);
        primarySets.addAll(charSets);
        primarySets.addAll(shortSets);
        primarySets.addAll(intSets);
        primarySets.addAll(longSets);
        primarySets.addAll(booleanSets);
        primarySets.addAll(stringSets);
    }


    public static Boolean isByte(@NonNull Class<?> clazz) {
        return byteSets.contains(clazz);
    }

    public static Boolean isChar(@NonNull Class<?> clazz) {
        return charSets.contains(clazz);
    }

    public static Boolean isShort(@NonNull Class<?> clazz) {
        return shortSets.contains(clazz);
    }

    public static Boolean isInt(@NonNull Class<?> clazz) {
        return intSets.contains(clazz);
    }

    public static Boolean isLong(@NonNull Class<?> clazz) {
        return longSets.contains(clazz);
    }

    public static Boolean isBoolean(@NonNull Class<?> clazz) {
        return booleanSets.contains(clazz);
    }

    public static Boolean isString(@NonNull Class<?> clazz) {
        return stringSets.contains(clazz);
    }

    public static Boolean isPrimary(@NonNull Class<?> clazz) {
        return primarySets.contains(clazz);
    }


}
