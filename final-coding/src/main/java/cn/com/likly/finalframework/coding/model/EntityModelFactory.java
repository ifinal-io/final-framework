package cn.com.likly.finalframework.coding.model;

import cn.com.likly.finalframework.data.annotation.MapperEntity;
import cn.com.likly.finalframework.data.annotation.PrimaryKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
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

    private static final Logger logger = LoggerFactory.getLogger(EntityModelFactory.class);

    public static EntityModel create(TypeElement entityElement) {
        final String className = entityElement.asType().toString();
        final String entityPackage = className.substring(0, className.lastIndexOf("."));
        final String entityName = className.substring(className.lastIndexOf(".") + 1);
        final String qentityPackage = entityPackage.replace(".entity", ".query");
        final String qentityName = "Q" + entityName;
        final String mapperPackage = entityPackage.replace(".entity", ".mapper");
        final String mapperName = entityName + "Mapper";
        final String repositoryPackage = entityPackage.replace(".entity", ".repository");
        final String repositoryName = entityName + "Repository";

        final List<Element> elements = getAllClosedElement(entityElement);

        EntityModel model = new EntityModel();
        model.setEntityPackage(entityPackage);
        model.setEntityName(entityName);
        model.setQentityPackage(qentityPackage);
        model.setQentityName(qentityName);
        model.setMapperPackage(mapperPackage);
        model.setMapperName(mapperName);
        model.setRepositoryPackage(repositoryPackage);
        model.setRepositoryName(repositoryName);
        model.setProperties(
                elements.stream()
                        .filter(it -> it.getKind().isField() && !it.getModifiers().contains(Modifier.STATIC) && !it.getSimpleName().toString().equals("serialVersionUID"))
                        .map(it -> {
                            PropertyModel propertyModel = new PropertyModel();
                            propertyModel.setName(it.getSimpleName().toString());
                            propertyModel.setType(it.asType().toString());
                            propertyModel.setIdProperty(it.getAnnotation(PrimaryKey.class) != null);

                            return propertyModel;
                        })
                        .collect(Collectors.toSet())
        );
        model.setMapperEntity(entityElement.getAnnotation(MapperEntity.class) != null);
        return model;

    }

    private static List<Element> getAllClosedElement(TypeElement typeElement) {
        List<Element> result = new ArrayList<>(typeElement.getEnclosedElements());
        TypeMirror superclass = typeElement.getSuperclass();
        while (superclass.getKind() != TypeKind.NONE) {
            TypeElement supperTypeElement = (TypeElement) ((DeclaredType) superclass).asElement();
            result.addAll(0, supperTypeElement.getEnclosedElements());
            superclass = supperTypeElement.getSuperclass();
        }

        return result;


    }

}
