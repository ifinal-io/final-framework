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
 * {@link org.springframework.context.ApplicationListener}
 *
 * <pre class="code">
 * public class MyApplicationEventListener implements ApplicationListener&lg;ApplicationReadyEvent&gt;{
 *
 * }
 * </pre>
 *
 * <p>Registration</p>
 *
 * <p>Java API</p>
 * Use {@link org.springframework.boot.SpringApplication#addListeners(org.springframework.context.ApplicationListener[])} java api like this:
 * <pre class="code">
 * SpringApplication application = new SpringApplication();
 * application.addListeners(new MyApplicationEventListener());
 * application.run();
 * </pre>
 *
 * <p>Spring SPI</p>
 * Use <code>META-INF/spring.factories</code> Spring SPI.
 * <pre class="code">
 * # Application Listeners
 * org.springframework.context.ApplicationListener=\
 * org.springframework.boot.ClearCachesApplicationListener,\
 * org.springframework.boot.builder.ParentContextCloserApplicationListener,\
 * org.springframework.boot.context.FileEncodingApplicationListener,\
 * org.springframework.boot.context.config.AnsiOutputApplicationListener,\
 * org.springframework.boot.context.config.DelegatingApplicationListener,\
 * org.springframework.boot.context.logging.LoggingApplicationListener,\
 * org.springframework.boot.env.EnvironmentPostProcessorApplicationListener
 * </pre>
 *
 *
 * <p>{@link org.springframework.boot.context.event.EventPublishingRunListener}</p>
 *
 * <ol>
 *     <li>{@link org.springframework.boot.context.event.ApplicationStartingEvent}</li>
 *     <li>{@link org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent}</li>
 *     <li>{@link org.springframework.boot.context.event.ApplicationContextInitializedEvent}</li>
 *     <li>{@link org.springframework.boot.context.event.ApplicationPreparedEvent}</li>
 *     <li>{@link org.springframework.boot.context.event.ApplicationStartedEvent}</li>
 *     <li>{@link org.springframework.boot.context.event.ApplicationReadyEvent}</li>
 *     <li>{@link org.springframework.boot.context.event.ApplicationFailedEvent}</li>
 * </ol>
 *
 * @author likly
 * @version 1.0.0
 * @see org.springframework.context.event.EventListener
 * @see org.springframework.context.ApplicationListener
 * @see org.springframework.boot.SpringApplication#setListeners(java.util.Collection)
 * @see org.springframework.boot.SpringApplicationRunListener
 * @see org.springframework.boot.context.event.EventPublishingRunListener
 * @since 1.0.0
 */

package org.ifinalframework.context.listener;
