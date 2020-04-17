package org.finalframework.coding.mapper;

import static org.finalframework.data.annotation.enums.PersistentType.JSON;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import org.finalframework.coding.entity.Property;
import org.finalframework.coding.mapper.handler.TypeHandlerRegistry;
import org.finalframework.coding.utils.TypeElements;
import org.finalframework.core.Assert;
import org.finalframework.data.annotation.Function;
import org.finalframework.data.annotation.IEnum;
import org.finalframework.data.annotation.Json;
import org.finalframework.data.annotation.TypeHandler;
import org.finalframework.data.annotation.enums.PersistentType;
import org.finalframework.data.annotation.enums.ReferenceMode;
import org.finalframework.data.mapping.converter.NameConverterRegistry;
import org.finalframework.mybatis.handler.sharing.LocalDateTimeTypeHandler;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * @author likly
 * @version 1.0
 * @date 2019-10-10 21:42:12
 * @since 1.0
 */
public final class TypeHandlers {

    private final ProcessingEnvironment processEnv;
    private final Types typeUtils;
    private final Elements elementUtils;

    private final TypeElements typeElements;


    /**
     * @see IEnum
     */
    private final TypeElement enumTypeElement;

    private TypeHandlerRegistry typeHandlerRegistry;

    public TypeHandlers(ProcessingEnvironment processEnv) {
        this.processEnv = processEnv;
        this.typeUtils = processEnv.getTypeUtils();
        this.elementUtils = processEnv.getElementUtils();
        this.typeElements = new TypeElements(typeUtils, elementUtils);
        this.enumTypeElement = processEnv.getElementUtils().getTypeElement(IEnum.class.getCanonicalName());
        this.typeHandlerRegistry = TypeHandlerRegistry.getInstance();
    }

    @NonNull
    public String formatPropertyWriteColumn(@Nullable Property referenceProperty, @NonNull Property property) {
        return formatPropertyColumn(referenceProperty, property);
    }

    @NonNull
    public static String formatPropertyColumn(@Nullable Property referenceProperty, @NonNull Property property) {
        String column = null;
        if (referenceProperty == null) {
            column = property.getColumn();

            if (property.isKeyword()) {
                column = String.format("`%s`", column);
            }


        } else {
            final String referenceColumn = referenceProperty.referenceColumn(property.getName()) != null ?
                    referenceProperty.referenceColumn(property.getName()) : property.getColumn();
            column = property.isIdProperty() && referenceProperty.referenceMode() == ReferenceMode.SIMPLE ?
                    referenceProperty.getColumn() : referenceProperty.getColumn() + referenceColumn.substring(0, 1).toUpperCase() + referenceColumn.substring(1);
        }

        return NameConverterRegistry.getInstance().getColumnNameConverter().convert(column);
    }

    @NonNull
    public String formatPropertyReadColumn(@Nullable Property referenceProperty, @NonNull Property property) {
        String column = formatPropertyColumn(referenceProperty, property);
        if (property.hasAnnotation(Function.class)) {
            Function annotation = property.getAnnotation(Function.class);
            String reader = annotation.reader();
            return String.format("%s AS %s", reader, column);
        }
        return column;
    }

    public TypeElement getTypeHandler(Property property) {

        if (property.hasAnnotation(TypeHandler.class)) {
            List<? extends AnnotationMirror> annotationMirrors = property.getElement().getAnnotationMirrors();

        }


        if (property.isMap() || typeElements.isObject(property.getElement())) {
            PersistentType persistentType = JSON;
            switch (persistentType) {
                case JSON:
                    return getTypeElement(typeHandlerRegistry.getJsonTypesHandlers().getObject());
                case BLOB:
                    return getTypeElement(typeHandlerRegistry.getJsonBlobTypeHandlers().getObject());
            }
        } else if (property.isCollection()) {
            PersistentType persistentType = JSON;
            if (property.isList()) {
                switch (persistentType) {
                    case JSON:
                        return getTypeElement(typeHandlerRegistry.getJsonTypesHandlers().getList());
                    case BLOB:
                        return getTypeElement(typeHandlerRegistry.getJsonBlobTypeHandlers().getList());
                    default:
                        return getTypeElement(typeHandlerRegistry.getListTypeHandler());
                }
            } else if (property.isSet()) {
                switch (persistentType) {
                    case JSON:
                        return getTypeElement(typeHandlerRegistry.getJsonTypesHandlers().getSet());
                    case BLOB:
                        return getTypeElement(typeHandlerRegistry.getJsonBlobTypeHandlers().getSet());
                    default:
                        return getTypeElement(typeHandlerRegistry.getSetTypeHandler());
                }
            }
        } else if (property.hasAnnotation(Json.class)) {
            PersistentType persistentType = JSON;
            switch (persistentType) {
                case JSON:
                    return getTypeElement(typeHandlerRegistry.getJsonTypesHandlers().getObject());
                case BLOB:
                    return getTypeElement(typeHandlerRegistry.getJsonBlobTypeHandlers().getObject());
            }
        } else if (property.isEnum()) {
            return getTypeElement(typeHandlerRegistry.getEnumTypeHandler());
        } else if (typeElements.isSameType(property.getElement(), LocalDateTime.class)) {
            return getTypeElement(LocalDateTimeTypeHandler.class);
        }

        return null;
    }


    public String formatPropertyValues(@Nullable Property referenceProperty, @NonNull Property property, String value) {
        final TypeElement javaType = property.getJavaTypeElement();
        final TypeElement typeHandler = Optional.ofNullable(property.getTypeHandler()).orElse(getTypeHandler(property));
        final StringBuilder builder = new StringBuilder();

        builder.append(property.placeholder() ? "#{" : "${");//.append(value);

        if (Assert.nonNull(referenceProperty)) {
            // entity.path != null ? entity.path.path : null
            builder.append(value).append(".").append(referenceProperty.getName()).append(" != null ? ")
                    .append(value).append(".").append(referenceProperty.getName()).append(".").append(property.getName())
                    .append(" : null");
        } else {
            builder.append(value).append(".").append(property.getName());
        }

        if (typeHandler != null && javaType != null) {
            builder.append(",javaType=").append(javaType.getQualifiedName().toString());
            builder.append(",typeHandler=").append(typeHandler.getQualifiedName().toString());
        }
        builder.append("}");

        return builder.toString();
    }

    private TypeElement getTypeElement(Class clz) {
        return getTypeElement(clz.getCanonicalName());
    }

    private TypeElement getTypeElement(String name) {
        return elementUtils.getTypeElement(name);
    }

    public boolean isEnum(Element element) {
        return isSubtype(element, enumTypeElement);
    }

    private boolean isSubtype(Element subTypeElement, Element parentTypeElement) {
        return typeUtils.isSubtype(typeUtils.erasure(subTypeElement.asType()), typeUtils.erasure(parentTypeElement.asType()));
    }


}
