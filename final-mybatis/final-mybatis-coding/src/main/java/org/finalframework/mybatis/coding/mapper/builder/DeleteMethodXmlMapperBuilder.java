package org.finalframework.mybatis.coding.mapper.builder;

import org.finalframework.data.coding.entity.Entity;
import org.finalframework.data.coding.entity.Property;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.lang.model.element.ExecutableElement;

/**
 * @author likly
 * @version 1.0
 * @date 2019-10-12 10:59:52
 * @since 1.0
 */
public class DeleteMethodXmlMapperBuilder extends AbsXmlMapperBuilder implements MethodXmlMapperBuilder {
    @Override
    public boolean supports(ExecutableElement method) {
        return !method.isDefault() && "delete".equals(method.getSimpleName().toString());
    }

    @Override
    public Element build(ExecutableElement method, Document document, Entity<Property> entity) {
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
