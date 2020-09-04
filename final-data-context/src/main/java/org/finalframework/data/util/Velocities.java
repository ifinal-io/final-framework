/*
 * Copyright (c) 2018-2020.  the original author or authors.
 *  <p>
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p>
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  <p>
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.finalframework.data.util;


import ch.qos.logback.classic.Level;
import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.tools.Scope;
import org.apache.velocity.tools.ToolContext;
import org.apache.velocity.tools.ToolManager;
import org.apache.velocity.tools.config.ConfigurationUtils;
import org.finalframework.core.Assert;
import org.finalframework.data.velocity.StringTemplateResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * @author likly
 * @version 1.0
 * @date 2020-07-17 10:24:29
 * @since 1.0
 */
public final class Velocities {
    public static VelocityEngine ve = new VelocityEngine();
    private static Properties p = new Properties();

    private static ToolManager toolManager;

    static {

        final Logger apacheLogger = LoggerFactory.getLogger("org.apache");
        if (apacheLogger instanceof ch.qos.logback.classic.Logger) {
            ((ch.qos.logback.classic.Logger) apacheLogger).setLevel(Level.ERROR);
        }

        Logger logger = LoggerFactory.getLogger(RuntimeConstants.DEFAULT_RUNTIME_LOG_NAME);
        if (logger instanceof ch.qos.logback.classic.Logger) {
            ((ch.qos.logback.classic.Logger) logger).setLevel(Level.ERROR);
        }
        //加载器名称
        p.setProperty("resource.loader", "template");
        //配置加载器实现类
        p.setProperty("template.resource.loader.class", StringTemplateResourceLoader.class.getCanonicalName());
        p.setProperty("input.encoding", "UTF-8");
        p.setProperty("output.encoding", "UTF-8");
        p.setProperty("log4j.logger.org.apache", "ERROR");
        p.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.NullLogChute");
        ve.init(p);
        if (!Scope.exists("coding")) {
            Scope.add("coding");
        }


        toolManager = new ToolManager();
        toolManager.setVelocityEngine(ve);
        toolManager.configure(ConfigurationUtils.getDefaultTools());
        toolManager.getToolboxFactory().createToolbox(Scope.APPLICATION);
    }

    public static String getValue(String express, Object params) {
        Template template = ve.getTemplate(express, "UTF-8");
        if (null == params || Assert.isBlank(express)) {
            return "";
        }
        //反射param设置key，value
        Context context = buildContext(params);
        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        return writer.toString();
    }


    /**
     * 构建velocity参数map。key:占位符key，value:占位符值
     *
     * @param param
     * @return
     */
    public static Context buildContext(Object param) {
        ToolContext context = toolManager.createContext();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(param.getClass());
            for (PropertyDescriptor propertyDescriptor : beanInfo.getPropertyDescriptors()) {
                Method readMethod = propertyDescriptor.getReadMethod();
                if (readMethod != null) {
                    context.put(propertyDescriptor.getName(), readMethod.invoke(param));
                }
            }
            context.put("record", param);
        } catch (Exception e) {
        }
        return context;
    }

}

