package cn.com.likly.finalframework.coding;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.IOException;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-22 15:47
 * @since 1.0
 */
public class Templates {
    private final Configuration configuration;

    public Templates() {
        this.configuration = new Configuration();
        this.configuration.setClassForTemplateLoading(this.getClass(), "/template/");
        this.configuration.setDefaultEncoding("UTF-8");

    }

    public Template getTemplate(String name) {
        try {
            return configuration.getTemplate(name);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }


}
