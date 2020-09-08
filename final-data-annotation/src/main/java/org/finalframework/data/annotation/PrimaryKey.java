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

package org.finalframework.data.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.finalframework.data.annotation.enums.PrimaryKeyType;
import org.springframework.core.annotation.AliasFor;

/**
 * Marked the element is a primary key.
 *
 * @author likly
 * @version 1.0
 * @date 2018-10-15 15:14
 * @see org.springframework.data.annotation.Id
 * @see PrimaryKeyType
 * @see IEntity
 * @since 1.0
 */
@Final
@Column
@Default
@Documented
@Order(Integer.MIN_VALUE)
@org.springframework.data.annotation.Id
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PrimaryKey {

  @AliasFor(annotation = Column.class, value = "value")
  String value() default "";

  @AliasFor(annotation = Column.class, value = "name")
  String name() default "";

  PrimaryKeyType type() default PrimaryKeyType.AUTO_INC;
}