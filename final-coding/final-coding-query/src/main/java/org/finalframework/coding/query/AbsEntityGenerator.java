package org.finalframework.coding.query;

import org.finalframework.coding.entity.EntityFactory;
import org.finalframework.coding.file.JavaSource;
import org.finalframework.coding.generator.JavaSourceGenerator;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

/**
 * @author likly
 * @version 1.0
 * @date 2019-11-21 18:05:44
 * @since 1.0
 */

public abstract class AbsEntityGenerator<T extends JavaSource> extends JavaSourceGenerator<T> {

    public AbsEntityGenerator(ProcessingEnvironment processEnv, String targetRoute) {
        super(processEnv, targetRoute);
    }

    @Override
    protected T buildJavaSource(TypeElement typeElement) {
        String packageName = packageNameGenerator.generate(typeElement);
        QEntity entity = QEntityFactory.create(processEnv, packageName, EntityFactory.create(processEnv, typeElement));
        return buildEntityJavaSource(typeElement, entity);
    }

    protected abstract T buildEntityJavaSource(TypeElement typeElement, QEntity entity);


}

