package org.finalframework.auto.coding.generator;


import org.finalframework.auto.coding.Coder;
import org.finalframework.core.generator.Generator;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;

/**
 * @author likly
 * @version 1.0
 * @date 2019-10-27 00:24:22
 * @since 1.0
 */
public abstract class TemplateCodeGenerator implements Generator<TypeElement,Void> {
    protected final Coder coder = Coder.getDefaultCoder();
    protected final ProcessingEnvironment processEnv;

    public TemplateCodeGenerator(ProcessingEnvironment processEnv) {
        this.processEnv = processEnv;
    }
}

