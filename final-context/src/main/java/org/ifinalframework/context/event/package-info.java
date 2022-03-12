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

/**
 * How is an event listener method converted to {@link org.springframework.context.ApplicationListener}.
 *
 * <ol>
 *     <li>First, register {@link org.springframework.context.event.EventListenerMethodProcessor} and {@link org.springframework.context.event.DefaultEventListenerFactory} with {@link org.springframework.context.annotation.AnnotationConfigUtils#registerAnnotationConfigProcessors(org.springframework.beans.factory.support.BeanDefinitionRegistry)}.</li>
 *     <li>Second, find all beans of {@link org.springframework.context.event.EventListenerFactory} on callback method of {@link org.springframework.context.event.EventListenerMethodProcessor#postProcessBeanFactory(org.springframework.beans.factory.config.ConfigurableListableBeanFactory)}.</li>
 *     <li>Then, find all methods which annotated with {@link org.springframework.context.event.EventListener} and convert to an {@link org.springframework.context.ApplicationListener} with {@link org.springframework.context.event.EventListenerFactory}.</li>
 * </ol>
 *
 *
 * <p>{@link org.springframework.context.event.EventListener}</p>
 * All methods of annotated with {@link org.springframework.context.event.EventListener} would be convent to a {@link org.springframework.context.event.ApplicationListenerMethodAdapter} by {@link org.springframework.context.event.DefaultEventListenerFactory}.
 *
 * @author likly
 * @version 1.2.4
 * @see org.springframework.context.annotation.AnnotationConfigUtils#registerAnnotationConfigProcessors(org.springframework.beans.factory.support.BeanDefinitionRegistry)
 * @see org.springframework.context.event.EventListenerMethodProcessor
 * @see org.springframework.context.event.DefaultEventListenerFactory
 * @see org.springframework.context.event.ApplicationListenerMethodAdapter
 **/
package org.ifinalframework.context.event;
