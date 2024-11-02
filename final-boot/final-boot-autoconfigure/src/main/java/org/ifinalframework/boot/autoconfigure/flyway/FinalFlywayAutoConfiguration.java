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

package org.ifinalframework.boot.autoconfigure.flyway;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import org.ifinalframework.boot.flyway.CleanFlywaySchemaHistoryCallback;

import org.flywaydb.core.Flyway;

/**
 * FinalFlywayAutoConfiguration.
 *
 * @author iimik
 * @version 1.5.0
 * @since 1.5.0
 */
@EnableConfigurationProperties(FinalFlywayProperties.class)
@AutoConfiguration(after = {DataSourceAutoConfiguration.class, JdbcTemplateAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class})
@ConditionalOnClass(Flyway.class)
@ConditionalOnProperty(prefix = "spring.flyway", name = "enabled", matchIfMissing = true)
public class FinalFlywayAutoConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "spring.flyway", name = "clean-schema-history-on-after-migrate-operation-finish")
    public CleanFlywaySchemaHistoryCallback cleanFlywaySchemaHistoryCallback(FinalFlywayProperties properties) {
        final CleanFlywaySchemaHistoryCallback callback = new CleanFlywaySchemaHistoryCallback();
        callback.setCleanSql(properties.getCleanSchemaHistorySql());
        return callback;
    }
}
