package org.finalframework.dashboard.web.entity;


import lombok.Data;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import reactor.util.annotation.Nullable;

import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2020-05-14 09:21:13
 * @see RequestMappingInfo
 * @since 1.0
 */
@Data
public class RequestHandler implements Comparable<RequestHandler> {
    /**
     * @see RequestMappingInfo#getName()
     */
    @Nullable
    private String name;
    private RequestMethod method;
    private String pattern;

    private List<ResultMapping> parameterMappings;
    private List<ResultMapping> resultMappings;

    @Override
    public int compareTo(RequestHandler o) {
        return pattern.compareTo(o.pattern);
    }
}

