package org.ifinal.finalframework.data.mapping.converter;

import org.ifinal.finalframework.data.annotation.NameConverter;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class SimpleNameConverter implements NameConverter {
    @Override
    public String convert(String name) {
        return name;
    }
}
