package com.ilikly.finalframework.data.coding.entity;

import com.ilikly.finalframework.data.annotation.Column;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-29 10:19
 * @since 1.0
 */
public interface EntityFactory {

    Logger logger = LoggerFactory.getLogger(EntityFactory.class);

    static Entity<Property> create(ProcessingEnvironment processEnv, TypeElement typeElement) {
//        logger.info("create entity: {}", typeElement.getSimpleName().toString());
        final BaseEntity<Property> result = new BaseEntity<>(processEnv, typeElement);

        while (typeElement != null) {
            typeElement
                    .getEnclosedElements()
                    .stream()
                    .filter(it -> (
                                    it.getKind().isField() && !it
                                            .getModifiers()
                                            .contains(Modifier.STATIC) && !"serialVersionUID".equalsIgnoreCase(it
                                            .getSimpleName()
                                            .toString()
                                    )
                                            ||
                                            (
                                                    it.getKind() == ElementKind.METHOD && it.getAnnotation(Column.class) != null && !it.getModifiers().contains(Modifier.STATIC)
                                            )

                            )

                    )
                    .map(it -> new BaseProperty(result, processEnv, it))
//                    .peek(it -> {
//                        logger.info("name={},rawType={},type={},,isCollection={},componentType={},isMap={},keyType={},valueType={}", it
//                                .getName(), it.getRawType(), it.getType(), it.isCollection(), it.getComponentType(), it.isMap(), it
//                                .getMapKeyType(), it.getMapValueType());
//                    })
                    .forEach(result::addProperty);
            TypeMirror superclass = typeElement.getSuperclass();
            if (superclass != null) {
                if (superclass.getKind() == TypeKind.NONE) {
                    typeElement = null;
                } else {
                    typeElement = (TypeElement) ((DeclaredType) superclass).asElement();
                }
            }
        }

        return result;
    }


}
