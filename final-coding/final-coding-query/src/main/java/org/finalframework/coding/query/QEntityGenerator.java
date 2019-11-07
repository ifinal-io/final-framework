package org.finalframework.coding.query;

import org.finalframework.coding.entity.EntityFactory;
import org.finalframework.coding.generator.TemplateCodeGenerator;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

/**
 * @author likly
 * @version 1.0
 * @date 2019-10-27 00:15:44
 * @since 1.0
 */
public class QEntityGenerator extends TemplateCodeGenerator {

    private static final String QUERY_PACKAGE_PATH = "query";

    public QEntityGenerator(ProcessingEnvironment processEnv) {
        super(processEnv, QUERY_PACKAGE_PATH);
    }

    @Override
    public Void generate(TypeElement typeElement) {
        String packageName = packageNameGenerator.generate(typeElement);
        coding(QEntityFactory.create(processEnv, packageName, EntityFactory.create(processEnv, typeElement)));
        return null;
    }

    private void coding(QEntity entity) {
        try {
            String className = entity.getPackage() + "." + entity.getName();
            TypeElement typeElement = processEnv.getElementUtils().getTypeElement(className);
            if (typeElement == null) {
                JavaFileObject sourceFile = processEnv.getFiler()
                        .createSourceFile(className);
                coder.coding(entity, sourceFile.openWriter());
            }
        } catch (Exception e) {
//            processEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.getMessage());
        }
    }

}

