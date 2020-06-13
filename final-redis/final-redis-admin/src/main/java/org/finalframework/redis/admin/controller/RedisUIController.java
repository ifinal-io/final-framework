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

package org.finalframework.redis.admin.controller;

import org.finalframework.ui.annotation.Menus;
import org.finalframework.ui.annotation.Title;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * @author likly
 * @version 1.0
 * @date 2019-11-09 16:58:30
 * @since 1.0
 */
@Controller
@RequestMapping("/redis")
public class RedisUIController {
    public static final Logger logger = LoggerFactory.getLogger(RedisUIController.class);

    @Title("Redis Value")
    @Menus({"redis", "value"})
    @GetMapping("/value")
    public String value() {
        return "redis/value";
    }

}

