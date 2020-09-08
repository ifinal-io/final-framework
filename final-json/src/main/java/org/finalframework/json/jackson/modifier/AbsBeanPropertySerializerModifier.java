/*
 * Copyright (c) 2018-2020.  the original author or authors.
 *  <p>
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p>
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  <p>
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.finalframework.json.jackson.modifier;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import org.finalframework.core.Asserts;
import org.finalframework.json.context.JsonContextHolder;

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
public abstract class AbsBeanPropertySerializerModifier extends BeanSerializerModifier implements
        BeanPropertySerializerModifier {

    @Override
    public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc,
                                                     List<BeanPropertyWriter> beanProperties) {
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
