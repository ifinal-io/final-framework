/*
 * Copyright 2020-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ifinalframework.json.jackson.modifier;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;

import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class AbsBeanPropertySerializerModifier extends BeanSerializerModifier implements
        BeanPropertySerializerModifier {

    @Override
    public List<BeanPropertyWriter> changeProperties(final SerializationConfig config, final BeanDescription beanDesc,
                                                     final List<BeanPropertyWriter> beanProperties) {

        // 1. 将原有的属性映射成一个Map，key为属性名称
        final Map<String, BeanPropertyWriter> beanPropertyWriterMap = beanProperties.stream()
                .collect(Collectors.toMap(BeanPropertyWriter::getName, Function.identity()));

        // 2. 遍历，找出实现了 IEnum 接口的属性，为其增加一个名称 xxxName 的新属性到 JavaBean的
        final List<BeanPropertyDefinition> properties = beanDesc.findProperties();
        for (BeanPropertyDefinition property : properties) {
            final BeanPropertyWriter beanPropertyWriter = beanPropertyWriterMap.get(property.getName());
            if (test(property, beanPropertyWriter)) {
                final Collection<BeanPropertyWriter> changeProperties
                        = changeProperties(config, beanDesc, property, beanPropertyWriter);
                if (!CollectionUtils.isEmpty(changeProperties)) {
                    final int index = beanProperties.indexOf(beanPropertyWriter);
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
