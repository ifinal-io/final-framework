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
public class BeanEnumPropertySerializerModifier extends BeanSerializerModifier {

    private static final Logger logger = LoggerFactory.getLogger(BeanEnumPropertySerializerModifier.class);

    @Override
    public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties) {

        // 1. 将原有的属性映射成一个Map，key为属性名称
        Map<String, BeanPropertyWriter> beanPropertyWriterMap = beanProperties.stream()
                .collect(Collectors.toMap(BeanPropertyWriter::getName, Function.identity()));

        // 2. 遍历，找出实现了 IEnum 接口的属性，为其增加一个名称 xxxName 的新属性到 JavaBean的
        List<BeanPropertyDefinition> properties = beanDesc.findProperties();
        for (BeanPropertyDefinition property : properties) {


            if (IEnum.class.isAssignableFrom(property.getPrimaryType().getRawClass())) {
                // 实现了 IEnum 的属性
                BeanPropertyWriter def = beanPropertyWriterMap.get(property.getName());
                //创建一个新的属性来描述增加的"xxxName"，并使用 EnumNameSerializer 来序列化该属性
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
    }
}
