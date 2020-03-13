package org.finalframework.coding.mapper.builder;

import org.finalframework.coding.entity.Entity;
import org.finalframework.coding.entity.Property;
import org.finalframework.coding.mapper.TypeHandlers;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * <pre>
 *     <code>
 *          <sql id="sql-table">
 *              {Entity.simpleName}
 *          </sql>
 *     </code>
 * </pre>
 * <a href="https://final.ilikly.com/mybatis/mapper/fragment/table">table</a>
 **/
final class SqlTableFragmentXmlMapperBuilder extends AbsSqlFragmentXmlMapperBuilder {
    public SqlTableFragmentXmlMapperBuilder(TypeHandlers typeHandlers) {
        super(typeHandlers);
    }

    @Override
    public String id() {
        return SQL_TABLE;
    }


    /**
     * <pre>
     *     <sql id="id">
     *      tableName
     *     </sql>
     * </pre>
     */
    @Override
    public Element buildSqlFragment(Document document, Entity<Property> entity) {
        final Element sql = sql(document, id());
        sql.appendChild(textNode(document, entity.getTable()));
        return sql;
    }
}
