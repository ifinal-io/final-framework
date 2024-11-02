
/*
 * Copyright 2020-2024 the original author or authors.
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

/**
 * 自动配置{@link org.springframework.cloud.openfeign.FeignClient}，进一步提高开发效率。
 *
 *
 * <h4>使用{@code className}当作{@code name}</h4>
 *
 * 在使用{@code FeignClient}时，开发者需要指定{@code name}或{@code value}来说明客户端和名称，一般情况下，可以使用接口的类名来作为客户端的名称，
 * 为减少开发者的这种显示指定，提高开发效率，通过修改{@code Spring}的注册流程，将使用接口的类名作为客户端的名称。
 *
 * <h4>使用{@code spring.cloud.openfeign.gateway.url}来配置默认的客户端URL</h4>
 *
 *
 * @author iimik
 * @since 1.6.0
 **/
package org.ifinalframework.feign;