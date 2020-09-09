

package org.finalframework.data.mapping.converter;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-09 16:02:00
 * @since 1.0
 */
public class SimpleNameConverter implements NameConverter {
    @Override
    public String convert(String name) {
        return name;
    }
}
