

package org.finalframework.json.jackson.modifier;


import com.fasterxml.jackson.core.io.SerializedString;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import org.finalframework.json.jackson.serializer.LocalDateTimeSerializer;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Collections;

/**
 * @author likly
 * @version 1.0
 * @date 2019-11-25 19:01:18
 * @since 1.0
 */
public class BeanLocalDateTimePropertySerializerModifier extends AbsSimpleBeanPropertySerializerModifier<LocalDateTime> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    protected boolean support(Class<?> clazz) {
        return LocalDateTime.class.isAssignableFrom(clazz);
    }

    @Override
    public Collection<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc, BeanPropertyDefinition property, BeanPropertyWriter writer) {
        //创建一个新的属性来描述增加的"xxxName"，并使用 EnumNameSerializer 来序列化该属性
        BeanPropertyWriter bpw = new BeanPropertyWriter(property,
                writer.getMember(), beanDesc.getClassAnnotations(), property.getPrimaryType(),
                new LocalDateTimeSerializer(FORMATTER), writer.getTypeSerializer(), writer.getSerializationType(),
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

