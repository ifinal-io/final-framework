package org.ifinal.finalframework.auto.coding.generator;


import org.ifinal.finalframework.auto.coding.Coder;
import org.ifinal.finalframework.core.generator.Generator;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class TemplateCodeGenerator implements Generator<TypeElement, Void> {
    protected final Coder coder = Coder.getDefaultCoder();
    protected final ProcessingEnvironment processEnv;

    public TemplateCodeGenerator(ProcessingEnvironment processEnv) {
        this.processEnv = processEnv;
    }
}

