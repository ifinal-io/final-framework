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

package org.finalframework.test.controller;

import org.finalframework.ui.annotation.Title;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * @author likly
 * @version 1.0
 * @date 2019-11-09 14:17:57
 * @since 1.0
 */
@Controller
@RequestMapping("/")
public class TestController {
    public static final Logger logger = LoggerFactory.getLogger(TestController.class);


    @GetMapping({"/index", ""})
    @Title("Hello World")
    public String index(Model model) {
        model.addAttribute("test", "test");
        model.addAttribute("title", "TITLE HAHA");
        return "index";
    }

}

