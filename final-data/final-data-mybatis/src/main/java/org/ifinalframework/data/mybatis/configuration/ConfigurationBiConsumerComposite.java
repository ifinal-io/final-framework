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

package org.ifinalframework.data.mybatis.configuration;

import org.ifinalframework.core.IEntity;

import org.apache.ibatis.session.Configuration;

import java.util.LinkedList;
import java.util.List;

/**
 * ConfigurationBiConsumerComposite.
 *
 * @author iimik
 * @version 1.4.0
 * @since 1.4.0
 */
public class ConfigurationBiConsumerComposite implements ConfigurationBiConsumer {

    private final List<ConfigurationBiConsumer> consumers = new LinkedList<>();

    public ConfigurationBiConsumerComposite() {
        //        consumers.add(new ResultMapConfigurationBiConsumer());
        //        consumers.add(new MapperConfigurationBiConsumer());
    }

    @Override
    public void accept(Configuration configuration, Class<? extends IEntity<?>> clazz) {
        consumers.forEach(it -> it.accept(configuration, clazz));
    }
}


