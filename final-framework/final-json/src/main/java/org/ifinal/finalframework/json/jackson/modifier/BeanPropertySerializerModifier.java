package org.ifinal.finalframework.json.jackson.modifier;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Collection;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface BeanPropertySerializerModifier {

    boolean support(final @NonNull BeanPropertyDefinition property);

    @Nullable
    Collection<BeanPropertyWriter> changeProperties(final SerializationConfig config, final BeanDescription beanDesc,
                                                    final BeanPropertyDefinition property, final BeanPropertyWriter writer);

}
