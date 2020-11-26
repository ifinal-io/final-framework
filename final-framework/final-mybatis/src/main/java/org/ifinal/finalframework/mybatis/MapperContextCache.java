package org.ifinal.finalframework.mybatis;

import org.apache.ibatis.parsing.XNode;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface MapperContextCache {

    void put(Class mapper, XNode context);

    XNode get(Class mapper);

}
