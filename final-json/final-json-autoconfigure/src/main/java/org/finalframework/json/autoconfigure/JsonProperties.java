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

package org.finalframework.json.autoconfigure;

import org.finalframework.json.JsonService;
import org.finalframework.json.jackson.JacksonJsonService;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-09 22:02:17
 * @since 1.0
 */
@ConfigurationProperties(JsonProperties.JSON_PROPERTIES)
public class JsonProperties {
    static final String JSON_PROPERTIES = "final.json";

    private Class<? extends JsonService> jsonService = JacksonJsonService.class;

    public Class<? extends JsonService> getJsonService() {
        return jsonService;
    }

    public void setJsonService(Class<? extends JsonService> jsonService) {
        this.jsonService = jsonService;
    }
}
