/*
 * Copyright (c) 2018-2020.  the original author or authors.
 *  <p>
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p>
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  <p>
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

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

import org.finalframework.coding.entity.Entity;
import org.finalframework.coding.entity.Property;
import org.finalframework.coding.mapper.handler.TypeHandlerRegistry;
import org.finalframework.coding.utils.TypeElements;
import org.finalframework.core.Assert;
import org.finalframework.data.annotation.*;
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
    public String formatPropertyWriteColumn(@NonNull Entity entity, @Nullable Property referenceProperty, @NonNull Property property) {
        return formatPropertyColumn(entity, referenceProperty, property);
    }

    @NonNull
    public String formatPropertyColumn(@NonNull Entity entity, @Nullable Property referenceProperty, @NonNull Property property) {
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

        column = NameConverterRegistry.getInstance().getColumnNameConverter().convert(column);
        if (Optional.ofNullable(referenceProperty).orElse(property).hasAnnotation(UpperCase.class) || entity.hasAnnotation(UpperCase.class)) {
            column = column.toUpperCase();
        }
        return column;
    }

    @NonNull
    public String formatPropertyReadColumn(@NonNull Entity entity, @Nullable Property referenceProperty, @NonNull Property property) {
        String column = formatPropertyColumn(entity, referenceProperty, property);
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


    public String formatPropertyValues(@Nullable Property property, @NonNull Property referenceProperty, String value) {
        final TypeElement javaType = referenceProperty.getJavaTypeElement();
        final TypeElement typeHandler = Optional.ofNullable(referenceProperty.getTypeHandler()).orElse(getTypeHandler(referenceProperty));
        final StringBuilder builder = new StringBuilder();

        builder.append(referenceProperty.placeholder() ? "#{" : "${");//.append(value);

        if (Assert.nonNull(property)) {
            // entity.path != null ? entity.path.path : null
//            builder.append(value).append(".").append(property.getName()).append(" != null ? ")
//                    .append(value).append(".").append(property.getName()).append(".").append(referenceProperty.getName())
//                    .append(" : null");

            builder.append(value).append(".").append(property.getName()).append(".").append(referenceProperty.getName());
        } else {
            builder.append(value).append(".").append(referenceProperty.getName());
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
