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

package org.finalframework.spring.web.autoconfiguration;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.servlet.config.annotation.CorsRegistration;

/**
 * @author likly
 * @version 1.0
 * @date 2020-05-05 22:54:58
 * @since 1.0
 */
@Data
@ConfigurationProperties(prefix = CorsProperties.PREFIX)
public class CorsProperties {

    public static final String PREFIX = "final.web.cors";
    /**
     * @see CorsRegistration#pathPattern
     */
    private String mapping = "/**";
    /**
     * @see CorsRegistration#allowCredentials(boolean)
     */
    private Boolean allowCredentials = true;
    /**
     * @see CorsRegistration#allowedMethods(String...)
     */
    private String[] allowedMethods = {"*"};

    /**
     * @see CorsRegistration#allowedMethods(String...)
     */
    private String[] allowedHeaders = {"*"};

    /**
     * @see CorsRegistration#allowedOrigins(String...)
     */
    private String[] allowedOrigins = {"*"};
}

