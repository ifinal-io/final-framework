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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;
import org.ifinalframework.dubbo.annotation.AutoFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author ilikly
 * @version 1.0.0
 * @since 1.0.0
 */
@Activate(
        group = {CommonConstants.PROVIDER, CommonConstants.CONSUMER}
)
@AutoFilter("logger")
@SuppressWarnings("unused")
public class LoggerFilter implements Filter {

    @Override
    public Result invoke(final Invoker<?> invoker, final Invocation invocation) {

        final Logger logger = LoggerFactory.getLogger(invoker.getInterface());
        final String service = invoker.getInterface().getSimpleName();
        String methodName = invocation.getMethodName();
        try {
            if (logger.isInfoEnabled()) {

                Method method = ReflectionUtils
                        .findMethod(invoker.getInterface(), methodName, invocation.getParameterTypes());

                Parameter[] parameters = method.getParameters();

                Map<String, Object> args = new LinkedHashMap<>(parameters.length);

                for (int i = 0; i < parameters.length; i++) {
                    args.put(parameters[i].getName(), invocation.getArguments()[i]);
                }

                logger.info("{}#{}, args={}", service, methodName, JSON.toJSONString(args, SerializerFeature.WriteNonStringKeyAsString));
            }
            final Result result = invoker.invoke(invocation);
            if (logger.isInfoEnabled()) {
                logger.info("{}#{}, result={}", service, methodName, JSON.toJSONString(result.getValue(), SerializerFeature.WriteNonStringKeyAsString));
            }
            return result;

        } catch (RpcException e) {
            throw e;
        } catch (Exception e) {
            throw new RpcException(e);
        }
    }

}

