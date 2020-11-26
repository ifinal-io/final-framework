package org.ifinal.finalframework.mybatis;

import org.apache.ibatis.parsing.XNode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
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
