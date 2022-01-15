package org.ifinalframework.web.resolver;

import org.ifinalframework.web.annotation.bind.RequestExcelPart;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.multipart.support.MultipartResolutionDelegate;

import javax.servlet.http.HttpServletRequest;

/**
 * @author likly
 * @version 1.2.4
 * @see org.springframework.web.reactive.result.method.HandlerMethodArgumentResolver
 * @see org.springframework.web.method.support.HandlerMethodArgumentResolver
 * @see org.springframework.web.servlet.mvc.method.annotation.RequestPartMethodArgumentResolver
 **/
public class RequestExcelPartMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(RequestExcelPart.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest request, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest servletRequest = request.getNativeRequest(HttpServletRequest.class);
        Assert.state(servletRequest != null, "No HttpServletRequest");

        RequestExcelPart requestPart = parameter.getParameterAnnotation(RequestExcelPart.class);
        boolean isRequired = ((requestPart == null || requestPart.required()) && !parameter.isOptional());

        String name = getPartName(parameter, requestPart);
        parameter = parameter.nestedIfOptional();
        Object arg = null;

        Object mpArg = MultipartResolutionDelegate.resolveMultipartArgument(name, parameter, servletRequest);
        if (mpArg != MultipartResolutionDelegate.UNRESOLVABLE) {
            arg = mpArg;
        }

        if (arg == null && isRequired) {
            if (!MultipartResolutionDelegate.isMultipartRequest(servletRequest)) {
                throw new MultipartException("Current request is not a multipart request");
            }
            else {
                throw new MissingServletRequestPartException(name);
            }
        }

        if(arg instanceof MultipartFile){

        }

        return null;
    }


    private String getPartName(MethodParameter methodParam, @Nullable RequestExcelPart requestPart) {
        String partName = (requestPart != null ? requestPart.name() : "");
        if (partName.isEmpty()) {
            partName = methodParam.getParameterName();
            if (partName == null) {
                throw new IllegalArgumentException("Request part name for argument type [" +
                        methodParam.getNestedParameterType().getName() +
                        "] not specified, and parameter name information not found in class file either.");
            }
        }
        return partName;
    }
}
