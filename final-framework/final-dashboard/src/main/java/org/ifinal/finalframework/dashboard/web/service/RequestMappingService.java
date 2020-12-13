package org.ifinal.finalframework.dashboard.web.service;

import java.util.List;
import org.ifinal.finalframework.dashboard.web.entity.RequestHandler;
import org.ifinal.finalframework.dashboard.web.entity.RequestPattern;
import org.ifinal.finalframework.dashboard.web.service.query.RequestPatternQuery;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface RequestMappingService {

    List<RequestPattern> query(RequestPatternQuery query);

    RequestHandler find(String pattern);

}
