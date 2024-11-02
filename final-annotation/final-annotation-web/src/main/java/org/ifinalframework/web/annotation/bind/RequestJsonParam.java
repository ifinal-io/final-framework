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

package org.ifinalframework.web.annotation.bind;

import java.lang.annotation.*;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ValueConstants;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

/**
 * An ext {@link Annotation} which can resolve {@code json} param from request parameters for {@link HandlerMethodArgumentResolver} like {@link RequestParam} and {@link RequestBody}.
 * <p>
 * Define an api with {@link RequestJsonParam} like this:
 * <pre class="code">
 * &#064;RestController
 * public class JsonParamController{
 *
 *      &#064;GetMapping("/json")
 *      public Object json(&#064;RequestJsonParam Object json){
 *          return json;
 *      }
 * }
 * </pre>
 * <p>
 * Then request this {@code api} with {@code curl} like this:
 *
 * <pre class="code">
 * curl http://localhost:8080/json?json={"name":haha}
 * </pre>
 *
 * <pre class="code>
 *     &#064;RequestBody = &#064;RequestJsonParam("requestBody")
 * </pre>
 *
 * @author iimik
 * @version 1.0.0
 * @see RequestParam
 * @see RequestBody
 * @see HandlerMethodArgumentResolver
 * @since 1.0.0
 */
@Target({ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestJsonParam {

    @AliasFor("name")
    String value() default "";

    @AliasFor("value")
    String name() default "";

    boolean required() default true;

    String defaultValue() default ValueConstants.DEFAULT_NONE;

}
