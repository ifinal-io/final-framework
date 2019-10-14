package org.finalframework.mybatis.coding.mapper.builder;

import org.finalframework.data.coding.entity.Entity;
import org.finalframework.data.coding.entity.Property;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

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
 **/
final class SqlTableFragmentXmlMapperBuilder extends AbsSqlFragmentXmlMapperBuilder {
    @Override
    public String id() {
        return SQL_TABLE;
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
    public Element buildSqlFragment(Document document, Entity<Property> entity) {
        //  <sql id="id">
        final Element sql = document.createElement("sql");
        sql.setAttribute("id", id());
        //      <choose>
        final Element choose = document.createElement("choose");
        //              <when test="tableName != null">
        final Element whenTableNameNotNull = document.createElement("when");
        whenTableNameNotNull.setAttribute("test", "tableName != null");
        //                  ${tableName}
        whenTableNameNotNull.appendChild(textNode(document, "${tableName}"));
        //              </when>
        choose.appendChild(whenTableNameNotNull);
        //              <otherwise>
        final Element otherwise = document.createElement("otherwise");
        //                  tableName
        otherwise.appendChild(textNode(document, entity.getTable()));
        //              </otherwise>
        choose.appendChild(otherwise);
        //      </choose>
        sql.appendChild(choose);
        return sql;
    }
}
