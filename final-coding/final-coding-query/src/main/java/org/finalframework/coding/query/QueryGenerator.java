package org.finalframework.coding.query;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;

/**
 * @author likly
 * @version 1.0
 * @date 2019-11-21 18:05:44
 * @since 1.0
 */

public class QueryGenerator extends AbsEntityGenerator<Query> {

    private static final String QUERY_PACKAGE_PATH = "query";

    public QueryGenerator(ProcessingEnvironment processEnv) {
        super(processEnv, QUERY_PACKAGE_PATH);
    }

    @Override
    protected Query buildEntityJavaSource(TypeElement typeElement, QEntity entity) {
        return Query.builder().packageName(entity.getPackageName())
                .simpleName(typeElement.getSimpleName() + "Query")
                .criteria(typeElement.getSimpleName() + "Criteria")
                .entity(entity)
                .build();
    }
}
