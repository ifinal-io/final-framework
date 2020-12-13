package org.ifinal.finalframework.dashboard.web.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.ifinal.finalframework.dashboard.web.entity.RequestHandler;
import org.ifinal.finalframework.dashboard.web.entity.RequestPattern;
import org.ifinal.finalframework.dashboard.web.service.RequestMappingService;
import org.ifinal.finalframework.dashboard.web.service.query.RequestPatternQuery;
import org.ifinal.finalframework.util.Asserts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
@SuppressWarnings("unused")
class RequestMappingServiceImpl implements RequestMappingService, InitializingBean {

    public static final Logger logger = LoggerFactory.getLogger(RequestMappingServiceImpl.class);

    /**
     * the key is {@link RequestMappingInfo#getPatternsCondition}
     */

    private final Map<String, RequestHandler> requestHandlers = new LinkedHashMap<>(256);

    private final List<RequestPattern> requestPatterns = new ArrayList<>();

    private final RequestMappingHandlerMapping requestMappingHandlerMapping;

    RequestMappingServiceImpl(final RequestMappingHandlerMapping requestMappingHandlerMapping) {

        this.requestMappingHandlerMapping = requestMappingHandlerMapping;
    }

    @Override
    public List<RequestPattern> query(final RequestPatternQuery query) {

        return requestPatterns.stream().filter(requestPattern -> !Asserts.nonEmpty(query.getPattern())
            || requestPattern.getPattern().toUpperCase().contains(query.getPattern().toUpperCase())).collect(Collectors.toList());
    }

    @Override
    public RequestHandler find(final String pattern) {

        return requestHandlers.get(pattern);
    }

    @Override
    public void afterPropertiesSet() {
        this.requestMappingHandlerMapping.getHandlerMethods().keySet().forEach(it -> logger.info(it.toString()));

        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : this.requestMappingHandlerMapping.getHandlerMethods().entrySet()) {
            final RequestMappingInfo requestMappingInfo = entry.getKey();
            final HandlerMethod handlerMethod = entry.getValue();

            if (!handlerMethod.hasMethodAnnotation(ResponseBody.class) && !AnnotatedElementUtils
                .isAnnotated(handlerMethod.getMethod().getDeclaringClass(), ResponseBody.class)) {
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

            }

        }

        Collections.sort(requestPatterns);
    }

}

