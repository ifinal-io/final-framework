package org.ifinal.finalframework.json.jackson.modifier;

import com.fasterxml.jackson.core.io.SerializedString;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.ifinal.finalframework.json.context.JsonContextHolder;
import org.ifinal.finalframework.util.Asserts;
import org.springframework.util.ReflectionUtils;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class AbsBeanPropertySerializerModifier extends BeanSerializerModifier implements
    BeanPropertySerializerModifier {

    /**
     * @see BeanPropertyWriter#getName()
     * @see SerializedString
     */
    private static final Field NAME_FIELD = Objects.requireNonNull(ReflectionUtils.findField(BeanPropertyWriter.class, "_name"));

    static {
        ReflectionUtils.makeAccessible(NAME_FIELD);
    }

    protected void setNameValue(final BeanPropertyWriter bpw, final String value) {

        ReflectionUtils.setField(NAME_FIELD, bpw, new SerializedString(value));
    }

    @Override
    public List<BeanPropertyWriter> changeProperties(final SerializationConfig config, final BeanDescription beanDesc,
        final List<BeanPropertyWriter> beanProperties) {

        if (JsonContextHolder.isIgnore()) {
            return super.changeProperties(config, beanDesc, beanProperties);
        }
        // 1. 将原有的属性映射成一个Map，key为属性名称
        Map<String, BeanPropertyWriter> beanPropertyWriterMap = beanProperties.stream()
            .collect(Collectors.toMap(BeanPropertyWriter::getName, Function.identity()));

        // 2. 遍历，找出实现了 IEnum 接口的属性，为其增加一个名称 xxxName 的新属性到 JavaBean的
        List<BeanPropertyDefinition> properties = beanDesc.findProperties();
        for (BeanPropertyDefinition property : properties) {
            if (support(property)) {
                BeanPropertyWriter def = beanPropertyWriterMap.get(property.getName());
                Collection<BeanPropertyWriter> changeProperties = changeProperties(config, beanDesc, property, def);
                if (Asserts.nonEmpty(changeProperties)) {
                    int index = beanProperties.indexOf(def);
                    if (index != -1) {
                        beanProperties.addAll(index + 1, changeProperties);
                    } else {
                        beanProperties.addAll(changeProperties);
                    }
                }
            }
        }

        return super.changeProperties(config, beanDesc, beanProperties);
    }

}
