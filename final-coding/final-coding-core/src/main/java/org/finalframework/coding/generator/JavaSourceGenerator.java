package org.finalframework.coding.generator;


import org.finalframework.coding.file.JavaSource;
import org.finalframework.core.configuration.Configuration;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import java.io.IOException;

/**
 * @author likly
 * @version 1.0
 * @date 2019-11-27 22:19:24
 * @since 1.0
 */
public abstract class JavaSourceGenerator<T extends JavaSource> extends TemplateCodeGenerator {
    protected final PackageNameGenerator packageNameGenerator;

    public JavaSourceGenerator(ProcessingEnvironment processEnv, String targetRoute) {
        super(processEnv, targetRoute);
        String entity = Configuration.getInstance().getString("final.coding.domain.entity", "entity");
        this.packageNameGenerator = new DefaultPackageNameGenerator(processEnv, entity, targetRoute);
    }

    @Override
    public Void generate(TypeElement typeElement) {
        coding(buildJavaSource(typeElement));
        return null;
    }

    protected abstract T buildJavaSource(TypeElement entity);


    protected void coding(T source) {
        try {
            TypeElement mapperElement = processEnv.getElementUtils().getTypeElement(source.getName());
            if (mapperElement == null) {
                coder.coding(source, processEnv.getFiler().createSourceFile(source.getName()).openWriter());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

