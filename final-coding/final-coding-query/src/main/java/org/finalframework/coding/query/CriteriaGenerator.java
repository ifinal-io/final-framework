package org.finalframework.coding.query;

import org.finalframework.core.configuration.Configuration;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;

/**
 * @author likly
 * @version 1.0
 * @date 2019-11-21 18:05:44
 * @since 1.0
 */

public class CriteriaGenerator extends AbsEntityGenerator<Criteria> {

    private static final String QUERY_PACKAGE_PATH = "dao.query";

    public CriteriaGenerator(ProcessingEnvironment processEnv) {
        super(processEnv, Configuration.getInstance().getString("final.coding.query.path", QUERY_PACKAGE_PATH));
    }

    @Override
    protected Criteria buildEntityJavaSource(TypeElement typeElement, QEntity entity) {
        return Criteria.builder().packageName(entity.getPackageName())
                .simpleName(typeElement.getSimpleName() + "Criteria")
                .entity(entity)
                .build();
    }
}

