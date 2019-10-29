package org.finalframework.coding;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.context.Context;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.apache.velocity.tools.generic.DateTool;

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

    public VelocityCoder() {
        Properties properties = new Properties();
        properties.setProperty("resource.loader", "classpath");
        //配置加载器实现类
        properties.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getCanonicalName());
        properties.setProperty("input.encoding", "UTF-8");
        properties.setProperty("output.encoding", "UTF-8");

        Velocity.init(properties);
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

    private Context buildContext(Object model) {
        VelocityContext context = new VelocityContext();
        context.put("date", new DateTool());
        context.put("version","1.0");
        context.put("user","likly");
        context.put("created",new Date());
        String name = model.getClass().getSimpleName();
        name = name.substring(0, 1).toLowerCase() + name.substring(1);
        context.put(name, model);
        return context;
    }
}
