/*
 * Copyright 2020-2021 the original author or authors.
 *
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

package org.ifinalframework.context.resource;

import java.util.Collection;

/**
 * ResourceValueManager.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface ResourceValueManager {

    Collection<ResourceValueHolder> getResourceValueHolders(String key);

    void addResourceValueHolder(String key, ResourceValueHolder holder);

    void setValue(String key, String value);

}
