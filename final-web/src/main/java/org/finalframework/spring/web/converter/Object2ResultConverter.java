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

package org.finalframework.spring.web.converter;


import org.finalframework.annotation.result.R;
import org.finalframework.annotation.result.Result;
import org.finalframework.core.converter.Converter;
import org.finalframework.json.jackson.view.JsonViewValue;
import org.finalframework.spring.web.response.view.PageJsonViewValue;

/**
 * @author likly
 * @version 1.0
 * @date 2019-12-09 11:17:03
 * @since 1.0
 */
public class Object2ResultConverter implements Converter<Object, Result<?>> {
    @Override
    public Result<?> convert(Object body) {
        if (body == null) return R.success();

        if (body instanceof Result) {
            return (Result<?>) body;
        }


        if (body instanceof PageJsonViewValue) {
            PageJsonViewValue<?> pageJsonViewValue = (PageJsonViewValue<?>) body;
            JsonViewValue jsonViewValue = pageJsonViewValue.getData();
            Result<JsonViewValue> result = R.success(jsonViewValue);
            result.setPagination(pageJsonViewValue.toPageInfo());
            result.setView(jsonViewValue.getView());
            return result;
        }

        return R.success(body);
    }
}

