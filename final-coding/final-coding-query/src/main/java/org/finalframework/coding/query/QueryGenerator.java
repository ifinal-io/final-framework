package org.finalframework.coding.query;

import org.finalframework.coding.entity.EntityFactory;
import org.finalframework.coding.generator.TemplateCodeGenerator;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

/**
 * @author likly
 * @version 1.0
 * @date 2019-11-21 18:05:44
 * @since 1.0
 */

public class QueryGenerator extends TemplateCodeGenerator {

    private static final String QUERY_PACKAGE_PATH = "query";

    public QueryGenerator(ProcessingEnvironment processEnv) {
        super(processEnv, QUERY_PACKAGE_PATH);
    }

    @Override
    public Void generate(TypeElement typeElement) {
        String packageName = packageNameGenerator.generate(typeElement);
        QEntity entity = QEntityFactory.create(processEnv, packageName, EntityFactory.create(processEnv, typeElement));
        Query query = Query.builder().packageName(entity.getPackage())
                .simpleName(typeElement.getSimpleName() + "Query")
                .entity(entity)
                .build();
        coding(query);
        return null;
    }

    private void coding(Query query) {
        try {
            TypeElement typeElement = processEnv.getElementUtils().getTypeElement(query.getName());
            if (typeElement == null) {
                JavaFileObject sourceFile = processEnv.getFiler()
                        .createSourceFile(query.getName());
                coder.coding(query, sourceFile.openWriter());
            }
        } catch (Exception e) {
//            processEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.getMessage());
        }
    }

}

