/*
 * Copyright 2020-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.ifinalframework.core;


import jakarta.validation.groups.Default;

/**
 * The marked interface of {@code view}.
 *
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
public interface IView extends Default {


    /**
     * 列表查询视图，URL一般为 {@code /{prefix}/{entities}}
     */
    interface List extends IView {
    }

    /**
     * 详情查询视图，URL一般为 {@code /{prefix}/{entities}/{id}}
     */
    interface Detail extends List {

    }

    /**
     * count view
     */
    interface Count extends IView {
    }

    /**
     * create view
     */
    interface Create extends IView {

    }

    /**
     * all update view
     */
    interface Update extends IView {

    }

    /**
     * patch update view
     */
    interface Patch extends IView {

    }

    /**
     * delete view
     */
    interface Delete extends IView {

    }

    /**
     * import view
     */
    interface Import extends IView {
    }

    /**
     * export view
     */
    interface Export extends IView {
    }

}
