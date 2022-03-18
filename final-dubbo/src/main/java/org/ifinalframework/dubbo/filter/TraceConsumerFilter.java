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

package org.ifinalframework.dubbo.filter;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;
import org.ifinalframework.dubbo.annotation.AutoFilter;
import org.slf4j.MDC;

import java.util.Optional;
import java.util.UUID;

/**
 * Put the {@code trace} which get from {@link MDC} or create by {@link UUID} into {@link Invocation}.
 *
 * @author ilikly
 * @version 1.0.0
 * @see TraceProviderFilter
 * @since 1.0.0
 */
@AutoFilter("traceConsumer")
@Activate(group = {CommonConstants.CONSUMER})
public class TraceConsumerFilter implements Filter {

    private static final String TRACE_NAME = "trace";


    @Override
    public Result invoke(final Invoker<?> invoker, final Invocation invocation) throws RpcException {
        String trace = Optional.ofNullable(MDC.get(TRACE_NAME)).orElse(UUID.randomUUID().toString());
        invocation.setAttachment(TRACE_NAME, trace);
        return invoker.invoke(invocation);

    }

}
