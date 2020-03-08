package org.finalframework.coding.query;

import org.finalframework.core.configuration.Configuration;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;

/**
 * @author likly
 * @version 1.0
 * @date 2019-10-27 00:15:44
 * @since 1.0
 */

public class QEntityGenerator extends AbsEntityGenerator<QEntity> {

    private static final String QUERY_PACKAGE_PATH = "dao.query";

    public QEntityGenerator(ProcessingEnvironment processEnv) {
        super(processEnv, Configuration.getInstance().getString("final.coding.dao.mapper", QUERY_PACKAGE_PATH));
    }

    @Override
    protected QEntity buildEntityJavaSource(TypeElement typeElement, QEntity entity) {
        return entity;
    }
}

