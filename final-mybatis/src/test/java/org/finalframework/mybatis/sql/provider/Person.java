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

package org.finalframework.mybatis.sql.provider;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.finalframework.data.annotation.*;
import org.finalframework.data.annotation.entity.AbsEntity;
import org.finalframework.mybatis.handler.PointTypeHandler;
import org.springframework.data.geo.Point;

/**
 * @author likly
 * @version 1.0
 * @date 2020-07-17 14:06:19
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Transient
public class Person extends AbsEntity {
    //    @Virtual
    public String vcolumn;
    private String name;
    private Integer age;
    @TypeHandler(PointTypeHandler.class)
    @Geometry
    private Point point;
    @Function(reader = "MAX(age) as ${column}")
    private Integer maxAge;
}

