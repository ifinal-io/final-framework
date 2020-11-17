package org.finalframework.dashboard.core.service;

import org.finalframework.dashboard.core.entity.RequestMapping;
import org.finalframework.dashboard.core.entity.RequestPattern;
import org.finalframework.dashboard.core.service.query.RequestPatternQuery;
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

    RequestMapping find(String pattern, RequestMethod method);
}
