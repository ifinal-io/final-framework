/*
 * Copyright 2020-2023 the original author or authors.
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

package org.ifinalframework.context.initializer;

import javassist.ClassPool;

import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.util.StopWatch;

import org.ifinalframework.auto.spring.factory.annotation.SpringFactory;
import org.ifinalframework.javassist.JavaAssistProcessor;

import java.util.ServiceLoader;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * 在Spring容器的{@linkplain ApplicationStartingEvent 启动}事件中，调用{@link JavaAssistProcessor}处理器对{@link Class}进行修改。
 *
 * @author iimik
 * @version 1.5.0
 * @since 1.5.0
 */
@Slf4j
@SpringFactory(ApplicationListener.class)
public class JavaAssistApplicationStartingListener implements ApplicationListener<ApplicationStartingEvent> {

    /**
     * 是否已经处理过标记
     */
    private boolean processed = false;

    @SneakyThrows
    @Override
    public void onApplicationEvent(ApplicationStartingEvent event) {

        if (processed) {
            return;
        }

        processed = true;

        final ClassPool classPool = ClassPool.getDefault();
        final ServiceLoader<JavaAssistProcessor> javaAssistProcessors = ServiceLoader.load(JavaAssistProcessor.class);

        StopWatch watch = new StopWatch("JavaAssistProcessor");

        for (final JavaAssistProcessor javaAssistProcessor : javaAssistProcessors) {
            final String taskName = javaAssistProcessor.getClass().getName();
            watch.start(taskName);
            javaAssistProcessor.process(classPool);
            watch.stop();
        }
        logger.info(watch.prettyPrint());

    }
}
