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

package org.finalframework.data.query;


import lombok.Getter;
import lombok.Setter;
import org.finalframework.data.annotation.Geometry;
import org.finalframework.data.annotation.Json;
import org.finalframework.data.annotation.entity.AbsEntity;
import org.springframework.data.geo.Point;

import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2020-06-05 23:06:16
 * @since 1.0
 */
@Setter
@Getter
public class QueryEntity extends AbsEntity {

    private String name;
    private Integer age;
    @Json
    private List<Integer> intList;

    @Geometry
    private Point point;
}

