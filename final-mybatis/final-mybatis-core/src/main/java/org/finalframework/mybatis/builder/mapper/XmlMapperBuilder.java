package org.finalframework.mybatis.builder.mapper;

import org.finalframework.data.mapping.Entity;
import org.springframework.lang.NonNull;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Collection;

/**
 * Mapper.xml 构建器
 *
 * @author likly
 * @version 1.0
 * @date 2019-03-09 19:32:52
 * @since 1.0
 */
public interface XmlMapperBuilder {

    /**
     * 构建Mapper对应的xml元素{@link Element}集合
     *
     * @param document xml 文档对象
     * @param mapper   mapper Class
     * @param entity   entity
     */
    Collection<Element> build(@NonNull Document document, @NonNull Class<?> mapper, @NonNull Entity<?> entity);

}