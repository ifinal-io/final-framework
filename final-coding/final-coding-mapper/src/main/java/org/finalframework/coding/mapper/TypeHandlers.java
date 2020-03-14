package org.finalframework.coding.mapper;

import org.finalframework.coding.entity.Property;
import org.finalframework.coding.mapper.handler.TypeHandlerRegistry;
import org.finalframework.coding.utils.TypeElements;
import org.finalframework.core.Assert;
import org.finalframework.data.annotation.FunctionColumn;
import org.finalframework.data.annotation.JsonColumn;
import org.finalframework.data.annotation.enums.PersistentType;
import org.finalframework.data.annotation.enums.ReferenceMode;
import org.finalframework.data.entity.enums.IEnum;
import org.finalframework.data.mapping.converter.NameConverterRegistry;
import org.finalframework.mybatis.handler.sharing.LocalDateTimeTypeHandler;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.time.LocalDateTime;

import static org.finalframework.data.annotation.enums.PersistentType.JSON;

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
    public String formatPropertyReadColumn(@Nullable Property referenceProperty, @NonNull Property property) {
        String column = formatPropertyColumn(referenceProperty, property);
        if (property.hasAnnotation(FunctionColumn.class)) {
            FunctionColumn annotation = (FunctionColumn) property.getAnnotation(FunctionColumn.class);
            String reader = annotation.reader();
            return String.format("%s AS %s", reader, column);
        }
        return column;
    }

    @NonNull
    public static String formatPropertyColumn(@Nullable Property referenceProperty, @NonNull Property property) {
        String column = null;
        if (referenceProperty == null) {
            column = property.getColumn();
        } else {
            final String referenceColumn = referenceProperty.referenceColumn(property.getName()) != null ?
                    referenceProperty.referenceColumn(property.getName()) : property.getColumn();
            column = property.isIdProperty() && referenceProperty.referenceMode() == ReferenceMode.SIMPLE ?
                    referenceProperty.getColumn() : referenceProperty.getColumn() + referenceColumn.substring(0, 1).toUpperCase() + referenceColumn.substring(1);
        }

        return NameConverterRegistry.getInstance().getColumnNameConverter().convert(column);
    }

    public TypeElement getTypeHandler(Property property) {
        if (property.hasAnnotation(JsonColumn.class) || typeElements.isObject(property.getElement())) {
            JsonColumn jsonColumn = property.getAnnotation(JsonColumn.class);
            PersistentType persistentType = jsonColumn == null ? JSON : jsonColumn.persistentType();
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
            } else if (property.isMap()) {
                switch (persistentType) {
                    case JSON:
                        return getTypeElement(typeHandlerRegistry.getJsonTypesHandlers().getObject());
                    case BLOB:
                        return getTypeElement(typeHandlerRegistry.getJsonBlobTypeHandlers().getObject());
                }
            } else if (jsonColumn != null) {
                switch (persistentType) {
                    case JSON:
                        return getTypeElement(typeHandlerRegistry.getJsonTypesHandlers().getObject());
                    case BLOB:
                        return getTypeElement(typeHandlerRegistry.getJsonBlobTypeHandlers().getObject());
                }
            }
        }

        if (property.isEnum()) {
            return getTypeElement(typeHandlerRegistry.getEnumTypeHandler());
        }

        if (typeElements.isSameType(property.getElement(), LocalDateTime.class)) {
            return getTypeElement(LocalDateTimeTypeHandler.class);
        }

        return null;
    }


    public String formatPropertyValues(@Nullable Property referenceProperty, @NonNull Property property, String value) {
        final TypeElement javaType = property.getMetaTypeElement();
        final TypeElement typeHandler = getTypeHandler(property);
        final StringBuilder builder = new StringBuilder();

        builder.append(property.placeholder() ? "#{" : "${").append(value);

        if (Assert.nonNull(referenceProperty)) {
            builder.append(".").append(referenceProperty.getName());
        }
        builder.append(".").append(property.getName());

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
