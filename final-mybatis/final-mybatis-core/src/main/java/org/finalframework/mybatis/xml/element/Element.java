package org.finalframework.mybatis.xml.element;

/**
 * 描述Mapper.xml文件中元素
 *
 * @author likly
 * @version 1.0
 * @date 2019-02-22 15:11:58
 * @since 1.0
 */
public interface Element {

    String name();

    ElementType type();

    enum ElementType {
        RESULT_MAP,
        ID_RESULT,
        RESULT,
        ASSOCIATION
    }
}
