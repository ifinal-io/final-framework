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

package org.finalframework.test.entity;

import com.fasterxml.jackson.annotation.JsonView;
import org.finalframework.data.annotation.*;
import org.finalframework.data.entity.AbsRecord;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-27 22:25
 * @since 1.0
 */
public class Person extends AbsRecord {
    private static final long serialVersionUID = -8785625823175210092L;
    //    @PrimaryKey(insertable = true)
//    private Long id;
    @JsonView(Person.class)
    @NotNull
    @View(Person.class)
    private String name;
    @Sharding
    @JsonView(Person.class)
    private Integer age;
    @ReadOnly
    @Function(reader = "MAX(age)")
    @View(Person.class)
    private Integer maxAge;
    @View(Person.class)
    @Json
    private List<String> stringList;
    @Column
    private List<Integer> intList;
    @Json
    private Map<String, Object> properties;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
    }

    public List<String> getStringList() {
        return stringList;
    }

    public void setStringList(List<String> stringList) {
        this.stringList = stringList;
    }

    public List<Integer> getIntList() {
        return intList;
    }

    public void setIntList(List<Integer> intList) {
        this.intList = intList;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    @Virtual
    public String getString2() {
        return Optional.ofNullable(this.stringList).map(list -> list.get(0)).orElse(null);
    }

}
