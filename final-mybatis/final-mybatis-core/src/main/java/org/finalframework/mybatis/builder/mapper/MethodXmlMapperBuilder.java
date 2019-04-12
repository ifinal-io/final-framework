package org.finalframework.mybatis.builder.mapper;

import org.finalframework.data.mapping.Entity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.lang.reflect.Method;
import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2019-02-12 20:40:24
 * @since 1.0
 */
public interface MethodXmlMapperBuilder {

    boolean supports(@NonNull Method method);

    @NonNull
    Element build(@NonNull Method method, Document document, Entity<?> entity);

    @Nullable
    default Collection<Element> sqlFragments(@NonNull Method method, @NonNull Document document, @NonNull Entity<?> entity) {
        return null;
    }

}
