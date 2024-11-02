/*
 * Copyright 2020-2022 the original author or authors.
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

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestPart;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre class="code">
 * &#064RestController
 * public class ExcelController{
 *
 *      &#064GetMapping("/excel)
 *      public List&lt;T&gt; excel(&#064RequestExcelPart List&lt;T&gt; list){
 *          return list;
 *      }
 *
 * }
 * </pre>
 *
 * @author iimik
 * @version 1.2.4
 * @see RequestPart
 **/
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestExcelPart {
    /**
     * Alias for {@link #name}.
     */
    @AliasFor("name")
    String value() default "";

    /**
     * The name of the part in the {@code "multipart/form-data"} request to bind to.
     */
    @AliasFor("value")
    String name() default "";

    /**
     * Whether the part is required.
     * <p>Defaults to {@code true}, leading to an exception being thrown
     * if the part is missing in the request. Switch this to {@code false} if you prefer a {@code null} value if the
     * part is not present in the request.
     */
    boolean required() default true;
}
