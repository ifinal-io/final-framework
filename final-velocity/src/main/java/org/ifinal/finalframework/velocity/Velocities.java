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
 *
 */

package org.ifinal.finalframework.velocity;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

import ch.qos.logback.classic.Level;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.tools.Scope;
import org.apache.velocity.tools.ToolContext;
import org.apache.velocity.tools.ToolManager;
import org.apache.velocity.tools.config.ConfigurationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
public final class Velocities {

    private static final String UTF_8 = "UTF-8";

    private static final VelocityEngine ve = new VelocityEngine();

    private static final Properties p = new Properties();

    private static final ToolManager toolManager;

    static {

        setLoggerLevel("org.apache", Level.ERROR);
        setLoggerLevel(RuntimeConstants.DEFAULT_RUNTIME_LOG_NAME, Level.ERROR);

        //加载器名称
        p.setProperty("resource.loader", "template");
        //配置加载器实现类
        p.setProperty("template.resource.loader.class", StringTemplateResourceLoader.class.getCanonicalName());
        p.setProperty("input.encoding", UTF_8);
        p.setProperty("output.encoding", UTF_8);
        p.setProperty("log4j.logger.org.apache", "ERROR");
        p.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.NullLogChute");
        ve.init(p);

        toolManager = new ToolManager();
        toolManager.setVelocityEngine(ve);
        toolManager.configure(ConfigurationUtils.getDefaultTools());
        toolManager.getToolboxFactory().createToolbox(Scope.APPLICATION);
    }

    private Velocities() {
    }

    private static void setLoggerLevel(String name, Level level) {
        Logger logger = LoggerFactory.getLogger(name);
        if (logger instanceof ch.qos.logback.classic.Logger) {
            ((ch.qos.logback.classic.Logger) logger).setLevel(level);
        }
    }

    public static String getValue(final String express, final Object params) {
        Template template = ve.getTemplate(express, UTF_8);
        //反射param设置key，value
        Context context = buildContext(params);
        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        return writer.toString();
    }

    /**
     * 构建velocity参数map。key:占位符key，value:占位符值
     *
     * @param param param
     * @return context
     */
    @SuppressWarnings("unchecked")
    public static Context buildContext(final Object param) {

        ToolContext context = toolManager.createContext();
        if (param instanceof Map) {
            context.putAll((Map<String, Object>) param);
        } else if (Objects.nonNull(param)) {
            try {
                BeanInfo beanInfo = Introspector.getBeanInfo(param.getClass());
                for (PropertyDescriptor propertyDescriptor : beanInfo.getPropertyDescriptors()) {
                    Method readMethod = propertyDescriptor.getReadMethod();

                    if (Objects.isNull(readMethod)) {
                        continue;
                    }

                    context.put(propertyDescriptor.getName(), readMethod.invoke(param));
                }
            } catch (Exception e) {
                throw new IllegalArgumentException(e);
            }
        }
        return context;
    }

}

