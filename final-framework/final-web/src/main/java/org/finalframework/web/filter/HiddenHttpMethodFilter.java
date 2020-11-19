package org.finalframework.web.filter;


import org.finalframework.util.Asserts;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * @author likly
 * @version 1.0
 * @date 2019-12-28 14:24:28
 * @since 1.0
 */
@Component
public class HiddenHttpMethodFilter extends OncePerRequestFilter {

    /**
     * Default method parameter: {@code _method}.
     */
    public static final String DEFAULT_METHOD_HEADER = "_method";
    private static final List<String> ALLOWED_METHODS =
            Collections.unmodifiableList(Arrays.asList(HttpMethod.PUT.name(),
                    HttpMethod.DELETE.name(), HttpMethod.PATCH.name()));
    private String methodHeader = DEFAULT_METHOD_HEADER;


    /**
     * Set the header name to look for HTTP methods.
     *
     * @see #DEFAULT_METHOD_HEADER
     */
    public void setMethodHeader(String methodHeader) {
        Asserts.isEmpty(methodHeader, "'methodHeader' must not be empty");
        this.methodHeader = methodHeader;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        HttpServletRequest requestToUse = request;

        if ("POST".equals(request.getMethod()) && request.getAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE) == null) {
            String headerValue = request.getHeader(this.methodHeader);
            if (StringUtils.hasLength(headerValue)) {
                String method = headerValue.toUpperCase(Locale.ENGLISH);
                if (ALLOWED_METHODS.contains(method)) {
                    requestToUse = new HttpMethodRequestWrapper(request, method);
                }
            }
        }

        filterChain.doFilter(requestToUse, response);
    }


    /**
     * Simple {@link HttpServletRequest} wrapper that returns the supplied method for
     * {@link HttpServletRequest#getMethod()}.
     */
    private static class HttpMethodRequestWrapper extends HttpServletRequestWrapper {

        private final String method;

        public HttpMethodRequestWrapper(HttpServletRequest request, String method) {
            super(request);
            this.method = method;
        }

        @Override
        public String getMethod() {
            return this.method;
        }
    }

}


