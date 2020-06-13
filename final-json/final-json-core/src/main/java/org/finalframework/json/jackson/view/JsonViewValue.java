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

package org.finalframework.json.jackson.view;


import com.fasterxml.jackson.annotation.JsonView;
import org.finalframework.data.view.Viewable;

import java.io.Serializable;

/**
 * @author likly
 * @version 1.0
 * @date 2019-12-06 10:32:29
 * @see JsonView
 * @since 1.0
 */
public class JsonViewValue<T> implements Viewable<T>, Serializable {
    private static final long serialVersionUID = -4251222094454545408L;
    private final T value;
    private final Class<?> view;

    public JsonViewValue(T value, Class<?> view) {
        this.value = value;
        this.view = view;
    }

    public T getValue() {
        return value;
    }

    @Override
    public Class<?> getView() {
        return view;
    }

}

