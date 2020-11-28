package org.ifinal.finalframework.retrofit;


import lombok.Setter;
import org.ifinal.finalframework.retrofit.annotation.Retrofit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.lang.NonNull;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Set;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class ClassPathRetrofitScanner extends ClassPathBeanDefinitionScanner {
    private static final Logger logger = LoggerFactory.getLogger(ClassPathRetrofitScanner.class);

    private static final String DEFAULT_RETROFIT_BEAN_NAME = "retrofit";

    @Setter
    private Class<? extends Annotation> annotationClass;
    @Setter
    private Class<?> markerInterface;
    @Setter
    private RetrofitFactoryBean<?> retrofitFactoryBean = new RetrofitFactoryBean<>();

    public ClassPathRetrofitScanner(BeanDefinitionRegistry registry) {
        super(registry, false);
    }

    @Override
    @NonNull
    public Set<BeanDefinitionHolder> doScan(@NonNull String... basePackages) {
        registerFilters();
        Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);

        if (beanDefinitions.isEmpty()) {
            logger.warn("No Retrofit service was found in '" + Arrays.toString(basePackages) + "' package. Please check your configuration.");
        } else {
            processBeanDefinitions(beanDefinitions);
        }

        return beanDefinitions;
    }

    private void processBeanDefinitions(Set<BeanDefinitionHolder> beanDefinitions) {
        GenericBeanDefinition definition;
        for (BeanDefinitionHolder holder : beanDefinitions) {

            try {
                definition = (GenericBeanDefinition) holder.getBeanDefinition();

                if (logger.isDebugEnabled()) {
                    logger.debug("Creating RetrofitFactoryBean with name '" + holder.getBeanName()
                            + "' and '" + definition.getBeanClassName() + "' retrofitInterface");
                }

                final Class<?> retrofitService = Class.forName(definition.getBeanClassName());
                final Retrofit retrofit = retrofitService.getAnnotation(Retrofit.class);
                final String retrofitBeanName = StringUtils.hasText(retrofit.value()) ? retrofit.value() : DEFAULT_RETROFIT_BEAN_NAME;

                // the mapper interface is the original class of the bean
                // but, the actual class of the bean is MapperFactoryBean
                definition.getConstructorArgumentValues().addGenericArgumentValue(definition.getBeanClassName()); // issue #59
                definition.setBeanClass(this.retrofitFactoryBean.getClass());

                logger.debug("register retrofit: {}", definition.getBeanClassName());

                definition.getPropertyValues().add("retrofit", new RuntimeBeanReference(retrofitBeanName));

                if (logger.isDebugEnabled()) {
                    logger.debug("Enabling autowire by type for RetrofitFactoryBean with name '" + holder.getBeanName() + "'.");
                }
                definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
            } catch (Exception e) {

            }

        }

    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent();
    }

    private void registerFilters() {
        boolean acceptAllInterfaces = true;

        // if specified, use the given annotation and / or marker interface
        if (this.annotationClass != null) {
            addIncludeFilter(new AnnotationTypeFilter(this.annotationClass));
            acceptAllInterfaces = false;
        }

        // override AssignableTypeFilter to ignore matches on the actual marker interface
        if (this.markerInterface != null) {
            addIncludeFilter(new AssignableTypeFilter(this.markerInterface) {
                @Override
                protected boolean matchClassName(String className) {
                    return false;
                }
            });
            acceptAllInterfaces = false;
        }

        if (acceptAllInterfaces) {
            // default include filter that accepts all classes
            addIncludeFilter((metadataReader, metadataReaderFactory) -> true);
        }

        // exclude package-info.java
        addExcludeFilter((metadataReader, metadataReaderFactory) -> {
            String className = metadataReader.getClassMetadata().getClassName();
            return className.endsWith("package-info");
        });
    }

}

