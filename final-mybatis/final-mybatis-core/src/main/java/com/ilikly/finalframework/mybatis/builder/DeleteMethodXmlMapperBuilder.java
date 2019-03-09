package com.ilikly.finalframework.mybatis.builder;


import com.ilikly.finalframework.data.mapping.Entity;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.lang.reflect.Method;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-09 17:46:25
 * @since 1.0
 */
public class DeleteMethodXmlMapperBuilder extends AbsMethodXmlMapperBuilder implements MethodXmlMapperBuilder {

    @Override
    public boolean supports(Method method) {
        return !method.isDefault() && method.getName().equals("delete");
    }

    @Override
    public Element build(Method method, Document document, Entity<?> entity) {
        /**
         *     <delete id="delete">
         *         DELETE FROM
         *         <include refid="sql-table"/>
         *         <where>
         *             <choose>
         *                 <when test="ids != null">
         *                     id IN
         *                     <foreach collection="ids" item="id" open="(" separator="," close=")">
         *                         #{id}
         *                     </foreach>
         *                 </when>
         *                 <when test="query != null">
         *                     #{query.sql}
         *                 </when>
         *             </choose>
         *         </where>
         *     </delete>
         */
        final Element delete = document.createElement("delete");
        delete.setAttribute("id", "delete");
        delete.appendChild(textNode(document, "\n\t\tDELETE FROM\n"));
        delete.appendChild(include(document, SQL_TABLE));
        delete.appendChild(where(document, whenIdsNotNull(document, entity), whenQueryNotNull(document)));
        delete.appendChild(include(document, SQL_ORDER));
        delete.appendChild(include(document, SQL_LIMIT));
        return delete;
    }
}
