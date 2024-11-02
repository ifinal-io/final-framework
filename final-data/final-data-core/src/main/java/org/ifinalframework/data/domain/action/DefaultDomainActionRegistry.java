/*
 * Copyright 2020-2023 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ifinalframework.data.domain.action;

import org.springframework.stereotype.Component;

import org.ifinalframework.context.exception.NotFoundException;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * DefaultDomainActionRegistry
 *
 * @author iimik
 * @since 1.5.2
 **/
@Component
public class DefaultDomainActionRegistry implements DomainActionRegistry {

    private final Map<String, DomainActions> resourceDomainActionMap = new LinkedHashMap<>(128);

    @Override
    public DomainActions get(String resource) {
        final DomainActions domainActions = resourceDomainActionMap.get(resource);
        if (Objects.isNull(domainActions)) {
            throw new NotFoundException("Not found domainAction for resource of " + resource);
        }
        return domainActions;
    }

    @Override
    public <T extends DomainAction> T get(String resource, String action, String property) {

        final DomainActions domainActions = get(resource);

        final String key = Stream.of(action, property).filter(Objects::nonNull).collect(Collectors.joining("#"));
        DomainAction domainAction = domainActions.getDomainActions().get(key);

        if(Objects.isNull(domainAction)){
            domainAction = domainActions.getDomainActions().get(String.join("#", action, "property"));
        }

        if (Objects.isNull(domainAction)) {
            throw new NotFoundException("Not found domainAction for resource of " + resource + " and type of " + action);
        }

        return (T) domainAction;
    }


    @Override
    public void registry(String resource, DomainActions domainActions) {
        resourceDomainActionMap.put(resource, domainActions);
    }
}
