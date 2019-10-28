package org.finalframework.coding.generator;


import org.finalframework.core.Assert;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;

/**
 * @author likly
 * @version 1.0
 * @date 2019-10-26 23:45:10
 * @since 1.0
 */
public class DefaultPackageNameGenerator implements PackageNameGenerator {

    private final ProcessingEnvironment processEnv;
    private final String entityRoute;
    private final String targetRoute;

    public DefaultPackageNameGenerator(ProcessingEnvironment processEnv, String entityRoute, String targetRoute) {
        this.processEnv = processEnv;
        this.entityRoute = entityRoute;
        this.targetRoute = targetRoute;
    }

    @Override
    public String generate(TypeElement entity) {
        PackageElement packageElement = processEnv.getElementUtils().getPackageOf(entity);
        final String packageName = packageElement.getQualifiedName().toString();
        final String domain = calcEntityDomain(packageName);
        final String entityPath = String.format("%s.%s", domain, entityRoute);
        final String targetPath = String.format("%s.%s", domain, targetRoute);
        final String route = packageName.replace(entityPath, "");

        return Assert.isBlank(route) ? targetPath : targetPath + route;
    }

    private String calcEntityDomain(String packageName) {
        int index = packageName.indexOf(entityRoute);
        if (index != -1) {
            return packageName.substring(0, index - 1);
        }
        return null;
    }
}

