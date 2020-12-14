package org.ifinal.finalframework.json.jackson.modifier;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import org.ifinal.finalframework.auto.service.annotation.AutoService;
import org.ifinal.finalframework.json.jackson.serializer.LocalDateSerializer;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@AutoService(BeanSerializerModifier.class)
public class BeanLocalDatePropertySerializerModifier extends AbsSimpleBeanPropertySerializerModifier {

    @Override
    protected boolean support(final Class<?> clazz) {

        return LocalDate.class.isAssignableFrom(clazz);
    }

    @Override
    public Collection<BeanPropertyWriter> changeProperties(final SerializationConfig config,
        final BeanDescription beanDesc,
        final BeanPropertyDefinition property, final BeanPropertyWriter writer) {

        final BeanPropertyWriter bpw = new BeanPropertyWriter(property,
            writer.getMember(), beanDesc.getClassAnnotations(), property.getPrimaryType(),
            LocalDateSerializer.INSTANCE, writer.getTypeSerializer(), writer.getSerializationType(),
            writer.willSuppressNulls(), null, property.findViews());

        setNameValue(bpw, bpw.getName() + "Format");
        return Collections.singleton(bpw);
    }

}

