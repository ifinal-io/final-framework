package org.ifinal.finalframework.json.jackson.modifier;

import com.fasterxml.jackson.core.io.SerializedString;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import org.springframework.lang.NonNull;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class AbsSimpleBeanPropertySerializerModifier<T> extends AbsBeanPropertySerializerModifier {

    /**
     * @see BeanPropertyWriter#getName()
     * @see SerializedString
     */
    private static final Field NAME_FIELD = Objects.requireNonNull(ReflectionUtils.findField(BeanPropertyWriter.class, "_name"));

    static {
        ReflectionUtils.makeAccessible(NAME_FIELD);
    }

    @Override
    public boolean support(@NonNull BeanPropertyDefinition property) {
        return support(property.getRawPrimaryType());
    }

    protected void setNameValue(BeanPropertyWriter bpw, String value) {
        ReflectionUtils.setField(NAME_FIELD, bpw, new SerializedString(value));
    }

    protected abstract boolean support(Class<?> clazz);
}
