package org.finalframework.json.jackson.modifier;


import com.fasterxml.jackson.core.io.SerializedString;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import org.finalframework.auto.service.AutoService;
import org.finalframework.json.jackson.serializer.DateSerializer;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

/**
 * @author likly
 * @version 1.0
 * @date 2019-11-25 19:01:18
 * @since 1.0
 */
@AutoService(BeanSerializerModifier.class)
public class BeanDatePropertySerializerModifier extends AbsSimpleBeanPropertySerializerModifier<Date> {

    @Override
    protected boolean support(Class<?> clazz) {
        return Date.class.isAssignableFrom(clazz);
    }

    @Override
    public Collection<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc, BeanPropertyDefinition property, BeanPropertyWriter writer) {
        BeanPropertyWriter bpw = new BeanPropertyWriter(property,
                writer.getMember(), beanDesc.getClassAnnotations(), property.getPrimaryType(),
                DateSerializer.INSTANCE, writer.getTypeSerializer(), writer.getSerializationType(),
                writer.willSuppressNulls(), null, property.findViews());

        try {
            Field name = BeanPropertyWriter.class.getDeclaredField("_name");
            name.setAccessible(true);
            name.set(bpw, new SerializedString(bpw.getName() + "Format"));
        } catch (Exception e) {
        }
        return Collections.singleton(bpw);
    }
}

