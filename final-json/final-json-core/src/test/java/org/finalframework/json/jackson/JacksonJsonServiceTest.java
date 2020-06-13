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

package org.finalframework.json.jackson;

import org.finalframework.data.entity.enums.YN;
import org.finalframework.json.Json;
import org.finalframework.json.JsonRegistry;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author likly
 * @version 1.0
 * @date 2020-02-16 22:38:47
 * @since 1.0
 */
public class JacksonJsonServiceTest {

    private String json;

    @Before
    public void setup() {
        JsonRegistry.getInstance().register(new JacksonJsonService());
        Map<String, Object> map = new HashMap<>();
        map.put("name", "XiaoMing");
        map.put("age", 12);
        Map<String,Object> properties = new HashMap<>();
        properties.put("add","中国");
        map.put("properties",properties);
        this.json = Json.toJson(map);

    }

    @Test
    public void toJson() {
        System.out.println(Json.toJson(YN.values()));
        JacksonBean bean = new JacksonBean();
        bean.setCreated(LocalDateTime.now());
        bean.setYn(YN.YES);
//        JsonContextHolder.setIgnore(false, false);
        bean.setYnTest(1);

        System.out.println(Json.toJson(bean));
    }

    @Test
    public void toObject() {
        Integer integer = Json.toObject("1", Integer.class);
        System.out.println(integer);
        System.out.println(Json.toObject("1", Object.class));
        System.out.println(Json.toObject("[1,2]", Object.class));
        System.out.println(Json.toObject("[\"a\",\"2\"]", Object.class));
    }


}