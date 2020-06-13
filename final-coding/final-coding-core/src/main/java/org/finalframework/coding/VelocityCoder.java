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

package org.finalframework.coding;

import ch.qos.logback.classic.Level;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.apache.velocity.tools.Scope;
import org.apache.velocity.tools.ToolContext;
import org.apache.velocity.tools.ToolManager;
import org.apache.velocity.tools.config.ConfigurationUtils;
import org.apache.velocity.tools.generic.DateTool;
import org.finalframework.core.beans.Beans;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Writer;
import java.util.Date;
import java.util.Properties;

/**
 * @author likly
 * @version 1.0
 * @date 2019-10-23 09:51:45
 * @since 1.0
 */
public class VelocityCoder implements Coder {

    private static ToolManager toolManager = new ToolManager();

    public VelocityCoder() {
        Properties properties = new Properties();
        properties.setProperty("resource.loader", "classpath");
        //配置加载器实现类
        properties.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getCanonicalName());
        properties.setProperty("runtime.log.invalid.references", "false");
        properties.setProperty("input.encoding", "UTF-8");
        properties.setProperty("output.encoding", "UTF-8");
        properties.setProperty("log4j.logger.org.apache.velocity", "ERROR");
        properties.setProperty("log4j.logger.root", "ERROR");


        final Logger apacheLogger = LoggerFactory.getLogger("org.apache");
        if (apacheLogger instanceof ch.qos.logback.classic.Logger) {
            ((ch.qos.logback.classic.Logger) apacheLogger).setLevel(Level.ERROR);
        }

        Logger logger = LoggerFactory.getLogger(RuntimeConstants.DEFAULT_RUNTIME_LOG_NAME);
        if (logger instanceof ch.qos.logback.classic.Logger) {
            ((ch.qos.logback.classic.Logger) logger).setLevel(Level.ERROR);
        }
        if (!Scope.exists("coding")) {
            Scope.add("coding");
        }
        Velocity.init(properties);
        toolManager.configure(ConfigurationUtils.getDefaultTools());
        toolManager.setVelocityEngine(new VelocityEngine());
    }


    @Override
    public void coding(String template, Object model, Writer writer) {
        try {
            Velocity.getTemplate("template/" + template).merge(buildContext(model), writer);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {

    }

    public Context buildContext(Object model) {
        ToolContext context = toolManager.createContext();
        context.addToolbox(toolManager.getToolboxFactory().createToolbox(Scope.APPLICATION));
        context.put("version", "1.0");
        context.put("user", "likly");
        context.put("created", new Date());

        context.putAll(Beans.toMap(model));


//        String name = model.getClass().getSimpleName();
//        name = name.substring(0, 1).toLowerCase() + name.substring(1);
//        context.put(name, model);
        return context;
    }
}
