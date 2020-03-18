package org.finalframework.coding.entity;

import org.finalframework.coding.beans.Bean;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-29 10:19
 * @since 1.0
 */
public final class EntityFactory {

    private static final Map<String, Entity> cache = new HashMap<>();

    public static Entity create(ProcessingEnvironment processEnv, TypeElement typeElement) {

        String name = typeElement.getQualifiedName().toString();

        if (cache.containsKey(name)) {
            return cache.get(name);
        }

        final BaseEntity result = new BaseEntity(processEnv, typeElement);

        while (typeElement != null) {

            Bean.from(processEnv, typeElement).stream()
//                    .filter(property -> property.getField().isPresent())
                    .filter(property -> {
                                if (property.getField().isPresent()) {
                                    return property.getField().map(field ->
                                            !(field.getModifiers().contains(Modifier.FINAL) || field.getModifiers().contains(Modifier.STATIC)))
                                            .orElse(true);
                                } else if (property.getGetter().isPresent()) {
                                    return property.getGetter().map(method ->
                                            !(method.getModifiers().contains(Modifier.FINAL) || method.getModifiers().contains(Modifier.STATIC))).orElse(true);
                                }

                                return false;
                            }
                    )
                    .map(property -> new AnnotationProperty(processEnv, property.getField(), Optional.of(property)))
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
