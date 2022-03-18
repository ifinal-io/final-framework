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
 */

package org.ifinalframework.monitor.trace;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import org.ifinalframework.core.generator.TraceGenerator;
import org.ifinalframework.core.generator.UuidTraceGenerator;
import org.ifinalframework.monitor.context.TraceContext;
import org.ifinalframework.util.Asserts;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

/**
 * @author ilikly
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
@Primary
@Component
public class MDCTracer implements Tracer {

    private static final String MDC_TRACER = "mdcTracer";

    private static final TraceGenerator traceGenerator = UuidTraceGenerator.INSTANCE;

    @Override
    public void start(final TraceContext context) {

        String trace = context.getTrace();
        String value = MDC.get(trace);
        if (Asserts.isBlank(value)) {
            value = traceGenerator.generate(null);
            MDC.put(trace, value);
            MDC.put(MDC_TRACER, Boolean.TRUE.toString());
            logger.info("put trace: trace={},value={}", trace, value);
        }
    }

    @Override
    public void stop(final TraceContext context) {

        if (Boolean.TRUE.equals(Boolean.valueOf(MDC.get(MDC_TRACER)))) {
            String trace = context.getTrace();
            String value = MDC.get(trace);
            logger.info("remove trace: trace={},value={}", trace, value);
            MDC.remove(trace);
        }
    }

}
