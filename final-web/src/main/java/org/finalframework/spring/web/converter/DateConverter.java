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

import org.finalframework.core.formatter.DateFormatters;
import org.finalframework.auto.spring.factory.annotation.SpringConverter;
import org.springframework.core.convert.converter.Converter;

import java.util.Date;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-29 16:32
 * @since 1.0
 */
@SpringConverter
public class DateConverter implements Converter<String, Date> {

    @Override
    public Date convert(String source) {
        return DateFormatters.DEFAULT.parse(source);
    }
}
