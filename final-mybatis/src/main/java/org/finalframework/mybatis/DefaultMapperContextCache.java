package org.finalframework.mybatis;

import org.apache.ibatis.parsing.XNode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author likly
 * @version 1.0
 * @date 2019-09-03 13:17:12
 * @since 1.0
 */
public class DefaultMapperContextCache implements MapperContextCache {

    public static final DefaultMapperContextCache INSTANCE = new DefaultMapperContextCache();

    private final Map<Class, XNode> cache = new HashMap<>();


    @Override
    public void put(Class mapper, XNode context) {
        cache.put(mapper, context);
    }

    @Override
    public XNode get(Class mapper) {
        return cache.get(mapper);
    }


}
