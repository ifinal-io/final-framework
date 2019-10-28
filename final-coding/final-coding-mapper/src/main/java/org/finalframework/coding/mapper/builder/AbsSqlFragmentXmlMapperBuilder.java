package org.finalframework.coding.mapper.builder;

import org.finalframework.coding.entity.Entity;
import org.finalframework.coding.entity.Property;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * @author likly
 * @version 1.0
 * @date 2019-10-14 16:48:46
 * @see SqlTableFragmentXmlMapperBuilder
 * @see SqlSelectColumnsFragmentXmlMapperBuilder
 * @see SqlWhereIdFragmentXmlMapperBuilder
 * @see SqlWhereIdsFragmentXmlMapperBuilder
 * @see SqlCriteriaFragmentXmlMapperBuilder
 * @see SqlOrderFragmentXmlMapperBuilder
 * @see SqlLimitFragmentXmlMapperBuilder
 * @see SqlQueryFragmentXmlMapperBuilder
 * @since 1.0
 */
public abstract class AbsSqlFragmentXmlMapperBuilder extends AbsXmlMapperBuilder implements SqlFragmentXmlMapperBuilder {
    @Override
    public final void build(Node root, Document document, Entity<Property> entity) {
        buildSqlFragmentStartComment(root, document, entity);
        root.appendChild(buildSqlFragment(document, entity));
        buildSqlFragmentEndComment(root, document, entity);
    }


    protected void buildSqlFragmentStartComment(Node root, Document document, Entity<Property> entity) {

    }

    protected void buildSqlFragmentEndComment(Node root, Document document, Entity<Property> entity) {

    }

    protected abstract Element buildSqlFragment(Document document, Entity<Property> entity);
}
