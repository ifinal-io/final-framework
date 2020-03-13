package org.finalframework.coding.mapper.xml;

import org.finalframework.coding.entity.Property;
import org.finalframework.coding.mapper.TypeHandlers;
import org.finalframework.data.annotation.PrimaryKey;

import javax.lang.model.element.TypeElement;

/**
 * @author likly
 * @version 1.0
 * @date 2019-10-10 19:10:44
 * @since 1.0
 */
public class Result implements Element {

    private final String property;
    private final String column;
    private final TypeElement javaType;
    private final TypeElement typeHandler;
    private final boolean idResult;

    private Result(Builder builder) {
        this.property = builder.property;
        this.column = builder.column;
        this.javaType = builder.javaType;
        this.typeHandler = builder.typeHandler;
        this.idResult = builder.idResult;
    }

    public static Result from(Property referenceProperty, Property property, TypeHandlers typeHandlers) {
        return new Builder(property.getName(),
                typeHandlers.formatPropertyWriteColumn(referenceProperty, property))
                .javaType(property.getMetaTypeElement())
                .typeHandler(typeHandlers.getTypeHandler(property))
                .idResult(property.hasAnnotation(PrimaryKey.class))
                .build();
    }


    @Override
    public final ElementType type() {
        return idResult ? ElementType.ID_RESULT : ElementType.RESULT;
    }

    public String getProperty() {
        return property;
    }

    public String getColumn() {
        return column;
    }

    public TypeElement getJavaType() {
        return javaType;
    }

    public TypeElement getTypeHandler() {
        return typeHandler;
    }

    public boolean isIdResult() {
        return idResult;
    }

    public static class Builder implements org.finalframework.core.Builder<Result> {
        private final String property;
        private final String column;
        private TypeElement javaType;
        private TypeElement typeHandler;
        private boolean idResult = false;

        public Builder(String property, String column) {
            this.property = property;
            this.column = column;
        }

        public Builder javaType(TypeElement javaType) {
            this.javaType = javaType;
            return this;
        }

        public Builder typeHandler(TypeElement typeHandler) {
            this.typeHandler = typeHandler;
            return this;
        }

        public Builder idResult(boolean idResult) {
            this.idResult = idResult;
            return this;
        }


        @Override
        public Result build() {
            return new Result(this);
        }
    }
}
