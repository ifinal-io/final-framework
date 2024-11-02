/*
 * Copyright 2020-2021 the original author or authors.
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
 */

package org.ifinalframework.data.mybatis.sql.provider;

import org.ifinalframework.core.IView;
import org.ifinalframework.core.lang.Transient;
import org.ifinalframework.data.annotation.AbsTenantEntity;
import org.ifinalframework.data.annotation.AbsUser;
import org.ifinalframework.data.annotation.Column;
import org.ifinalframework.data.annotation.Creator;
import org.ifinalframework.data.annotation.Reference;
import org.ifinalframework.data.annotation.Tenant;
import org.ifinalframework.data.annotation.View;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
@Tenant
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Transient
public class Person extends AbsTenantEntity {

    //    @Virtual
    @Column
    public String vcolumn = "32";

    @View(IView.class)
    private String name;

    private Integer age;

    private Integer maxAge;


    @Creator
    @Reference(properties = {"id", "name"})
    private AbsUser creator;

}

