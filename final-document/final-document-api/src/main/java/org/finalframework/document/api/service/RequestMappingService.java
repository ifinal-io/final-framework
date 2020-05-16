package org.finalframework.document.api.service;

import org.finalframework.document.api.entity.RequestMapping;
import org.finalframework.document.api.entity.RequestPattern;
import org.finalframework.document.api.service.query.RequestPatternQuery;
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
