package org.ifinal.finalframework.json.jackson.modifier;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import java.util.Arrays;
import java.util.Collection;
import org.ifinal.finalframework.annotation.core.IEnum;
import org.ifinal.finalframework.auto.service.annotation.AutoService;
import org.ifinal.finalframework.json.jackson.serializer.EnumCodeSerializer;
import org.ifinal.finalframework.json.jackson.serializer.EnumDescSerializer;
import org.ifinal.finalframework.json.jackson.serializer.EnumNameSerializer;
import org.ifinal.finalframework.json.jackson.serializer.EnumSerializer;

/**
 * 对JavaBean的枚举型属性（实现了{@link IEnum}接口）进行序列化修改。
 * <p></p>
 * 1. 将该枚举属性序列化为{@link IEnum#getCode()}所对应的值。 2. 增加"xxxName"属性，其值为{@link IEnum#getDesc()}所对应的值。
 * <p></p>
 *
 * @author likly
 * @version 1.0.0
 * @see EnumCodeSerializer
 * @see EnumNameSerializer
 * @see EnumDescSerializer
 * @since 1.0.0
 */
@AutoService(BeanSerializerModifier.class)
public class BeanEnumPropertySerializerModifier extends AbsSimpleBeanPropertySerializerModifier {

    private static final String ENUM_NAME_PROPERTY_SUFFIX = "Name";

    private static final String ENUM_DESC_PROPERTY_SUFFIX = "Desc";

    @Override
    public JsonSerializer<?> modifyEnumSerializer(final SerializationConfig config, final JavaType valueType,
        final BeanDescription beanDesc, final JsonSerializer<?> serializer) {

        if (IEnum.class.isAssignableFrom(valueType.getRawClass())) {
            final JsonFormat jsonFormat = beanDesc.getClassAnnotations().get(JsonFormat.class);
            if (jsonFormat != null && JsonFormat.Shape.OBJECT == jsonFormat.shape()) {
                return EnumSerializer.instance;
            }
            return EnumCodeSerializer.instance;
        }
        return super.modifyEnumSerializer(config, valueType, beanDesc, serializer);
    }

    @Override
    protected boolean support(final Class<?> clazz) {

        return IEnum.class.isAssignableFrom(clazz);
    }

    @Override
    public Collection<BeanPropertyWriter> changeProperties(final SerializationConfig config,
        final BeanDescription beanDesc,
        final BeanPropertyDefinition property, final BeanPropertyWriter writer) {

        final BeanPropertyWriter enumNamePropertyWriter = buildEnumNamePropertyWriter(beanDesc, property, writer);
        final BeanPropertyWriter enumDescPropertyWriter = buildEnumDescPropertyWriter(beanDesc, property, writer);
        return Arrays.asList(enumNamePropertyWriter, enumDescPropertyWriter);
    }

    private BeanPropertyWriter buildEnumNamePropertyWriter(final BeanDescription beanDesc,
        final BeanPropertyDefinition property,
        final BeanPropertyWriter writer) {

        final BeanPropertyWriter enumNamePropertyWriter = new BeanPropertyWriter(property,
            writer.getMember(), beanDesc.getClassAnnotations(), property.getPrimaryType(),
            EnumNameSerializer.instance, writer.getTypeSerializer(), writer.getSerializationType(),
            writer.willSuppressNulls(), null, property.findViews());

        setNameValue(enumNamePropertyWriter, enumNamePropertyWriter.getName() + ENUM_NAME_PROPERTY_SUFFIX);
        return enumNamePropertyWriter;
    }

    private BeanPropertyWriter buildEnumDescPropertyWriter(final BeanDescription beanDesc,
        final BeanPropertyDefinition property,
        final BeanPropertyWriter writer) {

        final BeanPropertyWriter enumDescriptionPropertyWriter = new BeanPropertyWriter(property,
            writer.getMember(), beanDesc.getClassAnnotations(), property.getPrimaryType(),
            EnumDescSerializer.instance, writer.getTypeSerializer(), writer.getSerializationType(),
            writer.willSuppressNulls(), null, property.findViews());
        setNameValue(enumDescriptionPropertyWriter,
            enumDescriptionPropertyWriter.getName() + ENUM_DESC_PROPERTY_SUFFIX);
        return enumDescriptionPropertyWriter;
    }

}
