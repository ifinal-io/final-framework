/*
 * Copyright 2020-2022 the original author or authors.
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

package org.ifinalframework.data.sharding.config;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
public class BindingTableRegistry {

    private final Collection<String> bindingTableGroups;

    public BindingTableRegistry(final Collection<String> bindingTableGroups) {

        this.bindingTableGroups = bindingTableGroups;
    }

    public BindingTableRegistry addBindingTable(final String... bindingTable) {

        this.bindingTableGroups.addAll(Arrays.asList(bindingTable));
        return this;
    }

    public BindingTableRegistry addAllBindingTables(final Collection<String> bindingTables) {

        this.bindingTableGroups.addAll(bindingTables);
        return this;
    }

}
