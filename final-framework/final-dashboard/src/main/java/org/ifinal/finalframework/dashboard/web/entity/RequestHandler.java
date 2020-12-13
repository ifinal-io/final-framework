package org.ifinal.finalframework.dashboard.web.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

/**
 * @author likly
 * @version 1.0.0
 * @see RequestMappingInfo
 * @since 1.0.0
 */
@Data
public class RequestHandler implements Comparable<RequestHandler>, Serializable {

    private static final long serialVersionUID = -7247307363089708279L;

    /**
     * @see RequestMappingInfo#getName()
     */
    private String name;

    private Set<RequestMethod> methods;

    private String pattern;

    private List<ResultMapping> parameterMappings;

    private List<ResultMapping> resultMappings;

    @Override
    public int compareTo(final RequestHandler o) {

        return pattern.compareTo(o.pattern);
    }

}

