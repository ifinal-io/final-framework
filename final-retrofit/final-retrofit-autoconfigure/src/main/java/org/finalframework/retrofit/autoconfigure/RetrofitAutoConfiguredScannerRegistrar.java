package org.finalframework.retrofit.autoconfigure;


import org.finalframework.retrofit.ClassPathRetrofitScanner;
import org.finalframework.retrofit.annotation.Retrofit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2020-04-25 17:20:31
 * @since 1.0
 */

public class RetrofitAutoConfiguredScannerRegistrar implements BeanFactoryAware, ImportBeanDefinitionRegistrar, ResourceLoaderAware {
    private static final Logger logger = LoggerFactory.getLogger(RetrofitAutoConfiguredScannerRegistrar.class);
    private BeanFactory beanFactory;
    private ResourceLoader resourceLoader;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        logger.debug("Searching for retrofits annotated with @Retrofit");
        final ClassPathRetrofitScanner scanner = new ClassPathRetrofitScanner(registry);

        try {
            if (this.resourceLoader != null) {
                scanner.setResourceLoader(this.resourceLoader);
            }

            List<String> packages = AutoConfigurationPackages.get(this.beanFactory);
            if (logger.isDebugEnabled()) {
                for (String pkg : packages) {
                    logger.debug("Using auto-configuration base package '{}'", pkg);
                }
            }
            scanner.setAnnotationClass(Retrofit.class);
            scanner.doScan(StringUtils.toStringArray(packages));
        } catch (IllegalStateException ex) {
            logger.debug("Could not determine auto-configuration package, automatic retrofit scanning disabled.", ex);
        }
    }
}

