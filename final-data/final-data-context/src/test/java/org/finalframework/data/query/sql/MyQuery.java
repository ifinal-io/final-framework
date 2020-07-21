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

package org.finalframework.data.query.sql;


import lombok.Data;
import org.finalframework.data.annotation.query.AndOr;
import org.finalframework.data.annotation.query.Criteria;
import org.finalframework.data.annotation.query.Equal;
import org.finalframework.data.annotation.query.NotEqual;

/**
 * @author likly
 * @version 1.0
 * @date 2020-07-17 18:02:52
 * @since 1.0
 */
@Data
@Criteria(AndOr.OR)
public class MyQuery {
    @Equal
    private String name;
    @NotEqual
    private Integer age;
}

