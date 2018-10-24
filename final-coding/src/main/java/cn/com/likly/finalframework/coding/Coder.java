package cn.com.likly.finalframework.coding;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Writer;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-23 14:40
 * @since 1.0
 */
public abstract class Coder {

    private static final Logger logger = LoggerFactory.getLogger(Coder.class);
    private static final Templates templates = new Templates();

    public static void coding(String templateName, Object model, Writer writer) {
        try {

            Template template = templates.getTemplate(templateName);
            template.process(model, writer);
            writer.close();
        } catch (TemplateException | IOException e) {
            logger.error("coding error, template={}", templateName, e);
            throw new RuntimeException(e);
        }
    }

}
