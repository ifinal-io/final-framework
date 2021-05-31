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

import org.ifinalframework.dubbo.annotation.AutoFilter;

import java.util.Optional;
import java.util.UUID;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcException;
import org.slf4j.MDC;

/**
 * TraceFilter.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@AutoFilter("traceProvider")
@Activate(group = {CommonConstants.PROVIDER})
public class TraceProviderFilter implements Filter {

    private static final String TRACE_NAME = "trace";

    @Override
    public Result invoke(final Invoker<?> invoker, final Invocation invocation) throws RpcException {

        String trace = Optional.ofNullable(invocation.getAttachment(TRACE_NAME)).orElse(UUID.randomUUID().toString());
        MDC.put(TRACE_NAME, trace);
        return invoker.invoke(invocation);

    }

}
