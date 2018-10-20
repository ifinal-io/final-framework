package cn.com.likly.finalframework.model;

import com.sun.tools.javac.code.Type;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-19 21:12
 * @since 1.0
 */
public class EntityModelFactory {

    public static EntityModel create(TypeElement entityElement) {
        final String entityName = entityElement.asType().toString();
        final String entitySimpleName = entityName.substring(entityName.lastIndexOf(".") + 1);
        final String packageName = entityName.substring(0, entityName.lastIndexOf("."));
        final String className = entitySimpleName + "EntityHolder";
        final List<Element> elements = getAllClosedElement(entityElement);

        return EntityModel.builder().entityName(entityName)
                .packageName(packageName)
                .properties(
                        elements.stream()
                                .filter(it -> it.getKind() == ElementKind.FIELD)
                                .map(it -> it.getSimpleName().toString())
                                .collect(Collectors.toSet())
                ).build();

    }

    private static List<Element> getAllClosedElement(TypeElement typeElement) {

        List<Element> result = new ArrayList<>(typeElement.getEnclosedElements());
        TypeMirror superclass = typeElement.getSuperclass();
        while (superclass.getKind() != TypeKind.NONE) {
            Type.ClassType classType = (Type.ClassType) superclass;
            result.addAll(0, classType.asElement().getEnclosedElements());
            superclass = classType.supertype_field;
        }

        return result;


    }

}
