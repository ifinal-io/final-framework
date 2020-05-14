package org.finalframework.document.api.service.impl;

import org.finalframework.core.PrimaryTypes;
import org.finalframework.data.mapping.Entity;
import org.finalframework.data.mapping.Property;
import org.finalframework.document.api.entity.ResultMapping;
import org.finalframework.document.api.entity.RequestMapping;
import org.finalframework.document.api.service.RequestMappingService;
import org.finalframework.document.api.service.query.RequestMappingQuery;
import org.finalframework.util.LinkedMultiKeyMap;
import org.finalframework.util.MultiKeyMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.MethodParameter;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.*;


/**
 * @author likly
 * @version 1.0
 * @date 2020-05-14 09:26:10
 * @since 1.0
 */
@Service
public class RequestMappingServiceImpl implements RequestMappingService {
    public static final Logger logger = LoggerFactory.getLogger(RequestMappingServiceImpl.class);
    private final ParameterNameDiscoverer discoverer = new DefaultParameterNameDiscoverer();
    /**
     * the key is {@link RequestMappingInfo#getPatternsCondition}
     */
    private final MultiKeyMap<String, RequestMethod, RequestMapping> requestMappingMap = new LinkedMultiKeyMap<>();
    private final List<RequestMapping> requestMappings = new ArrayList<>();

    private final RequestMappingHandlerMapping requestMappingHandlerMapping;

    public RequestMappingServiceImpl(RequestMappingHandlerMapping requestMappingHandlerMapping) {
        this.requestMappingHandlerMapping = requestMappingHandlerMapping;
        this.init();

        Collections.sort(requestMappings);
    }


    private void init() {
        this.requestMappingHandlerMapping.getHandlerMethods().keySet().forEach(it -> logger.info(it.toString()));

        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : this.requestMappingHandlerMapping.getHandlerMethods().entrySet()) {
            final RequestMappingInfo requestMappingInfo = entry.getKey();
            final HandlerMethod handlerMethod = entry.getValue();
            for (String pattern : requestMappingInfo.getPatternsCondition().getPatterns()) {
                for (RequestMethod method : requestMappingInfo.getMethodsCondition().getMethods()) {
                    final RequestMapping requestMapping = new RequestMapping();

                    requestMapping.setName(requestMappingInfo.getName());
                    requestMapping.setPattern(pattern);
                    requestMapping.setMethod(method);

                    final List<ResultMapping> parameterMappings = new ArrayList<>();
                    final MethodParameter[] methodParameters = handlerMethod.getMethodParameters();
                    for (MethodParameter methodParameter : methodParameters) {
                        methodParameter.initParameterNameDiscovery(discoverer);

                        final String parameterName = methodParameter.getParameterName();
                        final Class<?> parameterType = methodParameter.getParameterType();
                        final Type genericParameterType = methodParameter.getGenericParameterType();


                        if (methodParameter.hasParameterAnnotation(RequestParam.class)) {
                            final ResultMapping resultMapping = new ResultMapping();
                            resultMapping.setName(parameterName);
                            resultMapping.setType(parameterType);
                            resultMapping.setGenericType(genericParameterType);
                            resultMapping.setRequired(Objects.requireNonNull(methodParameter.getParameterAnnotation(RequestParam.class)).required());
                            parameterMappings.add(resultMapping);

                        } else if (methodParameter.hasParameterAnnotation(PathVariable.class)) {
                            final ResultMapping resultMapping = new ResultMapping();
                            resultMapping.setName(parameterName);
                            resultMapping.setType(parameterType);
                            resultMapping.setGenericType(genericParameterType);
                            resultMapping.setRequired(Objects.requireNonNull(methodParameter.getParameterAnnotation(PathVariable.class)).required());
                            parameterMappings.add(resultMapping);

                        } else if (PrimaryTypes.isPrimary(parameterType)) {
                            final ResultMapping resultMapping = new ResultMapping();
                            resultMapping.setName(parameterName);
                            resultMapping.setType(parameterType);
                            resultMapping.setGenericType(genericParameterType);
                            parameterMappings.add(resultMapping);

                        } else if (parameterType.isEnum()) {
                            final ResultMapping resultMapping = new ResultMapping();
                            resultMapping.setName(parameterName);
                            resultMapping.setType(parameterType);
                            resultMapping.setGenericType(genericParameterType);
                            resultMapping.setOptions(Arrays.asList(parameterType.getEnumConstants()));
                            parameterMappings.add(resultMapping);
                        } else if (!Collection.class.isAssignableFrom(parameterType)) {
                            // maybe a java bean
                            final Entity<?> entity = Entity.from(parameterType);
                            for (Property property : entity) {
                                final ResultMapping resultMapping = new ResultMapping();
                                resultMapping.setName(property.getName());
                                resultMapping.setType(property.getType());
//                                resultMapping.setGenericType(Optional.ofNullable(property.getField()).map(Field::getGenericType)
//                                        .orElse(property.getRequiredGetter().getGenericReturnType()));


                                parameterMappings.add(resultMapping);

                            }


                        }


                    }
                    requestMapping.setParameterMappings(parameterMappings);

                    final MethodParameter returnType = handlerMethod.getReturnType();
                    final Type genericParameterType = returnType.getGenericParameterType();
                    final Class<?> resultType = returnType.getParameterType();
                    final List<ResultMapping> resultMappings = new ArrayList<>();

                    if (PrimaryTypes.isPrimary(resultType)) {

                    } else if (!Collection.class.isAssignableFrom(resultType)) {
                        // maybe a java bean
                        final Entity<?> entity = Entity.from(resultType);
                        for (Property property : entity) {
                            final ResultMapping resultMapping = new ResultMapping();
                            resultMapping.setName(property.getName());
                            resultMapping.setType(property.getType());
//                            resultMapping.setGenericType(Optional.ofNullable(property.getField()).map(Field::getGenericType)
//                                    .orElse(property.getRequiredGetter().getGenericReturnType()));

                            resultMappings.add(resultMapping);
                        }
                    }

                    requestMapping.setResultMappings(resultMappings);


                    requestMappings.add(requestMapping);
                    requestMappingMap.add(pattern, method, requestMapping);

                }


            }
        }


    }

    @Override
    public List<RequestMapping> query(RequestMappingQuery query) {
        return requestMappings;
    }

    @Override
    public RequestMapping find(String pattern, RequestMethod method) {
        return requestMappingMap.get(pattern, method);
    }
}

