package org.finalframework.json.jackson.serializer;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.io.SerializedString;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import org.finalframework.data.entity.enums.IEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 对象枚举属性序列化修改器
 *
 * @author likly
 * @version 1.0
 * @date 2019-08-26 14:27:05
 * @since 1.0
 */
public class BeanEnumPropertySerializerModifier extends BeanSerializerModifier {

    private static final Logger logger = LoggerFactory.getLogger(BeanEnumPropertySerializerModifier.class);

    @Override
    public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties) {

        Map<String, BeanPropertyWriter> beanPropertyWriterMap = beanProperties.stream()
                .collect(Collectors.toMap(BeanPropertyWriter::getName, Function.identity()));

        List<BeanPropertyDefinition> properties = beanDesc.findProperties();
        for (BeanPropertyDefinition property : properties) {
            if (IEnum.class.isAssignableFrom(property.getPrimaryType().getRawClass())) {
                System.out.println(property.getName());
                BeanPropertyWriter def = beanPropertyWriterMap.get(property.getName());

                BeanPropertyWriter bpw = new BeanPropertyWriter(property,
                        def.getMember(), beanDesc.getClassAnnotations(), property.getPrimaryType(),
                        EnumNameSerializer.instance, def.getTypeSerializer(), def.getSerializationType(),
                        def.willSuppressNulls(), null, property.findViews());

                try {
                    Field name = BeanPropertyWriter.class.getDeclaredField("_name");
                    name.setAccessible(true);
                    name.set(bpw, new SerializedString(bpw.getName() + "Name"));
                } catch (Exception e) {
                    logger.error("", e);
                }


                beanProperties.add(bpw);
            }
        }

        return super.changeProperties(config, beanDesc, beanProperties);
    }

    @Override
    public JsonSerializer<?> modifyEnumSerializer(SerializationConfig config, JavaType valueType, BeanDescription beanDesc, JsonSerializer<?> serializer) {
        JsonFormat jsonFormat = beanDesc.getClassAnnotations().get(JsonFormat.class);
        if (jsonFormat != null && JsonFormat.Shape.OBJECT == jsonFormat.shape()) {
            return EnumSerializer.instance;
        }

        return EnumCodeSerializer.instance;
//        return super.modifyEnumSerializer(config, valueType, beanDesc, serializer);
    }
}
