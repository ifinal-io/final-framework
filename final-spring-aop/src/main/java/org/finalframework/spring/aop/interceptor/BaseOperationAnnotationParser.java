

package org.finalframework.spring.aop.interceptor;


import org.finalframework.core.Asserts;
import org.finalframework.spring.aop.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.*;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-26 12:27:36
 * @since 1.0
 */
@SuppressWarnings("all")
public class BaseOperationAnnotationParser implements OperationAnnotationParser {
    private final Set<Class<? extends Annotation>> annoTypes = new HashSet<>();
    private final OperationConfiguration configuration;


    public BaseOperationAnnotationParser(Collection<Class<? extends Annotation>> annoTypes, OperationConfiguration configuration) {
        this.annoTypes.addAll(annoTypes);
        this.configuration = configuration;
    }

    @Override
    public Collection<? extends Operation> parseOperationAnnotation(Class<?> type) {

        final Collection<Annotation> anns = new ArrayList<>();
        for (Class<? extends Annotation> annotation : annoTypes) {
            final OperationAnnotationFinder<? extends Annotation> finder = configuration.getOperationAnnotationFinder(annotation);
            final Collection<? extends Annotation> operationAnnotation = finder.findOperationAnnotation(type);
            if (operationAnnotation != null && !operationAnnotation.isEmpty()) {
                anns.addAll(operationAnnotation);
            }
        }

        if (Asserts.isEmpty(anns)) {
            return null;
        }
        final Collection<Operation> ops = new ArrayList<>(1);

        annoTypes.forEach(an -> {
            final OperationAnnotationBuilder<Annotation, Operation> builder = configuration.getOperationAnnotationBuilder(an);
            anns.stream().filter(ann -> ann.annotationType() == an)
                    .map(ann -> builder.build(type, ann))
                    .filter(Objects::nonNull)
                    .forEach(ops::add);


        });

        return ops;


    }

    @Override
    public Collection<? extends Operation> parseOperationAnnotation(Method method) {
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
        for (Class<? extends Annotation> annotation : annoTypes) {
            final OperationAnnotationFinder<? extends Annotation> finder = configuration.getOperationAnnotationFinder(annotation);
            final Collection<? extends Annotation> operationAnnotation = finder.findOperationAnnotation(method);
            if (operationAnnotation != null && !operationAnnotation.isEmpty()) {
                anns.addAll(operationAnnotation);
            }
        }

        if (Asserts.isEmpty(anns)) {
            return null;
        }
        final Collection<Operation> ops = new ArrayList<>(1);

        annoTypes.forEach(an -> {
            final OperationAnnotationBuilder<Annotation, Operation> builder = configuration.getOperationAnnotationBuilder(an);
            anns.stream().filter(ann -> ann.annotationType() == an)
                    .map(ann -> builder.build(method, ann))
                    .filter(Objects::nonNull)
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
            if (cacheOperations != null) {
                ops.addAll(cacheOperations);
            }
        }
        return ops;
    }


    private Collection<Operation> parseCacheAnnotations(Integer index, Parameter parameter, Type parameterType) {
        final Collection<Annotation> anns = new ArrayList<>();
        for (Class<? extends Annotation> annotation : annoTypes) {
            final OperationAnnotationFinder<? extends Annotation> finder = configuration.getOperationAnnotationFinder(annotation);
            final Collection<? extends Annotation> operationAnnotation = finder.findOperationAnnotation(parameter);
            if (operationAnnotation != null && !operationAnnotation.isEmpty()) {
                anns.addAll(operationAnnotation);
            }
        }

        if (Asserts.isEmpty(anns)) {
            return null;
        }
        final Collection<Operation> ops = new ArrayList<>(1);

        annoTypes.forEach(an -> {
            final OperationAnnotationBuilder<Annotation, Operation> builder = configuration.getOperationAnnotationBuilder(an);
            anns.stream().filter(ann -> ann.annotationType() == an)
                    .map(ann -> builder.build(index, parameter, parameterType, ann))
                    .forEach(ops::add);
        });

        return ops;

    }


}
