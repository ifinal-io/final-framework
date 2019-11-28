package org.finalframework.coding.generator;


import org.finalframework.coding.file.JavaSource;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

/**
 * @author likly
 * @version 1.0
 * @date 2019-11-27 22:19:24
 * @since 1.0
 */
public abstract class JavaSourceGenerator<T extends JavaSource> extends TemplateCodeGenerator {
    public JavaSourceGenerator(ProcessingEnvironment processEnv, String targetRoute) {
        super(processEnv, targetRoute);
    }

    @Override
    public Void generate(TypeElement typeElement) {
        coding(buildJavaSource(typeElement));
        return null;
    }

    protected abstract T buildJavaSource(TypeElement entity);


    private void coding(T query) {
        try {
            TypeElement typeElement = processEnv.getElementUtils().getTypeElement(query.getName());
            if (typeElement == null) {
                JavaFileObject sourceFile = processEnv.getFiler().createSourceFile(query.getName());
                coder.coding(query, sourceFile.openWriter());
            }
        } catch (Exception e) {
//            processEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.getMessage());
        }
    }
}

