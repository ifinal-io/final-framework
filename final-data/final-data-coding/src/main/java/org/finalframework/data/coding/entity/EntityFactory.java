package org.finalframework.data.coding.entity;

import org.finalframework.data.annotation.Column;
import org.finalframework.data.coding.entity.javac.PropertyElementVisitor;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import java.util.HashMap;
import java.util.Map;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-29 10:19
 * @since 1.0
 */
public final class EntityFactory {

    private static final Map<String, Entity<Property>> cache = new HashMap<>();

    public static Entity<Property> create(ProcessingEnvironment processEnv, TypeElement typeElement) {

        String name = typeElement.getQualifiedName().toString();

        if (cache.containsKey(name)) {
            return cache.get(name);
        }

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
                    .peek(it -> ((Element) it).accept(new PropertyElementVisitor(processEnv), null))
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

        cache.put(name, result);

        return result;
    }


}
