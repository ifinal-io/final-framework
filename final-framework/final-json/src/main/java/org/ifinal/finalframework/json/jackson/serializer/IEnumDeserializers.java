package org.ifinal.finalframework.json.jackson.serializer;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.module.SimpleDeserializers;
import org.ifinal.finalframework.annotation.core.IEnum;
import org.ifinal.finalframework.json.jackson.deserializer.EnumDeserializer;

/**
 * IEnumSerializers.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class IEnumDeserializers extends SimpleDeserializers {

    @Override
    @SuppressWarnings("unchecked")
    public JsonDeserializer<?> findEnumDeserializer(final Class<?> type, final DeserializationConfig config, final BeanDescription beanDesc)
        throws JsonMappingException {

        if (IEnum.class.isAssignableFrom(type)) {
            return new EnumDeserializer<>((Class<? extends IEnum<?>>) type);
        }

        return super.findEnumDeserializer(type, config, beanDesc);
    }

}
