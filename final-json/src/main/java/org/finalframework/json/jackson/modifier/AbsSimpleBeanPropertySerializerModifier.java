

package org.finalframework.json.jackson.modifier;

import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;

/**
 * @author likly
 * @version 1.0
 * @date 2019-08-26 14:27:05
 * @since 1.0
 */
public abstract class AbsSimpleBeanPropertySerializerModifier<T> extends AbsBeanPropertySerializerModifier {

    @Override
    public boolean support(BeanPropertyDefinition property) {
        return support(property.getRawPrimaryType());
    }

    protected abstract boolean support(Class<?> clazz);
}
