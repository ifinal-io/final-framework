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

package org.finalframework.ui.autoconfiguration;


import org.finalframework.spring.annotation.factory.SpringConfiguration;
import org.finalframework.ui.dialect.JsonDialect;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.TemplateEngine;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author likly
 * @version 1.0
 * @date 2019-11-09 16:12:09
 * @since 1.0
 */
@Configuration
@SpringConfiguration
public class TemplateDialectAutoConfiguration {
    @Resource
    private TemplateEngine templateEngine;


    @PostConstruct
    public void init() {
        templateEngine.addDialect(new JsonDialect());
    }
}

