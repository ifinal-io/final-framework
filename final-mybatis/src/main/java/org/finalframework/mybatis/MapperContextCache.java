

package org.finalframework.mybatis;

import org.apache.ibatis.parsing.XNode;

/**
 * @author likly
 * @version 1.0
 * @date 2019-09-03 11:40:22
 * @since 1.0
 */
public interface MapperContextCache {

    void put(Class mapper, XNode context);

    XNode get(Class mapper);

}
