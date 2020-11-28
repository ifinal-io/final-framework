package org.ifinal.finalframework.aop.interceptor;


import org.ifinal.finalframework.aop.*;
import org.ifinal.finalframework.util.Asserts;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.*;

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
    public Collection<Operation> parseOperationAnnotation(Class<?> type) {

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
        final Collection<Operation> ops = new ArrayList<>(1);

        annTypes.forEach(an -> {
            final OperationAnnotationBuilder<Annotation, Operation> builder = configuration.getOperationAnnotationBuilder(an);
            anns.stream().filter(ann -> ann.annotationType() == an)
                    .map(ann -> builder.build(type, ann))
                    .forEach(ops::add);


        });

        return ops;


    }

    @Override
    public Collection<Operation> parseOperationAnnotation(Method method) {
        final Collection<Operation> ops = new ArrayList<>(1);
        final Collection<Operation> methodOperationAnnotations = parseMethodOperationAnnotations(method);
        if (Asserts.nonEmpty(methodOperationAnnotations)) {
            ops.addAll(methodOperationAnnotations);
        }
        final Collection<Operation> parameterOperationAnnotations = parseMethodParameterOperationAnnotations(method);
        if (Asserts.nonEmpty(parameterOperationAnnotations)) {
            ops.addAll(parameterOperationAnnotations);
        }
        return ops;
    }

    private Collection<Operation> parseMethodOperationAnnotations(Method method) {
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
        final Collection<Operation> ops = new ArrayList<>(1);

        annTypes.forEach(an -> {
            final OperationAnnotationBuilder<Annotation, Operation> builder = configuration.getOperationAnnotationBuilder(an);
            anns.stream().filter(ann -> ann.annotationType() == an)
                    .map(ann -> builder.build(method, ann))
                    .forEach(ops::add);
        });

        return ops;
    }


    private Collection<Operation> parseMethodParameterOperationAnnotations(Method method) {
        final List<Operation> ops = new ArrayList<>();
        final Parameter[] parameters = method.getParameters();
        final Type[] genericParameterTypes = method.getGenericParameterTypes();
        for (int i = 0; i < parameters.length; i++) {
            final Collection<Operation> cacheOperations = parseCacheAnnotations(i, parameters[i], genericParameterTypes[i]);
            ops.addAll(cacheOperations);
        }
        return ops;
    }


    private Collection<Operation> parseCacheAnnotations(Integer index, Parameter parameter, Type parameterType) {
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
        final Collection<Operation> ops = new ArrayList<>(1);

        annTypes.forEach(an -> {
            final OperationAnnotationBuilder<Annotation, Operation> builder = configuration.getOperationAnnotationBuilder(an);
            anns.stream().filter(ann -> ann.annotationType() == an)
                    .map(ann -> builder.build(index, parameter, parameterType, ann))
                    .forEach(ops::add);
        });

        return ops;

    }


}
