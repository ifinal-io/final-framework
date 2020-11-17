package org.finalframework.dashboard.web.service;

import org.finalframework.dashboard.web.entity.RequestHandler;
import org.finalframework.dashboard.web.entity.RequestPattern;
import org.finalframework.dashboard.web.service.query.RequestPatternQuery;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2020-05-14 09:25:40
 * @since 1.0
 */
public interface RequestMappingService {
    List<RequestPattern> query(RequestPatternQuery query);

    RequestHandler find(String pattern, RequestMethod method);
}
