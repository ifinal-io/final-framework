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

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-29 13:20
 * @since 1.0
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
