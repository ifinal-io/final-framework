package com.ilikly.finalframework.data.mapping;

/**
 * @author likly
 * @version 1.0
 * @date 2019-02-13 20:10:33
 * @since 1.0
 */
public interface CompareProperty {

    static Builder builder(){
        return new BaseCompareProperty.BuilderImpl();
    }

    Property property();

    Object beforeValue();

    Object afterValue();

    boolean equals();

    interface Builder extends com.ilikly.finalframework.core.Builder<CompareProperty>{
        Builder property(Property property);

        Builder value(Object before,Object after);

    }
}
