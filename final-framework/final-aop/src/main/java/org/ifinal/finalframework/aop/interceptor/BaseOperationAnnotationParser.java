package org.ifinal.finalframework.aop.interceptor;


import org.ifinal.finalframework.aop.OperationAnnotationFinder;
import org.ifinal.finalframework.aop.OperationAnnotationParser;
import org.ifinal.finalframework.aop.OperationConfiguration;
import org.ifinal.finalframework.util.Asserts;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class BaseOperationAnnotationParser implements OperationAnnotationParser {
    private final Set<Class<? extends Annotation>> annTypes = new HashSet<>();
    private final OperationConfiguration configuration;


    public BaseOperationAnnotationParser(Collection<Class<? extends Annotation>> annTypes, OperationConfiguration configuration) {
        this.annTypes.addAll(annTypes);
        this.configuration = configuration;
    }

    @Override
    public Collection<AnnotationAttributes> parseOperationAnnotation(Class<?> type) {

        final Collection<Annotation> anns = new ArrayList<>();
        for (Class<? extends Annotation> annotation : annTypes) {
            final OperationAnnotationFinder<? extends Annotation> finder = configuration.getOperationAnnotationFinder(annotation);
            final Collection<? extends Annotation> operationAnnotation = finder.findOperationAnnotation(type);
            if (operationAnnotation != null && !operationAnnotation.isEmpty()) {
                anns.addAll(operationAnnotation);
            }
        }

        if (Asserts.isEmpty(anns)) {
            return Collections.emptyList();
        }


        return anns.stream()
                .map(annotation -> {
                    AnnotationAttributes annotationAttributes = AnnotationUtils.getAnnotationAttributes(type, annotation);
                    annotationAttributes.put("class", type);
                    return annotationAttributes;
                }).collect(Collectors.toList());


    }

    @Override
    public Collection<AnnotationAttributes> parseOperationAnnotation(Method method) {
        final Collection<AnnotationAttributes> ops = new ArrayList<>(1);
        final Collection<AnnotationAttributes> methodOperationAnnotations = parseMethodOperationAnnotations(method);
        if (Asserts.nonEmpty(methodOperationAnnotations)) {
            ops.addAll(methodOperationAnnotations);
        }
        final Collection<AnnotationAttributes> parameterOperationAnnotations = parseMethodParameterOperationAnnotations(method);
        if (Asserts.nonEmpty(parameterOperationAnnotations)) {
            ops.addAll(parameterOperationAnnotations);
        }
        return ops;
    }

    private Collection<AnnotationAttributes> parseMethodOperationAnnotations(Method method) {
        final Collection<Annotation> anns = new ArrayList<>();
        for (Class<? extends Annotation> annotation : annTypes) {
            final OperationAnnotationFinder<? extends Annotation> finder = configuration.getOperationAnnotationFinder(annotation);
            final Collection<? extends Annotation> operationAnnotation = finder.findOperationAnnotation(method);
            if (operationAnnotation != null && !operationAnnotation.isEmpty()) {
                anns.addAll(operationAnnotation);
            }
        }

        if (Asserts.isEmpty(anns)) {
            return Collections.emptyList();
        }


        return anns.stream()
                .map(annotation -> {
                    AnnotationAttributes annotationAttributes = AnnotationUtils.getAnnotationAttributes(method, annotation);
                    annotationAttributes.put("method", method);
                    return annotationAttributes;
                }).collect(Collectors.toList());
    }


    private Collection<AnnotationAttributes> parseMethodParameterOperationAnnotations(Method method) {
        final List<AnnotationAttributes> ops = new ArrayList<>();
        final Parameter[] parameters = method.getParameters();
        final Type[] genericParameterTypes = method.getGenericParameterTypes();
        for (int i = 0; i < parameters.length; i++) {
            final Collection<AnnotationAttributes> cacheOperations = parseCacheAnnotations(i, parameters[i], genericParameterTypes[i]);
            ops.addAll(cacheOperations);
        }
        return ops;
    }


    private Collection<AnnotationAttributes> parseCacheAnnotations(Integer index, Parameter parameter, Type parameterType) {
        final Collection<Annotation> anns = new ArrayList<>();
        for (Class<? extends Annotation> annotation : annTypes) {
            final OperationAnnotationFinder<? extends Annotation> finder = configuration.getOperationAnnotationFinder(annotation);
            final Collection<? extends Annotation> operationAnnotation = finder.findOperationAnnotation(parameter);
            if (operationAnnotation != null && !operationAnnotation.isEmpty()) {
                anns.addAll(operationAnnotation);
            }
        }

        if (Asserts.isEmpty(anns)) {
            return Collections.emptyList();
        }


        return anns.stream()
                .map(annotation -> {
                    AnnotationAttributes annotationAttributes = AnnotationUtils.getAnnotationAttributes(parameter, annotation);
                    annotationAttributes.put("parameter", parameter);
                    annotationAttributes.put("parameterIndex", index);
                    annotationAttributes.put("parameterType", parameterType);
                    return annotationAttributes;
                }).collect(Collectors.toList());


    }


}
