package org.ifinal.finalframework.auto.coding;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class FreeMarkerCoder {//}implements Coder {
//    //    private static final Logger logger = LoggerFactory.getLogger(FreeMakerCoder.class);
//    private Configuration configuration;
//
//    public FreeMarkerCoder() {
//
//        try {
//            this.configuration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
//            this.configuration.setClassForTemplateLoading(this.getClass(), "/template/");
//            this.configuration.setDefaultEncoding("UTF-8");
//        } catch (Exception e) {
//            System.err.println("++++++++++++++++++++++++++++++++" + e.getMessage());
//        }
//
//    }
//
//    private freemarker.template.Template getTemplate(String name) {
//        try {
//            return configuration.getTemplate(name);
//        } catch (IOException e) {
////            logger.error("Find the template error by name: {}", name, e);
//            throw new RuntimeException("Find the template error by name:" + name);
//        }
//    }
//
//    @Override
//    public void coding(String template, Object model, Writer writer) {
//        try {
//            getTemplate(template).process(model, writer);
//            writer.flush();
//            writer.close();
//        } catch (Exception e) {
////            logger.error("Coding template error:template={}", template);
//            throw new RuntimeException("\"Coding template error:template=" + template);
//        }
//    }
}
