package org.finalframework.mybatis.coding.mapper;

import org.finalframework.core.Assert;
import org.finalframework.data.annotation.JsonColumn;
import org.finalframework.data.annotation.enums.PersistentType;
import org.finalframework.data.annotation.enums.ReferenceMode;
import org.finalframework.data.coding.entity.Property;
import org.finalframework.data.entity.enums.IEnum;
import org.finalframework.mybatis.handler.*;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.List;
import java.util.Set;

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


    private String enumTypeHandler = EnumTypeHandler.class.getCanonicalName();

    private String listTypeHandler = DefaultListTypeHandler.class.getCanonicalName();
    private String setTypeHandler = DefaultSetTypeHandler.class.getCanonicalName();

    private String jsonObjectTypeHandler = JsonObjectTypeHandler.class.getCanonicalName();
    private String jsonListTypeHandler = JsonListTypeHandler.class.getCanonicalName();
    private String jsonSetTypeHandler = JsonSetTypeHandler.class.getCanonicalName();

    private String jsonListBlobTypeHandler = JsonListBlobTypeHandler.class.getCanonicalName();
    private String jsonSetBlobTypeHandler = JsonSetBlobTypeHandler.class.getCanonicalName();


    public Utils(ProcessingEnvironment processEnv) {
        this.processEnv = processEnv;
        this.typeUtils = processEnv.getTypeUtils();
        this.elementUtils = processEnv.getElementUtils();
        this.enumTypeElement = processEnv.getElementUtils().getTypeElement(IEnum.class.getCanonicalName());
        this.listTypeElement = processEnv.getElementUtils().getTypeElement(List.class.getCanonicalName());
        this.setTypeElement = processEnv.getElementUtils().getTypeElement(Set.class.getCanonicalName());
    }

    public static void init(ProcessingEnvironment processingEnvironment) {
        instance = new Utils(processingEnvironment);
    }

    public static Utils getInstance() {
        return instance;
    }


    public TypeElement getTypeHandler(Element element) {
        if (isEnum(element)) {
            return getTypeElement(enumTypeHandler);
        }
        JsonColumn jsonColumn = element.getAnnotation(JsonColumn.class);
        PersistentType persistentType = jsonColumn == null ? PersistentType.AUTO : jsonColumn.persistentType();
        if (isList(element)) {
            switch (persistentType) {
                case JSON:
                    return getTypeElement(jsonListTypeHandler);
                case BLOB:
                    return getTypeElement(jsonListBlobTypeHandler);
                default:
                    return getTypeElement(listTypeHandler);
            }
        } else if (isSet(element)) {
            switch (persistentType) {
                case JSON:
                    return getTypeElement(jsonSetTypeHandler);
                case BLOB:
                    return getTypeElement(jsonSetBlobTypeHandler);
                default:
                    return getTypeElement(setTypeHandler);
            }
        } else if (jsonColumn != null) {
            return getTypeElement(jsonObjectTypeHandler);
        }


        return null;
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
    public String formatPropertyColumn(@Nullable Property referenceProperty, @NonNull Property property) {
        if (referenceProperty == null) {
            return property.getColumn();
        } else {
            final String referenceColumn = referenceProperty.referenceColumn(property.getName()) != null ?
                    referenceProperty.referenceColumn(property.getName()) : property.getColumn();
            return property.isIdProperty() && referenceProperty.referenceMode() == ReferenceMode.SIMPLE ?
                    referenceProperty.getColumn() : referenceProperty.getColumn() + referenceColumn.substring(0, 1).toUpperCase() + referenceColumn.substring(1);
        }
    }


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
