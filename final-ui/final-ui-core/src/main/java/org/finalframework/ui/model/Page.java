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

package org.finalframework.ui.model;


import java.io.Serializable;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2019-11-09 14:01:04
 * @since 1.0
 */
public class Page implements Serializable {
    private String title;
    private List<String> menus;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getMenus() {
        return menus;
    }

    public void setMenus(List<String> menus) {
        this.menus = menus;
    }
}

