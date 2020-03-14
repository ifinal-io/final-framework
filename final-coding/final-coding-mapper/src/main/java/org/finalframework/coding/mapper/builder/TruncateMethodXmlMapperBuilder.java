package org.finalframework.coding.mapper.builder;


import org.finalframework.coding.entity.Entity;
import org.finalframework.coding.entity.Property;
import org.finalframework.coding.mapper.TypeHandlers;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.lang.model.element.ExecutableElement;

/**
 * @author likly
 * @version 1.0
 * @date 2019-11-18 22:31:42
 * @since 1.0
 */
public class TruncateMethodXmlMapperBuilder extends AbsMethodXmlMapperBuilder {

    public TruncateMethodXmlMapperBuilder(TypeHandlers typeHandlers) {
        super(typeHandlers);
    }

    @Override
    public boolean supports(ExecutableElement method) {
        return !method.isDefault() && "truncate".equals(method.getSimpleName().toString());
    }

    /**
     * <update id="truncate" >
     * <trim prefix="TRUNCATE TABLE">
     * <include refid="sql-table"/>
     * </trim>
     * </update>
     *
     * @param method
     * @param document
     * @param entity
     * @return
     */
    @Override
    protected Element buildMethodElement(ExecutableElement method, Document document, Entity entity) {
        Element truncate = document.createElement("update");
        truncate.setAttribute("id", method.getSimpleName().toString());

        Element trim = document.createElement("trim");
        trim.setAttribute("prefix", "TRUNCATE TABLE");
        trim.appendChild(include(document, SQL_TABLES));

        truncate.appendChild(trim);

        return truncate;
    }

}

