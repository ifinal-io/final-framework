package org.finalframework.dashboard.web.service.impl;

import org.finalframework.dashboard.web.entity.RequestHandler;
import org.finalframework.dashboard.web.entity.RequestPattern;
import org.finalframework.dashboard.web.service.RequestMappingService;
import org.finalframework.dashboard.web.service.query.RequestPatternQuery;
import org.finalframework.util.Asserts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;
import java.util.stream.Collectors;


/**
 * @author likly
 * @version 1.0
 * @date 2020-05-14 09:26:10
 * @since 1.0
 */
@Service
class RequestMappingServiceImpl implements RequestMappingService, InitializingBean {
    public static final Logger logger = LoggerFactory.getLogger(RequestMappingServiceImpl.class);
    private final ParameterNameDiscoverer discoverer = new DefaultParameterNameDiscoverer();
    /**
     * the key is {@link RequestMappingInfo#getPatternsCondition}
     */

    private final Map<String, RequestHandler> requestHandlers = new LinkedHashMap<>(256);
    private final List<RequestPattern> requestPatterns = new ArrayList<>();

    private final RequestMappingHandlerMapping requestMappingHandlerMapping;

    public RequestMappingServiceImpl(RequestMappingHandlerMapping requestMappingHandlerMapping) {
        this.requestMappingHandlerMapping = requestMappingHandlerMapping;
    }

    @Override
    public List<RequestPattern> query(RequestPatternQuery query) {
        return requestPatterns.stream().filter(requestPattern -> {
            return !Asserts.nonEmpty(query.getPattern()) || requestPattern.getPattern().toUpperCase().contains(query.getPattern().toUpperCase());
        }).collect(Collectors.toList());
    }

    @Override
    public RequestHandler find(String pattern) {
        return requestHandlers.get(pattern);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.requestMappingHandlerMapping.getHandlerMethods().keySet().forEach(it -> logger.info(it.toString()));

        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : this.requestMappingHandlerMapping.getHandlerMethods().entrySet()) {
            final RequestMappingInfo requestMappingInfo = entry.getKey();
            final HandlerMethod handlerMethod = entry.getValue();

            if (!handlerMethod.hasMethodAnnotation(ResponseBody.class) && !AnnotatedElementUtils.isAnnotated(handlerMethod.getMethod().getDeclaringClass(), ResponseBody.class)) {
                continue;
            }


            final String name = requestMappingInfo.getName();
            for (String pattern : requestMappingInfo.getPatternsCondition().getPatterns()) {
                final RequestHandler requestHandler = new RequestHandler();
                requestHandler.setName(name);
                requestHandler.setPattern(pattern);
                requestHandler.setMethods(requestMappingInfo.getMethodsCondition().getMethods());

                requestPatterns.add(new RequestPattern(name, requestMappingInfo.getMethodsCondition().getMethods(), pattern));
                requestHandlers.put(pattern, requestHandler);
//
//                final List<ResultMapping> parameterMappings = new ArrayList<>();
//                final MethodParameter[] methodParameters = handlerMethod.getMethodParameters();
//                for (MethodParameter methodParameter : methodParameters) {
//                    methodParameter.initParameterNameDiscovery(discoverer);
//
//                    final String parameterName = methodParameter.getParameterName();
//                    final Class<?> parameterType = methodParameter.getParameterType();
//                    final Type genericParameterType = methodParameter.getGenericParameterType();
//
//
//                    if (methodParameter.hasParameterAnnotation(RequestParam.class)) {
//                        final RequestParam requestParam = Objects.requireNonNull(methodParameter.getParameterAnnotation(RequestParam.class));
//                        final ResultMapping parameterMapping = new ResultMapping();
//                        parameterMapping.setName(parameterName);
//                        parameterMapping.setType(parameterType);
//                        parameterMapping.setGenericType(genericParameterType);
//                        parameterMapping.setRequired(requestParam.required());
//
//                        if (!ValueConstants.DEFAULT_NONE.equals(requestParam.defaultValue())) {
//                            if (parameterType.isEnum()) {
//                                Enum<?> value = Enum.valueOf((Class<? extends Enum>) parameterType, requestParam.defaultValue());
//                                parameterMapping.setValue(value);
//                            } else if (!"*".equals(requestParam.defaultValue())) {
//                                parameterMapping.setValue(Json.toObject(requestParam.defaultValue(), parameterType));
//                            } else {
//                                parameterMapping.setValue(requestParam.defaultValue());
//                            }
//                        }
//
//                        parameterMappings.add(parameterMapping);
//
//                    } else if (methodParameter.hasParameterAnnotation(PathVariable.class)) {
//                        final ResultMapping parameterMapping = new ResultMapping();
//                        parameterMapping.setName(parameterName);
//                        parameterMapping.setType(parameterType);
//                        parameterMapping.setGenericType(genericParameterType);
//                        parameterMapping.setRequired(Objects.requireNonNull(methodParameter.getParameterAnnotation(PathVariable.class)).required());
//                        parameterMappings.add(parameterMapping);
//
//                    } else if (Primaries.isPrimary(parameterType)) {
//                        final ResultMapping parameterMapping = new ResultMapping();
//                        parameterMapping.setName(parameterName);
//                        parameterMapping.setType(parameterType);
//                        parameterMapping.setGenericType(genericParameterType);
//                        parameterMappings.add(parameterMapping);
//
//                    } else if (parameterType.isEnum()) {
//                        final ResultMapping parameterMapping = new ResultMapping();
//                        parameterMapping.setName(parameterName);
//                        parameterMapping.setType(parameterType);
//                        parameterMapping.setGenericType(genericParameterType);
//                        parameterMapping.setOptions(Arrays.asList(parameterType.getEnumConstants()));
//                        parameterMappings.add(parameterMapping);
//                    } else if (!Collection.class.isAssignableFrom(parameterType)) {
//                        // maybe a java bean
//                        final Entity<?> entity = Entity.from(parameterType);
//                        for (Property property : entity) {
//                            final ResultMapping parameterMapping = new ResultMapping();
//                            parameterMapping.setName(property.getName());
//                            parameterMapping.setType(property.getType());
////                                parameterMapping.setGenericType(Optional.ofNullable(property.getField()).map(Field::getGenericType)
////                                        .orElse(property.getRequiredGetter().getGenericReturnType()));
//
//
//                            parameterMappings.add(parameterMapping);
//
//                        }
//
//
//                    }
//
//
//                }
//                requestHandler.setParameterMappings(parameterMappings);
//
//                final MethodParameter returnType = handlerMethod.getReturnType();
//                final Type genericParameterType = returnType.getGenericParameterType();
//                final Class<?> resultType = returnType.getParameterType();
//                final List<ResultMapping> resultMappings = new ArrayList<>();
//
//                if (Primaries.isPrimary(resultType)) {
//
//                } else if (!Collection.class.isAssignableFrom(resultType)) {
//                    // maybe a java bean
//                    final Entity<?> entity = Entity.from(resultType);
//                    for (Property property : entity) {
//                        final ResultMapping resultMapping = new ResultMapping();
//                        resultMapping.setName(property.getName());
//                        resultMapping.setType(property.getType());
////                            parameterMapping.setGenericType(Optional.ofNullable(property.getField()).map(Field::getGenericType)
////                                    .orElse(property.getRequiredGetter().getGenericReturnType()));
//
//                        parameterMappings.add(resultMapping);
//                    }
//                }
//
//                requestHandler.setResultMappings(resultMappings);


            }


        }


        Collections.sort(requestPatterns);
    }
}

