package org.finalframework.json.jackson.modifier;


import com.fasterxml.jackson.core.io.SerializedString;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import org.finalframework.annotation.data.EnumValue;
import org.finalframework.auto.service.annotation.AutoService;
import org.finalframework.json.jackson.serializer.EnumValueDescSerializer;
import org.finalframework.json.jackson.serializer.EnumValueNameSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-25 10:30:16
 * @since 1.0
 */
@AutoService(BeanSerializerModifier.class)
public class BeanEnumValuePropertySerializerModifier extends AbsBeanPropertySerializerModifier {
    private static final Logger logger = LoggerFactory.getLogger(BeanEnumValuePropertySerializerModifier.class);

    private static final String ENUM_NAME_PROPERTY_SUFFIX = "Name";
    private static final String ENUM_DESC_PROPERTY_SUFFIX = "Desc";
    private static final String ENUM_DESCRIPTION_PROPERTY_SUFFIX = "Description";

    @Override
    public boolean support(BeanPropertyDefinition property) {
        return property.getField() != null && property.getField().hasAnnotation(EnumValue.class);
    }

    @Override
    public Collection<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc, BeanPropertyDefinition property, BeanPropertyWriter writer) {
        BeanPropertyWriter enumNamePropertyWriter = buildEnumNamePropertyWriter(beanDesc, property, writer);
        BeanPropertyWriter enumDescPropertyWriter = buildEnumDescPropertyWriter(beanDesc, property, writer);
        BeanPropertyWriter enumDescriptionPropertyWriter = buildEnumDescriptionPropertyWriter(beanDesc, property, writer);
        return Arrays.asList(enumNamePropertyWriter, enumDescPropertyWriter, enumDescriptionPropertyWriter);
    }

    private BeanPropertyWriter buildEnumNamePropertyWriter(BeanDescription beanDesc, BeanPropertyDefinition property, BeanPropertyWriter writer) {
        BeanPropertyWriter enumNamePropertyWriter = new BeanPropertyWriter(property,
                writer.getMember(), beanDesc.getClassAnnotations(), property.getPrimaryType(),
                new EnumValueNameSerializer(property.getField().getAnnotation(EnumValue.class)), writer.getTypeSerializer(), writer.getSerializationType(),
                writer.willSuppressNulls(), null, property.findViews());

        try {
            Field name = BeanPropertyWriter.class.getDeclaredField("_name");
            name.setAccessible(true);
            name.set(enumNamePropertyWriter, new SerializedString(enumNamePropertyWriter.getName() + ENUM_NAME_PROPERTY_SUFFIX));
        } catch (Exception e) {
            logger.error("", e);
        }
        return enumNamePropertyWriter;
    }

    private BeanPropertyWriter buildEnumDescriptionPropertyWriter(BeanDescription beanDesc, BeanPropertyDefinition property, BeanPropertyWriter writer) {
        BeanPropertyWriter enumDescriptionPropertyWriter = new BeanPropertyWriter(property,
                writer.getMember(), beanDesc.getClassAnnotations(), property.getPrimaryType(),
                new EnumValueDescSerializer(property.getField().getAnnotation(EnumValue.class)), writer.getTypeSerializer(), writer.getSerializationType(),
                writer.willSuppressNulls(), null, property.findViews());

        try {
            Field name = BeanPropertyWriter.class.getDeclaredField("_name");
            name.setAccessible(true);
            name.set(enumDescriptionPropertyWriter, new SerializedString(enumDescriptionPropertyWriter.getName() + ENUM_DESCRIPTION_PROPERTY_SUFFIX));
        } catch (Exception e) {
            logger.error("", e);
        }
        return enumDescriptionPropertyWriter;
    }

    private BeanPropertyWriter buildEnumDescPropertyWriter(BeanDescription beanDesc, BeanPropertyDefinition property, BeanPropertyWriter writer) {
        BeanPropertyWriter enumDescriptionPropertyWriter = new BeanPropertyWriter(property,
                writer.getMember(), beanDesc.getClassAnnotations(), property.getPrimaryType(),
                new EnumValueDescSerializer(property.getField().getAnnotation(EnumValue.class)), writer.getTypeSerializer(), writer.getSerializationType(),
                writer.willSuppressNulls(), null, property.findViews());

        try {
            Field name = BeanPropertyWriter.class.getDeclaredField("_name");
            name.setAccessible(true);
            name.set(enumDescriptionPropertyWriter, new SerializedString(enumDescriptionPropertyWriter.getName() + ENUM_DESC_PROPERTY_SUFFIX));
        } catch (Exception e) {
            logger.error("", e);
        }
        return enumDescriptionPropertyWriter;
    }
}

