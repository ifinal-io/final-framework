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

package org.finalframework.document.api.entity;


import lombok.Data;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import reactor.util.annotation.Nullable;

import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2020-05-14 09:21:13
 * @see org.springframework.web.servlet.mvc.method.RequestMappingInfo
 * @since 1.0
 */
@Data
public class RequestMapping implements Comparable<RequestMapping> {
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
    public int compareTo(RequestMapping o) {
        return pattern.compareTo(o.pattern);
    }
}

