package cn.com.likly.finalframework.coding.coder;

import cn.com.likly.finalframework.coding.annotation.Template;

import java.io.Writer;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-29 13:18
 * @since 1.0
 */
public interface Coder {
    void process(String template, Object model, Writer writer);

    default void process(Object model, Writer writer) {
        Template template = model.getClass().getAnnotation(Template.class);
        if (template == null) {
            throw new NullPointerException("the model must one Template annotation , model=" + model
                    .getClass()
                    .getName());
        }
        process(template.value(), model, writer);
    }

}
