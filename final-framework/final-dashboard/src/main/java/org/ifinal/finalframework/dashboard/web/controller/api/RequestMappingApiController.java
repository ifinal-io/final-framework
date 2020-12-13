package org.ifinal.finalframework.dashboard.web.controller.api;

import java.util.List;
import javax.annotation.Resource;
import org.ifinal.finalframework.dashboard.web.entity.RequestHandler;
import org.ifinal.finalframework.dashboard.web.entity.RequestPattern;
import org.ifinal.finalframework.dashboard.web.service.RequestMappingService;
import org.ifinal.finalframework.dashboard.web.service.query.RequestPatternQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@RestController
@org.springframework.web.bind.annotation.RequestMapping("/api/request")
public class RequestMappingApiController {

    public static final Logger logger = LoggerFactory.getLogger(RequestMappingApiController.class);

    @Resource
    private RequestMappingService requestMappingService;

    @GetMapping(name = "find RequestMapping of query", path = "/patterns")
    public List<RequestPattern> patterns(final RequestPatternQuery query) {

        return requestMappingService.query(query);
    }

    @GetMapping(name = "find RequestMapping of pattern and method", path = "/handler")
    public RequestHandler mapping(final String pattern) {

        return requestMappingService.find(pattern);
    }

}

