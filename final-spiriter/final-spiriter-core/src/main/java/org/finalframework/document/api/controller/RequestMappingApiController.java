/*
 * Copyright (c) 2018-2020.  the original author or authors.
 *  <p>
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p>
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  <p>
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.finalframework.document.api.controller;

import org.finalframework.document.api.entity.RequestMapping;
import org.finalframework.document.api.entity.RequestPattern;
import org.finalframework.document.api.service.RequestMappingService;
import org.finalframework.document.api.service.query.RequestPatternQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author likly
 * @version 1.0
 * @date 2020-05-14 09:29:20
 * @since 1.0
 */
@RestController
@org.springframework.web.bind.annotation.RequestMapping("/api/request")
public class RequestMappingApiController {
    public static final Logger logger = LoggerFactory.getLogger(RequestMappingApiController.class);

    @Resource
    private RequestMappingService requestMappingService;

    @GetMapping(name = "find RequestMapping of query", path = "/patterns")
    public List<RequestPattern> patterns(RequestPatternQuery query) {
        return requestMappingService.query(query);
    }

    @GetMapping(name = "find RequestMapping of pattern and method", path = "/mapping")
    public RequestMapping mapping(String pattern, RequestMethod method) {
        return requestMappingService.find(pattern, method);
    }


}

