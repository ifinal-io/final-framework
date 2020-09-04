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

package org.finalframework.spring.web.configurer;

import org.finalframework.auto.spring.factory.annotation.SpringComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.annotation.AnnotationUtils;

/**
 * spring-config-*.xml 资源文件加载配置。
 *
 * @author likly
 * @version 1.0
 * @date 2018-09-29 16:31
 * @since 1.0
 */
@ImportResource({
        SpringResourceConfigurer.CLASS_PATH_SPRING_CONFIG_XML,
        SpringResourceConfigurer.CLASS_PATH_CONFIG_SPRING_CONFIG_XML,
        SpringResourceConfigurer.CLASS_PATH_SPRING_SPRING_CONFIG_XML
})
@SpringComponent
public class SpringResourceConfigurer {
    private static final Logger logger = LoggerFactory.getLogger(SpringResourceConfigurer.class);

    static final String CLASS_PATH_SPRING_CONFIG_XML = "classpath:spring-config-*.xml";
    static final String CLASS_PATH_CONFIG_SPRING_CONFIG_XML = "classpath*:config/spring-config-*.xml";
    static final String CLASS_PATH_SPRING_SPRING_CONFIG_XML = "classpath*:spring/spring-config-*.xml";

    public SpringResourceConfigurer() {
        logger.info("加载资源文件目录：{}", String.join(",", AnnotationUtils.findAnnotation(SpringResourceConfigurer.class, ImportResource.class).value()));
    }
}
