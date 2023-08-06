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

import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.function.BiPredicate;

/**
 * @author ilikly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface BeanPropertySerializerModifier extends BiPredicate<BeanPropertyDefinition, BeanPropertyWriter> {

    @Nullable
    Collection<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc,
                                                    BeanPropertyDefinition property, BeanPropertyWriter writer);

}
