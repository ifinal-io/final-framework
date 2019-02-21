package com.ilikly.finalframework.mybatis.agent;

import com.ilikly.finalframework.data.mapping.Dialect;
import com.ilikly.finalframework.data.mapping.DialectFactory;
import com.ilikly.finalframework.data.mapping.Entity;
import com.ilikly.finalframework.data.repository.Repository;
import com.ilikly.finalframework.mybatis.EntityHolderCache;
import com.ilikly.finalframework.mybatis.builder.DefaultXMLMapperBuilder;
import org.apache.ibatis.builder.BuilderException;
import org.apache.ibatis.parsing.XNode;

/**
 * @author likly
 * @version 1.0
 * @date 2018-12-21 23:40:31
 * @see org.apache.ibatis.builder.xml.XMLMapperBuilder
 * @since 1.0
 */
@SuppressWarnings("unused")
public class XMLMapperBuilderAgent {

    private static final EntityHolderCache cache = new EntityHolderCache();
    @SuppressWarnings("unused")
    public static void configurationDefaultElement(XNode context) {
        String namespace = context.getStringAttribute("namespace");
        if (namespace == null || namespace.equals("")) {
            throw new BuilderException("Mapper's namespace cannot be empty");
        }
        try {
            Class mapperClass = Class.forName(namespace);
            if (Repository.class.isAssignableFrom(mapperClass)) {
                Dialect dialect = DialectFactory.getDialect(mapperClass);
                Entity entity = cache.get(mapperClass);
                final DefaultXMLMapperBuilder builder = new DefaultXMLMapperBuilder(dialect, context,mapperClass,entity);
            }

        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Mapper cannot be found:mapper=" + namespace);
        }
    }


}
