package org.finalframework.json.jackson.modifier;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.io.SerializedString;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import org.finalframework.data.entity.enums.IEnum;
import org.finalframework.json.jackson.EnumCodeSerializer;
import org.finalframework.json.jackson.EnumNameSerializer;
import org.finalframework.json.jackson.EnumSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Collections;

/**
 * 对JavaBean的枚举型属性（实现了{@link IEnum}接口）进行序列化修改。
 * <p/>
 * 1. 将该枚举属性序列化为{@link IEnum#getCode()}所对应的值。
 * 2. 增加"xxxName"属性，其值为{@link IEnum#getDescription()}所对应的值。
 * <p/>
 * <a href="https://final.ilikly.com/json/jackson/serializer/bean-enum-property-serializer-modifier">BeanEnumPropertySerializerModifier</a>
 *
 * @author likly
 * @version 1.0
 * @date 2019-08-26 14:27:05
 * @see EnumCodeSerializer
 * @see EnumNameSerializer
 * @since 1.0
 */
public class BeanEnumPropertySerializerModifier extends AbsSimpleBeanPropertySerializerModifier<IEnum> {

    private static final Logger logger = LoggerFactory.getLogger(BeanEnumPropertySerializerModifier.class);

    @Override
    public JsonSerializer<?> modifyEnumSerializer(SerializationConfig config, JavaType valueType, BeanDescription beanDesc, JsonSerializer<?> serializer) {
        JsonFormat jsonFormat = beanDesc.getClassAnnotations().get(JsonFormat.class);
        if (jsonFormat != null && JsonFormat.Shape.OBJECT == jsonFormat.shape()) {
            return EnumSerializer.instance;
        }
        return EnumCodeSerializer.instance;
    }

    @Override
    protected boolean support(Class<?> clazz) {
        return IEnum.class.isAssignableFrom(clazz);
    }

    @Override
    public Collection<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc, BeanPropertyDefinition property, BeanPropertyWriter writer) {
        //创建一个新的属性来描述增加的"xxxName"，并使用 EnumNameSerializer 来序列化该属性
        BeanPropertyWriter bpw = new BeanPropertyWriter(property,
                writer.getMember(), beanDesc.getClassAnnotations(), property.getPrimaryType(),
                EnumNameSerializer.instance, writer.getTypeSerializer(), writer.getSerializationType(),
                writer.willSuppressNulls(), null, property.findViews());

        try {
            Field name = BeanPropertyWriter.class.getDeclaredField("_name");
            name.setAccessible(true);
            name.set(bpw, new SerializedString(bpw.getName() + "Name"));
        } catch (Exception e) {
            logger.error("", e);
        }
        return Collections.singleton(bpw);
    }
}
