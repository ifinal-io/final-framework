package org.finalframework.coding.generator;


import org.finalframework.core.generator.Generator;

import javax.lang.model.element.TypeElement;

/**
 * @author likly
 * @version 1.0
 * @date 2019-10-27 00:22:44
 * @since 1.0
 */
public interface TemplateCodeGenerator extends Generator {

    void generate(TypeElement typeElement);

}

