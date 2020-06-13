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
        return new VelocityCoder();
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
