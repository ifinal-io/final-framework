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

package org.finalframework.spring.web.converter;

import org.finalframework.core.converter.Converter;
import org.finalframework.context.exception.NotFoundException;
import org.finalframework.auto.spring.factory.annotation.SpringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * convert the name to a {@link Class<?>}, throw a {@link NotFoundException} if the {@link Class} of name is not found.
 *
 * @author likly
 * @version 1.0
 * @date 2020-05-11 09:59:53
 * @since 1.0
 */
@SpringConverter
public class String2ClassConverter implements Converter<String, Class<?>> {

    public static final Logger logger = LoggerFactory.getLogger(String2ClassConverter.class);

    @Override
    public Class<?> convert(String source) {
        try {
            return Class.forName(source);
        } catch (ClassNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
    }
}
