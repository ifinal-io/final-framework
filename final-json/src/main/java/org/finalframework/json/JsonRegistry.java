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

package org.finalframework.json;


import org.finalframework.core.Asserts;

/**
 * Json service register.
 *
 * @author likly
 * @version 1.0
 * @date 2018-09-26 21:27
 * @since 1.0
 */
public class JsonRegistry {

    private static final JsonRegistry instance = new JsonRegistry();
    private static final String DEFAULT_JSON_SERVICE = "org.finalframework.json.jackson.JacksonJsonService";
    private JsonService jsonService;
    private boolean initDefaulted = false;

    private JsonRegistry() {
    }

    public static JsonRegistry getInstance() {
        return instance;
    }

    /**
     * return the {@link JsonService} registered by {@link #register(JsonService)}
     */
    public JsonService getJsonService() {
        if (jsonService == null && !initDefaulted) {
            initDefaultJsonService();
        }
        Asserts.isNull(jsonService, "json service is not registered!!!");
        return jsonService;
    }

    private synchronized void initDefaultJsonService() {

        try {
            this.jsonService = (JsonService) Class.forName(DEFAULT_JSON_SERVICE).newInstance();
        } catch (Exception e) {
            // ignore
        } finally {
            initDefaulted = true;
        }

    }

    public synchronized void register(JsonService jsonService) {
        Asserts.isNull(jsonService, "json service can not be null");
        this.jsonService = jsonService;
    }
}
