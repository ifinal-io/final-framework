package org.finalframework.spring.aop.interceptor;


import org.finalframework.core.Assert;
import org.finalframework.spring.aop.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-26 12:27:36
 * @since 1.0
 */
@SuppressWarnings("all")
public class BaseOperationAnnotationParser<O extends Operation> implements OperationAnnotationParser<O> {

    private final OperationConfiguration configuration;
    private final Map<Class<? extends Annotation>, OperationAnnotationFinder> annotationFinders = new ConcurrentHashMap<>(8);

    public BaseOperationAnnotationParser(OperationConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public Collection<O> parseOperationAnnotation(Class<?> type) {
        final Set<Class<? extends Annotation>> operationAnnotations = configuration.getOperationAnnotations();
        final Collection<Annotation> anns = new ArrayList<>();
        for (Class<? extends Annotation> annotation : operationAnnotations) {
            final OperationAnnotationFinder<? extends Annotation> finder = getOperationAnnotationFinder(annotation);
            final Collection<? extends Annotation> operationAnnotation = finder.findOperationAnnotation(type);
            if (operationAnnotation != null && !operationAnnotation.isEmpty()) {
                anns.addAll(operationAnnotation);
            }
        }

        if (Assert.isEmpty(anns)) {
            return null;
        }
        final Collection<O> ops = new ArrayList<>(1);

        operationAnnotations.forEach(an -> {
            final OperationAnnotationBuilder<Annotation, O> builder = configuration.getOperationAnnotationBuilder(an);
            anns.stream().filter(ann -> ann.annotationType() == an)
                    .forEach(ann -> ops.add(builder.build(type, ann)));
        });

        return ops;
    }

    @Override
    public Collection<O> parseOperationAnnotation(Method method) {
        final Collection<O> ops = new ArrayList<>(1);
        final Collection<O> methodOperationAnnotations = parseMethodOperationAnnotations(method);
        if (Assert.nonEmpty(methodOperationAnnotations)) {
            ops.addAll(methodOperationAnnotations);
        }
        final Collection<O> parameterOperationAnnotations = parseMethodParameterOperationAnnotations(method);
        if (Assert.nonEmpty(parameterOperationAnnotations)) {
            ops.addAll(parameterOperationAnnotations);
        }
        return ops;
    }

    @SuppressWarnings("unchecked")
    private <A extends Annotation> OperationAnnotationFinder<A> getOperationAnnotationFinder(Class<A> ann) {
        OperationAnnotationFinder finder = annotationFinders.get(ann);
        if (finder == null) {
            finder = new FinalOperationAnnotationFinder(ann);
            annotationFinders.put(ann, finder);
        }
        return finder;
    }


    private Collection<O> parseMethodOperationAnnotations(Method method) {
        final Set<Class<? extends Annotation>> operationAnnotations = configuration.getOperationAnnotations();
        final Collection<Annotation> anns = new ArrayList<>();
        for (Class<? extends Annotation> annotation : operationAnnotations) {
            final OperationAnnotationFinder<? extends Annotation> finder = getOperationAnnotationFinder(annotation);
            final Collection<? extends Annotation> operationAnnotation = finder.findOperationAnnotation(method);
            if (operationAnnotation != null && !operationAnnotation.isEmpty()) {
                anns.addAll(operationAnnotation);
            }
        }

        if (Assert.isEmpty(anns)) {
            return null;
        }
        final Collection<O> ops = new ArrayList<>(1);

        operationAnnotations.forEach(an -> {
            final OperationAnnotationBuilder<Annotation, O> builder = configuration.getOperationAnnotationBuilder(an);
            anns.stream().filter(ann -> ann.annotationType() == an)
                    .forEach(ann -> ops.add(builder.build(method, ann)));
        });

        return ops;
    }


    private Collection<O> parseMethodParameterOperationAnnotations(Method method) {
        final List<O> ops = new ArrayList<>();
        final Parameter[] parameters = method.getParameters();
        final Type[] genericParameterTypes = method.getGenericParameterTypes();
        for (int i = 0; i < parameters.length; i++) {
            final Collection<O> cacheOperations = parseCacheAnnotations(i, parameters[i], genericParameterTypes[i]);
            if (cacheOperations != null) {
                ops.addAll(cacheOperations);
            }
        }
        return ops;
    }


    private Collection<O> parseCacheAnnotations(Integer index, Parameter parameter, Type parameterType) {

        final Set<Class<? extends Annotation>> operationAnnotations = configuration.getOperationAnnotations();
        final Collection<Annotation> anns = new ArrayList<>();
        for (Class<? extends Annotation> annotation : operationAnnotations) {
            final OperationAnnotationFinder<? extends Annotation> finder = getOperationAnnotationFinder(annotation);
            final Collection<? extends Annotation> operationAnnotation = finder.findOperationAnnotation(parameter);
            if (operationAnnotation != null && !operationAnnotation.isEmpty()) {
                anns.addAll(operationAnnotation);
            }
        }

        if (Assert.isEmpty(anns)) {
            return null;
        }
        final Collection<O> ops = new ArrayList<>(1);

        operationAnnotations.forEach(an -> {
            final OperationAnnotationBuilder<Annotation, O> builder = configuration.getOperationAnnotationBuilder(an);
            anns.stream().filter(ann -> ann.annotationType() == an)
                    .forEach(ann -> ops.add(builder.build(index, parameter, parameterType, ann)));
        });

        return ops;

    }


}
