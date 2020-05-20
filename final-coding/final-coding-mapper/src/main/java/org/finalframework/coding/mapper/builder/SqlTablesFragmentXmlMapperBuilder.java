package org.finalframework.coding.mapper.builder;

import org.finalframework.coding.entity.Entity;
import org.finalframework.coding.entity.Property;
import org.finalframework.coding.mapper.TypeHandlers;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Optional;

/**
 * <pre>
 *     <code>
 *          <sql id="sql-table">
 *              <choose>
 *                  <when test="tableName != null">${tableName}</when>
 *              <otherwise>{Entity.simpleName}</otherwise>
 *              </choose>
 *          </sql>
 *     </code>
 * </pre>
 * <a href="https://final.ilikly.com/mybatis/mapper/fragment/table">table</a>
 **/
public final class SqlTablesFragmentXmlMapperBuilder extends AbsSqlFragmentXmlMapperBuilder {
    public SqlTablesFragmentXmlMapperBuilder(TypeHandlers typeHandlers) {
        super(typeHandlers);
    }

    @Override
    public String id() {
        return SQL_TABLES;
    }


    /**
     * <pre>
     *     <sql id="id">
     *         <choose>
     *             <when test="tableName != null">
     *                 ${tableName}
     *             </when>
     *             <otherwise>
     *                 tableName
     *             </otherwise>
     *         </choose>
     *     </sql>
     * </pre>
     *
     * @param document
     * @param entity
     * @return
     */
    @Override
    public Element buildSqlFragment(Document document, Entity entity) {
        //  <sql id="id">
        final Element sql = sql(document, id());
        //      <choose>
        final Element choose = document.createElement("choose");
        //              <when test="tableName != null">
        final Element whenTableNameNotNull = document.createElement("when");

        final String schema = Optional.ofNullable(entity.getSchema()).map(value -> value + ".").orElse("");

        whenTableNameNotNull.setAttribute("test", "table != null");
        //                  ${tableName}
        whenTableNameNotNull.appendChild(textNode(document, schema + "${table}"));
        //              </when>
        choose.appendChild(whenTableNameNotNull);
        //              <otherwise>
        final Element otherwise = document.createElement("otherwise");
        //                  tableName
        otherwise.appendChild(textNode(document, schema + entity.getTable()));
        //              </otherwise>
        choose.appendChild(otherwise);
        //      </choose>
        sql.appendChild(choose);
        return sql;
    }
}
