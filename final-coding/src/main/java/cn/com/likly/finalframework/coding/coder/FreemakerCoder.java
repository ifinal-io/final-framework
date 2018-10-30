package cn.com.likly.finalframework.coding.coder;

import freemarker.template.Configuration;

import java.io.IOException;
import java.io.Writer;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-29 13:20
 * @since 1.0
 */
public class FreemakerCoder implements Coder {

    private final Configuration configuration;

    public FreemakerCoder() {
        this.configuration = new Configuration();
        this.configuration.setClassForTemplateLoading(this.getClass(), "/template/");
        this.configuration.setDefaultEncoding("UTF-8");

    }

    private freemarker.template.Template getTemplate(String name) {
        try {
            return configuration.getTemplate(name);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void coding(String template, Object model, Writer writer) {
        try {
            getTemplate(template).process(model, writer);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
