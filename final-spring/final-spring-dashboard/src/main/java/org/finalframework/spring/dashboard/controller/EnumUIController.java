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

package org.finalframework.spring.dashboard.controller;

import org.finalframework.ui.annotation.Menus;
import org.finalframework.ui.annotation.Title;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * @author
 * @version 1.0
 * @date 2020-05-09 14:48:13
 * @since 1.0
 */
@Controller
@RequestMapping("/beans/enums")
public class EnumUIController {
    public static final Logger logger = LoggerFactory.getLogger(EnumUIController.class);

    @Title("Enums")
    @Menus({"beans", "enums", "index"})
    @GetMapping
    public String enums() {
        return "beans/enums/index";
    }

    @Title("I18N")
    @Menus({"beans", "enums", "i18n"})
    @GetMapping({"/i18n", "/i18n/{name}"})
    public String i18n(@PathVariable(value = "name", required = false) String name, Model model) {
        model.addAttribute("name", name);
        return "beans/enums/i18n";
    }


}

