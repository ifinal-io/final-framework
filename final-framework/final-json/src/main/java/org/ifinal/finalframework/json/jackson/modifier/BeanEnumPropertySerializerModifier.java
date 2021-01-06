package org.ifinal.finalframework.json.jackson.modifier;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import org.ifinal.finalframework.annotation.core.IEnum;
import org.ifinal.finalframework.auto.annotation.AutoService;

/**
 * <h4>Feature:</h4>
 * <ul>
 *     <li>the property serialize the value of {@link IEnum#getCode()}.</li>
 *     <li>add property name serialize the value of {@link Enum#name()}.</li>
 *     <li>add property desc serialize the value of {@link IEnum#getDesc()}.</li>
 * </ul>
 *
 * <h4>Java Entity Example:</h4>
 * <pre class="code">
 *     &#64;Data
 *     static class EnumBean {
 *          private YN yn = YN.YES;
 *     }
 * </pre>
 * <h4>Json Example:</h4>
 * <pre class="code">
 *      {
 *          "yn": 1,
 *          "ynName": "YES",
 *          "ynDesc": "有效"
 *      }
 * </pre>
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
    public Collection<BeanPropertyWriter> changeProperties(final SerializationConfig config, final BeanDescription beanDesc,
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

    /**
     * 枚举{@link IEnum}码序列化器，将枚举序列化为其{@link IEnum#getCode()}所描述的值。
     *
     * @author likly
     * @version 1.0.0
     * @since 1.0.0
     */
    @SuppressWarnings("rawtypes")
    private static class EnumCodeSerializer extends JsonSerializer<IEnum> {

        public static final EnumCodeSerializer instance = new EnumCodeSerializer();

        @Override
        public void serialize(final IEnum value, final JsonGenerator gen, final SerializerProvider serializers)
            throws IOException {

            gen.writeObject(value.getCode());
        }

    }

    /**
     * 枚举{@link IEnum}码序列化器，将枚举序列化为其{@link IEnum#getDesc()}所描述的值。
     *
     * @author likly
     * @version 1.0.0
     * @since 1.0.0
     */
    private static class EnumDescSerializer extends JsonSerializer<IEnum<?>> {

        public static final EnumDescSerializer instance = new EnumDescSerializer();

        @Override
        public void serialize(final IEnum<?> value, final JsonGenerator gen, final SerializerProvider serializers)
            throws IOException {
            gen.writeString(value.getDesc());
        }

    }

    /**
     * @author likly
     * @version 1.0.0
     * @since 1.0.0
     */
    private static class EnumNameSerializer extends JsonSerializer<Enum<?>> {

        public static final EnumNameSerializer instance = new EnumNameSerializer();

        @Override
        public void serialize(final Enum<?> value, final JsonGenerator gen, final SerializerProvider serializers)
            throws IOException {

            gen.writeString(value.name());
        }

    }

    /**
     * 枚举{@link IEnum}对象序列化器，将枚举序列化成一个Json对象。
     *
     * <pre>
     *     <code>
     *         {
     *             "name" : "NAME",
     *             "code" : 1,
     *             "description" : "名称",
     *         }
     *     </code>
     * </pre>
     *
     * @author likly
     * @version 1.0.0
     * @since 1.0.0
     */
    private static class EnumSerializer extends JsonSerializer<IEnum<?>> {

        public static final EnumSerializer instance = new EnumSerializer();

        @Override
        public void serialize(final IEnum<?> value, final JsonGenerator gen, final SerializerProvider serializers)
            throws IOException {

            gen.writeStartObject();
            gen.writeFieldName("code");
            gen.writeObject(value.getCode());
            if (value instanceof Enum) {
                gen.writeFieldName("name");
                gen.writeObject(((Enum<?>) value).name());
            }
            gen.writeFieldName("desc");
            gen.writeObject(value.getDesc());
            gen.writeEndObject();
        }

    }

}
