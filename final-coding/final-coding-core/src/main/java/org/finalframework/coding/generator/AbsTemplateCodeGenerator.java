package org.finalframework.coding.generator;


import org.finalframework.coding.Coder;
import org.finalframework.core.configuration.Configuration;

import javax.annotation.processing.ProcessingEnvironment;

/**
 * @author likly
 * @version 1.0
 * @date 2019-10-27 00:24:22
 * @since 1.0
 */
public abstract class AbsTemplateCodeGenerator implements TemplateCodeGenerator {
    protected final Coder coder = Coder.getDefaultCoder();
    protected final ProcessingEnvironment processEnv;
    protected final PackageNameGenerator packageNameGenerator;

    public AbsTemplateCodeGenerator(ProcessingEnvironment processEnv,String targetRoute) {
        this.processEnv = processEnv;
        String entity = Configuration.getInstance().getString("final.coding.domain.entity", "entity");
        this.packageNameGenerator = new DefaultPackageNameGenerator(processEnv, entity, targetRoute);
    }
}

