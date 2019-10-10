package org.finalframework.coding;

import org.finalframework.coding.annotation.Template;
import org.finalframework.core.Assert;

import java.io.Writer;

/**
 * The generator of template code.
 * @author likly
 * @version 1.0
 * @date 2018-10-29 13:18
 * @since 1.0
 */
public interface Coder {

    static Coder getDefaultCoder(){
        return new FreeMarkerCoder();
    }

    /**
     * coding the template code with data model by writer.
     *
     * @param template the name of template
     * @param model    the data model of template
     * @param writer   the writer of coding file
     */
    void coding(String template, Object model, Writer writer);

    default void coding(Object model, Writer writer) {
        Assert.isNull(model, "the model must not ne null!");
        Assert.isNull(writer, "the writer must not be null!");

        Template template = model.getClass().getAnnotation(Template.class);

        if (template == null) {
            throw new NullPointerException("the model must one Template annotation , model=" + model
                    .getClass()
                    .getName());
        }
        coding(template.value(), model, writer);
    }

}
