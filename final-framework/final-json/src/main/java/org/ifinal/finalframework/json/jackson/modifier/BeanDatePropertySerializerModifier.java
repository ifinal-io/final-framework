package org.ifinal.finalframework.json.jackson.modifier;


import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import org.ifinal.finalframework.auto.service.annotation.AutoService;
import org.ifinal.finalframework.json.jackson.serializer.DateSerializer;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
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

        setNameValue(bpw, bpw.getName() + "Format");
        return Collections.singleton(bpw);
    }
}

