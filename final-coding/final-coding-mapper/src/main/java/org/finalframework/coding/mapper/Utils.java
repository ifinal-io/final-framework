package org.finalframework.coding.mapper;

import org.finalframework.coding.entity.Property;
import org.finalframework.coding.mapper.handler.TypeHandlerRegistry;
import org.finalframework.core.Assert;
import org.finalframework.data.annotation.JsonColumn;
import org.finalframework.data.annotation.enums.PersistentType;
import org.finalframework.data.annotation.enums.ReferenceMode;
import org.finalframework.data.entity.enums.IEnum;
import org.finalframework.data.mapping.converter.NameConverterRegistry;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.List;
import java.util.Set;

import static org.finalframework.data.annotation.enums.PersistentType.AUTO;

/**
 * @author likly
 * @version 1.0
 * @date 2019-10-10 21:42:12
 * @since 1.0
 */
public final class Utils {

    private static Utils instance;
    private final ProcessingEnvironment processEnv;
    private final Types typeUtils;
    private final Elements elementUtils;
    private final TypeElement enumTypeElement;
    private final TypeElement listTypeElement;
    private final TypeElement setTypeElement;

    private TypeHandlerRegistry typeHandlerRegistry;

    private Utils(ProcessingEnvironment processEnv) {
        this.processEnv = processEnv;
        this.typeUtils = processEnv.getTypeUtils();
        this.elementUtils = processEnv.getElementUtils();
        this.enumTypeElement = processEnv.getElementUtils().getTypeElement(IEnum.class.getCanonicalName());
        this.listTypeElement = processEnv.getElementUtils().getTypeElement(List.class.getCanonicalName());
        this.setTypeElement = processEnv.getElementUtils().getTypeElement(Set.class.getCanonicalName());
        this.typeHandlerRegistry = TypeHandlerRegistry.getInstance();
    }

    public static void init(ProcessingEnvironment processingEnvironment) {
        instance = new Utils(processingEnvironment);
    }

    public static Utils getInstance() {
        return instance;
    }


    public static String formatPropertyName(@Nullable Property referenceProperty, @NonNull Property property) {
        if (referenceProperty == null) {
            return property.getName();
        } else {
            return property.isIdProperty() ?
                    referenceProperty.referenceMode() == ReferenceMode.SIMPLE ?
                            referenceProperty.getName() : referenceProperty.getName() + property.getName().substring(0, 1).toUpperCase() + property.getName().substring(1)
                    : referenceProperty.getName() + property.getName().substring(0, 1).toUpperCase() + property.getName().substring(1);
        }
    }

    @NonNull
    public String formatPropertyWriteColumn(@Nullable Property referenceProperty, @NonNull Property property) {
        return formatPropertyColumn(referenceProperty, property);
    }

    @NonNull
    public String formatPropertyReadColumn(@Nullable Property referenceProperty, @NonNull Property property) {
        return formatPropertyColumn(referenceProperty, property);
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

    public TypeElement getTypeHandler(Element element) {
        if (isEnum(element)) {
            return getTypeElement(typeHandlerRegistry.getEnumTypeHandler());
        }
        JsonColumn jsonColumn = element.getAnnotation(JsonColumn.class);
        PersistentType persistentType = jsonColumn == null ? AUTO : jsonColumn.persistentType();
        if (isList(element)) {
            switch (persistentType) {
                case JSON:
                    return getTypeElement(typeHandlerRegistry.getJsonTypesHandlers().getList());
                case BLOB:
                    return getTypeElement(typeHandlerRegistry.getJsonBlobTypeHandlers().getList());
                default:
                    return getTypeElement(typeHandlerRegistry.getListTypeHandler());
            }
        } else if (isSet(element)) {
            switch (persistentType) {
                case JSON:
                    return getTypeElement(typeHandlerRegistry.getJsonTypesHandlers().getSet());
                case BLOB:
                    return getTypeElement(typeHandlerRegistry.getJsonBlobTypeHandlers().getSet());
                default:
                    return getTypeElement(typeHandlerRegistry.getSetTypeHandler());
            }
        } else if (jsonColumn != null) {
            switch (persistentType) {
                case JSON:
                    return getTypeElement(typeHandlerRegistry.getJsonTypesHandlers().getObject());
                case BLOB:
                    return getTypeElement(typeHandlerRegistry.getJsonBlobTypeHandlers().getObject());
            }
        }


        return null;
    }
//
//    @NonNull
//    public String formatPropertyColumn(@Nullable Property referenceProperty, @NonNull Property property) {
//        return org.finalframework.coding.query.Utils.formatPropertyColumn(referenceProperty, property);
//    }


    public String formatPropertyValues(@Nullable Property referenceProperty, @NonNull Property property, String value) {
        final TypeElement javaType = property.getMetaTypeElement();
        final TypeElement typeHandler = getTypeHandler(property.getElement());
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

    public boolean isList(Element element) {
        return isSubtype(element, listTypeElement);
    }

    public boolean isSet(Element element) {
        return isSubtype(element, setTypeElement);
    }


    private boolean isSubtype(Element subTypeElement, Element parentTypeElement) {
        return typeUtils.isSubtype(typeUtils.erasure(subTypeElement.asType()), typeUtils.erasure(parentTypeElement.asType()));
    }


}
