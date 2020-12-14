/*
 * Copyright (c) 2018-2020.  the original author or authors.
 *  <p>
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p>
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  <p>
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.ifinal.finalframework.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Stream;
import org.ifinal.finalframework.util.stream.Streamable;

/**
 * 常见主要基础类型及其包装类型集
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public final class Primaries implements Streamable<Class<?>>, Iterable<Class<?>> {

    /**
     * Byte.
     */
    public static final Primaries BYTE = new Primaries(byte.class, Byte.class);

    /**
     * Char.
     */
    public static final Primaries CHAR = new Primaries(char.class, Character.class);

    /**
     * Boolean.
     */
    public static final Primaries BOOLEAN = new Primaries(boolean.class, Boolean.class);

    /**
     * Short.
     */
    public static final Primaries SHORT = new Primaries(short.class, Short.class);

    /**
     * Integer.
     */
    public static final Primaries INTEGER = new Primaries(int.class, Integer.class);

    /**
     * Long.
     */
    public static final Primaries LONG = new Primaries(long.class, Long.class);

    /**
     * Float.
     */
    public static final Primaries FLOAT = new Primaries(float.class, Float.class);

    /**
     * Double.
     */
    public static final Primaries DOUBLE = new Primaries(double.class, Double.class);

    /**
     * String.
     */
    public static final Primaries STRING = new Primaries(String.class);

    /**
     * Number.
     */
    public static final Primaries NUMBER = new Primaries(
        byte.class, Byte.class, short.class, Short.class,
        int.class, Integer.class, long.class, Long.class,
        float.class, Float.class, double.class, Double.class
    );

    /**
     * All.
     */
    public static final Primaries ALL = new Primaries(
        byte.class, Byte.class, short.class, Short.class,
        char.class, Character.class, boolean.class, Boolean.class,
        int.class, Integer.class, long.class, Long.class,
        float.class, Float.class, double.class, Double.class,
        String.class, Class.class);

    /**
     * types.
     */
    private final Set<Class<?>> types = new HashSet<>();

    private Primaries(final Class<?>... classes) {
        this.types.addAll(Arrays.asList(classes));
    }

    public static boolean isByte(final Class<?> clazz) {
        return BYTE.types.contains(clazz);
    }

    public static boolean isChar(final Class<?> clazz) {
        return CHAR.types.contains(clazz);
    }

    public static boolean isBoolean(final Class<?> clazz) {
        return BOOLEAN.types.contains(clazz);
    }

    public static boolean isShort(final Class<?> clazz) {
        return SHORT.types.contains(clazz);
    }

    public static boolean isInteger(final Class<?> clazz) {
        return INTEGER.types.contains(clazz);
    }

    public static boolean isLong(final Class<?> clazz) {
        return LONG.types.contains(clazz);
    }

    public static boolean isFloat(final Class<?> clazz) {
        return FLOAT.types.contains(clazz);
    }

    public static boolean isDouble(final Class<?> clazz) {
        return DOUBLE.types.contains(clazz);
    }

    public static boolean isString(final Class<?> clazz) {
        return STRING.types.contains(clazz);
    }

    public static boolean isPrimary(final Class<?> clazz) {
        return ALL.types.contains(clazz);
    }

    @Override
    public Stream<Class<?>> stream() {
        return types.stream();
    }

    @Override
    public Iterator<Class<?>> iterator() {
        return types.iterator();
    }

}
