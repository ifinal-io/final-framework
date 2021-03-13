package org.ifinal.finalframework.data.util;

import org.ifinal.finalframework.data.velocity.StringTemplateResourceLoader;
import org.ifinal.finalframework.util.Asserts;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.Map;
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
        p.setProperty("input.encoding", UTF_8);
        p.setProperty("output.encoding", UTF_8);
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

    private Velocities() {
    }

    public static String getValue(final String express, final Object params) {

        Template template = ve.getTemplate(express, UTF_8);
        if (null == params || Asserts.isBlank(express)) {
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
     * @param param param
     * @return context
     */
    @SuppressWarnings("unchecked")
    public static Context buildContext(final Object param) {

        ToolContext context = toolManager.createContext();
        if (param instanceof Map) {
            context.putAll((Map<String, Object>) param);
        } else {
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
                throw new IllegalArgumentException(e);
            }
        }
        return context;
    }

}

