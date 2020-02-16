package org.finalframework.json.jackson.modifier;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import org.finalframework.core.Assert;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0
 * @date 2019-08-26 14:27:05
 * @since 1.0
 */
public abstract class AbsBeanPropertySerializerModifier extends BeanSerializerModifier implements BeanPropertySerializerModifier {

    @Override
    public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties) {
        // 1. 将原有的属性映射成一个Map，key为属性名称
        Map<String, BeanPropertyWriter> beanPropertyWriterMap = beanProperties.stream()
                .collect(Collectors.toMap(BeanPropertyWriter::getName, Function.identity()));

        // 2. 遍历，找出实现了 IEnum 接口的属性，为其增加一个名称 xxxName 的新属性到 JavaBean的
        List<BeanPropertyDefinition> properties = beanDesc.findProperties();
        for (BeanPropertyDefinition property : properties) {
            if (support(property)) {
                BeanPropertyWriter def = beanPropertyWriterMap.get(property.getName());
                Collection<BeanPropertyWriter> changeProperties = changeProperties(config, beanDesc, property, def);
                if (Assert.nonEmpty(changeProperties)) {
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
