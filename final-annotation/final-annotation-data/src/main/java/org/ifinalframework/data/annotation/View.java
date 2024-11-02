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

package org.ifinalframework.data.annotation;

import java.lang.annotation.*;

/**
 * 视图，根据视图生成SQL。 实现根据不同的实图查询不同的列的需求。
 *
 * <pre class="code">
 * public class Entity implements IEntity&lt;Long&gt;{
 *      private Long id;
 *      &#064;View(ViewName.class)
 *      private String name;
 *      &#064;View(ViewAge.class)
 *      private String age;
 *
 *      public interface ViewName{};
 *      public interface ViewAge{};
 * }
 * </pre>
 *
 * <pre class="code">
 *      // sql: select id,name from entity
 *      entityRepository.select(ViewName.class);
 * </pre>
 *
 * @author iimik
 * @version 1.0.0
 * @see org.springframework.data.repository.Repository
 * @since 1.0.0
 */
@Documented
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface View {

    Class<?>[] value();

}
