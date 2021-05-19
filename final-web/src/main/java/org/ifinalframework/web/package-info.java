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

/**
 * package-info.
 *
 *
 * Spring MVC 处理请求由 {@link org.springframework.web.servlet.DispatcherServlet}
 *
 * <h4>Spring MVC九大核心策略接口</h4>
 * <ul>
 *     <li>文件上传解析{@link org.springframework.web.multipart.MultipartResolver}</li>
 *     <li>语言环境解析{@link org.springframework.web.servlet.LocaleResolver},{@link org.springframework.web.servlet.LocaleContextResolver}</li>
 *     <li>{@link org.springframework.web.servlet.ViewResolver}</li>
 *     <li>{@link org.springframework.web.servlet.ThemeResolver}</li>
 *     <li>{@link org.springframework.web.servlet.HandlerExceptionResolver}</li>
 *     <li>{@link org.springframework.web.servlet.FlashMapManager}</li>
 *     <li>{@link org.springframework.web.servlet.RequestToViewNameTranslator}</li>
 *     <li>{@link org.springframework.web.servlet.HandlerMapping}</li>
 *     <li>{@link org.springframework.web.servlet.HandlerAdapter}</li>
 * </ul>
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */

package org.ifinalframework.web;
