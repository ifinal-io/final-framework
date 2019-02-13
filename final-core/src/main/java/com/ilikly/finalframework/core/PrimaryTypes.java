package com.ilikly.finalframework.core;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-22 13:27:32
 * @since 1.0
 */
public final class PrimaryTypes implements Streamable<Class>,Iterable<Class> {

    private static List<Class> types = Arrays.asList(
            byte.class, Byte.class, short.class, Short.class,
            char.class, Character.class, boolean.class, Boolean.class,
            int.class, Integer.class, long.class, Long.class,
            float.class, Float.class, double.class, Double.class,
            String.class
    );

    public static final PrimaryTypes INSTANCE = new PrimaryTypes();

    private PrimaryTypes(){}

    @Override
    public Stream<Class> stream() {
        return types.stream();
    }

    @NotNull
    @Override
    public Iterator<Class> iterator() {
        return types.iterator();
    }
}
